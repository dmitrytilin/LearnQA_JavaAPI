package Homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestSecretPasswordHW {

  @Test
  public void testSecretPasswordHW() {
    String location = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
    String secondLocation = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
    String[] uniqueValues = getUniqueValues("D:\\Work\\LearnQA_JavaAPI\\src\\test\\java\\Homework\\PopPass.csv");
    Map<String, String> data = new HashMap<>();
    data.put("login", "super_admin");

    for (String uniqueValue : uniqueValues) {
      data.put("password", uniqueValue);
      Response response = makePostRequest(location, data);
      Map<String, String> cookies = new HashMap<>();
      cookies.put("auth_cookie", response.getCookie("auth_cookie"));

        Response authResponse = makePostRequestWhithCookie(secondLocation, cookies);

        if (authResponse.asString().equals("You are authorized")) {
          System.out.println();
          System.out.print("\nServer response was \"" + authResponse.asString() + "\" ||");
          System.out.print(" Password \"" +  uniqueValue + "\"" + " for login \"" + data.get("login") + " CORRECT");
          break;
        } else {
          System.out.print("\n\"" + authResponse.asString() + "\" ||");
          System.out.print(" Password \"" +  uniqueValue + "\" INCORRECT");
        }
    }
  }

  // Универсальная функция для выполнения POST-запроса без куки
  private Response makePostRequest(String url, Map<String, String> data) {
    return RestAssured
            .given()
            .body(data)
            //.log().body()
            //.log().cookies()
            .when()
            .post(url)
            .then()
            .extract()
            .response();
  }

  // Универсальная функция для выполнения POST-запроса с кукой
  private Response makePostRequestWhithCookie(String url, Map<String, String> cookies) {
    return RestAssured
            .given()
            .cookies(cookies)
            //.log().body()
            //.log().cookies()
            .when()
            .post(url)
            .then()
            .extract()
            .response();
  }

  // Функция перебора значений файла .csv, возвращает массив уникальных паролей
  public static String[] getUniqueValues(String filePath) {
    Set<String> uniqueValues = new LinkedHashSet<>();

    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String[] cells = scanner.nextLine().split(";"); // Разделение по запятым
        for (String cell : cells) {
          if (!cell.trim().isEmpty()) {
            uniqueValues.add(cell.trim());
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return new String[0];
    }
    return uniqueValues.toArray(new String[0]);
  }
}
