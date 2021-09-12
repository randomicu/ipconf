package cc.ipconf.controllers;

import cc.ipconf.api.Paths;
import cc.ipconf.dto.CityDto;
import cc.ipconf.dto.CountryDto;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.services.RequestService;
import cc.ipconf.services.LocationService;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(Paths.API_ROOT_PATH + Paths.API_VERSION)
public class LocationController {

  private final LocationService locationService;
  private final RequestService requestService;

  @Autowired
  public LocationController(LocationService locationService, RequestService requestService) {
    this.locationService = locationService;
    this.requestService = requestService;
  }

  @GetMapping(Paths.COUNTRY_ENDPOINT)
  public ResponseEntity<CountryDto> showCountryJson(HttpServletRequest request) {
    log.info("Replying with application/json");

    IpAddressDto ipAddress = requestService.getClientIpAddress(request);
    log.info("Client IP address from requestService: {}", ipAddress.getIpAddress());

    CountryDto country = locationService.getCountry(ipAddress.getIpAddress());
    log.info("Client country from mmdb database: {}", country.getCountry());

    return new ResponseEntity<>(country, HttpStatus.OK);
  }

  @GetMapping(value = Paths.COUNTRY_ENDPOINT, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> showCountryText(HttpServletRequest request) {
    log.info("Replying with plain/text");

    IpAddressDto ipAddress = requestService.getClientIpAddress(request);
    log.info("Client IP address from requestService: {}", ipAddress.getIpAddress());

    CountryDto country = locationService.getCountry(ipAddress.getIpAddress());
    log.info("Client country from mmdb database: {}", country.getCountry());

    return new ResponseEntity<>(country.getCountry(), HttpStatus.OK);
  }

  @GetMapping(value = Paths.CITY_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CityDto> showCityJson(HttpServletRequest request) {
    log.info("Replying with application/json");

    IpAddressDto ipAddress = requestService.getClientIpAddress(request);
    log.info("Client IP address from requestService: {}", ipAddress.getIpAddress());

    CityDto city = locationService.getCity(ipAddress.getIpAddress());
    log.info("Client city from mmdb database: {}", city.getCity());

    return new ResponseEntity<>(city, HttpStatus.OK);
  }

  @GetMapping(value = Paths.CITY_ENDPOINT, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> showCityText(HttpServletRequest request) {
    log.info("Replying with plain/text");

    IpAddressDto ipAddress = requestService.getClientIpAddress(request);
    log.info("Client IP address from requestService: {}", ipAddress.getIpAddress());

    CityDto cityDto = locationService.getCity(ipAddress.getIpAddress());
    log.info("Client city from mmdb database: {}", cityDto.getCity());

    return new ResponseEntity<>(cityDto.getCity(), HttpStatus.OK);
  }

}
