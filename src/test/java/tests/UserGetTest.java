package tests;

import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;


import java.util.HashMap;
import java.util.Map;


public class UserGetTest extends BaseTestCase {
  String url = "https://playground.learnqa.ru/api/user/2";
  private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

  @Test
  @DisplayName("Получение данных не авторизованного пользователя")
  @Description("Авторизует поль")
  public void testGetUserDataNotAuth() {
    Response responceUserData = apiCoreRequests.makeGetRequestNullOrMap(url, null);

    Assertions.assertJsonHasField(responceUserData, "username");
    Assertions.assertJsonHasNotFields(responceUserData,
            new String[]{"firstName", "lastName", "email"});
    //System.out.println(responceUserData.asString());

  }

  @Test
  @DisplayName("Получение данных авторизованного пользователя")
  public void testGetUserDetailsAuthAsSameUser() {
    String url = "https://playground.learnqa.ru/api/user/login";

    Map<String,String> authData = new HashMap<>();
    authData.put("email", "vinkotov@example.com");
    authData.put("password", "1234");
    Response responseGetAuth = apiCoreRequests.makePostRequest(url, authData);

    authData = new HashMap<>();
    authData.put("x-csrf-token", getHeader(responseGetAuth, "x-csrf-token"));
    authData.put("auth_sid", getCookie(responseGetAuth, "auth_sid"));
    url = "https://playground.learnqa.ru/api/user/2";

    responseGetAuth = apiCoreRequests.makeGetRequestNullOrMap(url, authData);
    Assertions.assertJsonHasFields(responseGetAuth, new String[] {"id", "username", "email" , "firstName", "lastName"});
    responseGetAuth.print();

  }
}
