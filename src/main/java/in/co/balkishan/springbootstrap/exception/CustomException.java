package in.co.balkishan.springbootstrap.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String message;
  private final String code;

  public CustomException(String message, String code) {
    super();
    this.message = message;
    this.code = code;
  }
}
