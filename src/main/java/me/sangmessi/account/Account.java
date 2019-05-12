package me.sangmessi.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.sangmessi.common.Audit;
import me.sangmessi.common.AuditListener;
import me.sangmessi.common.Auditable;
import me.sangmessi.reservation.Reservation;
import me.sangmessi.store.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditListener.class)
@ToString(exclude = {"stores", "reservations"})
public class Account implements Auditable {

    @Embedded
    private Audit audit;

    @Id @GeneratedValue
    private long id;
    @NotNull
    @Column(nullable = false, unique = true)
    private String userName;
    private String password;
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;
    private String mobileNumber;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Store> stores = new HashSet<>();

    public void addStore(Store store) {
        this.stores.add(store);
        store.setOwner(this);
    }

    @OneToMany(mappedBy = "customer")
    private Set<Reservation> reservations = new HashSet<>();

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setCustomer(this);
    }


}
