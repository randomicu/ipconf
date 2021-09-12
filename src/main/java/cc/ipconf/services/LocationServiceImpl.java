package cc.ipconf.services;

import cc.ipconf.config.MMDBLoaderConfig;
import cc.ipconf.dto.CityDto;
import cc.ipconf.dto.CountryDto;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocationServiceImpl implements LocationService {

  private final MMDBLoaderConfig mmdbLoader;

  @Autowired
  public LocationServiceImpl(MMDBLoaderConfig mmdbLoader) {
    this.mmdbLoader = mmdbLoader;
  }

  @Override
  public CountryDto getCountry(String clientIpAddress) {
    CountryDto countryDto = new CountryDto();
    DatabaseReader reader = mmdbLoader.getCityDatabaseReader();

    InetAddress ip = this.getIpAddress(clientIpAddress);
    CountryResponse response = this.getCountryResponse(reader, ip);

    countryDto.setCountry(response.getCountry().getName());
    countryDto.setIsoCode(response.getCountry().getIsoCode());
    countryDto.setIsInEuropeanUnion(response.getCountry().isInEuropeanUnion());

    return countryDto;
  }

  @Override
  public CityDto getCity(String clientIpAddress) {
    CityDto cityDto = new CityDto();
    DatabaseReader reader = mmdbLoader.getCityDatabaseReader();

    InetAddress ip = this.getIpAddress(clientIpAddress);
    CityResponse response = this.getCityResponse(reader,ip);

    cityDto.setCity(response.getCity().getName());

    return cityDto;
  }

  private InetAddress getIpAddress(String clientIpAddress) {
    try {
      return InetAddress.getByName(clientIpAddress);
    } catch (UnknownHostException e) {
      throw new IllegalArgumentException("IP address is not valid");
    }
  }

  private CountryResponse getCountryResponse(@NotNull DatabaseReader reader, InetAddress ip) {
    try {
      return reader.country(ip);
    } catch (IOException | GeoIp2Exception e) {
      log.error("Error when trying to get country from database ");
      throw new RuntimeException(e.getMessage());
    }
  }

  private CityResponse getCityResponse(@NotNull DatabaseReader reader, InetAddress ip) {
    try {
      return reader.city(ip);
    } catch (IOException | GeoIp2Exception e) {
      log.error("Error when trying to get city from database ");
      throw new RuntimeException(e.getMessage());
    }
  }

}
