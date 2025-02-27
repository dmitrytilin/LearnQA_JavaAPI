package Archive.Homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class TestRedirectHW {

  @Test
  public void testRedirectHW(){

    Response response = RestAssured
            .given()
            .redirects()
            .follow(false)
            .when()
            .get("https://playground.learnqa.ru/api/long_redirect")
            .andReturn();
    response.prettyPrint();
    System.out.println("Redirect Location is: " + response.getHeader("Location"));
  }
}
