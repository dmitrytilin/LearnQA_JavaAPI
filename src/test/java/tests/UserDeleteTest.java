package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import lib.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("LearnQA auto Java")
@Feature("Удаление пользователя")
@DisplayName("Тест Удаления Пользователя")
public class UserDeleteTest extends BaseTestCase {
  private final UserHelper userHelper = new UserHelper();
  // Поля для хранения данных пользователя
  private static String email, password, cookie, header, undeletedUserCookie, undeletedUserHeader;
  private static int userId, undeletedUserId;

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

    // Авторизация пользователя
    userHelper.userAuth("vinkotov@example.com", "1234");
    undeletedUserCookie = userHelper.getCookie();
    undeletedUserHeader = userHelper.getHeader();
    undeletedUserId = userHelper.getUserIdOnAuth();
  }

  @Test
  @Story("Удаление не удаляемого юзера")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка удаления не удаляемого юзера")
  public void testDeleteUndeletedUser() {
    // Редактирование данных пользователя
    userHelper.userDelete(undeletedUserHeader, undeletedUserCookie, undeletedUserId);

    Assertions.assertJsonHasField(userHelper.getUserDeleteData(), "error");
    Assertions.assertResponceTextEquals(userHelper.getUserDeleteData(), "error",
           "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

  }

  @Test
  @Story("Удаление пользователя из под другого")
  @Owner("Tilin D.A.")
  @DisplayName("Удаление пользователя из под другого")
  public void testDeleteUserFromOther() {
    //Создание нового пользователя
    userHelper.userRegister();
    email = userHelper.getEmail();
    password = userHelper.getPassword();

    // Авторизация пользователя
    userHelper.userAuth(email, password);
    String otherCookie = userHelper.getCookie();
    String otherHeader = userHelper.getHeader();

    // Удаление данных пользователя
    userHelper.userDelete(otherHeader, otherCookie, userId);
    Assertions.assertJsonHasField(userHelper.getUserDeleteData(), "error");
    Assertions.assertResponceTextEquals(userHelper.getUserDeleteData(), "error",
            "This user can only delete their own account.");

    // Получение данных пользователя
    userHelper.userGetData(header, cookie, userId);
    Assertions.assertJsonHasFields(userHelper.getUserDeleteData(),
            new String[]{
                    "id",
                    "username",
                    "email",
                    "firstName",
                    "lastName"
            });
  }

  @Test
  @Story("Успешное удаление вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Позитивный тест удаления")
  public void testDeleteUser() {
    // Удаление данных пользователя
    userHelper.userDelete(header, cookie, userId);
    Assertions.assertJsonHasField(userHelper.getUserDeleteData(), "success");

    // Получение данных пользователя
    userHelper.userGetData(header, cookie, userId);
    Assertions.assertResponceTextEquals(userHelper.getUserDeleteData(),
            "User not found");
  }

}
