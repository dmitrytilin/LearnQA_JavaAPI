package Homework;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class TestGetJSONHW {

  @Test
  public void getJSONHW(){
    JsonPath response = RestAssured

            .get("https://playground.learnqa.ru/api/get_json_homework")
            .jsonPath();

    System.out.println("Текст второго сообщения: " + response.get("messages[1].message"));

    /* Без отдельного параметра код короче
    String message = response.get("messages[1].message");
    System.out.println("Текст второго сообщения: " + message);*/

  }
}

