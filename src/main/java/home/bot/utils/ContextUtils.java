package home.bot.utils;

import java.util.Objects;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextUtils implements ApplicationContextAware {

  public static <T> T getBean(Class<T> clazz) {
    if (Objects.nonNull(context)) {
      return context.getBean(clazz);
    }
    throw new IllegalStateException("Context don't initialize!");
  }

  private static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ContextUtils.context = applicationContext;
  }
}
