package me.sangmessi.reservation;

import me.sangmessi.account.Account;
import me.sangmessi.common.BaseControllerTest;
import me.sangmessi.common.MethodDescription;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerTest extends BaseControllerTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Before
    public void initializeTestData() {
        List<Reservation> all = this.reservationRepository.findAllByNameContaining("TEST_");
        this.reservationRepository.deleteAll(all);
        IntStream.range(0, 100).forEach(this::generateReservation);
    }

    @Test
    public void retrieveReservation() throws Exception {
        //Given
        List<Reservation> all = reservationRepository.findAll();
        Reservation reservation = all.get(0);
        long id = reservation.getId();

        //When
        mockMvc.perform(get("/api/reservations/{id}", id)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("bookedOn").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.update-reservation").exists())
                .andDo(document("get-reservation",
                        links(
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("update-reservation").description("Link to modify reservation")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept Type to Server")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Contents Type to Client")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Long.class).description("Identification of reservation"),
                                fieldWithPath("bookedOn").type(LocalDateTime.class).description("Reservation time"),
                                fieldWithPath("name").type(String.class).description("Name of reservation"),
                                fieldWithPath("description").type(String.class).description("Description of reservation"),
                                fieldWithPath("numbers").type(Integer.class).description("The number of participants"),
                                fieldWithPath("reservationStatus").type(ReservationStatus.class).description("Status of reservation"),
                                fieldWithPath("reservationType").type(ReservationType.class).description("Type of reservation"),
                                fieldWithPath("deposit").type(Integer.class).description("Deposit for reservation"),
                                fieldWithPath("totalFee").type(Integer.class).description("Total fee of reservation"),
                                fieldWithPath("_links.self.href").type(String.class).description("Link to retrieve reservation"),
                                fieldWithPath("_links.profile.href").type(String.class).description("Link to profile reservation"),
                                fieldWithPath("_links.update-reservation.href").type(String.class).description("Link to modify this reservation")
                        )
                ));
    }

    @Test
    public void retrieveReservations() throws Exception {
        //Given && When
        mockMvc.perform(get("/api/reservations")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "bookedOn,DESC")
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.reservationDTOList[0].name").exists())
                .andExpect(jsonPath("_embedded.reservationDTOList[0].description").exists())
                .andExpect(jsonPath("_embedded.reservationDTOList[0].description").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.prev").exists())
                .andExpect(jsonPath("_links.next").exists())
                .andDo(document("get-reservations",
                        links(
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("get-reservation").description("Link to retrieve one reservation by id"),
                                linkWithRel("first").description("Link to first page of reservation"),
                                linkWithRel("last").description("Link to last page of reservation"),
                                linkWithRel("prev").description("Link to previous page of reservation"),
                                linkWithRel("next").description("Link to next page of reservation")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept Type to Server")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Contents Type to Client")
                        ),
                        responseFields(
                                fieldWithPath("page.size").type(String.class).description("Size of page"),
                                fieldWithPath("page.totalElements").type(String.class).description("The total number of elements"),
                                fieldWithPath("page.totalPages").type(String.class).description("The total number of pages"),
                                fieldWithPath("page.number").type(String.class).description("The current page number"),
                                fieldWithPath("_embedded.reservationDTOList").type(Collections.class).description("list of User"),
                                fieldWithPath("_embedded.reservationDTOList[].id").type(Long.class).description("Identification of reservation"),
                                fieldWithPath("_embedded.reservationDTOList[].bookedOn").type(LocalDateTime.class).description("Reservation time"),
                                fieldWithPath("_embedded.reservationDTOList[].name").type(String.class).description("Name of reservation"),
                                fieldWithPath("_embedded.reservationDTOList[].description").type(String.class).description("Description of reservation"),
                                fieldWithPath("_embedded.reservationDTOList[].numbers").type(Integer.class).description("The number of participants"),
                                fieldWithPath("_embedded.reservationDTOList[].reservationStatus").type(ReservationStatus.class).description("Status of reservation"),
                                fieldWithPath("_embedded.reservationDTOList[].reservationType").type(ReservationType.class).description("Type of reservation"),
                                fieldWithPath("_embedded.reservationDTOList[].deposit").type(Integer.class).description("Deposit for reservation"),
                                fieldWithPath("_embedded.reservationDTOList[].totalFee").type(Integer.class).description("Total fee of reservation"),

                                fieldWithPath("_links.self.href").type(String.class).description("Link to self"),
                                fieldWithPath("_links.profile.href").type(String.class).description("Link to profile"),
                                fieldWithPath("_links.get-reservation.href").type(String.class).description("Link to retrieve an reservation"),
                                fieldWithPath("_links.first.href").type(String.class).description("Link to first page of reservations"),
                                fieldWithPath("_links.last.href").type(String.class).description("Link to last page of reservations"),
                                fieldWithPath("_links.prev.href").type(String.class).description("Link to previous page of reservations"),
                                fieldWithPath("_links.next.href").type(String.class).description("Link to next page of reservations")
                        )
                ));
    }

    @Test
    @MethodDescription("예약 을 생성 한다.")
    public void createReservation() throws Exception {
        //Given
        Random random = new Random();
        Reservation reservation = this.generateReservation(random.nextInt(10000));

        //When
        mockMvc.perform(post("/api/reservations")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("bookedOn").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.get-reservation").exists())
                .andDo(document("create-reservation",
                        links(
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("get-reservation").description("Link to retrieve a reservation")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept Type to Server")
                        ),
                        requestFields(
                                fieldWithPath("id").type(Long.class).description("Identification of reservation"),
                                fieldWithPath("bookedOn").type(LocalDateTime.class).description("Reservation time"),
                                fieldWithPath("name").type(String.class).description("Name of reservation"),
                                fieldWithPath("description").type(String.class).description("Description of reservation"),
                                fieldWithPath("numbers").type(Integer.class).description("The number of participants"),
                                fieldWithPath("reservationStatus").type(ReservationStatus.class).description("Status of reservation"),
                                fieldWithPath("reservationType").type(ReservationType.class).description("Type of reservation"),
                                fieldWithPath("deposit").type(Integer.class).description("Deposit for reservation"),
                                fieldWithPath("totalFee").type(Integer.class).description("Total fee of reservation"),
                                fieldWithPath("owner").type(Account.class).description("Account Info")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Contents Type to Client")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Long.class).description("Identification of reservation"),
                                fieldWithPath("bookedOn").type(LocalDateTime.class).description("Reservation time"),
                                fieldWithPath("name").type(String.class).description("Name of reservation"),
                                fieldWithPath("description").type(String.class).description("Description of reservation"),
                                fieldWithPath("numbers").type(Integer.class).description("The number of participants"),
                                fieldWithPath("reservationStatus").type(ReservationStatus.class).description("Status of reservation"),
                                fieldWithPath("reservationType").type(ReservationType.class).description("Type of reservation"),
                                fieldWithPath("deposit").type(Integer.class).description("Deposit for reservation"),
                                fieldWithPath("totalFee").type(Integer.class).description("Total fee of reservation"),
                                fieldWithPath("_links.self.href").type(String.class).description("Link to retrieve reservation"),
                                fieldWithPath("_links.profile.href").type(String.class).description("Link to profile reservation"),
                                fieldWithPath("_links.get-reservation.href").type(String.class).description("Link to retrieve this reservation")
                        )
                ));

    }

    @Test
    @MethodDescription("예약 을 수정 한다.")
    @Transactional
    public void modifyReservation() throws Exception {
        //Given
        List<Reservation> all = this.reservationRepository.findAll();
        Reservation reservation = all.get(0);
        reservation.setNumbers(9);
        reservation.setDescription("예약내용 변경됫습니다.");
        //When
        mockMvc.perform(put("/api/reservations")
                .accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("bookedOn").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.get-reservation").exists())
                .andDo(document("update-reservation",
                        links(
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("get-reservation").description("Link to retrieve a reservation")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept Type to Server")
                        ),
                        requestFields(
                                fieldWithPath("id").type(Long.class).description("Identification of reservation"),
                                fieldWithPath("bookedOn").type(LocalDateTime.class).description("Reservation time"),
                                fieldWithPath("name").type(String.class).description("Name of reservation"),
                                fieldWithPath("description").type(String.class).description("Description of reservation"),
                                fieldWithPath("numbers").type(Integer.class).description("The number of participants"),
                                fieldWithPath("reservationStatus").type(ReservationStatus.class).description("Status of reservation"),
                                fieldWithPath("reservationType").type(ReservationType.class).description("Type of reservation"),
                                fieldWithPath("deposit").type(Integer.class).description("Deposit for reservation"),
                                fieldWithPath("totalFee").type(Integer.class).description("Total fee of reservation"),
                                fieldWithPath("owner").type(Account.class).description("Account Info")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Contents Type to Client")
                        ),
                        responseFields(
                                fieldWithPath("id").type(Long.class).description("Identification of reservation"),
                                fieldWithPath("bookedOn").type(LocalDateTime.class).description("Reservation time"),
                                fieldWithPath("name").type(String.class).description("Name of reservation"),
                                fieldWithPath("description").type(String.class).description("Description of reservation"),
                                fieldWithPath("numbers").type(Integer.class).description("The number of participants"),
                                fieldWithPath("reservationStatus").type(ReservationStatus.class).description("Status of reservation"),
                                fieldWithPath("reservationType").type(ReservationType.class).description("Type of reservation"),
                                fieldWithPath("deposit").type(Integer.class).description("Deposit for reservation"),
                                fieldWithPath("totalFee").type(Integer.class).description("Total fee of reservation"),
                                fieldWithPath("_links.self.href").type(String.class).description("Link to retrieve reservation"),
                                fieldWithPath("_links.profile.href").type(String.class).description("Link to profile reservation"),
                                fieldWithPath("_links.get-reservation.href").type(String.class).description("Link to retrieve this reservation")
                        )
                ));

    }

    @Test
    public void deleteReservation() throws Exception {
        //Given
        List<Reservation> all = reservationRepository.findAll();
        Reservation reservation = all.get(0);
        long id = reservation.getId();

        //When
        mockMvc.perform(delete("/api/reservations/{id}", id)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("bookedOn").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.create-reservation").exists())
                .andDo(document("delete-reservation",
                        links(
                                linkWithRel("self").description("Link to self"),
                                linkWithRel("profile").description("Link to profile"),
                                linkWithRel("create-reservation").description("Link to modify reservation")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept Type to Server")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Contents Type to Client")
                        ),
                        requestFields(
                                fieldWithPath("id").type(Long.class).description("Deleted identification of reservation"),
                                fieldWithPath("_links.self.href").type(String.class).description("Link to retrieve reservation"),
                                fieldWithPath("_links.profile.href").type(String.class).description("Link to profile reservation"),
                                fieldWithPath("_links.create-reservation.href").type(String.class).description("Link to make a reservation")
                        )
                ));
    }

    private Reservation generateReservation(int index) {
        Reservation reservation = Reservation.builder()
                .bookedOn(LocalDateTime.of(2019, 6, 11, 18, 10))
                .name("TEST_"+index)
                .description("TEST")
                .numbers(index)
                .reservationStatus(ReservationStatus.PREBOOKED)
                .reservationType(ReservationType.FOOD)
                .build();

        return this.reservationRepository.save(reservation);
    }



}