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

  @Value("${ipaddr.mmdb.city-filename}")
  private String cityDatabaseName;

  @Value("${ipaddr.mmdb.city-database-path}")
  private String cityDatabasePath;

  @Bean
  public DatabaseReader getDatabaseReader() {
    log.info("Current working directory: {}", Path.of("").toAbsolutePath());

    File cityDb = new File(Paths.get(cityDatabasePath).toString());
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(cityDb);
    log.info("Full database file path: {}", cityDb.getAbsolutePath());

    try {
      log.info("Loading database: {}", cityDatabaseName);
      return databaseReaderBuilder.build();
    } catch (IOException e) {
      Sentry.captureException(e);
      throw new IllegalArgumentException("Database file not found");
    }
  }

}
