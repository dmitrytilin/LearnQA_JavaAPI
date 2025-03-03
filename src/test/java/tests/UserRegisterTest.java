package tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.*;

import org.junit.jupiter.api.Test; // Используем JUnit 5 Test
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;

@Epic("LearnQA auto Java")
@Feature("Регистрация")
@DisplayName("Тест Регистрации Пользователя")
public class UserRegisterTest extends BaseTestCase {
  String url = Constants.BASE_URL + "/user/";
  private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Story("Успешная регистрация пользователя")
    @Owner("Tilin D.A.")
    @DisplayName("Позитивный тест Регистрации")
    public void testCreateUserPositive(){
      UserHelper userHelper = new UserHelper();
      userHelper.userRegister();

      Assertions.assertJsonHasField(userHelper.getRegistrationResponce(),"id");
    }

    @Story("Регистрация пользователя с некорректными данными")
    @Owner("Tilin D.A.")
    @ParameterizedTest(name = "{index} Набор тестовых данных")
    @MethodSource("lib.DataGenerator#dataProviderMethod")
    @DisplayName("Негативные тесты Регистрации")
    @Description("Попытка создания пользователя с некорректными данными")

    public void testCreateUserNegative (Map<String, String> userTestData)  {
      String expectedErrorMessage = userTestData.get("expectedErrorMessage");
      userTestData.remove("expectedErrorMessage");

      Response testResponse = apiCoreRequests.makePostRequest(url, userTestData);

      Assertions.assertResponceTextEquals(testResponse, expectedErrorMessage);
  }
}
