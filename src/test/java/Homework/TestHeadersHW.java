package Homework;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestHeadersHW {
  private Map<String, String> headers;

  @BeforeEach
  public void initialRequest(){
   //RestAssured.proxy("localhost", 8889);
   //RestAssured.useRelaxedHTTPSValidation();
    Response response = RestAssured
            .get("https://playground.learnqa.ru/api/homework_header")
            .then()
            .extract()
            .response();
    assertEquals(200, response.statusCode(), "Unexpected status code");
    this.headers = response.getHeaders().asList()
            .stream()
            .collect(Collectors.toMap(
                    io.restassured.http.Header::getName,
                    io.restassured.http.Header::getValue
            ));
  }
  @Test
  public void testHeadersHW() {
    //RestAssured.proxy("localhost", 8889);
    //RestAssured.useRelaxedHTTPSValidation();
    Response response = RestAssured
            .get("https://playground.learnqa.ru/api/homework_header")
            .then()
            .extract()
            .response();

    assertEquals(200, response.statusCode(), "Unexpected status code");
    Headers responseHeaders = response.getHeaders();

    assertNotNull(responseHeaders, "Headers is empty");

    // Проверка каждого заголовка
    for (String headerName : this.headers.keySet()) {

      String headerValue = responseHeaders.getValue(headerName);

      assertNotNull(headerValue, "Value of \"" + headerName + "\" Header is null");
      assertFalse(headerValue.isEmpty(), "Header \"" + headerName + "\" IS EMPTY || response: \n" + response.headers());
    }
    // Сравнение всех заголовков
    Map<String, String> responseHeaderMap = responseHeaders.asList().stream()
            .collect(Collectors.toMap(
                    io.restassured.http.Header::getName,
                    io.restassured.http.Header::getValue
            ));
    assertEquals(this.headers, responseHeaderMap, "Headers do not match");
  }
}

