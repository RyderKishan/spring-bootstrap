package in.co.balkishan.springbootstrap.utils;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class Utils {
  public static String decode(String encodedString) {
    try {
      return new String(Base64.getDecoder().decode(encodedString));
    } catch (Exception e) {
      return encodedString;
    }
  }

  public static String encodeString(String inputString) {
    try {
      return Base64.getEncoder().encodeToString(inputString.getBytes());
    } catch (Exception e) {
      return inputString;
    }
  }

}