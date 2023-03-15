package in.co.balkishan.springbootstrap.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.co.balkishan.springbootstrap.model.PingResponse;
import in.co.balkishan.springbootstrap.model.SignupRequest;
import in.co.balkishan.springbootstrap.model.SignupResponse;
import in.co.balkishan.springbootstrap.model.User;
import in.co.balkishan.springbootstrap.security.JwtTokenProvider;
import in.co.balkishan.springbootstrap.service.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

  private final UserService userService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @GetMapping("/ping")
  public ResponseEntity<PingResponse> ping() {
    PingResponse pingResponse = new PingResponse();
    pingResponse.setMessage("AuthController is UP!");
    pingResponse.setTimeStamp(LocalDateTime.now(ZoneOffset.UTC));
    return new ResponseEntity<>(pingResponse, HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<SignupResponse> addUser(@RequestBody SignupRequest signupRequest) {
    User user = signupRequest.getUser();

    String password = user.getPassword();
    System.out.println("password : " + password);
    String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    System.out.println("encryptedPassword : " + encryptedPassword);
    user.setPassword(encryptedPassword);
    userService.addUser(user);
    String token = jwtTokenProvider.createToken(user);

    SignupResponse signupResponse = new SignupResponse();
    signupResponse.setToken(token);

    return new ResponseEntity<>(signupResponse, HttpStatus.OK);
  }

}
