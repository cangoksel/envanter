package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TibbiCihazTehlikeSinifi.
 */
@Entity
@Table(name = "tibbi_cihaz_tehlike_sinifi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TibbiCihazTehlikeSinifi implements Serializable {

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
    @Column(name = "sinif", nullable = false)
    private String sinif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public TibbiCihazTehlikeSinifi deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public TibbiCihazTehlikeSinifi version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getSinif() {
        return sinif;
    }

    public TibbiCihazTehlikeSinifi sinif(String sinif) {
        this.sinif = sinif;
        return this;
    }

    public void setSinif(String sinif) {
        this.sinif = sinif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi = (TibbiCihazTehlikeSinifi) o;
        if (tibbiCihazTehlikeSinifi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tibbiCihazTehlikeSinifi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TibbiCihazTehlikeSinifi{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", sinif='" + getSinif() + "'" +
            "}";
    }
}
