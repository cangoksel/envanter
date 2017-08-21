package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TesisBilgisi.
 */
@Entity
@Table(name = "tesis_bilgisi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TesisBilgisi implements Serializable {

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
    @Column(name = "uretim_alani", nullable = false)
    private String uretimAlani;

    @ManyToOne
    private Firma firma;

    @OneToOne
    @JoinColumn(unique = true)
    private Ulke bulunduguUlke;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public TesisBilgisi deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public TesisBilgisi version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUretimAlani() {
        return uretimAlani;
    }

    public TesisBilgisi uretimAlani(String uretimAlani) {
        this.uretimAlani = uretimAlani;
        return this;
    }

    public void setUretimAlani(String uretimAlani) {
        this.uretimAlani = uretimAlani;
    }

    public Firma getFirma() {
        return firma;
    }

    public TesisBilgisi firma(Firma firma) {
        this.firma = firma;
        return this;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public Ulke getBulunduguUlke() {
        return bulunduguUlke;
    }

    public TesisBilgisi bulunduguUlke(Ulke ulke) {
        this.bulunduguUlke = ulke;
        return this;
    }

    public void setBulunduguUlke(Ulke ulke) {
        this.bulunduguUlke = ulke;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TesisBilgisi tesisBilgisi = (TesisBilgisi) o;
        if (tesisBilgisi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tesisBilgisi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TesisBilgisi{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", uretimAlani='" + getUretimAlani() + "'" +
            "}";
    }
}
