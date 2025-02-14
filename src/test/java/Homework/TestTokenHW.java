package Homework;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

public class TestTokenHW {

  @Test
  public void testTokenHW() {
    String location = "https://playground.learnqa.ru/ajax/api/longtime_job";
    String token = null;
    int delay = 0;

    for (int i = 0; i < 3; i++) {
      Map<String, String> params = new HashMap<>();
      if (token != null) {
        params.put("token", token);
      }

      JsonPath response = makeGetRequest(location, params);

      if (i == 0) {
        token = response.get("token");
        delay = response.get("seconds");
        System.out.println("Token received: " + token);
        System.out.println("Delay: " + delay + " seconds");
      } else if (i == 1) {
        processStatus(response.get("status"), "Job is NOT ready");
        waitForDelay(delay);
      } else if (i == 2) {
        processStatus(response.get("status"), "Job is ready");
        if (response.get("result") != null){
          System.out.println("Job result is: " + response.get("result") + " - Test Passed");
        } else System.out.println("Job result is: " + response.get("result") + "Expected \"result\" is not \"null\" - Test Failed");
      }
    }
  }

  // Универсальная функция для выполнения GET-запроса
  private JsonPath makeGetRequest(String url, Map<String, String> queryParams) {
    return RestAssured
            .given()
            .queryParams(queryParams) // Добавляем параметры, если они есть
            .log().uri()
            .when()
            .get(url)
            .jsonPath();
  }

  // Метод для обработки статуса
  private void processStatus(String actualStatus, String expectedStatus) {
    if (expectedStatus.equals(actualStatus)) {
      System.out.println("Current status is: \"" + actualStatus + "\" - Test Passed");
    } else {
      System.out.println("Current status is not OK. Expected: \"" + expectedStatus + "\", but got: \"" + actualStatus + "\"");
    }
  }

  // Метод для ожидания завершения задачи
  private void waitForDelay(int delayInSeconds) {
    try {
      TimeUnit.SECONDS.sleep(delayInSeconds);
    } catch (InterruptedException e) {
      throw new RuntimeException("Delay was interrupted", e);
    }
  }
}