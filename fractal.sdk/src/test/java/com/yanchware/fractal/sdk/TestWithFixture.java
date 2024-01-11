package com.yanchware.fractal.sdk;

import com.flextrade.jfixture.JFixture;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestWithFixture {

  protected JFixture fixture;
  private Random random;

  @BeforeEach
  public void init() {
    fixture = new JFixture();
    random = new Random();
  }

  protected <T> T a(Class<T> classToInstantiate) {
    return fixture.create(classToInstantiate);
  }

  protected <T> Set<T> aSetOf(Class<T> elementsClass) {
    return Set.copyOf(fixture.collections().createCollection(elementsClass));
  }

  protected <T> List<T> aListOf(Class<T> elementsClass) {
    return List.copyOf(fixture.collections().createCollection(elementsClass));
  }

  protected String aLowerCaseAlphanumericString(int length) {

    return aAlphanumericString(length, true, false,null);
  }

  protected String aComponentId() {

    return aAlphanumericString(20, true, true, "-");
  }

  protected String aLowerCaseAlphanumericString(int length, boolean mustStartWithLetter, String additionalChars) {

    return aAlphanumericString(length, true, mustStartWithLetter, additionalChars);
  }

  protected String aAlphanumericString(int length) {

    return aAlphanumericString(length, false, false,null);
  }

  protected String aAlphanumericString(int length, boolean mustStartWithLetter) {

    return aAlphanumericString(length, false, mustStartWithLetter, null);
  }

  protected String aAlphanumericString(int length, boolean lowercaseOnly, boolean mustStartWithLetter, String additionalChars) {
    String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
    String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String numberChars = "0123456789";
    String characters = lowercaseChars + (lowercaseOnly ? "" : uppercaseChars) + numberChars + (additionalChars != null ? additionalChars : "");
    StringBuilder sb = new StringBuilder();

    char lastChar = '\0'; // Initialize with a value that won't match any character

    synchronized (random) { // Ensure thread-safety
      for (int i = 0; i < length; i++) {
        char nextChar;
        do {
          nextChar = characters.charAt(random.nextInt(characters.length()));
        } while (nextChar == lastChar); // Keep generating until a different character is found

        sb.append(nextChar);
        lastChar = nextChar; // Update lastChar for the next iteration
      }

      // If the string must start with a letter, replace the first character
      if (mustStartWithLetter) {
        char startChar = lowercaseOnly ? lowercaseChars.charAt(random.nextInt(lowercaseChars.length()))
            : characters.charAt(random.nextInt(lowercaseChars.length() + uppercaseChars.length()));
        sb.setCharAt(0, startChar);
      }
    }

    return sb.toString();
  }
}
