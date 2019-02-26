package me.sangmessi.store;

import lombok.Data;
import me.sangmessi.account.Account;

import javax.persistence.*;

@Data
@Entity
public class Store {

    @Id @GeneratedValue
    private long id;
    private String storeName;
    private int storeNumber;
    private String storeAddress;
    @Enumerated
    private FoodType foodType;
    @ManyToOne
    private Account owner;



}
