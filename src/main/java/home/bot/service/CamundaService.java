package home.bot.service;

import home.bot.camunda.BpmnProcess;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public interface CamundaService {

  void start(BpmnProcess process, String businessKey);

  void start(BpmnProcess process, String businessKey, Map<String, Object> variables);

  void createMessageCorrelation(BpmnProcess process, String businessKey, String message);

  void createMessageCorrelationWithoutThrow(BpmnProcess process, String businessKey, String message, Map<String, Object> vars);

  void createMessageCorrelation(BpmnProcess process, String businessKey, String message, Map<String, Object> vars);

  String getActiveInstanceIdByBusinessKeyWithThrow(BpmnProcess process, String businessKey);

  List<ProcessInstance> getActiveInstanceIdByBusinessKey(BpmnProcess process, String businessKey);

  Map<ProcessInstance, ActivityInstance> getActiveInstanceByBusinessKey(String businessKey);

  void setVariables(BpmnProcess process, String businessKey, Map<String, Object> variables);

  void setVariables(DelegateExecution execution, Map<String, Object> variables);

  void setVariablesLocal(DelegateExecution execution, Map<String, Object> variables);

  Object getVariable(String varName, DelegateExecution execution);

  Object getVariableWithoutThrow(String varName, DelegateExecution execution);

}
