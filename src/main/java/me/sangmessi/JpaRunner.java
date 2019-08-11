package me.sangmessi;

import me.sangmessi.account.Account;
import me.sangmessi.account.AccountDTO;
import me.sangmessi.account.AccountRepository;
import me.sangmessi.account.AccountRole;
import me.sangmessi.reservation.Reservation;
import me.sangmessi.reservation.ReservationRepository;
import me.sangmessi.reservation.ReservationStatus;
import me.sangmessi.reservation.ReservationType;
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
import java.util.List;
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
    StoreRepository storeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
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
//        Account sangmessi2 = Account.builder()
//                .email("owen200@gmail.com")
//                .password(passwordEncoder.encode("rlatkdap1!"))
//                .userName("김상민")
//                .mobileNumber("010-9919-1913")
//                .roles(Set.of(AccountRole.GENERAL))
//                .build();
//
//        this.repository.save(sangmessi);
//        this.repository.save(sangmessi2);
//
//        Store store = Store.builder()
//                .address("수원시 영통구 123")
//                .foodType(FoodType.KOREAN)
//                .name("교촌치킨")
//                .number(10L)
//                .owner(sangmessi)
//                .build();
//
//        this.storeRepository.save(store);
//
//        Reservation reservation = Reservation.builder()
//                .bookedOn(LocalDateTime.now())
//                .reservationStatus(ReservationStatus.ASK_BOOKING)
//                .reservationType(ReservationType.FOOD)
//                .numbers(10)
//                .name("예약 1")
//                .user(sangmessi2)
//                .store(store)
//                .build();
//
//        reservationRepository.save(reservation);


//        Optional<Account> byId = this.repository.findByEmail("messi1913@gmail.com");
        Account account = entityManager.find(Account.class, 5L);


//        Optional<Account> byId = this.repository.findById(5L);
//        if(byId.isEmpty()){
//            System.err.println("왜 없노 ");
//        }
//        Set<Store> stores = byId.get().getStores();
//        stores.forEach(System.out::println);


    }
}
