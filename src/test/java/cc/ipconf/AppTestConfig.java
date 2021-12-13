package cc.ipconf;

import com.maxmind.geoip2.DatabaseReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@TestConfiguration
public class AppTestConfig {

  @Value("${ipconf.mmdb.city-database.filename}")
  private String cityDatabaseName;

  @Value("${ipconf.mmdb.city-database.path}")
  private String cityDatabasePath;

  @Value("${ipconf.mmdb.asn-database.filename}")
  private String asnDatabaseName;

  @Value("${ipconf.mmdb.asn-database.path}")
  private String asnDatabasePath;

  @Bean
  public DatabaseReader getAsnDatabaseReader() throws IOException {
    File asnDb = new File(Paths.get(asnDatabasePath).toString());
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(asnDb);
    log.info("ASN mini mmdb path: {}", asnDb.getAbsolutePath());
    log.info("Loading ASN mini database: {}", asnDatabaseName);

    return databaseReaderBuilder.build();
  }

  @Bean
  public DatabaseReader getCityDatabaseReader() throws IOException {
    File cityDb = new File(Paths.get(cityDatabasePath).toString());
    DatabaseReader.Builder databaseReaderBuilder = new DatabaseReader.Builder(cityDb);
    log.info("City mini mmdb path: {}", cityDb.getAbsolutePath());
    log.info("Loading City mini database: {}", cityDatabaseName);

    return databaseReaderBuilder.build();
  }

}
