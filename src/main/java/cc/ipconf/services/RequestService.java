package cc.ipconf.services;

import cc.ipconf.dto.IpAddressDto;
import javax.servlet.http.HttpServletRequest;

public interface RequestService {

  IpAddressDto getClientIpAddress(HttpServletRequest request);

}
