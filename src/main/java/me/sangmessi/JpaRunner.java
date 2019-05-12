package me.sangmessi;

import me.sangmessi.account.Account;
import me.sangmessi.account.AccountDTO;
import me.sangmessi.account.AccountRepository;
import me.sangmessi.store.FoodType;
import me.sangmessi.store.Store;
import me.sangmessi.store.StoreRepository;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
    StoreRepository storeRep;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Spring boot 수행시 기동 되는 로직 부분.
        Account account = new Account();
        account.setEmail("messi1913@gmail.com");
        account.setUserName("김상민");
        account.setPassword("rlatkdap1");
        account.setMobileNumber("010-9989-1913");

//        repository.save(account);
    }
}
