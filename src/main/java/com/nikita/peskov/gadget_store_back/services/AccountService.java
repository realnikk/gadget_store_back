package com.nikita.peskov.gadget_store_back.services;

import com.nikita.peskov.gadget_store_back.models.Account;
import com.nikita.peskov.gadget_store_back.models.User;

import java.util.List;

public interface AccountService {
    Account registerAccount(Account account);
    List<Account> getAccounts();
    void deleteAccount(String email);
    Account getAccount(String email);
}
