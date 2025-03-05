package tests;

import io.qameta.allure.*;
import lib.*;
import lib.Assertions;
import org.junit.jupiter.api.*;

@Epic("LearnQA auto Java")
@Feature("Редактирование пользователя")
@DisplayName("Тест Редактирования Пользователя")
public class UserEditTest extends BaseTestCase {

  private final UserHelper userHelper = new UserHelper();

  // Поля для хранения данных пользователя
  private static String email, password, firstName, cookie, header;
  private static int userId;


  // Метод для создания и авторизации пользователя (выполняется один раз перед всеми тестами)
  @BeforeAll
  public static void createAndAuthUser() {
    UserHelper userHelper = new UserHelper();

    // Создание пользователя
    userHelper.userRegister();
    email = userHelper.getEmail();
    password = userHelper.getPassword();
    userId = userHelper.getUserIdOnRegister();

    // Авторизация пользователя
    userHelper.userAuth(email, password);
    cookie = userHelper.getCookie();
    header = userHelper.getHeader();
    firstName = userHelper.getFirstName();
  }

  @Test
  @Story("Успешное редактирование вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Позитивный тест редактирования")
  public void testEditCorrect() {
    // Редактирование данных пользователя
    String newName = "EditedUserName";
    userHelper.userEdit(header, cookie, "username", newName, userId);

    // Получение данных пользователя
    userHelper.userGetData(header, cookie, userId);
    Assertions.assertJsonByName(userHelper.getUserData(), "username", newName);
  }

  @Test
  @Story("Редактирование неавторизованного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка редактировать пользователя без авторизации")
  public void testEditUnAuthUser() {
    // Редактирование данных пользователя
    String newName = "EditedUserName";
    userHelper.userEdit(null, null, "username", newName, userId);

    Assertions.assertJsonHasField(userHelper.getUserEditedData(), "error");
    Assertions.assertJsonHasNotField(userHelper.getUserEditedData(), "success");
  }

  @Test
  @Story("Редактирование пользователя другим пользователем")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменить пользователя другим пользователем")
  public void testEditAuthOtherUser() {
    // Создание второго пользователя
    userHelper.userRegister();
    int otherUserId = userHelper.getUserIdOnRegister();

    // Редактирование данных второго пользователя первым пользователем
    userHelper.userEdit(header, cookie, "username", "EditedUserName", otherUserId);

    Assertions.assertJsonHasField(userHelper.getUserData(), "error");
    Assertions.assertJsonHasNotField(userHelper.getUserData(), "success");
  }

  @Test
  @Story("Редактирование Email вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменения Email на некорректный")
  public void testEditIncorrectEmail() {
    // Редактирование данных пользователя
    String newEmail = "testtest.ru";
    userHelper.userEdit(header, cookie, "email", newEmail, userId);

    Assertions.assertJsonHasField(userHelper.getUserData(), "error");

    // Получение данных пользователя
    userHelper.userGetData(header, cookie, userId);
    Assertions.assertJsonByName(userHelper.getUserData(), "email", email);
  }

  @Test
  @Story("Редактирование firstName вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменения firstName на некорректный")
  public void testEditShortFirstName() {

    // Редактирование данных пользователя
    String newName = "t";
    userHelper.userEdit(header, cookie, "firstName", newName, userId);

    Assertions.assertJsonHasField(userHelper.getUserData(), "error");

    // Получение данных пользователя
    userHelper.userGetData(header, cookie, userId);
    Assertions.assertJsonByName(userHelper.getUserData(), "firstName", firstName);
  }
}