package by.es.ejb.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alexey.Koyro
 */
@MappedSuperclass
public abstract class PersistenceEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "PERSISTENCE_SEQUENCE")
    @GeneratedValue(generator = "PERSISTENCE_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + (id != null ? id.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        PersistenceEntity that = (PersistenceEntity) obj;

        return id != null && id.equals(that.getId());
    }
}
