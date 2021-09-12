package cc.ipconf.services;

import cc.ipconf.dto.CityDto;
import cc.ipconf.dto.CountryDto;

public interface LocationService {
  CountryDto getCountry(String ipAddress);

  CityDto getCity(String ipAddress);

}
