package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Adres.
 */
@Entity
@Table(name = "adres")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Adres implements Serializable {

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
    @Column(name = "acik_adres", nullable = false)
    private String acikAdres;

    @OneToOne
    @JoinColumn(unique = true)
    private Il il;

    @OneToOne
    @JoinColumn(unique = true)
    private Ulke ulke;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Adres deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public Adres version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAcikAdres() {
        return acikAdres;
    }

    public Adres acikAdres(String acikAdres) {
        this.acikAdres = acikAdres;
        return this;
    }

    public void setAcikAdres(String acikAdres) {
        this.acikAdres = acikAdres;
    }

    public Il getIl() {
        return il;
    }

    public Adres il(Il il) {
        this.il = il;
        return this;
    }

    public void setIl(Il il) {
        this.il = il;
    }

    public Ulke getUlke() {
        return ulke;
    }

    public Adres ulke(Ulke ulke) {
        this.ulke = ulke;
        return this;
    }

    public void setUlke(Ulke ulke) {
        this.ulke = ulke;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Adres adres = (Adres) o;
        if (adres.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adres.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Adres{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", acikAdres='" + getAcikAdres() + "'" +
            "}";
    }
}
