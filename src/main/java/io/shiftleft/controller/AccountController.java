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
    public Account createAccount(Account account) {
        this.accountRepository.save(account);
        log.info("Account Data is {}", account.toString());
        return account;
    }

	@GetMapping("/account/{accountId}")
	public Account getAccount(@PathVariable long accountId) {
	    log.info("Account Data is {}", this.accountRepository.findOne(1l).toString());
	    return this.accountRepository.findOne(accountId);
	}

    public Account getAccount(@PathVariable long accountId) {
        log.info("Account Data is {}", this.accountRepository.findOne(1l).toString());
        return this.accountRepository.findOne(accountId);
    }

	@PostMapping("/account/{accountId}/deposit")
    public Account depositIntoAccount(@RequestParam double amount, @PathVariable long accountId) {
        // Retrieve the currently authenticated user
        User currentUser = getCurrentUser();
        
        // Check if the current user has permission to access the account
        if (!currentUser.hasPermissionToAccessAccount(accountId)) {
            throw new AccessDeniedException("User does not have permission to access this account.");
        }
        
        Account account = this.accountRepository.findOne(accountId);
        log.info("Account Data is {}", account.toString());
        account.deposit(amount);
        this.accountRepository.save(account);
        return account;
    }

    public Account depositIntoAccount(@RequestParam double amount, @PathVariable long accountId) {
        Account account = this.accountRepository.findOne(accountId);
        log.info("Account Data is {}", account.toString());
        account.deposit(amount);
        this.accountRepository.save(account);
        return account;
    }

    @PostMapping("/account/{accountId}/withdraw")
    public Account withdrawFromAccount(@RequestParam double amount, @PathVariable long accountId) {
        Account account = this.accountRepository.findOne(accountId);
        account.withdraw(amount);
        this.accountRepository.save(account);
        log.info("Account Data is {}", account.toString());
        return account;
    }

	@PostMapping("/account/{accountId}/addInterest")
	public Account addInterestToAccount(@RequestParam double amount, @PathVariable long accountId) {
	    // Check if the user has permission to access the account
	    if (!userHasPermissionToAccessAccount(accountId)) {
	        throw new AccessDeniedException("User does not have permission to access this account.");
	    }
	    
	    Account account = this.accountRepository.findOne(accountId);
	    account.addInterest();
	    this.accountRepository.save(account);
	    log.info("Account Data is {}", account.toString());
	    return account;
	}

	private boolean userHasPermissionToAccessAccount(long accountId) {
	    // Logic to determine if the current user has permission to access the account
	    // This is a placeholder for actual implementation
	    return true; // Assuming the user has permission for this example
	}

    public Account addInterestToAccount(@RequestParam double amount, @PathVariable long accountId) {
        Account account = this.accountRepository.findOne(accountId);
        account.addInterest();
        this.accountRepository.save(account);
        log.info("Account Data is {}", account.toString());
        return account;
    }

}



