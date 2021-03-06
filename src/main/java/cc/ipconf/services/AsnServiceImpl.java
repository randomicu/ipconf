package cc.ipconf.services;

import cc.ipconf.config.GeoDatabaseLoaderConfig;
import cc.ipconf.dto.AsnDto;
import cc.ipconf.dto.NetworkDto;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Service
public class AsnServiceImpl implements AsnService {

  private final GeoDatabaseLoaderConfig mmdbLoader;

  public AsnServiceImpl(GeoDatabaseLoaderConfig mmdbLoader) {
    this.mmdbLoader = mmdbLoader;
  }


  @Override
  public AsnDto getAsn(String ipAddress) {
    DatabaseReader reader = mmdbLoader.getAsnDatabaseReader();

    InetAddress ip = this.getIpAddress(ipAddress);
    AsnResponse asnResponse = this.getAsnResponse(reader, ip);

    return this.convertAsnResponseToAsnDto(asnResponse);
  }

  private InetAddress getIpAddress(String clientIpAddress) {
    try {
      return InetAddress.getByName(clientIpAddress);
    } catch (UnknownHostException e) {
      throw new IllegalArgumentException("IP address is not valid");
    }
  }

  private AsnResponse getAsnResponse(@NotNull DatabaseReader reader, InetAddress ip) {
    try {
      return reader.asn(ip);
    } catch (IOException | GeoIp2Exception e) {
      log.error("Error when trying to get ASN from database ");

      throw new RuntimeException(e.getMessage());
    }
  }

  private @NotNull AsnDto convertAsnResponseToAsnDto(@NotNull AsnResponse asnResponse) {
    AsnDto asnDto = new AsnDto();
    NetworkDto networkDto = NetworkDto.convertNetworkObjToNetworkDto(asnResponse.getNetwork());

    asnDto.setAutonomousSystemNumber(asnResponse.getAutonomousSystemNumber());
    asnDto.setNetworkDto(networkDto);
    asnDto.setAutonomousSystemOrganization(asnResponse.getAutonomousSystemOrganization());

    return asnDto;
  }

}
