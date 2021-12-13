package cc.ipconf.config;

import com.maxmind.geoip2.DatabaseReader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static cc.ipconf.utils.GeoDatabaseUtils.generateDatabasePath;

@Slf4j
@Configuration
public class GeoDatabaseLoaderConfig {

  @Value("${ipconf.mmdb.city-database.filename-pattern}")
  private String cityDatabaseNamePattern;

  @Value("${ipconf.mmdb.databases.directory}")
  private String databasesDirectory;

  @Value("${ipconf.mmdb.asn-database.filename-pattern}")
  private String asnDatabaseNamePattern;

  @Getter
  private String cityDatabaseName;

  @Getter
  private String asnDatabaseName;

  @Value("${ipconf.mmdb.databases.directory}${ipconf.mmdb.city-database-fallback-filename}")
  private String cityDatabaseDefaultPath;

  @Value("${ipconf.mmdb.databases.directory}${ipconf.mmdb.asn-database-fallback-filename}")
  private String asnDatabaseDefaultPath;

  @Value("${ipconf.mmdb.databases.production.initialization}")
  private boolean isProduction;

  private final ConcurrentMap<String, DatabaseReader> GEO_DATABASES = new ConcurrentHashMap<>();

  @PostConstruct
  public void initGeoDatabases() throws IOException {

    // workaround for PostConstruct in tests
    if (isProduction) {
      log.info("Starting geolocation databases initialization");

      String cityDatabasePath = generateDatabasePath(cityDatabaseNamePattern, databasesDirectory);
      String asnDatabasePath = generateDatabasePath(asnDatabaseNamePattern, databasesDirectory);

      DatabaseReader cityDatabaseReader = getDatabaseReader(cityDatabasePath);
      DatabaseReader asnDatabaseReader = getDatabaseReader(asnDatabasePath);

      GEO_DATABASES.put("cityDatabaseReader", cityDatabaseReader);
      GEO_DATABASES.put("asnDatabaseReader", asnDatabaseReader);
      log.info("Finishing geolocation databases initialization");
    }
  }

  @Bean
  public DatabaseReader getCityDatabaseReader() {
    return GEO_DATABASES.get("cityDatabaseReader");
  }

  @Bean
  public DatabaseReader getAsnDatabaseReader() {
    return GEO_DATABASES.get("asnDatabaseReader");
  }

  @NotNull
  private DatabaseReader getDatabaseReader(String rawPath) throws IOException {
    File databaseFile = validateDatabasePath(rawPath);
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(databaseFile);

    log.info("Trying to load database: {}", databaseFile.getAbsolutePath());
    DatabaseReader reader = databaseReaderBuilder.build();
    log.info("Database loaded successfully");

    return reader;
  }

  @NotNull
  private File validateDatabasePath(String databasePath) {
    log.info("Validating database path: {}", databasePath);

    File databaseFile = new File(Paths.get(databasePath).toString());

    if (!databaseFile.exists()) {
      log.error("Database file {} was not found", databaseFile);
      if (databaseFile.getName().startsWith("city")) {
        log.info("Loading default city database from: {}", cityDatabaseDefaultPath);

        File cityDatabaseFile = new File(Paths.get(cityDatabaseDefaultPath).toString());
        cityDatabaseName = cityDatabaseFile.getName();

        databaseFile = cityDatabaseFile;

      } else if (databaseFile.getName().startsWith("asn")) {
        log.info("Loading default asn database from: {}", asnDatabaseDefaultPath);

        File asnDatabaseFile = new File(Paths.get(asnDatabaseDefaultPath).toString());
        asnDatabaseName = asnDatabaseFile.getName();

        databaseFile = asnDatabaseFile;
      }
    }

    if (databaseFile.getName().startsWith("city")) {
      cityDatabaseName = databaseFile.getName();

    } else if (databaseFile.getName().startsWith("asn")) {
      asnDatabaseName = databaseFile.getName();
    }

    return databaseFile;
  }

}
