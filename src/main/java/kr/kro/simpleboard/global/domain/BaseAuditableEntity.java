package kr.kro.simpleboard.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditableEntity extends BaseTimeEntity {


    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    protected Long createdBy;

    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    protected Long updatedBy;

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}