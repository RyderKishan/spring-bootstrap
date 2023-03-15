package in.co.balkishan.springbootstrap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.co.balkishan.springbootstrap.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}
