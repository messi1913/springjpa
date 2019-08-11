package me.sangmessi.reservation;

        import org.springframework.data.jpa.repository.JpaRepository;

        import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(long ownerId);

    List<Reservation> findAllByNameContaining(String name);
}
