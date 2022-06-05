package in.co.balkishan.springbootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.co.balkishan.springbootstrap.model.User;
import in.co.balkishan.springbootstrap.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("")
  public ResponseEntity<Page<User>> listUsers(Pageable pageable) {
    Page<User> page = userService.listUsers(pageable);
    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<User> addUser(@RequestBody User user) {
    User savedUser = userService.addUser(user);
    return new ResponseEntity<>(savedUser, HttpStatus.OK);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUser(
      @PathVariable Integer userId) {
    User user = userService.getUser(userId);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

}
