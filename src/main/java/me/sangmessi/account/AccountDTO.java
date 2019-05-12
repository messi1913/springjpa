package me.sangmessi.account;

import lombok.Builder;
import lombok.Data;
import me.sangmessi.store.Store;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class AccountDTO {

    private String userName;
    private String email;
    private String mobileNumber;
    private Set<Store> stores = new HashSet<>();
}
