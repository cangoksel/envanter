package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UretimBilgisi.
 */
@Entity
@Table(name = "uretim_bilgisi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UretimBilgisi implements Serializable {

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

    @Column(name = "firma_aciklamasi")
    private String firmaAciklamasi;

    @ManyToOne
    private Firma firma;

    @ManyToOne
    private Urun urun;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public UretimBilgisi deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public UretimBilgisi version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getFirmaAciklamasi() {
        return firmaAciklamasi;
    }

    public UretimBilgisi firmaAciklamasi(String firmaAciklamasi) {
        this.firmaAciklamasi = firmaAciklamasi;
        return this;
    }

    public void setFirmaAciklamasi(String firmaAciklamasi) {
        this.firmaAciklamasi = firmaAciklamasi;
    }

    public Firma getFirma() {
        return firma;
    }

    public UretimBilgisi firma(Firma firma) {
        this.firma = firma;
        return this;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public Urun getUrun() {
        return urun;
    }

    public UretimBilgisi urun(Urun urun) {
        this.urun = urun;
        return this;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UretimBilgisi uretimBilgisi = (UretimBilgisi) o;
        if (uretimBilgisi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uretimBilgisi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UretimBilgisi{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", firmaAciklamasi='" + getFirmaAciklamasi() + "'" +
            "}";
    }
}
