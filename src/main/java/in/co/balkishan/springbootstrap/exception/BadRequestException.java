package in.co.balkishan.springbootstrap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends CustomException {
  private static final long serialVersionUID = 1L;

  public BadRequestException(String errorMessage, String errorCode) {
    super(errorMessage, errorCode);
  }
}
