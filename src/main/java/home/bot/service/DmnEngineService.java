package home.bot.service;

import java.util.Map;

public interface DmnEngineService {
  Object getSingleResult(String fileName, String decisionKey, Map<String, Object> vars);

  Map<String, Object> getEntryMap(String fileName, String decisionKey, Map<String, Object> vars);
}
