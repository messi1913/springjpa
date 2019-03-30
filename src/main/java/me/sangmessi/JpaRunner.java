package me.sangmessi;

import me.sangmessi.account.Account;
import me.sangmessi.account.AccountDTO;
import me.sangmessi.account.AccountRepository;
import me.sangmessi.reservation.Reservation;
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
//        Account account1 = new Account();
//        account1.setEmail("owen200@gmail.com");
//        account1.setPassword("rlatkdap1!");
//        account1.setUserName("김상민");
//        Store store = new Store();
//        store.setAddress("강남구 상메로2");
//        store.setFoodType(FoodType.CHINESE);
//        store.setName("Chinese");
//        long l = Long.parseLong("01099891913");
//        store.setNumber(l);
//        store.setOwner(account);
//
//        account.addStore(store);
//        entityManager.persist(store);
//        repository.save(account1);

//        Reservation reservation = new Reservation();
//        reservation.setBookedOn(LocalDateTime.of(2019, 4, 1, 18, 0, 0));
//        reservation.setMemberNumber(10);
//        reservation.setStore(first.get());
//        reservation.setCustomer(account2);
//        entityManager.persist(reservation);

        Optional<Account> byId = repository.findByUserName("상메시");
        Account account = byId.orElse(new Account());

        Optional<Account> byId2 = repository.findByUserName("김상민");
        Account account2 = byId2.orElse(new Account());

        Set<Store> stores = account.getStores();
        Optional<Store> first = stores.stream().findFirst();

        Store store = first.orElse(new Store());

        System.out.println(store.getName());

        Reservation reservation = store.getReservations().stream().findFirst().orElse(new Reservation());

        Set<Reservation> reservations = account2.getReservations();
        Optional<Reservation> first1 = reservations.stream().findFirst();

        System.out.println(first1.get().getBookedOn());
        System.out.println(reservation.getMemberNumber());




//
//        // store && account 에 등록
//        store.addReservation(reservation);
//        account.addReservation(reservation);
//        repository.save(account);

//        Optional<Account> byId = repository.findById(3L);
//        Account account = byId.orElse(new Account());










    }
}
