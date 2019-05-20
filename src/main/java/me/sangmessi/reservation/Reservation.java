package me.sangmessi.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import me.sangmessi.account.Account;
import me.sangmessi.common.Audit;
import me.sangmessi.common.AuditListener;
import me.sangmessi.common.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditListener.class)
@ToString(exclude = {"owner"})
@EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor  @AllArgsConstructor
public class Reservation implements Auditable {

    @Embedded
    @JsonIgnore
    private Audit audit;

    @GeneratedValue @Id
    private long id;
    @Column(nullable = false)
    @NotNull
    private LocalDateTime bookedOn;
    private String description;
    private int numbers;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    @Enumerated(EnumType.ORDINAL)
    private ReservationType reservationType;
    private String name;

    @ManyToOne
    private Account owner;
}
