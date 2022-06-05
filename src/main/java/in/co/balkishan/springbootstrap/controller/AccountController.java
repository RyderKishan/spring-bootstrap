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

import in.co.balkishan.springbootstrap.model.Account;
import in.co.balkishan.springbootstrap.service.AccountService;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("")
  public ResponseEntity<Page<Account>> listAccounts(Pageable pageable) {
    Page<Account> page = accountService.listAccounts(pageable);
    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Account> addAccount(@RequestBody Account account) {
    Account savedAccount = accountService.addAccount(account);
    return new ResponseEntity<>(savedAccount, HttpStatus.OK);
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<Account> getAccount(
      @PathVariable Integer accountId) {
    Account account = accountService.getAccount(accountId);
    return new ResponseEntity<>(account, HttpStatus.OK);
  }

}
