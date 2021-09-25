package cc.ipconf.controller;

import static cc.ipconf.AppPaths.ASN_ENDPOINT;
import cc.ipconf.AppTestBase;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.services.RequestService;
import cc.ipconf.enums.GeolocationDatabaseType;
import javax.servlet.http.HttpServletRequest;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class AsnControllerTest extends AppTestBase {

  private final IpAddressDto ipAddressDto = getTestIpAddressDto(GeolocationDatabaseType.ASN);

  private final String ASN_ORGANIZATION = "AT&T Services";
  private final int ASN_NETWORK_PREFIX_LENGTH = 22;
  private final int ASN_PATH = 7018;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RequestService requestService;

  @BeforeEach
  public void setUp() {
    when(requestService.getClientIpAddress(any(HttpServletRequest.class))).thenReturn(ipAddressDto);
  }

  @Test
  public void testAsnAsJson() throws Exception {
    String organizationPath = "organization";
    String asnPath = "asn";
    String networkAddressPath = "network.network_address";
    String networkPrefixLengthPath = "network.prefix_length";
    String networkRoutePath = "network.route";

    mockMvc.perform(get((ASN_ENDPOINT))
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath(asnPath, is(ASN_PATH)))
        .andExpect(jsonPath(organizationPath, is(ASN_ORGANIZATION)))
        .andExpect(jsonPath(networkAddressPath, is(ASN_IP_ADDRESS)))
        .andExpect(jsonPath(networkPrefixLengthPath, is(ASN_NETWORK_PREFIX_LENGTH)))
        .andExpect(jsonPath(networkRoutePath, is("%s/%s".formatted(ASN_IP_ADDRESS, ASN_NETWORK_PREFIX_LENGTH))));
  }

  @Test
  public void testAsnAsText() throws Exception {
    mockMvc.perform(get(ASN_ENDPOINT)
            .accept(MediaType.TEXT_PLAIN_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().string("%s,%s,%s/%s".formatted(
            ASN_PATH, ASN_ORGANIZATION, ASN_IP_ADDRESS, ASN_NETWORK_PREFIX_LENGTH
        )));
  }

  @Test
  public void testAsnAsDefault() throws Exception {
    mockMvc.perform(get(ASN_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().string("%s,%s,%s/%s".formatted(
            ASN_PATH, ASN_ORGANIZATION, ASN_IP_ADDRESS, ASN_NETWORK_PREFIX_LENGTH
        )));
  }

}
