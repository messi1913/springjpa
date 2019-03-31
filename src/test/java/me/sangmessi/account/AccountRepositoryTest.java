package me.sangmessi.account;

import me.sangmessi.reservation.Reservation;
import me.sangmessi.store.FoodType;
import me.sangmessi.store.Store;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @PersistenceContext
    EntityManager entityManager;

//    @Autowired
//    ModelMapper modelMapper;

    @Test
    public void validationCheck() {
        Reservation reservation = new Reservation();
        reservation.setMemberNumber(1);
        reservation.setBookedOn(LocalDateTime.now());
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Reservation>> validate = validator.validate(reservation);
        assertThat(validate.size()).isEqualTo(0);
    }

    @Test
    public void jpaTest() {
        ModelMapper modelMapper = new ModelMapper();
        Account account1 = new Account();
        account1.setEmail("messi1913@gmail.com");
        account1.setPassword("rlatkdap1!");
        account1.setUserName("상메시");


        Store store = new Store();
        store.setOwner(account1);
        store.setAddress("강남구 상메로2");
        store.setFoodType(FoodType.CHINESE);
        store.setName("Chinese");
        long l = Long.parseLong("01099891913");
        store.setNumber(l);

//        account1.addStore(store);
        accountRepository.save(account1);

        Account account = new Account();
        account.setEmail("owen200@gmail.com");
        account.setPassword("rlatkdap1!");
        account.setUserName("김상민");

        Reservation reservation = new Reservation();
        reservation.setBookedOn(LocalDateTime.of(2019, 4, 1, 18, 0, 0));
        reservation.setMemberNumber(10);
        reservation.setStore(store);
        reservation.setCustomer(account);

        accountRepository.save(account);

        entityManager.persist(reservation);

        account.addReservation(reservation);
        accountRepository.save(account);

        System.out.println("get reservation info");
        System.out.println("Reservation 객체 : "+account.getReservations());
        System.out.println("Reservation 정보 : ");
        account.getReservations().forEach(k -> System.out.println(k.getBookedOn()+","+k.getMemberNumber()));














    }
}
