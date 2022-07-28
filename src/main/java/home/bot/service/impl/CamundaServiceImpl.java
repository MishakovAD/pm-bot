package home.bot.service.impl;

import home.bot.camunda.BpmnProcess;
import home.bot.exception.ErrorCode;
import home.bot.exception.CamundaProcessException;
import home.bot.service.CamundaService;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class CamundaServiceImpl implements CamundaService {

  private final RuntimeService runtimeService;

  @Override
  public void start(BpmnProcess process, String businessKey) {
    start(process, businessKey, new HashMap<>());
  }

  @Override
  public void start(BpmnProcess process, String businessKey, Map<String, Object> variables) {
    LOGGER.info("Start bpmn process {} with businessKey: {} and variables: {}",
        process.name(),
        businessKey,
        variables);
    List<ProcessInstance> activeProcesses = getActiveProcesses(businessKey);
    if (activeProcesses.size() > 0) {
      LOGGER.warn("More than one process cannot run at one time. BusinessKey: {}", businessKey);
      throw new CamundaProcessException(ErrorCode.CAMUNDA_START_PROCESS);
    }
    runtimeService.createProcessInstanceByKey(process.getId())
        .businessKey(businessKey)
        .setVariables(variables)
        .execute();
  }

  @Override
  public void createMessageCorrelation(BpmnProcess process, String businessKey, String message) {
    createMessageCorrelation(process, businessKey, message, new HashMap<>());
  }

  @Override
  public void createMessageCorrelationWithoutThrow(BpmnProcess process,
      String businessKey,
      String message,
      Map<String, Object> vars) {
    try {
      createMessageCorrelation(process, businessKey, message, vars);
    } catch (Exception e) {
      LOGGER.warn("Failed resume camunda process! Message: {}", e.getMessage());
    }
  }

  @Override
  public void createMessageCorrelation(BpmnProcess process,
      String businessKey,
      String message,
      Map<String, Object> vars) {
    LOGGER.info("Start create message correlation for process: {} with message: {} and vars: {}",
        process.name(),
        message,
        vars);
    String id = getActiveInstanceIdByBusinessKeyWithThrow(process, businessKey);
    Optional.ofNullable(getEventSubscription(id, message)).ifPresent(subscription -> {
      runtimeService.messageEventReceived(subscription.getEventName(), subscription.getExecutionId(), vars);
    });
  }

  @Override
  public String getActiveInstanceIdByBusinessKeyWithThrow(BpmnProcess process, String businessKey) {
    LOGGER.info("Get instance id by businessKey: {} for process: {}", businessKey, process.name());
    List<ProcessInstance> ids = getActiveInstanceIdByBusinessKey(process, businessKey);
    if (ids.size() != 1) {
      throw new RuntimeException("More than one or zero process has been launched for this businessKey!");
    }
    return ids.get(0).getId();
  }

  @Override
  public List<ProcessInstance> getActiveInstanceIdByBusinessKey(BpmnProcess process, String businessKey){
    var processInstanceQuery = runtimeService
        .createProcessInstanceQuery()
        .processInstanceBusinessKey(businessKey)
        .processDefinitionKey(process.getId());
    List<ProcessInstance> result = processInstanceQuery.unlimitedList();
    LOGGER.info("Active instance ids: {}, size: {},  by businessKey: {}", result, result.size(), businessKey);
    return result;
  }

  @Override
  public Map<ProcessInstance, ActivityInstance> getActiveInstanceByBusinessKey(String businessKey) {
    LOGGER.info("Start get Activity by businessKey: {}", businessKey);
    return runtimeService.createProcessInstanceQuery()
        .active()
        .processInstanceBusinessKey(businessKey)
        .unlimitedList()
        .stream()
        .map(processInstance -> {
          ActivityInstance activityInstance = Optional.of(processInstance.getProcessInstanceId())
              .map(runtimeService::getActivityInstance)
              .map(ActivityInstance::getChildActivityInstances)
              .map(Arrays::asList)
              .stream()
              .flatMap(Collection::stream)
              .findAny().get(); //TODO: возможно ли, что нет активного инстанса? - Да, если нет верного обработчика
          return Map.entry(processInstance, activityInstance);
        })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Override
  public void setVariables(BpmnProcess process, String businessKey, Map<String, Object> variables) {
    LOGGER.debug("Start set variables for process: {} with businessKey: {}", process.name(), businessKey);
    String id = getActiveInstanceIdByBusinessKeyWithThrow(process, businessKey);
    variables.forEach((varName, varValue) -> {
      setVariable(id, varName, varValue);
    });
    LOGGER.info("End set variables :{} for bpm process :{} with key :{}", variables, process, businessKey);
  }

  @Override
  public void setVariables(DelegateExecution execution, Map<String, Object> variables) {
    execution.setVariables(variables);
    LOGGER.info("End set variables for process: {} with businessKey: {}",
        execution.getCurrentActivityName(),
        execution.getBusinessKey());
  }

  @Override
  public void setVariablesLocal(DelegateExecution execution, Map<String, Object> variables) {
    execution.setVariablesLocal(variables);
    LOGGER.info("End set variables local for process: {} with businessKey: {}",
        execution.getCurrentActivityName(),
        execution.getBusinessKey());
  }

  @Override
  public Object getVariable(String varName, DelegateExecution execution) {
    return Optional.ofNullable(execution.getVariable(varName))
        .orElseThrow(() -> new RuntimeException(String.format("The variable %s must be filled!", varName)));
  }

  @Override
  public Object getVariableWithoutThrow(String varName, DelegateExecution execution) {
    try {
      return Optional.ofNullable(execution.getVariable(varName))
          .orElseThrow(() -> new RuntimeException(String.format("The variable %s is not filled!", varName)));
    } catch (Exception e) {
      LOGGER.info("This variable :{} has not been set. ActivityId: {}", varName, execution.getCurrentActivityId());
      return null;
    }
  }

  private EventSubscription getEventSubscription(String executionId, String eventName) {
    return runtimeService.createEventSubscriptionQuery()
        .processInstanceId(executionId)
        .eventName(eventName)
        .singleResult();
  }

  private void setVariable(String executionId, String varName, Object varValue) {
    LOGGER.debug("Start setVariable for executionId: {}. varName: {}, varValue: {}",
        executionId,
        varName,
        varValue);
    runtimeService.setVariable(executionId, varName, varValue);
  }

  private List<ProcessInstance> getActiveProcesses(String businessKey) {
    return runtimeService.createProcessInstanceQuery()
        .active()
        .processInstanceBusinessKey(businessKey)
        .unlimitedList();
  }
}
