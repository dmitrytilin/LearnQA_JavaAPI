package Archive.Practice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestCookie {

  @Test
  public void testToken(){
    Map<String, Object> data = new HashMap<>();
    data.put("login", "secret_login");
    data.put("password", "secret_pass");

    Response response = RestAssured
            .given()
            .body(data)
            .when()
            .post("https://playground.learnqa.ru/ajax/api/get_auth_cookie")
            .andReturn();


  System.out.println(response.getCookie("auth_cookie"));

  }
}
