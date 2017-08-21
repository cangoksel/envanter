package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Il.
 */
@Entity
@Table(name = "il")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Il implements Serializable {

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
    @Column(name = "jhi_label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "plaka_no", nullable = false)
    private String plakaNo;

    @OneToOne
    @JoinColumn(unique = true)
    private Ulke ulke;

    @OneToOne
    @JoinColumn(unique = true)
    private Bolge bolge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Il deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public Il version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getLabel() {
        return label;
    }

    public Il label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlakaNo() {
        return plakaNo;
    }

    public Il plakaNo(String plakaNo) {
        this.plakaNo = plakaNo;
        return this;
    }

    public void setPlakaNo(String plakaNo) {
        this.plakaNo = plakaNo;
    }

    public Ulke getUlke() {
        return ulke;
    }

    public Il ulke(Ulke ulke) {
        this.ulke = ulke;
        return this;
    }

    public void setUlke(Ulke ulke) {
        this.ulke = ulke;
    }

    public Bolge getBolge() {
        return bolge;
    }

    public Il bolge(Bolge bolge) {
        this.bolge = bolge;
        return this;
    }

    public void setBolge(Bolge bolge) {
        this.bolge = bolge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Il il = (Il) o;
        if (il.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), il.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Il{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", label='" + getLabel() + "'" +
            ", plakaNo='" + getPlakaNo() + "'" +
            "}";
    }
}
