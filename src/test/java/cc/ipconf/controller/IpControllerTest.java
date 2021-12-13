package cc.ipconf.controller;

import cc.ipconf.AppTestBase;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.enums.GeoDatabaseType;
import cc.ipconf.services.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import static cc.ipconf.AppPaths.IP_ENDPOINT;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class IpControllerTest extends AppTestBase {

  private final IpAddressDto ipAddressDto = getTestIpAddressDto(GeoDatabaseType.ASN);

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
        .andExpect(content().string(ASN_IP_ADDRESS));
  }

  @Test
  public void testIpAsJson() throws Exception {
    mockMvc.perform(get(IP_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("ip_address", is(ASN_IP_ADDRESS)));
  }

  @Test
  public void testIpAsDefault() throws Exception {
    mockMvc.perform(get(IP_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().string(ASN_IP_ADDRESS));
  }

}
