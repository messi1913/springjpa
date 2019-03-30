package me.sangmessi.common;

public interface Auditable {
    Audit getAudit();
    void setAudit(Audit audit);
}
