package me.sangmessi.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 1536302546L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccount account = new QAccount("account");

    public final me.sangmessi.common.QAudit audit;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> mobileNumber = createNumber("mobileNumber", Long.class);

    public final StringPath password = createString("password");

    public final SetPath<me.sangmessi.reservation.Reservation, me.sangmessi.reservation.QReservation> reservations = this.<me.sangmessi.reservation.Reservation, me.sangmessi.reservation.QReservation>createSet("reservations", me.sangmessi.reservation.Reservation.class, me.sangmessi.reservation.QReservation.class, PathInits.DIRECT2);

    public final SetPath<me.sangmessi.store.Store, me.sangmessi.store.QStore> stores = this.<me.sangmessi.store.Store, me.sangmessi.store.QStore>createSet("stores", me.sangmessi.store.Store.class, me.sangmessi.store.QStore.class, PathInits.DIRECT2);

    public final StringPath userName = createString("userName");

    public QAccount(String variable) {
        this(Account.class, forVariable(variable), INITS);
    }

    public QAccount(Path<? extends Account> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccount(PathMetadata metadata, PathInits inits) {
        this(Account.class, metadata, inits);
    }

    public QAccount(Class<? extends Account> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.audit = inits.isInitialized("audit") ? new me.sangmessi.common.QAudit(forProperty("audit")) : null;
    }

}

