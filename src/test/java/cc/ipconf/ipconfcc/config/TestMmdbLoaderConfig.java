package cc.ipconf.ipconfcc.config;

import com.maxmind.geoip2.DatabaseReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@Slf4j
@TestConfiguration
public class TestMmdbLoaderConfig {

  @Value("${ipconf.mmdb.city-database.filename}")
  private String cityDatabaseName;

  @Value("${ipconf.mmdb.city-database.path}")
  private String cityDatabasePath;

  @Value("${ipconf.mmdb.asn-database.filename}")
  private String asnDatabaseName;

  @Value("${ipconf.mmdb.asn-database.path}")
  private String asnDatabasePath;

  @Bean
  public DatabaseReader getCityDatabaseReader() {
    File cityDb = new File(Paths.get(cityDatabasePath).toString());
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(cityDb);
    log.info("Full City test database file path: {}", cityDb.getAbsolutePath());

    try {
      log.info("Loading City test database: {}", cityDatabaseName);
      return databaseReaderBuilder.build();
    } catch (IOException e) {
      throw new IllegalArgumentException("City test database file not found");
    }
  }

  @Bean
  public DatabaseReader getAsnDatabaseReader() {
    File asnDb = new File(Paths.get(asnDatabasePath).toString());
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(asnDb);
    log.info("Full ASN test database file path: {}", asnDb.getAbsolutePath());

    try {
      log.info("Loading ASN test database: {}", asnDatabaseName);
      return databaseReaderBuilder.build();
    } catch (IOException e) {
      throw new IllegalArgumentException("ASN test database file not found");
    }
  }

}
