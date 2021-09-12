package cc.ipconf.services;

import cc.ipconf.dto.AsnDto;

public interface AsnService {

  AsnDto getAsn(String ipAddress);

}
