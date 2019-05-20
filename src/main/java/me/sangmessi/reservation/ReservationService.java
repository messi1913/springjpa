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

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        Reservation savedReservation = repository.save(reservation);
        return modelMapper.map(savedReservation, ReservationDTO.class);
    }

    public ReservationDTO getReservation(Long id) {
        Optional<Reservation> optionalReservation = repository.findById(id);
        if(optionalReservation.isEmpty())
            return null;

        return modelMapper.map(optionalReservation.get(), ReservationDTO.class);
    }

    public ReservationDTO updateReservation(ReservationDTO reservationDTO) throws NotFoundException {
        Optional<Reservation> optionalReservation = this.repository.findById(reservationDTO.getId());
        Reservation reservation = optionalReservation.orElseThrow(() -> new NotFoundException("There is no reservation to modify"));
        modelMapper.map(reservationDTO, reservation);
        Reservation updatedReservation = this.repository.save(reservation);
        return modelMapper.map(updatedReservation, ReservationDTO.class);
    }

    public ReservationDTO deleteReservation(Long id) {
        this.repository.deleteById(id);
        return ReservationDTO.builder().id(id).build();
    }

    public Page<ReservationDTO> getReservations(Pageable pageable) {
        Page<Reservation> reservations = this.repository.findAll(pageable);
        return reservations.map(this::to);
    }

    private ReservationDTO to(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }
}
