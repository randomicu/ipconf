package cc.ipconf.config;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class MMDBLoaderConfigTest {

  MMDBLoaderConfig mmdbLoaderConfig;
  String databasePath;

  @BeforeEach
  void setUp() {
    mmdbLoaderConfig = new MMDBLoaderConfig();
    ReflectionTestUtils.setField(mmdbLoaderConfig, "databasesDirectoryPath", "mmdb/");
  }

  @Test
  void testDatabasePathGenerator() {
    String databasePathPattern = "city-%%ACTUAL_DATE%%.mmdb";
    LocalDate date = LocalDate.now();
    var month = date.getMonthValue();
    var year = date.getYear();

    databasePath = ReflectionTestUtils.invokeMethod(mmdbLoaderConfig, "generateDatabasePath", databasePathPattern);

    assertEquals("mmdb/city-%s-%s.mmdb".formatted(year, month), databasePath);
  }

}
