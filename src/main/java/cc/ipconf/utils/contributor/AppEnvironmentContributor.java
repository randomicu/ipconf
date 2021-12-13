package cc.ipconf.utils.contributor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppEnvironmentContributor implements InfoContributor {

  @Value("${ipconf-web-env:not_set}")
  private String env;


  @Override
  public void contribute(Info.Builder builder) {
    Map<String, String> envDetails = new HashMap<>();
    envDetails.put("IPCONF_WEB_ENV", env);

    builder.withDetail("environment", envDetails);
  }
}
