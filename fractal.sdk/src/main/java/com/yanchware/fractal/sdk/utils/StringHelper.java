package com.yanchware.fractal.sdk.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringHelper {
  private static final String WORD_SEPARATOR = " ";
  private static final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");
  
  public static String convertToTitleCase(String text) {
    if (text == null || text.isEmpty()) {
      return text;
    }

    return Arrays
        .stream(text.split(WORD_SEPARATOR))
        .map(word -> word.isEmpty()
            ? word
            : Character.toTitleCase(text.charAt(0)) + word
            .substring(1)
            .toLowerCase())
        .collect(Collectors.joining(WORD_SEPARATOR));
  }

  public static String toWords(String text) {
    Matcher matcher = WORD_FINDER.matcher(text);
    List<String> words = new ArrayList<>();
    while (matcher.find()) {
      words.add(matcher.group(0));
    }

    return String.join(" ", words);
  }
}
