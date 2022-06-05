package in.co.balkishan.springbootstrap.exception;

import in.co.balkishan.springbootstrap.model.ErrorDetail;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ErrorDetail> handleBadRequestException(BadRequestException exception,
      WebRequest request) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, request);
  }

  @ExceptionHandler(NoContentException.class)
  public final ResponseEntity<ErrorDetail> handleNoContentException(NoContentException exception, WebRequest request) {
    return buildErrorResponse(HttpStatus.NO_CONTENT, exception, request);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public final ResponseEntity<ErrorDetail> handleUnauthorizedException(UnauthorizedException exception,
      WebRequest request) {
    return buildErrorResponse(HttpStatus.UNAUTHORIZED, exception, request);
  }

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ErrorDetail> handleNotFoundException(
      NotFoundException exception,
      WebRequest request) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, exception, request);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity<ErrorDetail> handleConstraintViolationException(
      ConstraintViolationException exception, WebRequest webRequest) {
    Map<String, String> errors = new HashMap<>();
    exception.getConstraintViolations().iterator().forEachRemaining(constraintViolation -> {
      errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
    });
    ErrorDetail errorDetail = new ErrorDetail();
    errorDetail.setCode("E002001");
    errorDetail.setMessage("ConstraintViolationException");
    errorDetail.setException(exception.getClass().getName());
    errorDetail.setDescription(exception.getMessage());
    errorDetail.setUrl(webRequest.getDescription(false));
    errorDetail.setSessionId(webRequest.getSessionId());
    errorDetail.setErrors(errors);

    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ArithmeticException.class)
  public final ResponseEntity<ErrorDetail> handleArithmeticException(
      ArithmeticException exception, WebRequest webRequest) {
    ErrorDetail errorDetail = new ErrorDetail();
    errorDetail.setCode("E002002");
    errorDetail.setMessage("ArithmeticException");
    errorDetail.setException(exception.getClass().getName());
    errorDetail.setDescription(exception.getMessage());
    errorDetail.setUrl(webRequest.getDescription(false));
    errorDetail.setSessionId(webRequest.getSessionId());
    errorDetail.setErrors(null);

    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorDetail> buildErrorResponse(HttpStatus httpStatus, CustomException exception,
      WebRequest webRequest) {
    ErrorDetail errorDetail = new ErrorDetail();
    errorDetail.setCode(exception.getCode());
    errorDetail.setMessage(httpStatus.getReasonPhrase());
    errorDetail.setException(exception.getClass().getName());
    errorDetail.setDescription(exception.getMessage());
    errorDetail.setUrl(webRequest.getDescription(false));
    errorDetail.setSessionId(webRequest.getSessionId());
    errorDetail.setErrors(null);
    return new ResponseEntity<>(errorDetail, httpStatus);
  }
}
