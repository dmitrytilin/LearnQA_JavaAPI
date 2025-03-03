package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {

  public static void assertJsonByName (Response Response, String name, int expectedValue){
    Response.then().assertThat().body("$", hasKey(name));

    int value = Response.jsonPath().getInt(name);
    assertEquals(expectedValue, value, "JSON Value is not equal to expected value");
  }

  public static void assertResponceTextEquals(Response Responce, String expectedAnswer){
    assertEquals(
            expectedAnswer,
            Responce.asString(),
            "Responce text is unexpected"
    );
  }

  public static void assertResponceCodeEquals(Response Responce, Integer expectedStatusCode){
    assertEquals(
            expectedStatusCode,
            Responce.statusCode(),
            "Responce status code is unexpected"
    );
  }
  public static void assertJsonHasField(Response Response, String expectedFieldName){
    Response.then().assertThat().body("$", hasKey(expectedFieldName));
  }

  public static void assertJsonHasFields(Response Response, String[] expectedFieldNames){
    for (String expectedFieldName : expectedFieldNames){
      assertJsonHasField(Response, expectedFieldName);
    }
  }

  public static void assertJsonHasNotField(Response Response, String unexpectedFieldName){
    Response.then().assertThat().body("$", not(hasKey(unexpectedFieldName)));
  }

  public static void assertJsonHasNotFields(Response Response, String[] expectedFieldNames){
    for (String expectedFieldName : expectedFieldNames){
      assertJsonHasNotField(Response, expectedFieldName);
    }
  }

}

