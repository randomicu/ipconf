package cc.ipconf.ipconfcc.services;

import cc.ipconf.ipconfcc.dto.AsnDto;

public interface AsnService {

  AsnDto getAsn(String ipAddress);

}
