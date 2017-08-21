package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FaaliyetKodu.
 */
@Entity
@Table(name = "faaliyet_kodu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FaaliyetKodu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @NotNull
    @Column(name = "version", nullable = false)
    private Long version;

    @NotNull
    @Column(name = "faaliyet_kodu", nullable = false)
    private String faaliyetKodu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public FaaliyetKodu deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public FaaliyetKodu version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getFaaliyetKodu() {
        return faaliyetKodu;
    }

    public FaaliyetKodu faaliyetKodu(String faaliyetKodu) {
        this.faaliyetKodu = faaliyetKodu;
        return this;
    }

    public void setFaaliyetKodu(String faaliyetKodu) {
        this.faaliyetKodu = faaliyetKodu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FaaliyetKodu faaliyetKodu = (FaaliyetKodu) o;
        if (faaliyetKodu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faaliyetKodu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FaaliyetKodu{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", faaliyetKodu='" + getFaaliyetKodu() + "'" +
            "}";
    }
}
