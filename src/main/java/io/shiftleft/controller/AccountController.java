package io.shiftleft.controller;

import io.shiftleft.data.DataLoader;
import io.shiftleft.model.Account;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.shiftleft.repository.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Admin checks login
 */

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    private static Logger log = LoggerFactory.getLogger(DataLoader.class);
    
    @GetMapping("/account")
    public Iterable<Account> getAccountList(HttpServletResponse response, HttpServletRequest request) {
        response.addHeader("test-header-detection", new Account().toString());
        log.info("Account Data is {}", this.accountRepository.findOne(1l).toString());
        return this.accountRepository.findAll();
    }

@PostMapping("/account")
public Account createAccount(@RequestBody Account account) {
    // Encrypt sensitive data
    String encryptedAccountNumber = encrypt(account.getAccountNumber(), "MySecretKey");
    String encryptedRoutingNumber = encrypt(account.getRoutingNumber(), "MySecretKey");
    account.setAccountNumber(encryptedAccountNumber);
    account.setRoutingNumber(encryptedRoutingNumber);
    
    this.accountRepository.save(account);
    log.info("Account Data is {}", account.toString());
    return account;
}

public static String encrypt(String valueToEnc, String secret) {
    try {
        Key key = new SecretKeySpec(secret.getBytes(), "AES");
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(c.doFinal(valueToEnc.getBytes()));
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    return account;
}

        return account;
    }

    @GetMapping("/account/{accountId}")
    public Account getAccount(@PathVariable long accountId) {
        log.info("Account Data is {}", this.accountRepository.findOne(1l).toString());
        return this.accountRepository.findOne(accountId);
    }

    @PostMapping("/account/{accountId}/deposit")
    public Account depositIntoAccount(@RequestParam double amount, @PathVariable long accountId) {
        Account account = this.accountRepository.findOne(accountId);
        log.info("Account Data is {}", account.toString());
        account.deposit(amount);
        this.accountRepository.save(account);
        return account;
    }

@PostMapping("/account/{accountId}/withdraw")
public Account withdrawFromAccount(@RequestParam double amount, @PathVariable long accountId) {
    Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
    if(optionalAccount.isPresent()) {
        Account account = optionalAccount.get();
        // Check if the user trying to access the account is authorized
        if (!isUserAuthorized(accountId)) {
            throw new RuntimeException("Unauthorized access");
        }
        account.withdraw(amount);
        this.accountRepository.save(account);
        Logger log = LoggerFactory.getLogger(AccountController.class);
        log.info("Account Data is {}", "REDACTED");
        return account;
    } else {
        throw new RuntimeException("Account not found");
    }
}

public Account withdrawFromAccount(@RequestParam double amount, @PathVariable("accountId") long accountId, HttpServletResponse response) throws IOException {
    Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
    if (!optionalAccount.isPresent()) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Account not found");
        return null;
    }
    Account account = optionalAccount.get();
    if (account.getBalance() < amount) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Insufficient balance");
        return null;
    }
    account.withdraw(amount);
    this.accountRepository.save(account);
    log.info("Account Data is {}", account.toString());
    return account;
}

    public Account withdrawFromAccount(@RequestParam double amount, @PathVariable long accountId) {
        Account account = this.accountRepository.findOne(accountId);
        account.withdraw(amount);
        this.accountRepository.save(account);
        log.info("Account Data is {}", account.toString());
        return account;
    }

    @PostMapping("/account/{accountId}/addInterest")
    public Account addInterestToAccount(@RequestParam double amount, @PathVariable long accountId) {
        Account account = this.accountRepository.findOne(accountId);
        account.addInterest();
        this.accountRepository.save(account);
        log.info("Account Data is {}", account.toString());
        return account;
    }

}




