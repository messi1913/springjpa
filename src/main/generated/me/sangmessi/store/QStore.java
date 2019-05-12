package me.sangmessi.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 1409356538L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final StringPath address = createString("address");

    public final me.sangmessi.common.QAudit audit;

    public final EnumPath<FoodType> foodType = createEnum("foodType", FoodType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> number = createNumber("number", Long.class);

    public final me.sangmessi.account.QAccount owner;

    public final SetPath<me.sangmessi.reservation.Reservation, me.sangmessi.reservation.QReservation> reservations = this.<me.sangmessi.reservation.Reservation, me.sangmessi.reservation.QReservation>createSet("reservations", me.sangmessi.reservation.Reservation.class, me.sangmessi.reservation.QReservation.class, PathInits.DIRECT2);

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.audit = inits.isInitialized("audit") ? new me.sangmessi.common.QAudit(forProperty("audit")) : null;
        this.owner = inits.isInitialized("owner") ? new me.sangmessi.account.QAccount(forProperty("owner"), inits.get("owner")) : null;
    }

}

