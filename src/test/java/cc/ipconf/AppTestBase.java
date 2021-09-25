package cc.ipconf;

import cc.ipconf.controllers.AsnController;
import cc.ipconf.controllers.InfoController;
import cc.ipconf.controllers.IpController;
import cc.ipconf.controllers.LocationController;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.enums.GeolocationDatabaseType;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {AppTestConfig.class})
public abstract class AppTestBase {

  public final String ASN_IP_ADDRESS = "12.81.92.0";
  public final String LOCATION_IP_ADDRESS = "81.2.69.142";

  @Autowired
  protected AsnController asnController;

  @Autowired
  protected InfoController infoController;

  @Autowired
  protected IpController ipController;

  @Autowired
  protected LocationController locationController;

  protected IpAddressDto getTestIpAddressDto(@NotNull GeolocationDatabaseType databaseType) {
    IpAddressDto dto = new IpAddressDto();

    switch (databaseType) {
      case ASN -> dto.setIpAddress(ASN_IP_ADDRESS);
      case LOCATION -> dto.setIpAddress(LOCATION_IP_ADDRESS);
      default -> throw new IllegalStateException("Unexpected value: " + databaseType);
    }

    return dto;
  }

}
