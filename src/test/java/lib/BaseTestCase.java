package lib;


import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTestCase {

  public static class Constants {
    public static final String BASE_URL = "https://playground.learnqa.ru/api";
  }

  protected String getHeader(Response Response, String name){
    Headers headers = Response.getHeaders();

    assertTrue(headers.hasHeaderWithName(name), "Response does' not have header whith name " + name);
    return headers.getValue(name);
  }

  protected String getCookie(Response Response, String name){
    Map<String, String> cookies  = Response.getCookies();

    assertTrue(cookies.containsKey(name), "Responce doesn't have cookie whith name " + name);
    return cookies.get(name);
  }

  protected int getIntFromJson(Response Response, String name){
    Response.then().assertThat().body("$", hasKey(name));
    return Response.jsonPath().getInt(name);
  }

}
