package cc.ipconf.config;

import static cc.ipconf.utils.GeoDatabaseUtils.generateDatabasePath;
import com.maxmind.geoip2.DatabaseReader;
import io.sentry.Sentry;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GeoDatabaseLoaderConfig {

  @Value("${ipconf.mmdb.city-database.filename-pattern}")
  private String cityDatabaseNamePattern;

  @Value("${ipconf.mmdb.databases.directory}")
  private String databasesDirectory;

  @Value("${ipconf.mmdb.asn-database.filename-pattern}")
  private String asnDatabaseNamePattern;

  private String cityDatabaseName;
  private String asnDatabaseName;

  @Bean
  public DatabaseReader getCityDatabaseReader() {
    String cityDatabasePath = generateDatabasePath(cityDatabaseNamePattern, databasesDirectory);
    File database = new File(Paths.get(cityDatabasePath).toString());
    cityDatabaseName = database.getName();
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(database);

    try {
      log.info("Loading City database: {}", database.getAbsolutePath());
      return databaseReaderBuilder.build();
    } catch (IOException e) {
      Sentry.captureException(e);
      throw new IllegalArgumentException("City database file not found");
    }
  }

  @Bean
  public DatabaseReader getAsnDatabaseReader() {
    String asnDatabasePath = generateDatabasePath(asnDatabaseNamePattern, databasesDirectory);
    File database = new File(Paths.get(asnDatabasePath).toString());
    asnDatabaseName = database.getName();
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(database);

    try {
      log.info("Loading ASN database: {}", database.getAbsolutePath());
      return databaseReaderBuilder.build();
    } catch (IOException e) {
      Sentry.captureException(e);
      throw new IllegalArgumentException("ASN database file not found");
    }
  }

  public String getCityDatabaseName() {
    return cityDatabaseName;
  }

  public String getAsnDatabaseName() {
    return asnDatabaseName;
  }

}
