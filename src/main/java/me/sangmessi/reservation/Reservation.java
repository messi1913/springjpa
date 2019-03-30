package me.sangmessi.reservation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.sangmessi.account.Account;
import me.sangmessi.common.Audit;
import me.sangmessi.common.AuditListener;
import me.sangmessi.common.Auditable;
import me.sangmessi.store.Store;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditListener.class)
@ToString(exclude = {"store", "customer"})
public class Reservation implements Auditable {

    @Embedded
    private Audit audit;

    @GeneratedValue @Id
    private long id;
    @Column(nullable = false)
    private LocalDateTime bookedOn;
    @Column(nullable = false)
    private int memberNumber;

    @ManyToOne
    private Account customer;

    @ManyToOne
    private Store store;


}
