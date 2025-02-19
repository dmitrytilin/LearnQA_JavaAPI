package Homework;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestCookieHW {
  @Test
  public void testCookieHW() {
    Response response = RestAssured
            .get("https://playground.learnqa.ru/api/homework_cookie")
            .then()
            .extract()
            .response();

    assertEquals(200, response.statusCode(), "Unexpected status code");
    Headers headers = response.getHeaders();
    assertNotNull(headers,"Headers is empty");
    headers.forEach(header -> {
              assertNotNull(header.getValue(),
                      "Value of \"" + header.getName() + "\" Header is null \n" + header.getName() + " = " + header.getValue());
              assertFalse(Boolean.parseBoolean(header.getValue()),
                      "Header \"" + header.getName() + "\" IS EMPTY || responce: \n" + response.headers());
              System.out.println(header.getName() + " = " + header.getValue());

                }
            );

    Map<String, String> cookies = response.getCookies();
    assertFalse(cookies.isEmpty(), "No Cookies in responce " + cookies);

    for (Map.Entry<String, String> entry : cookies.entrySet()) {
      assertNotNull(entry.getValue(),
              "Value of \"" + entry.getKey() + "\" Cookie is null \n" + entry.getKey() + " = " + entry.getValue());
      assertFalse(entry.getValue().isEmpty(),
              "Value of \"" + entry.getKey() + "\" Cookie is empty \n" + entry.getKey() + " = " + entry.getValue());
      System.out.println(entry.getKey() + " = " + entry.getValue());
    }
    //System.out.println(response.cookies());
  }
}
