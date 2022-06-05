package in.co.balkishan.springbootstrap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends CustomException {
  private static final long serialVersionUID = 1L;

  public NotFoundException(String errorMessage, String errorCode) {
    super(errorMessage, errorCode);
  }
}
