package Archive.Homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestCookiesHW {
  String Url = "https://playground.learnqa.ru/api/homework_cookie";
  private Map<String, String> expectedCookies;

  @BeforeEach
  public void initialRequest() {
    // Первый запрос для получения эталонных кук
    Response response = makeGetRequest(Url);

    assertEquals(200, response.statusCode(), "Unexpected status code");

    // Сохраняем куки из первого запроса
    this.expectedCookies = response.getCookies();
  }

  @Test
  public void testCookiesHW() {
    // Второй запрос для проверки кук
    Response response = makeGetRequest(Url);




    assertEquals(200, response.statusCode(), "Unexpected status code");

    // Получаем куки из второго запроса
    Map<String, String> actualCookies = response.getCookies();

    /*actualCookies.forEach((name, Value) -> {
              System.out.println(name + " = " + Value);
              }
            );*/
    // Проверка наличия кук
    assertNotNull(actualCookies, "Cookies should not be null");
    assertFalse(actualCookies.isEmpty(), "No cookies in response");

    // Проверка каждой куки
    expectedCookies.forEach((name, expectedValue) -> {
      assertTrue(actualCookies.containsKey(name),
              "Cookie '" + name + "' is missing");
      String actualValue = actualCookies.get(name);
      assertNotNull(actualValue,
              "Value of cookie '" + name + "' is null");
      assertFalse(actualValue.isEmpty(),
              "Value of cookie '" + name + "' is empty");
      assertEquals(expectedValue, actualValue,
              "Value of cookie '" + name + "' doesn't match");
    });

    // Сравнение всех кук
    assertEquals(expectedCookies, actualCookies,
            "Cookies do not match the expected values");

    // Вывод значений кук для наглядности
    actualCookies.forEach((name, value) ->
            System.out.println("Cookie: " + name + " = " + value)
    );
  }
  private Response makeGetRequest(String url){
    return RestAssured
            .get(url)
            .then()
            .extract()
            .response();
  }
}