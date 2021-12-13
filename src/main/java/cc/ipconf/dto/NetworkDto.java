package cc.ipconf.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.maxmind.db.Network;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"networkAddress", "prefixLength", "fullNetworkAddress"})
public class NetworkDto {

  @JsonProperty("prefix_length")
  private int prefixLength;

  @JsonProperty("network_address")
  private InetAddress networkAddress;

  @JsonProperty("route")
  private String fullNetworkAddress;


  public static @NotNull NetworkDto convertNetworkObjToNetworkDto(@NotNull Network networkObj) {
    NetworkDto dto = new NetworkDto();
    dto.setPrefixLength(networkObj.getPrefixLength());
    dto.setNetworkAddress(networkObj.getNetworkAddress());
    dto.setFullNetworkAddress("%s/%d".formatted(networkObj.getNetworkAddress().getHostAddress(), networkObj.getPrefixLength()));

    return dto;
  }

}
