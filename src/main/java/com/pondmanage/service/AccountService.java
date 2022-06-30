package com.pondmanage.service;

import com.pondmanage.dto.AccountDTO;
import com.pondmanage.dto.PasswordDTO;
import com.pondmanage.model.Account;

public interface AccountService {
    void register(AccountDTO accountDTO);
    void changePassword(PasswordDTO passwordDTO);
    Account getCurrentAccount();
    boolean deleteAccount(AccountDTO accountDTO);
}
