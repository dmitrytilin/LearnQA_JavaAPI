package Homework;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStringLengthHW {
  @ParameterizedTest
  @ValueSource(strings = {"","Short text","15 symbols text","Text longer then 15 symbols"})
  public void testInputtedString(String text) {
    assertTrue(text.length() > 15, "Entered text length less then 15 symbols");
  }
}