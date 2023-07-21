package cc.ipconf.services;

import cc.ipconf.dto.IpAddressDto;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestService {

  IpAddressDto getClientIpAddress(HttpServletRequest request);

}
