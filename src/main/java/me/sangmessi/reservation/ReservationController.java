package me.sangmessi.reservation;

import lombok.RequiredArgsConstructor;
import me.sangmessi.common.ErrorResource;
import me.sangmessi.common.MethodDescription;
import me.sangmessi.exception.NotFoundException;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/reservations", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @GetMapping("/{id}")
    @MethodDescription("ID 를 통해서 예약 결재건 한건을 조회한다.")
    public ResponseEntity retrieveReservation(@PathVariable Long id) {
        ReservationDTO reservation = this.service.getReservation(id);
        if(Objects.isNull(reservation))
            return ResponseEntity.badRequest().body("We can't find this reservation");

        var resource = new ReservationResource(reservation);
        resource.add(linkTo(ReservationController.class).slash(reservation.getId()).withSelfRel());
        resource.add(linkTo(ReservationController.class).withRel("update-reservation"));
        resource.add(new Link("/docs/reservation.html#resources-reservation-get").withRel("profile"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    @MethodDescription("Paging 을 통해서 예약결재건 전체를 조회한다.")
    public ResponseEntity retrieveReservations(Pageable pageable, PagedResourcesAssembler<ReservationDTO> assembler) {
        Page<ReservationDTO> reservations = this.service.getReservations(pageable);
        var resource = assembler.toResource(reservations, r -> new ReservationResource(r));
//        resource.add(linkTo(ReservationController.class).withSelfRel());
        resource.add(linkTo(ReservationController.class).withRel("get-reservation"));
        resource.add(new Link("/docs/reservation.html#resources-reservation-list").withRel("profile"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    @MethodDescription("예약 신청을 통해서 예약을 생성하는 API")
    public ResponseEntity createReservation(@RequestBody @Valid ReservationDTO reservationDTO, Errors errors) {
        if(errors.hasErrors())
            return badRequest(errors);

        ReservationDTO reservation = this.service.createReservation(reservationDTO);
        var resource = new ReservationResource(reservation);
        resource.add(linkTo(ReservationController.class).withSelfRel());
        resource.add(linkTo(ReservationController.class).withRel("get-reservation"));
        resource.add(new Link("/docs/reservation.html#resources-reservation-create").withRel("profile"));
        return ResponseEntity.ok(resource);
    }

    @PutMapping
    @MethodDescription("예약 변경을 처리하는 API")
    public ResponseEntity updateReservation(@RequestBody ReservationDTO reservationDTO, Errors errors) {
        if(errors.hasErrors())
            return badRequest(errors);

        try {
            ReservationDTO updateReservation = this.service.updateReservation(reservationDTO);
            var resource = new ReservationResource(updateReservation);
            resource.add(linkTo(ReservationController.class).withSelfRel());
            resource.add(linkTo(ReservationController.class).withRel("get-reservation"));
            resource.add(new Link("/docs/reservation.html#resources-reservation-update").withRel("profile"));
            return ResponseEntity.ok(resource);

        } catch (NotFoundException e) {
            errors.reject("Not Found", e.getMessage());
            return badRequest(errors);
        }
    }

    @DeleteMapping("/{id}")
    @MethodDescription("예약 취소를 통한 예약 내역 삭제를 하는 API")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        ReservationDTO reservationDTO = this.service.deleteReservation(id);
        var resource = new ReservationResource(reservationDTO);
        resource.add(linkTo(ReservationController.class).slash(reservationDTO.getId()).withSelfRel());
        resource.add(linkTo(ReservationController.class).withRel("create-reservation"));
        resource.add(new Link("/docs/reservation.html#resources-reservation-delete").withRel("profile"));
        return ResponseEntity.ok(resource);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorResource(errors));
    }



}
