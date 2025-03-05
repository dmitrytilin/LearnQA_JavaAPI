package tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.UserHelper;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("LearnQA auto Java")
@Feature("Получение данных")
@DisplayName("Тест Получения данных пользователя")

public class UserGetTest extends BaseTestCase {
  String url = Constants.BASE_URL + "/user/2";
  private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

  @Test
  @Story("Получение данных не а/п")
  @Owner("Tilin D.A.")
  @DisplayName("Получение данных не а/п")
  @Description("Попытка получения не данных а/п")

  public void testGetUserDataNotAuth() {
    Response responceUserData = apiCoreRequests.makeGetRequestNullOrMap(url, null);

    Assertions.assertJsonHasField(responceUserData, "username");
    Assertions.assertJsonHasNotFields(responceUserData,
            new String[]{
                    "firstName",
                    "lastName",
                    "email"
            });
  }

  @Test
  @Story("Получение данных а/п")
  @Owner("Tilin D.A.")
  @DisplayName("Получение данных а/п")
  @Description("Попытка получения данных а/п")

  public void testGetUserDetailsAuthAsSameUser() {
    UserHelper userHelper = new UserHelper();
    userHelper.userAuth("vinkotov@example.com","1234");

    Map<String, String> authData = new HashMap<>();
    authData.put("x-csrf-token", userHelper.getHeader());
    authData.put("auth_sid", userHelper.getCookie());

    url = Constants.BASE_URL + "/user/" + userHelper.getUserIdOnAuth();

    Response responseGetAuth = apiCoreRequests.makeGetRequestNullOrMap(url, authData);

    Assertions.assertJsonHasFields(responseGetAuth, new String[]{
            "id",
            "username",
            "email",
            "firstName",
            "lastName"
    });
  }

  @Test
  @Story("Получение данных а/п под другим пользователем")
  @Owner("Tilin D.A.")
  @DisplayName("Получение данных а/п под другим пользователем")
  @Description("Попытка получения Получение данных а/п под другим пользователем")

  public void testGetUserDetailsAuthAsOtherUser() {
    // Создаем нового пользователя, получаем user_id
    UserHelper userHelper = new UserHelper();
    userHelper.userRegister();
    int userID = userHelper.getUserIdOnRegister();

    //Авторизуемся под vinkotov@example.com, получаем x-csrf-token и auth_sid
    userHelper.userAuth("vinkotov@example.com","1234");
    Map<String, String> authData = new HashMap<>();
    authData.put("x-csrf-token", userHelper.getHeader());
    authData.put("auth_sid", userHelper.getCookie());

    //Запрашиваем данные по эндпоинту с user_id нового пользователя
    // с x-csrf-token и auth_sid пользователя vinkotov@example.com

    Response responceUserData = apiCoreRequests.makeGetRequestNullOrMap(
            Constants.BASE_URL + "/user/" + userID,
            authData
    );

    Assertions.assertJsonHasField(responceUserData, "username");
    Assertions.assertJsonHasNotFields(responceUserData,
            new String[]{
                    "firstName",
                    "lastName",
                    "email"
            });
  }
}

