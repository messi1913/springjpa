package me.sangmessi.reservation;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ReservationResource extends Resource<ReservationDTO> {

    public ReservationResource(ReservationDTO content, Link... links) {
        super(content, links);
    }
}
