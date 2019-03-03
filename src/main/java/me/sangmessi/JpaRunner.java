package me.sangmessi;

import me.sangmessi.account.Account;
import me.sangmessi.account.AccountDTO;
import me.sangmessi.account.AccountRepository;
import me.sangmessi.store.FoodType;
import me.sangmessi.store.Store;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
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
    ModelMapper modelMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Account account = new Account();
//        account.setEmail("messi1913@gmail.com");
//        account.setPassword("rlatkdap1!");
//        account.setUserName("상메시");
//
//        Store store = new Store();
//        store.setStoreAddress("강남");
//        store.setStoreName("교촌치킨");
//        store.setFoodType(FoodType.KOREAN);
//
//        account.addStore(store);
//
//        Session session = entityManager.unwrap(Session.class);
//        session.save(account);
//        session.save(store);
//
//        Account cachedObject = session.load(Account.class, account.getId());
//        cachedObject.setUpdater("sangmessi1");
//        System.out.println("===============================");
//        System.out.println(cachedObject.getId()+" : "+cachedObject.getUserName());

        Account account1 = repository.findById(1l).get();
//        account1.setEmail("messi1913@gmail.com");
//        account1.setPassword("rlatkdap1!");
//        account1.setUserName("상메시2");
//
//        account1.addStore(store);

        Account save = repository.save(account1);
        save.setUpdater("sangmessi");

        Optional<Account> byId = repository.findById(save.getId());
        Account account = byId.get();
        System.out.println("Updater : "+account.getUpdater());

        account.setUpdater("test2");

        System.out.println("Origin Updater : "+save.getUpdater());

        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setUpdater("당연히 안바뀌겠지??");

        System.out.println(account.getUpdater()+" : "+accountDTO.getUpdater());



    }
}
