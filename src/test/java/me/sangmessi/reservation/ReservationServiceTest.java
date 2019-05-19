package me.sangmessi.reservation;

import me.sangmessi.account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    ReservationService service;

    @Test
    public void createReservation() {
        // Given
        Reservation reservation = Reservation.builder()
                .bookedOn(LocalDateTime.of(2019, 6, 11, 18, 10))
                .description("창가자리 부탁")
                .numbers(10)
                .build();

        // When
        Reservation successRes = this.service.createReservation(reservation);

        //Then
        assertThat(successRes.getId()).isNotNull();
        assertThat(successRes.getAudit().getCreatedBy()).isNotNull();
        assertThat(successRes.getAudit().getCreatedOn()).isNotNull();
    }

    @Test
    public void getReservation() {
        //Given
        Long id = 63L;

        //When
        Reservation reservation = this.service.getReservation(id);

        //Then
        assertThat(reservation.getId()).isEqualTo(63L);
        assertThat(reservation.getBookedOn()).isNotNull();
    }

    @Test
    public void updateReservation() throws Exception{
        //Given
        Reservation reservation = this.service.getReservation(63L);
        reservation.setNumbers(9);

        //when
        Reservation reservation1 = this.service.updateReservation(reservation);

        //Then
        assertThat(reservation1.getId()).isNotNull();
        assertThat(reservation1.getAudit().getUpdatedBy()).isNotNull();
        assertThat(reservation1.getAudit().getUpdatedOn()).isNotNull();

    }

    @Test
//    @Transactional
    public void deleteReservation() {
        Long id = 63L;
        this.service.deleteReservation(id);

        Reservation reservation = this.service.getReservation(id);
        assertThat(reservation.getBookedOn()).isNull();
        assertThat(reservation.getAudit()).isNull();

    }

}