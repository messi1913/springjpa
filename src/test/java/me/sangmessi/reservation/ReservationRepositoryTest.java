package me.sangmessi.reservation;

import me.sangmessi.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    public void test() {
        List<Reservation> allByCustomerId = reservationRepository.findAllByCustomerId(6);
        assertThat(allByCustomerId.size()).isEqualTo(1);

        Reservation reservation = allByCustomerId.get(0);
        Account customer = reservation.getCustomer();
        assertThat(customer.getUserName()).isEqualTo("김상민");

    }
}
