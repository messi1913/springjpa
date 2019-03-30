package me.sangmessi.store;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.sangmessi.account.Account;
import me.sangmessi.common.Audit;
import me.sangmessi.common.AuditListener;
import me.sangmessi.common.Auditable;
import me.sangmessi.reservation.Reservation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = { "reservations"})
@EntityListeners(AuditListener.class)
@ToString(exclude = {"owner", "reservations"})
public class Store implements Auditable {

    @Embedded
    Audit audit;

    @Id @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long number;
    @Column(nullable = false)
    private String address;
    @Enumerated
    private FoodType foodType;
    @ManyToOne
    private Account owner;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setStore(this);
    }



}
