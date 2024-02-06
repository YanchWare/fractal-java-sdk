package com.yanchware.fractal.sdk;

import com.flextrade.jfixture.JFixture;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Method;
import java.util.Collection;
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
    if (ExtendableEnum.class.isAssignableFrom(classToInstantiate)) {
      return createExtendableEnumInstance(classToInstantiate);
    }
    
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


  /**
   * Generates a positive integer less than or equal to 999
   * @return A random positive integer less than or equal to 999
   */
  protected int aPositiveInteger() {
    return aPositiveIntegerWithMax(999);
  }

  /**
   * Generates a positive integer less than or equal to the specified maximum value.
   *
   * @param max The maximum value for the generated integer (inclusive).
   * @return A random positive integer less than or equal to max.
   */
  protected int aPositiveIntegerWithMax(int max) {
    if (max <= 0) {
      throw new IllegalArgumentException("Maximum value must be positive");
    }
    return random.nextInt(max) + 1; // Add 1 to ensure it's positive and within the range
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

    char lastChar = '\0'; 
    
    for (int i = 0; i < length; i++) {
      char nextChar;
      do {
        nextChar = characters.charAt(random.nextInt(characters.length()));

        if (nextChar == '-') {
          if (i != length - 1) {
            Character.isLetterOrDigit(lastChar);
          }
        }
      } while (nextChar == lastChar);
      
      sb.append(nextChar);
      lastChar = nextChar;
    }
    
    if (mustStartWithLetter && !Character.isLetter(sb.charAt(0))) {
      char startChar = lowercaseChars.charAt(random.nextInt(lowercaseChars.length()));
      sb.setCharAt(0, startChar);
    }

    // Ensure the last character is a letter
    if (!Character.isLetter(sb.charAt(length - 1))) {
      char endChar = lowercaseChars.charAt(random.nextInt(lowercaseChars.length()));
      sb.setCharAt(length - 1, endChar);
    }

    return sb.toString();
  }

  @SuppressWarnings("unchecked")
  private <T> T createExtendableEnumInstance(Class<T> enumClass) {
    try {
      Method valuesMethod = enumClass.getMethod("values");
      Collection<?> values = (Collection<?>) valuesMethod.invoke(null);
      int index = random.nextInt(values.size());
      return (T) values.toArray()[index];
    } catch (Exception e) {
      throw new RuntimeException("Error creating instance of " + enumClass.getName(), e);
    }
  }
}
