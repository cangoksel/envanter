package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MkysKodu.
 */
@Entity
@Table(name = "mkys_kodu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MkysKodu implements Serializable {

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
    @Column(name = "kod", nullable = false)
    private String kod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public MkysKodu deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public MkysKodu version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getKod() {
        return kod;
    }

    public MkysKodu kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MkysKodu mkysKodu = (MkysKodu) o;
        if (mkysKodu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mkysKodu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MkysKodu{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", kod='" + getKod() + "'" +
            "}";
    }
}
