package me.sangmessi.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.sangmessi.account.Account;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ReservationDTO {

    private Long id;
    @NotNull
    private LocalDateTime bookedOn;
    private String description;
    @NotEmpty
    private String name;
    @NotNull
    private Integer numbers;
    @NotNull
    private ReservationStatus reservationStatus;
    @NotNull
    private ReservationType reservationType;

    private Integer deposit;
    private Integer totalFee;

    private Account owner;
}
