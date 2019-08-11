package me.sangmessi.store;

import lombok.*;
import me.sangmessi.account.Account;
import me.sangmessi.common.Audit;
import me.sangmessi.common.AuditListener;
import me.sangmessi.common.Auditable;
import me.sangmessi.reservation.Reservation;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditListener.class)
@ToString(exclude = {"owner", "reservations"})
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Store implements Auditable {

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long number;
    @Column(nullable = false)
    private String address;
    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account owner;

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setStore(this);
    }

    @Embedded
    @JsonIgnore
    Audit audit;
}
