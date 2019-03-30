package me.sangmessi.common;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AuditListener {

    @PrePersist
    public void setCreatedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();
        if(Objects.isNull(audit)) {
            audit = new Audit();
            auditable.setAudit(audit);
        }
        audit.setCreatedOn(LocalDateTime.now());
        audit.setCreatedBy("Created User");
    }

    @PreUpdate
    public void setUpdatedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();

        audit.setUpdatedOn(LocalDateTime.now());
        audit.setUpdatedBy("Updated User");
    }
}
