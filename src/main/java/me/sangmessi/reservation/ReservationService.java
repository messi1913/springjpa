package me.sangmessi.reservation;

import lombok.RequiredArgsConstructor;
import me.sangmessi.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final ModelMapper modelMapper;

    public Reservation createReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    public Reservation getReservation(Long id) {
        Optional<Reservation> optionalReservation = repository.findById(id);
        return optionalReservation.orElse(new Reservation());
    }

    public Reservation updateReservation(Reservation reservation) throws NotFoundException {
        Optional<Reservation> optionalReservation = this.repository.findById(reservation.getId());
        if(optionalReservation.isEmpty())
            throw new NotFoundException("There is no reservation");

        Reservation reservationFromDB = optionalReservation.get();
        modelMapper.map(reservation, reservationFromDB);

        return this.repository.save(reservation);
    }

    public void deleteReservation(Long id) {
        this.repository.deleteById(id);
    }

    public Page<Reservation> getReservations(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
