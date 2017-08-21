package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UrunKodu.
 */
@Entity
@Table(name = "urun_kodu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UrunKodu implements Serializable {

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
    @Column(name = "urun_kodu_adi", nullable = false)
    private String urunKoduAdi;

    @NotNull
    @Column(name = "urun_kod", nullable = false)
    private String urunKod;

    @OneToOne
    @JoinColumn(unique = true)
    private UrunGrubuKodu urunGrubuKodu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public UrunKodu deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public UrunKodu version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUrunKoduAdi() {
        return urunKoduAdi;
    }

    public UrunKodu urunKoduAdi(String urunKoduAdi) {
        this.urunKoduAdi = urunKoduAdi;
        return this;
    }

    public void setUrunKoduAdi(String urunKoduAdi) {
        this.urunKoduAdi = urunKoduAdi;
    }

    public String getUrunKod() {
        return urunKod;
    }

    public UrunKodu urunKod(String urunKod) {
        this.urunKod = urunKod;
        return this;
    }

    public void setUrunKod(String urunKod) {
        this.urunKod = urunKod;
    }

    public UrunGrubuKodu getUrunGrubuKodu() {
        return urunGrubuKodu;
    }

    public UrunKodu urunGrubuKodu(UrunGrubuKodu urunGrubuKodu) {
        this.urunGrubuKodu = urunGrubuKodu;
        return this;
    }

    public void setUrunGrubuKodu(UrunGrubuKodu urunGrubuKodu) {
        this.urunGrubuKodu = urunGrubuKodu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrunKodu urunKodu = (UrunKodu) o;
        if (urunKodu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), urunKodu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UrunKodu{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", urunKoduAdi='" + getUrunKoduAdi() + "'" +
            ", urunKod='" + getUrunKod() + "'" +
            "}";
    }
}
