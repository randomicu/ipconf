package cc.ipconf.services;

import cc.ipconf.dto.InfoDto;

import javax.servlet.http.HttpServletRequest;

public interface InfoService {

  InfoDto getInfo(HttpServletRequest request);

}
