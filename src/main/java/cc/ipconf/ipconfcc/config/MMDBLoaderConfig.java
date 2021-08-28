package cc.ipconf.ipconfcc.config;

import com.maxmind.geoip2.DatabaseReader;
import io.sentry.Sentry;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MMDBLoaderConfig {

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
    log.info("Full City database file path: {}", cityDb.getAbsolutePath());

    try {
      log.info("Loading City database: {}", cityDatabaseName);
      return databaseReaderBuilder.build();
    } catch (IOException e) {
      Sentry.captureException(e);
      throw new IllegalArgumentException("City database file not found");
    }
  }

  @Bean
  public DatabaseReader getAsnDatabaseReader() {
    File asnDb = new File(Paths.get(asnDatabasePath).toString());
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(asnDb);
    log.info("Full ASN database file path: {}", asnDb.getAbsolutePath());

    try {
      log.info("Loading ASN database: {}", asnDatabaseName);
      return databaseReaderBuilder.build();
    } catch (IOException e) {
      Sentry.captureException(e);
      throw new IllegalArgumentException("ASN database file not found");
    }
  }

}
