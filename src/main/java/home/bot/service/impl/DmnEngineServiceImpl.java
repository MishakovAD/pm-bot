package home.bot.service.impl;

import home.bot.service.DmnEngineService;
import java.io.InputStream;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "SERVICE")
public class DmnEngineServiceImpl implements DmnEngineService {

  @Override
  public Object getSingleResult(String fileName, String decisionKey, Map<String, Object> vars) {
    try {
      return getResult(fileName, decisionKey, vars).getSingleResult().getSingleEntry();
    } catch (Exception ex) {
      LOGGER.error("Error getSingleResult()!", ex);
      throw new RuntimeException("Error dmn parse!");
    }
  }

  @Override
  public Map<String, Object> getEntryMap(String fileName, String decisionKey, Map<String, Object> vars) {
    try {
      return getResult(fileName, decisionKey, vars).getSingleResult().getEntryMap();
    } catch (Exception ex) {
      LOGGER.error("Error getEntryMap()!", ex);
      throw new RuntimeException("Error dmn parse!");
    }
  }

  private DmnDecisionTableResult getResult(String fileName, String decisionKey, Map<String, Object> vars) {
    DmnEngine dmnEngine = DmnEngineConfiguration
        .createDefaultDmnEngineConfiguration()
        .buildEngine();

    try {
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
      DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(inputStream);
      DmnDecision decisions = dmnEngine.parseDecision(decisionKey, dmnModelInstance);
      return dmnEngine.evaluateDecisionTable(decisions, vars);
    } catch (Exception ex) {
      LOGGER.error("Error getResult()!", ex);
      throw new RuntimeException("Error dmn parse!");
    }
  }
}
