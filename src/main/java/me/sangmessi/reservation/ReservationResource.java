package me.sangmessi.reservation;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ReservationResource extends Resource<Reservation> {

    public ReservationResource(Reservation content, Link... links) {
        super(content, links);
    }
}
