package me.sangmessi.account;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class AccountResource extends Resource<AccountDTO> {
    public AccountResource(AccountDTO content, Link... links) {
        super(content, links);
    }
}
