package me.sangmessi;

import me.sangmessi.account.Account;
import me.sangmessi.account.AccountDTO;
import me.sangmessi.account.AccountRepository;
import me.sangmessi.account.AccountRole;
import me.sangmessi.store.FoodType;
import me.sangmessi.store.Store;
import me.sangmessi.store.StoreRepository;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    AccountRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Spring boot 수행시 기동 되는 로직 부분.
//        Account sangmessi = Account.builder()
//                .email("messi1913@gmail.com")
//                .password(passwordEncoder.encode("rlatkdap1!"))
//                .userName("sangmessi")
//                .mobileNumber("010-9989-1913")
//                .roles(Set.of(AccountRole.ADMIN, AccountRole.GENERAL))
//                .build();
//
//        this.repository.save(sangmessi);


    }
}
