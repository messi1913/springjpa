package me.sangmessi.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import me.sangmessi.account.Account;
import me.sangmessi.common.Audit;
import me.sangmessi.common.AuditListener;
import me.sangmessi.common.Auditable;
import me.sangmessi.store.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditListener.class)
@ToString(exclude = {"user", "store"})
@EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor  @AllArgsConstructor
public class Reservation implements Auditable {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime bookedOn;
    private String description;
    private String name;
    private int numbers;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;

    private Integer deposit;
    private Integer totalFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Embedded
    @JsonIgnore
    private Audit audit;
}
