package me.sangmessi.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.sangmessi.common.Audit;
import me.sangmessi.common.AuditListener;
import me.sangmessi.common.Auditable;
import me.sangmessi.reservation.Reservation;
import me.sangmessi.store.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditListener.class)
@ToString(exclude = {"stores", "reservations"})
@Builder
@NoArgsConstructor  @AllArgsConstructor

public class Account implements Auditable {

    @Id @GeneratedValue
    private long id;
    @NotNull
    @Column(nullable = false, unique = true)
    private String userName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;
    private String mobileNumber;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Store> stores = new HashSet<>();

    public void addStore(Store store) {
        this.stores.add(store);
        store.setOwner(this);
    }

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setUser(this);
    }

    @Embedded
    @JsonIgnore
    private Audit audit;

}
