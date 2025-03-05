package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import lib.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("LearnQA auto Java")
@Feature("Редактирование пользователя")
@DisplayName("Тест Редактирования Пользователя")
public class UserEditTest extends BaseTestCase {
  private final UserHelper userHelper = new UserHelper();

  private Object[] createUser() {
    userHelper.userRegister();
    userHelper.userAuth(userHelper.getEmail(), userHelper.getPassword());
    email = userHelper.getEmail();
    password = userHelper.getPassword();
    firstUserId = userHelper.getUserIdOnRegister();

    return new Object[]{email, password, firstUserId};
  }

  Object[] createdUserData = createUser();
  String email = (String) createdUserData[0];
  String password = (String) createdUserData[1];
  int firstUserId = (int) createdUserData[2];

  private Object[] authUser(String email, String password) {
    userHelper.userAuth(email, password);
    cookie = userHelper.getCookie();
    header =userHelper.getHeader();
    secondUserId = userHelper.getUserIdOnAuth();

    return new Object[]{cookie, header, secondUserId};
  }

  Object[] authUserData = authUser(email, password);
  String cookie = (String) authUserData[0];
  String header = (String) authUserData[1];
  int secondUserId = (int) authUserData[2];

  @Test
  @Story("Успешное редактирование вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Позитивный тест редактирования")
  public void testEditCorrect(){

    //Редактирование данных пользователя
    String newName = "EditedUserName";
    userHelper.userEdit(header, cookie,"username", newName, secondUserId);

    //Получение данных пользователя
    userHelper.userGetData(header,cookie,secondUserId);
    Assertions.assertJsonByName(userHelper.getUserData(),"username", newName);
  }

  @Test
  @Story("Редактирование неавторизованного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка редактировать пользователя без авторизации")
  public void testEditUnAthUser(){

    //Редактирование данных пользователя
    String newName = "EditedUserName";
    userHelper.userEdit(
            null,
            null,
            "username",
            newName,
            firstUserId);

    Assertions.assertJsonHasField(userHelper.getUserEditedData(), "error");
    Assertions.assertJsonHasNotField(userHelper.getUserEditedData(),"success");

  }

  @Test
  @Story("Редактирование пользователя другим пользователем")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменить пользователя, другим пользователем")
  public void testEditAthOtherUser() {
    userHelper.userRegister();
    int userId = userHelper.getUserIdOnRegister();

    //Редактирование данных пользователя
    String newName = "EditedUserName";
    userHelper.userEdit(header, cookie,"username", newName, userId);

    Assertions.assertJsonHasField(userHelper.getUserData(), "error");
    Assertions.assertJsonHasNotField(userHelper.getUserData(),"success");

  }

  @Test
  @Story("Редактирование Email вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменения Email на некорректный")
  public void testEditInCorrectEmail(){

    //Редактирование данных пользователя
    String newName = "testtest.ru";
    userHelper.userEdit(header, cookie,"email", newName, secondUserId);
    Assertions.assertJsonHasField(userHelper.getUserData(), "error");

    //Получение данных пользователя
    userHelper.userGetData(header,cookie,secondUserId);
    Assertions.assertJsonByName(userHelper.getUserData(),"email", email);
  }

  @Test
  @Story("Редактирование firstName вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменения firstName на некорректный")
  public void testEditShortFirstName(){

    String firstName  = userHelper.getFirstName();

    //Редактирование данных пользователя
    String newName = "t";
    userHelper.userEdit(header, cookie,"firstName", newName, secondUserId);
    Assertions.assertJsonHasField(userHelper.getUserData(), "error");

    //Получение данных пользователя
    userHelper.userGetData(header,cookie,secondUserId);
    Assertions.assertJsonByName(userHelper.getUserData(),"firstName", firstName);
  }

}
