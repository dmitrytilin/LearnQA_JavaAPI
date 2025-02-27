package Archive.Practice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitFirstTest {
  @ParameterizedTest
  @ValueSource(strings = {"","Jon","Peter"})

  public void testFor200(String name){
    Map <String, String> queryParams = new HashMap<>();
    if (name.length() > 0){
      queryParams.put("name", name);
    }

    JsonPath response = RestAssured
            .given()
            .queryParams(queryParams)
            .get("https://playground.learnqa.ru/api/hello")
            .jsonPath();
    String expectedName = (name.length() > 0) ? name : "someone";
    assertEquals("Hello, " + expectedName , response.getString("answer"), "The answer is not expected");

  }


}
