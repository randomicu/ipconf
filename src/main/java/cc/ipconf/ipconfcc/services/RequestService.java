package cc.ipconf.ipconfcc.services;

import cc.ipconf.ipconfcc.dto.IpAddressDto;
import javax.servlet.http.HttpServletRequest;

public interface RequestService {

  IpAddressDto getClientIpAddress(HttpServletRequest request);

}
