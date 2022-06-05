package in.co.balkishan.springbootstrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.co.balkishan.springbootstrap.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
