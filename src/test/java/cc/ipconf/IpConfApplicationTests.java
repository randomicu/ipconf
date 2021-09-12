package cc.ipconf;

import cc.ipconf.controllers.AsnController;
import cc.ipconf.controllers.InfoController;
import cc.ipconf.controllers.IpController;
import cc.ipconf.controllers.LocationController;
import com.maxmind.geoip2.DatabaseReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class IpConfApplicationTests {

  @Autowired
  private AsnController asnController;

  @Autowired
  private InfoController infoController;

  @Autowired
  private IpController ipController;

  @Autowired
  private LocationController locationController;

  @Test
  void contextLoads() {
    assertThat(asnController).isNotNull();
    assertThat(infoController).isNotNull();
    assertThat(ipController).isNotNull();
    assertThat(locationController).isNotNull();
  }

  @Slf4j
  @TestConfiguration
  public static class TestMMDBLoaderConfig {

    @Value("${ipconf.mmdb.city-database.filename}")
    private String cityDatabaseName;

    @Value("${ipconf.mmdb.city-database.path}")
    private String cityDatabasePath;

    @Value("${ipconf.mmdb.asn-database.filename}")
    private String asnDatabaseName;

    @Value("${ipconf.mmdb.asn-database.path}")
    private String asnDatabasePath;

    @Bean
    public DatabaseReader getAsnDatabaseReader() {
      File asnDb = new File(Paths.get(asnDatabasePath).toString());
      DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(asnDb);
      log.info("Full ASN mini database file path: {}", asnDb.getAbsolutePath());

      try {
        log.info("Loading ASN mini database: {}", asnDatabaseName);
        return databaseReaderBuilder.build();
      } catch (IOException e) {
        throw new IllegalArgumentException("ASN mini database file not found");
      }
    }

    @Bean
    public DatabaseReader getCityDatabaseReader() {
      File cityDb = new File(Paths.get(cityDatabasePath).toString());
      DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(cityDb);
      log.info("Full City mini database file path: {}", cityDb.getAbsolutePath());

      try {
        log.info("Loading City mini database: {}", cityDatabaseName);
        return databaseReaderBuilder.build();
      } catch (IOException e) {
        throw new IllegalArgumentException("City mini database file not found");
      }
    }
  }

}
