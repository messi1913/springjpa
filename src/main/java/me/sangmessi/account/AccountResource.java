package me.sangmessi.account;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

class AccountResource extends Resource<AccountDTO> {
    AccountResource(AccountDTO content, Link... links) {
        super(content, links);
    }
}
