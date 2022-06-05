package in.co.balkishan.springbootstrap.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Data
public class ErrorDetail {
  private LocalDateTime timestamp;
  private String code;
  private String message;
  private String exception;
  private Map<String, String> errors;
  private String description;
  private String url;
  private String sessionId;

  public ErrorDetail() {
    super();
    this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
  }

}
