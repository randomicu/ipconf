package cc.ipconf.config;

import java.nio.file.Path;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AppConfig {

  @PostConstruct
  public void printCurrentWorkingDirectory() {
    log.info("Current working directory: {}", Path.of("").toAbsolutePath());
  }

}
