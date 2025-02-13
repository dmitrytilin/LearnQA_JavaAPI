package Homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class TestLongRedirectHW {
  @Test
  public void testRedirectHW() {
    String location = "https://playground.learnqa.ru/api/long_redirect";
    int redCount = 0, code = 0;
    while (true) {

      Response response = RestAssured
              .given()
              .redirects()
              .follow(false)
              .when()
              .get(location)
              .andReturn();

      System.out.println(redCount + " Location is: " + location);
      location = response.getHeader("Location");
      code = response.getStatusCode();

      if (code == 200) {
        System.out.println("Total URL's to redirect count: " + redCount);
        redCount++;
        System.out.println("Total URL's count: " + redCount);
        break;
      }
      redCount++;
    }
  }
}