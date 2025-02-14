package Practice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;

public class TestTokenHW {
  @Test
  public void testTokenHW(){
    String location = "https://playground.learnqa.ru/ajax/api/longtime_job";
    JsonPath firstResponseForToken = RestAssured
            .given()
            .get(location)
            .jsonPath();
    String token = firstResponseForToken.get("token");
    int delay = firstResponseForToken.get("seconds");
    firstResponseForToken.prettyPrint();

    JsonPath secondResponceWhithToken = RestAssured
            .given()
            .queryParam("token",token)
            .get(location)
            .jsonPath();
    secondResponceWhithToken.prettyPrint();
    String status = "Job is NOT ready";
      if (status.equals(secondResponceWhithToken.get("status"))){
        System.out.println("Current status is: \"" + secondResponceWhithToken.get("status") + "\" - Test Passed");
      } else System.out.println("Current status is not OK");

    try {
      TimeUnit.SECONDS.sleep(delay);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    JsonPath thirdResponceWhithToken = RestAssured
            .given()
            .queryParam("token",token)
            .get(location)
            .jsonPath();
    thirdResponceWhithToken.prettyPrint();
    status = "Job is ready";
    if (status.equals(thirdResponceWhithToken.get("status"))) {
      System.out.println("Current status is: \"" + thirdResponceWhithToken.get("status") + "\" - Test Passed");
    } else System.out.println("Current status is not OK");

  }
}
