package in.co.balkishan.springbootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.co.balkishan.springbootstrap.exception.NotFoundException;
import in.co.balkishan.springbootstrap.model.User;
import in.co.balkishan.springbootstrap.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Page<User> listUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public User addUser(User user) {
    return userRepository.save(user);
  }

  public User getUser(Integer userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found", "E004001"));
  }

}
