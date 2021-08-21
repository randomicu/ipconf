package cc.ipconf.ipconfcc.services;

import cc.ipconf.ipconfcc.dto.CityDto;
import cc.ipconf.ipconfcc.dto.CountryDto;
import cc.ipconf.ipconfcc.dto.InfoDto;
import cc.ipconf.ipconfcc.dto.IpAddressDto;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InfoServiceImpl implements InfoService {

  private final LocationService locationService;
  private final RequestService requestService;

  @Autowired
  public InfoServiceImpl(LocationService locationService, RequestService requestService) {
    this.locationService = locationService;
    this.requestService = requestService;
  }

  @Override
  public InfoDto getInfo(HttpServletRequest request) {
    InfoDto infoDto = new InfoDto();

    IpAddressDto ipAddressDto = requestService.getClientIpAddress(request);
    CountryDto countryDto = locationService.getCountry(ipAddressDto.getIpAddress());
    CityDto cityDto = locationService.getCity(ipAddressDto.getIpAddress());

    infoDto.setIpAddress(ipAddressDto.getIpAddress());
    infoDto.setCountry(countryDto);
    infoDto.setCity(cityDto.getCity());

    return infoDto;
  }

}
