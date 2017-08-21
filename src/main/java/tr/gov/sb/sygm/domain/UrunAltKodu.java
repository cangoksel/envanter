package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UrunAltKodu.
 */
@Entity
@Table(name = "urun_alt_kodu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UrunAltKodu implements Serializable {

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
    @Column(name = "urun_alt_kodu_adi", nullable = false)
    private String urunAltKoduAdi;

    @NotNull
    @Column(name = "urun_alt_kod", nullable = false)
    private String urunAltKod;

    @OneToOne
    @JoinColumn(unique = true)
    private UrunKodu urunKodu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public UrunAltKodu deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public UrunAltKodu version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUrunAltKoduAdi() {
        return urunAltKoduAdi;
    }

    public UrunAltKodu urunAltKoduAdi(String urunAltKoduAdi) {
        this.urunAltKoduAdi = urunAltKoduAdi;
        return this;
    }

    public void setUrunAltKoduAdi(String urunAltKoduAdi) {
        this.urunAltKoduAdi = urunAltKoduAdi;
    }

    public String getUrunAltKod() {
        return urunAltKod;
    }

    public UrunAltKodu urunAltKod(String urunAltKod) {
        this.urunAltKod = urunAltKod;
        return this;
    }

    public void setUrunAltKod(String urunAltKod) {
        this.urunAltKod = urunAltKod;
    }

    public UrunKodu getUrunKodu() {
        return urunKodu;
    }

    public UrunAltKodu urunKodu(UrunKodu urunKodu) {
        this.urunKodu = urunKodu;
        return this;
    }

    public void setUrunKodu(UrunKodu urunKodu) {
        this.urunKodu = urunKodu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrunAltKodu urunAltKodu = (UrunAltKodu) o;
        if (urunAltKodu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), urunAltKodu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UrunAltKodu{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", urunAltKoduAdi='" + getUrunAltKoduAdi() + "'" +
            ", urunAltKod='" + getUrunAltKod() + "'" +
            "}";
    }
}
