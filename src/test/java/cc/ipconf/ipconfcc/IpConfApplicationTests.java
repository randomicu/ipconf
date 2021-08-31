package cc.ipconf.ipconfcc;

import cc.ipconf.ipconfcc.controllers.AsnController;
import cc.ipconf.ipconfcc.controllers.InfoController;
import cc.ipconf.ipconfcc.controllers.IpController;
import cc.ipconf.ipconfcc.controllers.LocationController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IpConfApplicationTests {

	@Autowired
	private AsnController asnController;

	@Autowired
	private InfoController infoController;

	@Autowired
	private IpController ipController;

	@Autowired
	private LocationController locationController;

	@Test
	void contextLoads() {
		assertThat(asnController).isNotNull();
		assertThat(infoController).isNotNull();
		assertThat(ipController).isNotNull();
		assertThat(locationController).isNotNull();
	}

}
