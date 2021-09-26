package cc.ipconf.controller;

import static cc.ipconf.AppPaths.LOCATION_CITY_ENDPOINT;
import static cc.ipconf.AppPaths.LOCATION_COUNTRY_ENDPOINT;
import cc.ipconf.AppTestBase;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.enums.GeolocationDatabaseType;
import cc.ipconf.services.RequestService;
import javax.servlet.http.HttpServletRequest;
import org.hamcrest.Matchers;
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
public class LocationControllerTest extends AppTestBase {

  private final IpAddressDto ipAddressDto = getTestIpAddressDto(GeolocationDatabaseType.LOCATION);
  private final String EXPECTED_CITY = "London";
  private final String EXPECTED_COUNTRY = "United Kingdom";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RequestService requestService;

  @BeforeEach
  public void setUp() {
    when(requestService.getClientIpAddress(any(HttpServletRequest.class))).thenReturn(ipAddressDto);
  }

  @Test
  public void testCityAsText() throws Exception {
    mockMvc.perform(get(LOCATION_CITY_ENDPOINT)
            .accept(MediaType.TEXT_PLAIN_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().string(EXPECTED_CITY));
  }

  @Test
  public void testCityAsJson() throws Exception {
    String cityPath = "city";

    mockMvc.perform(get(LOCATION_CITY_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath(cityPath, Matchers.is(EXPECTED_CITY)));
  }

  @Test
  public void testCountryAsText() throws Exception {
    mockMvc.perform(get(LOCATION_COUNTRY_ENDPOINT)
            .accept(MediaType.TEXT_PLAIN_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().string(EXPECTED_COUNTRY));
  }

  @Test
  public void testCountryAsJson() throws Exception {
    String countryPath = "country";
    String isoCodePath = "iso_code";
    String isEuPath = "is_eu";
    String expectedCountryIsoCode = "GB";

    mockMvc.perform(get(LOCATION_COUNTRY_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath(countryPath, Matchers.is(EXPECTED_COUNTRY)))
        .andExpect(jsonPath(isoCodePath, Matchers.is(expectedCountryIsoCode)))
        .andExpect(jsonPath(isEuPath, Matchers.is(true)));
  }

  @Test
  public void testCityAsDefault() throws Exception {
    mockMvc.perform(get(LOCATION_CITY_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().string(EXPECTED_CITY));
  }

  @Test
  public void testCountryAsDefault() throws Exception {
    mockMvc.perform(get(LOCATION_COUNTRY_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(content().string(EXPECTED_COUNTRY));
  }

}
