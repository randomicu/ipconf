package cc.ipconf.controllers;

import cc.ipconf.api.Paths;
import cc.ipconf.dto.AsnDto;
import cc.ipconf.dto.IpAddressDto;
import cc.ipconf.services.AsnService;
import cc.ipconf.services.RequestService;
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
public class AsnController {

  private final AsnService asnService;
  private final RequestService requestService;

  @Autowired
  public AsnController(AsnService asnService, RequestService requestService) {
    this.asnService = asnService;
    this.requestService = requestService;
  }

  @GetMapping(value = Paths.ASN_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AsnDto> showASNJson(HttpServletRequest request) {
    log.info("Replying with application/json");

    IpAddressDto ipAddress = requestService.getClientIpAddress(request);
    log.info("Client IP address from requestService: {}", ipAddress.getIpAddress());

    AsnDto asn = asnService.getAsn(ipAddress.getIpAddress());
    log.info("Client ASN (AsnDto) from asnService: {}", asn);

    return new ResponseEntity<>(asn, HttpStatus.OK);
  }

  @GetMapping(value = Paths.ASN_ENDPOINT, consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> showASNText(HttpServletRequest request) {
    log.info("Replying with plain/text");

    IpAddressDto ipAddress = requestService.getClientIpAddress(request);
    log.info("Client IP address from requestService: {}", ipAddress.getIpAddress());

    AsnDto asnDto = asnService.getAsn(ipAddress.getIpAddress());
    log.info("Client ASN (AsnDto) from asnService: {}", asnDto);

    int asNumber = asnDto.getAutonomousSystemNumber();
    String asnOrganization = asnDto.getAutonomousSystemOrganization();
    String asnNetworkAddress = asnDto.getNetworkDto().getNetworkAddress().getHostAddress();
    int asnPrefixLength = asnDto.getNetworkDto().getPrefixLength();

    String response = "%d,%s,%s/%d".formatted(asNumber, asnOrganization, asnNetworkAddress, asnPrefixLength);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
