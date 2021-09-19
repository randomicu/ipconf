package cc.ipconf.controller;

import static cc.ipconf.AppPaths.IP_ENDPOINT;
import cc.ipconf.AppTestBase;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.services.RequestService;
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
public class IpControllerTest extends AppTestBase {

  private final IpAddressDto ipAddressDto = getTestIpAddressDto("asn");

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RequestService requestService;

  @BeforeEach
  public void setUp() {
    when(requestService.getClientIpAddress(any(HttpServletRequest.class))).thenReturn(ipAddressDto);
  }

  @Test
  public void testIpAsText() throws Exception {
    mockMvc.perform(get(IP_ENDPOINT)
            .accept(MediaType.TEXT_PLAIN_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().string(IP_ADDRESS_ASN));
  }

  @Test
  public void testIpAsJson() throws Exception {
    mockMvc.perform(get(IP_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("ip_address", is(IP_ADDRESS_ASN)));
  }

  @Test
  public void testIpAsDefault() throws Exception {
    mockMvc.perform(get(IP_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().string(IP_ADDRESS_ASN));
  }

}
