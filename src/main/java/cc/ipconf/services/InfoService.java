package cc.ipconf.services;

import cc.ipconf.dto.InfoDto;

import jakarta.servlet.http.HttpServletRequest;

public interface InfoService {

  InfoDto getInfo(HttpServletRequest request);

}
