package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActKodu.
 */
@Entity
@Table(name = "act_kodu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActKodu implements Serializable {

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

    public ActKodu deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public ActKodu version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getKod() {
        return kod;
    }

    public ActKodu kod(String kod) {
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
        ActKodu actKodu = (ActKodu) o;
        if (actKodu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actKodu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActKodu{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", kod='" + getKod() + "'" +
            "}";
    }
}
