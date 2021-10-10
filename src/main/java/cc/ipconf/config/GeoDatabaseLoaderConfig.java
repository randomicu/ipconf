package cc.ipconf.config;

import com.maxmind.geoip2.DatabaseReader;
import io.sentry.Sentry;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
public class GeoDatabaseLoaderConfig {

  @Value("${ipconf.mmdb.city-database.filename-pattern}")
  private String cityDatabaseNamePattern;

  @Value("${ipconf.mmdb.databases.directory}")
  private String databasesDirectoryPath;

  @Value("${ipconf.mmdb.asn-database.filename-pattern}")
  private String asnDatabaseNamePattern;

  @Bean
  public DatabaseReader getCityDatabaseReader() {
    String cityDatabasePath = generateDatabasePath(cityDatabaseNamePattern);
    File database = new File(Paths.get(cityDatabasePath).toString());
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
    String asnDatabasePath = generateDatabasePath(asnDatabaseNamePattern);
    File database = new File(Paths.get(asnDatabasePath).toString());
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(database);

    try {
      log.info("Loading ASN database: {}", database.getAbsolutePath());
      return databaseReaderBuilder.build();
    } catch (IOException e) {
      Sentry.captureException(e);
      throw new IllegalArgumentException("ASN database file not found");
    }
  }

  private String generateDatabasePath(String databasePathPattern) {
    LocalDate currentDate = LocalDate.now();
    int currentMonth = currentDate.getMonthValue();
    int currentYear = currentDate.getYear();

    String generatedFileName = StringUtils.replace(
        databasePathPattern, "%%ACTUAL_DATE%%", "%d-%d".formatted(currentYear, currentMonth)
    );
    String databasePath = "%s%s".formatted(databasesDirectoryPath, generatedFileName);

    log.info("Generated database path is: {}", databasePath);

    return databasePath;
  }

}
