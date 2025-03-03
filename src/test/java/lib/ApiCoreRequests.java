package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;


public class ApiCoreRequests {
  @Step("Выполнение GET запроса с токеном и куки")
  public Response makeGetRequest(String url, String token, String cookie) {
    return  given()
            .filter(new AllureRestAssured())
            .header(new Header("x-csrf-token", token))
            .cookie("auth_sid", cookie)
            .get(url)
            .andReturn();
  }

  @Step("Выполнение GET запроса только с куки")
  public Response makeGetRequestWithCookie(String url, String cookie) {
    return  given()
            .filter(new AllureRestAssured())
            .cookie("auth_sid", cookie)
            .get(url)
            .andReturn();
  }

  @Step("Выполнение GET запроса только с токеном")
  public Response makeGetRequestWithToken(String url, String token) {
    return  given()
            .filter(new AllureRestAssured())
            .header(new Header("x-csrf-token", token))
            .get(url)
            .andReturn();
  }

  @Step("Выполнение POST запроса")
  public Response makePostRequest(String url, Map<String, String> authData) {
    return  given()
            .filter(new AllureRestAssured())
            .body(authData)
            //.log().body()
            .post(url)
            .andReturn();
  }

  @Step("Выполнение GET запроса без данных или с Хедером и Кукой")
  // Универсальная функция для выполнения GET-запроса без данных или с Хедером и Кукой
  public Response makeGetRequestNullOrMap(String url,Map<String, String> data) {
    if (data == null) {
      return RestAssured
              .get(url)
              .andReturn();
    } else {
      return RestAssured
              .given()
              .header("x-csrf-token", data.get("x-csrf-token"))
              .cookie("auth_sid", data.get("auth_sid"))
              //.log().all()
              .get(url)
              .andReturn();
    }
  }

}
