package Practice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class TestParsingJSON {

  @Test
  public void testParsingJSON(){

    Response response = RestAssured
            .given()
            .queryParam("param1", "value1")
            .queryParam("param2", "value2")
            .get("https://playground.learnqa.ru/api/check_type")
            .andReturn();
    response.print();

  }
}
