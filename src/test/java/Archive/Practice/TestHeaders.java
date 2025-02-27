package Archive.Practice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.http.Headers;

import java.util.HashMap;
import java.util.Map;

public class TestHeaders {

  @Test
  public void testParsingJSON(){
    Map<String, Object> headers = new HashMap<>();
    headers.put("header1", "value1");
    headers.put("header2", "value2");
    Response response = RestAssured
            .given()
            .headers(headers)
            .when()
            .post("https://playground.learnqa.ru/ajax/api/longtime_job")
            .andReturn();
    response.prettyPrint();
    Headers responceHeaders = (Headers) response.getHeaders();
    System.out.println(responceHeaders);
  }
}
