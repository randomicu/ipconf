package cc.ipconf.utils;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class GeoDatabaseUtils {

  public static String generateDatabasePath(String databasePathPattern, String directoryPath) {
    LocalDate currentDate = LocalDate.now();
    int currentMonth = currentDate.getMonthValue();
    int currentYear = currentDate.getYear();

    String generatedFileName = StringUtils.replace(
        databasePathPattern, "%%ACTUAL_DATE%%", "%d-%d".formatted(currentYear, currentMonth)
    );
    String databasePath = "%s%s".formatted(directoryPath, generatedFileName);

    log.info("Generated database path is: {}", databasePath);

    return databasePath;
  }

}
