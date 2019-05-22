package me.sangmessi.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserName(String userName);

    List<Account> findByUserNameContaining(String userName);

    Optional<Account> findByEmail(String username);
}
