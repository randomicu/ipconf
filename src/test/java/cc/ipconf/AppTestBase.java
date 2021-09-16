package cc.ipconf;

import cc.ipconf.controllers.AsnController;
import cc.ipconf.controllers.InfoController;
import cc.ipconf.controllers.IpController;
import cc.ipconf.controllers.LocationController;
import cc.ipconf.dto.IpAddressDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {AppTestConfig.class})
public abstract class AppTestBase {

  public static final String IP_ADDRESS_ASN = "12.81.92.0";
  public static final String IP_ADDRESS_LOCATION = "81.2.69.142";

  @Autowired
  protected AsnController asnController;

  @Autowired
  protected InfoController infoController;

  @Autowired
  protected IpController ipController;

  @Autowired
  protected LocationController locationController;

  protected IpAddressDto getTestIpAddressDto(@NotNull String databaseType) {
    IpAddressDto dto = new IpAddressDto();

    switch (databaseType) {
      case "asn" -> dto.setIpAddress(IP_ADDRESS_ASN);
      case "location" -> dto.setIpAddress(IP_ADDRESS_LOCATION);
      default -> throw new IllegalStateException("Unexpected value: " + databaseType);
    }

    return dto;
  }

}
