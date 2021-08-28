package cc.ipconf.ipconfcc.api;

public class Paths {

  public static final String API_ROOT_PATH = "/api";
  public static final String API_VERSION = "/v1";

  public static final String IP_ENDPOINT = "/ip";
  public static final String INFO_ENDPOINT = "/info";
  public static final String COUNTRY_ENDPOINT = "/location/country";
  public static final String CITY_ENDPOINT = "/location/city";
  public static final String ASN_ENDPOINT = "/asn";


  private Paths() {
    throw new IllegalStateException("Path Constants class should not be used this way");
  }

}
