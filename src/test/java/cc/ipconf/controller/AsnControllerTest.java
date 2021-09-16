package cc.ipconf.controller;

import static cc.ipconf.AppPaths.ASN_ENDPOINT;
import cc.ipconf.AppTestBase;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.services.RequestService;
import javax.servlet.http.HttpServletRequest;
import static org.hamcrest.Matchers.is;
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

  private final IpAddressDto ipAddressDto = getTestIpAddressDto("asn");

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RequestService requestService;

  @Test
  public void TestAsnJson() throws Exception {
    String organizationPath = "organization";
    String asnPath = "asn";
    String networkAddressPath = "network.network_address";
    String networkPrefixLengthPath = "network.prefix_length";
    String networkRoutePath = "network.route";

    when(requestService.getClientIpAddress(any(HttpServletRequest.class))).thenReturn(ipAddressDto);

    mockMvc.perform(get((ASN_ENDPOINT))
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath(asnPath, is(Integer.parseInt("7018"))))
        .andExpect(jsonPath(organizationPath, is("AT&T Services")))
        .andExpect(jsonPath(networkAddressPath, is("12.81.92.0")))
        .andExpect(jsonPath(networkPrefixLengthPath, is(Integer.parseInt("22"))))
        .andExpect(jsonPath(networkRoutePath, is("12.81.92.0/22")));
  }

  @Test
  public void TestAsnAsText() throws Exception {
    when(requestService.getClientIpAddress(any(HttpServletRequest.class))).thenReturn(ipAddressDto);

    mockMvc.perform(get(ASN_ENDPOINT)
            .accept(MediaType.TEXT_PLAIN_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().string("7018,AT&T Services,12.81.92.0/22"));
  }

  @Test
  public void TestAsnAsDefault() throws Exception {
    when(requestService.getClientIpAddress(any(HttpServletRequest.class))).thenReturn(ipAddressDto);

    mockMvc.perform(get(ASN_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().string("7018,AT&T Services,12.81.92.0/22"));
  }

}
