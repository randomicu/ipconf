package cc.ipconf.services;

import cc.ipconf.dto.AsnDto;
import cc.ipconf.dto.CityDto;
import cc.ipconf.dto.CountryDto;
import cc.ipconf.dto.InfoDto;
import cc.ipconf.dto.IpAddressDto;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InfoServiceImpl implements InfoService {

  private final LocationService locationService;
  private final RequestService requestService;
  private final AsnService asnService;

  @Autowired
  public InfoServiceImpl(LocationService locationService, RequestService requestService, AsnService asnService) {
    this.locationService = locationService;
    this.requestService = requestService;
    this.asnService = asnService;
  }

  @Override
  public InfoDto getInfo(HttpServletRequest request) {
    InfoDto infoDto = new InfoDto();

    IpAddressDto ipAddressDto = requestService.getClientIpAddress(request);
    CountryDto countryDto = locationService.getCountry(ipAddressDto.getIpAddress());
    CityDto cityDto = locationService.getCity(ipAddressDto.getIpAddress());
    AsnDto asnDto = asnService.getAsn(ipAddressDto.getIpAddress());

    infoDto.setIpAddress(ipAddressDto.getIpAddress());
    infoDto.setCountry(countryDto);
    infoDto.setCity(cityDto.getCity());
    infoDto.setAsn(asnDto);

    return infoDto;
  }

}
