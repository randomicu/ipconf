package cc.ipconf.ipconfcc.services;

import cc.ipconf.ipconfcc.dto.CityDto;
import cc.ipconf.ipconfcc.dto.CountryDto;

public interface LocationService {
  CountryDto getCountry(String ipAddress);

  CityDto getCity(String ipAddress);

}
