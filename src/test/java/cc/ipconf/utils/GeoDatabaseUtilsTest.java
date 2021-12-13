package cc.ipconf.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoDatabaseUtilsTest {

  @Test
  void databasePathGeneratorTest() {
    String databasePathPattern = "city-%%ACTUAL_DATE%%.mmdb";
    LocalDate date = LocalDate.now();
    var month = date.getMonthValue();
    var year = date.getYear();

    String databasePath = GeoDatabaseUtils.generateDatabasePath(databasePathPattern, "mmdb/");

    assertEquals("mmdb/city-%s-%s.mmdb".formatted(year, month), databasePath);
  }

}
