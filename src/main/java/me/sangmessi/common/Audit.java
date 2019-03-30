package me.sangmessi.common;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
public class Audit {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String createdBy;
    private String updatedBy;

}
