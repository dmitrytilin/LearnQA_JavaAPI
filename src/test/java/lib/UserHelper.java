package lib;

import io.qameta.allure.Step;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class UserHelper extends BaseTestCase{

  private String cookie, header, email, password, firstName;
  private int userIdOnAuth, userId;
  private Response responseGetRegistration, responseGetAuth, responseGetUserData;

  private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

  @Step("Авторизация пользователем")
  public void userAuth(String email, String password) {
    // Данные для авторизации
    Map<String, String> authData = new HashMap<>();
    authData.put("email", email);
    authData.put("password", password);

    // Выполнение запроса на авторизацию
    Response responseGetAuth = apiCoreRequests.makePostRequest(
            Constants.BASE_URL + "/user/login", authData);

    responseGetAuth.prettyPrint();

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

  public Response getAuthResponce(){
    return responseGetAuth;
  }

  @Step("Регистрация нового пользователя с уникальным Email")
  public void userRegister() {
    email = DataGenerator.getRandomEmail();

    Map<String, String> userTestData = new HashMap<>();
    userTestData.put("email", email);
    userTestData.put("password", "1235");
    userTestData.put("username", "lernqa");
    userTestData.put("firstName", "lernqa");
    userTestData.put("lastName", "lernqa");

    responseGetRegistration  = apiCoreRequests.makePostRequest(
            Constants.BASE_URL + "/user/", userTestData);

    this.userId = this.getIntFromJson(responseGetRegistration, "id");
    this.password = userTestData.get("password");
    this.firstName = userTestData.get("firstName");
  }

  public int getUserIdOnRegister() {
    return userId;
  }

  public Response getRegistrationResponce() {
    return responseGetRegistration;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  @Step("Редактирование пользователя")
  public void userEdit(String header, String cookie, String fieldName, String fieldValue, int user_id){
    Map<String, String> data = new HashMap<>();
    data.put("x-csrf-token",this.header);
    data.put("auth_sid",this.cookie);
    data.put(fieldName,fieldValue);

    responseGetUserData  = apiCoreRequests.makePutRequest(
            Constants.BASE_URL + "/user/" + user_id, data);
  }

  public void userGetData(String header, String cookie, int user_id){
    responseGetUserData  = apiCoreRequests.makeGetRequest(
            Constants.BASE_URL + "/user/" + user_id, this.header, this.cookie);
  }

  public Response getUserData() {
    return responseGetUserData;
  }



}