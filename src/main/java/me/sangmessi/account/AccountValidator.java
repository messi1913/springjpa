package me.sangmessi.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AccountValidator {

    private final AccountService service;

    public void validateSave(Account account, Errors errors) {
        if(!service.existsUser(account)) {
            errors.rejectValue("Not found", "Can not find this account!!");
        }
    }


}
