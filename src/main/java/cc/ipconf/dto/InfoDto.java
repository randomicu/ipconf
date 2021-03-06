package cc.ipconf.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"ipAddress", "city", "country", "asn"})
public class InfoDto {

  @JsonProperty("ip_address")
  String ipAddress;

  CountryDto country;

  String city;

  AsnDto asn;

}
