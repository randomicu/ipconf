package cc.ipconf.utils.contributor;

import cc.ipconf.config.GeoDatabaseLoaderConfig;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class GeoDatabaseContributor implements InfoContributor {

  private final GeoDatabaseLoaderConfig mmdbLoader;

  public GeoDatabaseContributor(GeoDatabaseLoaderConfig mmdbLoader) {
    this.mmdbLoader = mmdbLoader;
  }

  @Override
  public void contribute(Info.Builder builder) {
    String cityDatabaseName = mmdbLoader.getCityDatabaseName();
    String asnDatabaseName = mmdbLoader.getAsnDatabaseName();

    Map<String, String> geoDatabaseDetails = new HashMap<>();
    geoDatabaseDetails.put("city", cityDatabaseName);
    geoDatabaseDetails.put("asn", asnDatabaseName);

    builder.withDetail("geo_databases", geoDatabaseDetails);
  }
}
