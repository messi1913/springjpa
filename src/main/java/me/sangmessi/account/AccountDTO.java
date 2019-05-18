package me.sangmessi.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.sangmessi.store.Store;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long id;
    private String userName;
    private String email;
    private String mobileNumber;
    private Set<Store> stores;
}
