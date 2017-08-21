package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UrunGrubuKodu.
 */
@Entity
@Table(name = "urun_grubu_kodu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UrunGrubuKodu implements Serializable {

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
    @Column(name = "urun_grubu_kodu_adi", nullable = false)
    private String urunGrubuKoduAdi;

    @NotNull
    @Column(name = "urun_grubu_kod", nullable = false)
    private String urunGrubuKod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public UrunGrubuKodu deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public UrunGrubuKodu version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUrunGrubuKoduAdi() {
        return urunGrubuKoduAdi;
    }

    public UrunGrubuKodu urunGrubuKoduAdi(String urunGrubuKoduAdi) {
        this.urunGrubuKoduAdi = urunGrubuKoduAdi;
        return this;
    }

    public void setUrunGrubuKoduAdi(String urunGrubuKoduAdi) {
        this.urunGrubuKoduAdi = urunGrubuKoduAdi;
    }

    public String getUrunGrubuKod() {
        return urunGrubuKod;
    }

    public UrunGrubuKodu urunGrubuKod(String urunGrubuKod) {
        this.urunGrubuKod = urunGrubuKod;
        return this;
    }

    public void setUrunGrubuKod(String urunGrubuKod) {
        this.urunGrubuKod = urunGrubuKod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrunGrubuKodu urunGrubuKodu = (UrunGrubuKodu) o;
        if (urunGrubuKodu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), urunGrubuKodu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UrunGrubuKodu{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", urunGrubuKoduAdi='" + getUrunGrubuKoduAdi() + "'" +
            ", urunGrubuKod='" + getUrunGrubuKod() + "'" +
            "}";
    }
}
