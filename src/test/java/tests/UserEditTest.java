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

  @Test
  @Story("Успешное редактирование вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Позитивный тест редактирования")
  public void testEditCorrect(){
    //Создается новый пользователь с рандомным Email
    UserHelper userHelper = new UserHelper();
    userHelper.userRegister();
    String email = userHelper.getEmail();
    String password = userHelper.getPassword();

    //Авторизация созданным пользователем
    userHelper.userAuth(email, password);
    String header = userHelper.getHeader();
    String cookie = userHelper.getCookie();
    int userId = userHelper.getUserIdOnAuth();

    //Редактирование данных пользователя
    String newName = "EditedUserName";
    userHelper.userEdit(header, cookie,"username", newName, userId);

    //Получение данных пользователя
    userHelper.userGetData(header,cookie,userId);
    Assertions.assertJsonByName(userHelper.getUserData(),"username", newName);
  }

  @Test
  @Story("Редактирование неавторизованного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка редактирвоать пользователя без авторизации")
  public void testEditUnAthUser(){
    //Создается новый пользователь с рандомным Email
    UserHelper userHelper = new UserHelper();
    userHelper.userRegister();
    int userId = userHelper.getUserIdOnAuth();

    //Редактирование данных пользователя
    String newName = "EditedUserName";
    userHelper.userEdit(null, null,"username", newName, userId);

    Assertions.assertJsonHasField(userHelper.getUserData(), "error");
    Assertions.assertJsonHasNotField(userHelper.getUserData(),"success");

  }

  @Test
  @Story("Редактирование пользователя другим пользователем")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменить пользователя, другим пользователем")
  public void testEditAthOtherUser() {
    //Создается новый пользователь с рандомным Email
    UserHelper userHelper = new UserHelper();
    userHelper.userRegister();
    String email = userHelper.getEmail();
    String password = userHelper.getPassword();

    userHelper.userRegister();
    int userId = userHelper.getUserIdOnRegister();

    //Авторизация пользователем
    userHelper.userAuth(email, password);
    String header = userHelper.getHeader();
    String cookie = userHelper.getCookie();

    //Редактирование данных пользователя
    String newName = "EditedUserName";
    userHelper.userEdit(header, cookie,"username", newName, userId);

    Assertions.assertJsonHasField(userHelper.getUserData(), "error");
    Assertions.assertJsonHasNotField(userHelper.getUserData(),"success");

    //userHelper.getUserData().prettyPrint();
  }

  @Test
  @Story("Редактирование Email вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменения Email на некорректный")
  public void testEditInCorrectEmail(){
    //Создается новый пользователь с рандомным Email
    UserHelper userHelper = new UserHelper();
    userHelper.userRegister();
    String email = userHelper.getEmail();
    String password = userHelper.getPassword();

    //Авторизация созданным пользователем
    userHelper.userAuth(email, password);
    String header = userHelper.getHeader();
    String cookie = userHelper.getCookie();
    int userId = userHelper.getUserIdOnAuth();

    //Редактирование данных пользователя
    String newName = "testtest.ru";
    userHelper.userEdit(header, cookie,"email", newName, userId);
    Assertions.assertJsonHasField(userHelper.getUserData(), "error");


    //Получение данных пользователя
    userHelper.userGetData(header,cookie,userId);
    Assertions.assertJsonByName(userHelper.getUserData(),"email", email);
  }

  @Test
  @Story("Редактирование firstName вновь созданного пользователя")
  @Owner("Tilin D.A.")
  @DisplayName("Попытка изменения firstName на некорректный")
  public void testEditShortFirstName(){
    //Создается новый пользователь с рандомным Email
    UserHelper userHelper = new UserHelper();
    userHelper.userRegister();
    String email = userHelper.getEmail();
    String password = userHelper.getPassword();

    //Авторизация созданным пользователем
    userHelper.userAuth(email, password);
    String header = userHelper.getHeader();
    String cookie = userHelper.getCookie();
    String firstName  = userHelper.getFirstName();
    int userId = userHelper.getUserIdOnAuth();

    //Редактирование данных пользователя
    String newName = "t";
    userHelper.userEdit(header, cookie,"firstName", newName, userId);

    Assertions.assertJsonHasField(userHelper.getUserData(), "error");


    //Получение данных пользователя
    userHelper.userGetData(header,cookie,userId);

    Assertions.assertJsonByName(userHelper.getUserData(),"firstName", firstName);
  }

}
