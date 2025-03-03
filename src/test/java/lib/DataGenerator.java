package lib;

import io.restassured.path.json.JsonPath;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DataGenerator {
  public static String getRandomEmail() {
    String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
    return timestamp + "lernqa@example.com";
  }

  // Функция чтения JSON файла, возвращает Stream<Map<String, String>> для передачи в тест
public static Stream<Map<String, String>> dataProviderMethod() {

    JsonPath jsonPath = JsonPath.from(new File("src/test/java/lib/testData/testDataUserRegistration.json"));
    List<Map<String, Object>> userTestData = jsonPath.getList("$");

    return userTestData.stream().map(map -> {
      Map<String, String> result = new java.util.HashMap<>();
      result.put("email", (String) map.get("email"));
      result.put("password", (String) map.get("password"));
      result.put("username", (String) map.get("username"));
      result.put("firstName", (String) map.get("firstName"));
      result.put("lastName", (String) map.get("lastName"));
      result.put("expectedErrorMessage", (String) map.get("expectedErrorMessage"));
      return result;
    });
  }

}
