package me.sangmessi.reservation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = -300970736L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final me.sangmessi.common.QAudit audit;

    public final DateTimePath<java.time.LocalDateTime> bookedOn = createDateTime("bookedOn", java.time.LocalDateTime.class);

    public final me.sangmessi.account.QAccount customer;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> memberNumber = createNumber("memberNumber", Integer.class);

    public final me.sangmessi.store.QStore store;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.audit = inits.isInitialized("audit") ? new me.sangmessi.common.QAudit(forProperty("audit")) : null;
        this.customer = inits.isInitialized("customer") ? new me.sangmessi.account.QAccount(forProperty("customer"), inits.get("customer")) : null;
        this.store = inits.isInitialized("store") ? new me.sangmessi.store.QStore(forProperty("store"), inits.get("store")) : null;
    }

}

