package lib;

import io.qameta.allure.Step;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class UserHelper extends BaseTestCase{

  private String cookie, header;
  private int userIdOnAuth, user_id;
  private Response responseGetRegistration;

  private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

  @Step("Авторизация пользователем vinkotov@example.com")
  public void userAuth() {
    // Данные для авторизации
    Map<String, String> authData = new HashMap<>();
    authData.put("email", "vinkotov@example.com");
    authData.put("password", "1234");

    // Выполнение запроса на авторизацию
    Response responseGetAuth = apiCoreRequests.makePostRequest(
            Constants.BASE_URL + "/user/login", authData);

    // Получение данных из ответа
    this.cookie = this.getCookie(responseGetAuth, "auth_sid");
    this.header = this.getHeader(responseGetAuth, "x-csrf-token");
    this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");
  }

  // Геттеры для получения данных авторизации
  public String getCookie() {
    return cookie;
  }

  public String getHeader() {
    return header;
  }

  public int getUserIdOnAuth() {
    return userIdOnAuth;
  }

  @Step("Регистрация нового пользователя с уникальным Email")
  public void userRegister() {
    String email = DataGenerator.getRandomEmail();

    Map<String, String> userTestData = new HashMap<>();
    userTestData.put("email", email);
    userTestData.put("password", "1235");
    userTestData.put("username", "lernqa");
    userTestData.put("firstName", "lernqa");
    userTestData.put("lastName", "lernqa");

    responseGetRegistration  = apiCoreRequests.makePostRequest(
            Constants.BASE_URL + "/user/", userTestData);

    this.user_id = this.getIntFromJson(responseGetRegistration, "id");
  }

  public int getUserIdOnRegister() {
    return user_id;
  }

  public Response getRegistrationResponce() {
    return responseGetRegistration;
  }
}