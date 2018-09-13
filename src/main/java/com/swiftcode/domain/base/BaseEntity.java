package com.swiftcode.domain.base;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author chen
 */
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<T>> {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    /**Returns the identity of this entity object.*/
    public Long getId(){return id;}

    /**
     * Entities compare by identity, not by attributes.
     *
     * @param that The other entity of the same type
     * @return true if the identities are the same, regardless of the other attributes.
     * @throws IllegalStateException one of the entities does not have the identity attribute set.
     */
    public boolean sameIdentityAs(final T that){
        return this.equals(that);
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof BaseEntity)) {
            return false;
        }
        final BaseEntity<?> that = (BaseEntity<?>) object;
        _checkIdentity(this);
        _checkIdentity(that);
        return this.id.equals(that.getId());
    }

    /**
     * Checks the passed entity, if it has an identity. It gets an identity only by saving.
     * @throws IllegalStateException the passed entity does not have the identity attribute set.
     */
    private void _checkIdentity(final BaseEntity<?> entity) {
        if(entity.getId()==null){
            throw new IllegalStateException("Identity missing in entity: " + entity);
        }
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
