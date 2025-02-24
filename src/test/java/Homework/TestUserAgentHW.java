package Homework;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUserAgentHW {
  String url = "https://playground.learnqa.ru/ajax/api/user_agent_check";

  //Функция ситывания JSON файла, возвращает Stream<Object[]> для передачи в тест
  static Stream<Object[]> dataProviderMethod() {
    JsonPath jsonPath = JsonPath.from(new File("src/test/java/Homework/User_Agent_data.json"));
    List<Map<String, Object>> userAgents = jsonPath.getList("$");

    return userAgents.stream().map(map -> new Object[]{
            map.get("userAgent"),
            map.get("platform"),
            map.get("browser"),
            map.get("device")
    });
  }

  // Универсальная функция для выполнения GET-запроса без куки
  private JsonPath makeGetRequest(String url, String userAgentValue) {
    return RestAssured
            .given()
            .header("User-Agent", userAgentValue)
            .when()
            .get(url)
            .then()
            .extract()
            .jsonPath();
  }

  @ParameterizedTest
  @MethodSource("dataProviderMethod")
  public void testUserAgentHW(String userAgent, String exPlatform, String exBrowser, String exDevice){

    JsonPath testResponse = makeGetRequest(url, userAgent);
    assertEquals(exPlatform, testResponse.getString("platform"), "Unexpected \"platform\" value");
    assertEquals(exBrowser, testResponse.getString("browser"),"Unexpected \"browser\" value");
    assertEquals(exDevice, testResponse.getString("device"),"Unexpected \"device\" value");

  }
}
