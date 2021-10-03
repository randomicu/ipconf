package cc.ipconf;

public class AppPaths {

  public static String API_PATH = "/api";
  public static String API_VERSION = "v1";
  public static String ASN_ENDPOINT = "%s/%s/asn".formatted(API_PATH, API_VERSION);
  public static String IP_ENDPOINT = "%s/%s/ip".formatted(API_PATH, API_VERSION);
  public static String INFO_ENDPOINT = "%s/%s/info".formatted(API_PATH, API_VERSION);
  public static String LOCATION_CITY_ENDPOINT = "%s/%s/location/city".formatted(API_PATH, API_VERSION);
  public static String LOCATION_COUNTRY_ENDPOINT = "%s/%s/location/country".formatted(API_PATH, API_VERSION);

}
