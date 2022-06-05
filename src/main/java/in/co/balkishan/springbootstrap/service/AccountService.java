package in.co.balkishan.springbootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.co.balkishan.springbootstrap.exception.NotFoundException;
import in.co.balkishan.springbootstrap.model.Account;
import in.co.balkishan.springbootstrap.repository.AccountRepository;

@Service
public class AccountService {
  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Page<Account> listAccounts(Pageable pageable) {
    return accountRepository.findAll(pageable);
  }

  public Account addAccount(Account account) {
    return accountRepository.save(account);
  }

  public Account getAccount(Integer accountId) {
    return accountRepository.findById(accountId)
        .orElseThrow(() -> new NotFoundException("Account not found", "E003001"));
  }

}
