package me.sangmessi.reservation;

import lombok.RequiredArgsConstructor;
import me.sangmessi.account.AccountController;
import me.sangmessi.account.AccountDTO;
import me.sangmessi.account.AccountResource;
import me.sangmessi.account.AccountService;
import me.sangmessi.exception.NotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/reservations", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ReservationController {

    private final AccountService service;

    @GetMapping("/{id}")
    public ResponseEntity getReservation(@PathVariable Long id) {
        try {
            AccountDTO accountDTO = service.getUser(id);
            AccountResource resource = new AccountResource(accountDTO);
            resource.add(linkTo(AccountController.class).slash(id).withSelfRel());
            resource.add(new Link("/docs/reservation.html#resources-account-get").withRel("profile"));
            resource.add(linkTo(AccountController.class).withRel("update-reservation"));
            return ResponseEntity.ok(resource);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
