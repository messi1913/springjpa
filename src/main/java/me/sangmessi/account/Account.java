package me.sangmessi.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.sangmessi.store.Store;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "stores")
public class Account {

    @Id @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    private String userName;
    private String password;
    private String email;
    private int mobileNumber;
    @Temporal(TemporalType.TIME)
    private Date created;
    private String creator;
    @Temporal(TemporalType.TIME)
    private Date updated;
    private String updater;

    @OneToMany(mappedBy = "owner")
    private Set<Store> stores = new HashSet<>();

    public void addStore(Store store) {
        this.stores.add(store);
        store.setOwner(this);
    }


}
