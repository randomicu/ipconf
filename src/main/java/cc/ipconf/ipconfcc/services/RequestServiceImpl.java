package cc.ipconf.ipconfcc.services;

import cc.ipconf.ipconfcc.dto.IpAddressDto;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@SuppressWarnings("FieldCanBeLocal")
@Slf4j
@Service
public class RequestServiceImpl implements RequestService {

  private final String DEFAULT_IP_ADDRESS = "1.1.1.1";

  @Override
  public IpAddressDto getClientIpAddress(@NotNull HttpServletRequest request) {
    IpAddressDto ipDto = new IpAddressDto();

    String ipAddress = request.getHeader("X-Forwarded-For");
    log.info("Trying to get IP address from request.getHeader(\"X-Forwarded-For\"): {}", ipAddress);

    if (!StringUtils.hasLength(ipAddress)) {
      ipAddress = request.getRemoteAddr();
      log.info("Trying to get IP address from request.getRemoteAddr(): {}", ipAddress);
    }

    if (isRunningInDevEnv()) {
      log.info("Dirty hack for localhost with overriding IP address to: {}", DEFAULT_IP_ADDRESS);
      ipAddress = DEFAULT_IP_ADDRESS;
    }

    ipDto.setIpAddress(ipAddress);
    return ipDto;
  }

  private boolean isRunningInDevEnv() {
    log.info("Check if running on localhost");
    try {
      InetAddress inetAddress = InetAddress.getLocalHost();

      log.info("isAnyLocalAddress: {}", inetAddress.isAnyLocalAddress());
      log.info("isLoopBackAddress: {}", inetAddress.isLoopbackAddress());
      log.info("isSiteLocalAddress: {}", inetAddress.isSiteLocalAddress());

      return inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress() || inetAddress.isSiteLocalAddress();

    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    return true;
  }

}
