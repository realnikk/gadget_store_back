package com.nikita.peskov.gadget_store_back.services;

import com.nikita.peskov.gadget_store_back.exceptions.AccountAlreadyExistsException;
import com.nikita.peskov.gadget_store_back.exceptions.AccountNotFoundException;
import com.nikita.peskov.gadget_store_back.models.Account;
import com.nikita.peskov.gadget_store_back.models.Role;
import com.nikita.peskov.gadget_store_back.repositories.AccountRepository;
import com.nikita.peskov.gadget_store_back.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public Account registerAccount(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())){
            throw new AccountAlreadyExistsException(account.getEmail() + " already exists");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        System.out.println(account.getPassword());
        Role accountRole = roleRepository.findByName("ADMIN").get();
        account.setRole(accountRole);
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteAccount(String email) {
        Account theAccount = getAccount(email);
        if (theAccount != null){
            accountRepository.deleteByEmail(email);
        }

    }

    @Override
    public Account getAccount(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }
}

