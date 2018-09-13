package com.swiftcode.domain.base;

import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

/**
 * @author chen
 */
@MappedSuperclass
public class AbstractEntity extends BaseEntity<AbstractEntity> {
    private static final Integer ENTITY_ENABLE = 0;
    private static final Integer ENTITY_DISABLE = 1;

    @Version
    protected Long version;
    @Column(name = "created_date", nullable = false)
    protected LocalDateTime createdDate;
    @Column(name = "updated_date", nullable = false)
    protected LocalDateTime updatedDate;
    @Column(name = "is_disable", nullable = false)
    protected Integer isDisable = ENTITY_ENABLE;

    public Long version() {
        return version;
    }

    public LocalDateTime createdDate() {
        return createdDate;
    }

    public LocalDateTime updatedDate() {
        return updatedDate;
    }

    public boolean isDisable() {
        return isDisable.equals(ENTITY_DISABLE);
    }

    public void toEnable() {
        isDisable = ENTITY_ENABLE;
    }

    public void toDisable() {
        isDisable = ENTITY_DISABLE;
    }

    @PrePersist
    protected void prePersist() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

    @PreRemove
    protected void preRemove() {
    }

    @PostPersist
    protected void postPersist() {
    }

    @PostUpdate
    protected void postUpdate() {
    }

    @PostRemove
    protected void postRemove() {
    }

    @PostLoad
    protected void postLoad() {
    }
}
