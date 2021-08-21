package cc.ipconf.ipconfcc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDto {

  String country;

  @JsonProperty("iso_code")
  String isoCode;

  @JsonProperty("is_eu")
  Boolean isInEuropeanUnion;

}
