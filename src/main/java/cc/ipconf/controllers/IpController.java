package cc.ipconf.controllers;

import cc.ipconf.api.Paths;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.services.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(Paths.API_ROOT_PATH + Paths.API_VERSION)
public class IpController {

  private final RequestService requestService;

  @Autowired
  public IpController(RequestService requestService) {
    this.requestService = requestService;
  }

  @CrossOrigin(methods = {RequestMethod.GET})
  @GetMapping(value = Paths.IP_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<IpAddressDto> getClientIpAddressJson(HttpServletRequest request) {
    log.info("Replying with application/json");

    IpAddressDto ipAddress = requestService.getClientIpAddress(request);
    log.info("Client IP address from requestService: {}", ipAddress.getIpAddress());
    return new ResponseEntity<>(ipAddress, HttpStatus.OK);
  }

  @GetMapping(value = Paths.IP_ENDPOINT, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> getClientIpAddressText(HttpServletRequest request) {
    log.info("Replying with text/plain");

    IpAddressDto ipAddress = requestService.getClientIpAddress(request);
    log.info("Client IP address from requestService: {}", ipAddress.getIpAddress());
    return new ResponseEntity<>(ipAddress.getIpAddress(), HttpStatus.OK);
  }

}
