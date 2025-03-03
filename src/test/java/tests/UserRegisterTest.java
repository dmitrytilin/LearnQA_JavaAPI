package tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;

import org.junit.jupiter.api.Test; // Используем JUnit 5 Test
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Тест Регистрации Пользователя")
public class UserRegisterTest extends BaseTestCase {
  String url = "https://playground.learnqa.ru/api/user/";
  private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Epic("LearnQA auto Java")
    @Feature("Регистрация")
    @Story("Успешная регистрация пользователя")
    @Owner("Tilin D.A.")
    @DisplayName("Позитивный тест Регистрации")
    public void testCreateUserPositive(){
      String email = DataGenerator.getRandomEmail();

      Map<String, String> userTestData = new HashMap<>();
      userTestData.put("email", email);
      userTestData.put("password", "1235");
      userTestData.put("username", "lernqa");
      userTestData.put("firstName", "lernqa");
      userTestData.put("lastName", "lernqa");

      Response testResponse = apiCoreRequests.makePostRequest(url, userTestData);

      Assertions.assertJsonHasField(testResponse,"id");
    }

    @Epic("LearnQA auto Java")
    @Feature("Регистрация")
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
