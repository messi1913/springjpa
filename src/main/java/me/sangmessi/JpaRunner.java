package me.sangmessi;

import me.sangmessi.account.Account;
import me.sangmessi.store.FoodType;
import me.sangmessi.store.Store;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setEmail("messi1913@gmail.com");
        account.setPassword("rlatkdap1!");
        account.setUserName("상메시");

        Store store = new Store();
        store.setStoreAddress("강남");
        store.setStoreName("교촌치킨");
        store.setFoodType(FoodType.KOREAN);

        account.addStore(store);

        Session sesseion = entityManager.unwrap(Session.class);
        sesseion.save(account);
        sesseion.save(store);

        Thread.sleep(3000);

        account.setUpdater("sangmin10.kim");
        sesseion.save(account);

    }
}
