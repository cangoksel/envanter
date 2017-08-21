package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjeBilgisi.
 */
@Entity
@Table(name = "proje_bilgisi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjeBilgisi implements Serializable {

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
    @Column(name = "konusu", nullable = false)
    private String konusu;

    @ManyToOne
    private Firma firma;

    @OneToOne
    @JoinColumn(unique = true)
    private Kurum destekVerenKurum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public ProjeBilgisi deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public ProjeBilgisi version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getKonusu() {
        return konusu;
    }

    public ProjeBilgisi konusu(String konusu) {
        this.konusu = konusu;
        return this;
    }

    public void setKonusu(String konusu) {
        this.konusu = konusu;
    }

    public Firma getFirma() {
        return firma;
    }

    public ProjeBilgisi firma(Firma firma) {
        this.firma = firma;
        return this;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public Kurum getDestekVerenKurum() {
        return destekVerenKurum;
    }

    public ProjeBilgisi destekVerenKurum(Kurum kurum) {
        this.destekVerenKurum = kurum;
        return this;
    }

    public void setDestekVerenKurum(Kurum kurum) {
        this.destekVerenKurum = kurum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjeBilgisi projeBilgisi = (ProjeBilgisi) o;
        if (projeBilgisi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projeBilgisi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjeBilgisi{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", konusu='" + getKonusu() + "'" +
            "}";
    }
}
