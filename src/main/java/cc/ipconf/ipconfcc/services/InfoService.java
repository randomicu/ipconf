package cc.ipconf.ipconfcc.services;

import cc.ipconf.ipconfcc.dto.InfoDto;
import javax.servlet.http.HttpServletRequest;

public interface InfoService {

  InfoDto getInfo(HttpServletRequest request);

}
