package cc.ipconf.config;

import java.nio.file.Path;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
public class AppConfig {

  @Value("${ipconf-web-env:not_set}")
  private String env;

  @PostConstruct
  public void printAppEnvironment() {
    if (StringUtils.hasLength(env) && !env.equals("not_set")) {
      log.info("IPCONF_WEB_ENV variable: {}", env);
    } else {
      log.info("IPCONF_WEB_ENV VARIABLE IS NOT SET");
    }

    log.info("Current working directory: {}", Path.of("").toAbsolutePath());
  }

}
