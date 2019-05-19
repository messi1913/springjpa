package me.sangmessi.reservation;

import me.sangmessi.common.BaseControllerTest;
import me.sangmessi.common.MethodDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerTest extends BaseControllerTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    @MethodDescription("예약 을 생성 한다.")
    public void createReservation() throws Exception {
        //Given
        Reservation reservation = this.generateReservation();

        //When
        mockMvc.perform(post("/api/reservations")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andDo(print())
                .andExpect(status().isOk());

        //Then



    }

    private Reservation generateReservation() {
        Reservation reservation = Reservation.builder()
                .bookedOn(LocalDateTime.of(2019, 6, 11, 18, 10))
                .description("창가자리 부탁")
                .numbers(10)
                .reservationStatus(ReservationStatus.ASK_BOOKING)
                .reservtionType(ReservationType.FOOD)
                .build();

        return this.reservationRepository.save(reservation);
    }


}