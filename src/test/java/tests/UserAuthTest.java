package tests;

import io.qameta.allure.*;

import lib.BaseTestCase;
import lib.Assertions;
import lib.ApiCoreRequests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Epic("LearnQA auto Java")
@Feature("Авторизация")
@DisplayName("Тест авторизации пользователя")

public class UserAuthTest extends BaseTestCase{
  String cookie, header;
  int userIdOnAuth;
  private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

  @BeforeEach
  public void loginUser(){
    UserHelper userHelper = new UserHelper();
    userHelper.userAuth("vinkotov@example.com","1234");
  }

  @Test
  @Story("Авторизация пользователя с корректными данными")
  @Owner("Tilin D.A.")
  @DisplayName("Позитивная авторизация")
  @Description("Успешно авторизует пользователя по Email и Password")
  public void testAuthUser(){
    Response responseCheckAuth = apiCoreRequests.makeGetRequest(
            Constants.BASE_URL + "/user/auth",
                    this.header,
                    this.cookie
            );

    Assertions.assertJsonByName(responseCheckAuth, "user_id", this.userIdOnAuth);
  }

  @Story("Авторизация без куки или токена")
  @Owner("Tilin D.A.")
  @Description("Тест проверяет авторизацию без отправки авторизационной куки или токена")
  @DisplayName("Негативная авторизация пользователя")
  @ParameterizedTest
  @ValueSource(strings = {"cookie", "headers"})
  public void testNegativeAuthUser(String condition){
    RequestSpecification spec = RestAssured.given();
    spec.baseUri(Constants.BASE_URL + "/user/auth");

    if (condition.equals("cookie")){
      Response responseForCheck = apiCoreRequests.makeGetRequestWithCookie(
              Constants.BASE_URL + "/user/auth",
                      this.cookie
              );
      Assertions.assertJsonByName(responseForCheck, "user_id", 0);
    } else if (condition.equals("headers")){
      Response responseForCheck = apiCoreRequests.makeGetRequestWithToken(
              Constants.BASE_URL + "/user/auth",
              this.header
      );
      Assertions.assertJsonByName(responseForCheck, "user_id", 0);
    } else {
      throw new IllegalArgumentException("Condition value is unknown " + condition);
    }
  }
}































