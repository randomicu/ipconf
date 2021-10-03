package cc.ipconf.controller;

import static cc.ipconf.AppPaths.INFO_ENDPOINT;
import cc.ipconf.controllers.InfoController;
import cc.ipconf.dto.AsnDto;
import cc.ipconf.dto.CityDto;
import cc.ipconf.dto.CountryDto;
import cc.ipconf.dto.InfoDto;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.dto.NetworkDto;
import cc.ipconf.services.InfoService;
import com.maxmind.db.Network;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InfoController.class)
@AutoConfigureMockMvc
class InfoControllerTest {

  private static final String IP_ADDRESS = "2.2.2.2";
  private static final String CITY = "Mocked City";
  private static final String COUNTRY = "Mocked Country";
  private static final String ISO_CODE = "MC";
  private static final boolean IS_EU = true;
  private static final int ASN = 10203;
  private static final String ASN_ORG = "Mocked ASN Organization";
  private static final String NETWORK_ADDRESS = "2.2.0.0";
  private static final int PREFIX_LENGTH = 18;
  private static final String NETWORK_ROUTE = "%s/%d".formatted(IP_ADDRESS, PREFIX_LENGTH);
  private final InfoDto mockedInfoDto = getMockedInfoDto();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InfoService infoService;

  @BeforeEach
  public void setUp() {
    when(infoService.getInfo(any(HttpServletRequest.class))).thenReturn(mockedInfoDto);
  }

  @Test
  void testInfoAsJson() throws Exception {
    mockMvc.perform(get(INFO_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("ip_address", Matchers.is(IP_ADDRESS)))
        .andExpect(jsonPath("city", Matchers.is(CITY)))
        .andExpect(jsonPath("country.country", Matchers.is(COUNTRY)))
        .andExpect(jsonPath("country.iso_code", Matchers.is(ISO_CODE)))
        .andExpect(jsonPath("country.is_eu", Matchers.is(IS_EU)))
        .andExpect(jsonPath("asn.asn", Matchers.is(ASN)))
        .andExpect(jsonPath("asn.organization", Matchers.is(ASN_ORG)))
        .andExpect(jsonPath("asn.network.network_address", Matchers.is(NETWORK_ADDRESS)))
        .andExpect(jsonPath("asn.network.prefix_length", Matchers.is(PREFIX_LENGTH)))
        .andExpect(jsonPath("asn.network.route", Matchers.is(NETWORK_ROUTE)));
  }

  private @NotNull InfoDto getMockedInfoDto() {
    InfoDto infoDto = new InfoDto();
    IpAddressDto ipAddressDto = new IpAddressDto();
    CountryDto countryDto = new CountryDto();
    CityDto cityDto = new CityDto();
    AsnDto asnDto = new AsnDto();
    NetworkDto networkDto = new NetworkDto();
    Network network = getNetwork();

    ipAddressDto.setIpAddress(IP_ADDRESS);

    countryDto.setCountry(COUNTRY);
    countryDto.setIsoCode(ISO_CODE);
    countryDto.setIsInEuropeanUnion(IS_EU);

    cityDto.setCity(CITY);

    networkDto.setNetworkAddress(network.getNetworkAddress());
    networkDto.setPrefixLength(PREFIX_LENGTH);
    networkDto.setFullNetworkAddress(NETWORK_ROUTE);

    asnDto.setNetworkDto(networkDto);
    asnDto.setAutonomousSystemNumber(ASN);
    asnDto.setAutonomousSystemOrganization(ASN_ORG);

    infoDto.setIpAddress(ipAddressDto.getIpAddress());
    infoDto.setCountry(countryDto);
    infoDto.setCity(cityDto.getCity());
    infoDto.setAsn(asnDto);

    return infoDto;
  }

  private Network getNetwork() {
    try {
      return new Network(InetAddress.getByName(IP_ADDRESS), PREFIX_LENGTH);
    } catch (UnknownHostException e) {
      throw new IllegalArgumentException("IP address is not valid");
    }
  }

}
