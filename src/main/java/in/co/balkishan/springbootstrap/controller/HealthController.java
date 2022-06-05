package in.co.balkishan.springbootstrap.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.co.balkishan.springbootstrap.model.PingResponse;

@RestController
@RequestMapping(value = "/")
public class HealthController {

  @GetMapping("/ping")
  public ResponseEntity<PingResponse> ping() {
    PingResponse pingResponse = new PingResponse();
    pingResponse.setMessage("Server is UP!");
    pingResponse.setTimeStamp(LocalDateTime.now(ZoneOffset.UTC));
    return new ResponseEntity<>(pingResponse, HttpStatus.OK);
  }

}
