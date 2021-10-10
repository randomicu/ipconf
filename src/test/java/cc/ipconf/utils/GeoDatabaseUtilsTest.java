package cc.ipconf.utils;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class GeoDatabaseUtilsTest {

  String databasePath;

  @Test
  void testDatabasePathGenerator() {
    String databasePathPattern = "city-%%ACTUAL_DATE%%.mmdb";
    LocalDate date = LocalDate.now();
    var month = date.getMonthValue();
    var year = date.getYear();

    databasePath = GeoDatabaseUtils.generateDatabasePath(databasePathPattern,"mmdb/");

    assertEquals("mmdb/city-%s-%s.mmdb".formatted(year, month), databasePath);
  }

}
