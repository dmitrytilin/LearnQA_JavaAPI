package Homework;

import io.restassured.RestAssured;
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

    assertTrue(response.headers().hasHeaderWithName("Set-Cookie"),
            "No \"Set-Cookie\" Header in responce: \n" + response.headers());
    assertNotNull(response.header("Set-Cookie"), "Value of \"Set-Cookie\" Header is null");

    Map<String, String> cookies = response.getCookies();

    assertFalse(cookies.isEmpty(), "No Cookies in responce " + cookies);
    assertTrue(cookies.containsKey("HomeWork"),
            "No \"HomeWork\" Cookie in responce \n" + response.cookies());

    String homeworkCookieValue = cookies.get("HomeWork");

    assertNotNull(homeworkCookieValue, "Value of \"HomeWork\" cookie is null");
    assertFalse(homeworkCookieValue.isEmpty(),
            "Value of \"HomeWork\" cookie is empty");
  }
}
