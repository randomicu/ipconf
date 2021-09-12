package cc.ipconf.controllers;

import cc.ipconf.api.Paths;
import cc.ipconf.dto.InfoDto;
import cc.ipconf.services.InfoService;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(Paths.API_ROOT_PATH + Paths.API_VERSION)
public class InfoController {

  private final InfoService infoService;

  @Autowired
  public InfoController(InfoService infoService) {
    this.infoService = infoService;
  }

  @CrossOrigin(methods = {RequestMethod.GET})
  @GetMapping(value = Paths.INFO_ENDPOINT)
  public ResponseEntity<InfoDto> getInfoJson(HttpServletRequest request) {
    InfoDto infoDto = infoService.getInfo(request);

    return new ResponseEntity<>(infoDto, new HttpHeaders(), HttpStatus.OK);
  }

}
