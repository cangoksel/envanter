package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CalisanSayiBilgisi.
 */
@Entity
@Table(name = "calisan_sayi_bilgisi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CalisanSayiBilgisi implements Serializable {

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

    @Column(name = "kisi_sayisi")
    private Long kisiSayisi;

    @ManyToOne
    private IsyeriBilgileri isyeriBilgileri;

    @OneToOne
    @JoinColumn(unique = true)
    private CalisanTipi calisanTipi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public CalisanSayiBilgisi deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public CalisanSayiBilgisi version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getKisiSayisi() {
        return kisiSayisi;
    }

    public CalisanSayiBilgisi kisiSayisi(Long kisiSayisi) {
        this.kisiSayisi = kisiSayisi;
        return this;
    }

    public void setKisiSayisi(Long kisiSayisi) {
        this.kisiSayisi = kisiSayisi;
    }

    public IsyeriBilgileri getIsyeriBilgileri() {
        return isyeriBilgileri;
    }

    public CalisanSayiBilgisi isyeriBilgileri(IsyeriBilgileri isyeriBilgileri) {
        this.isyeriBilgileri = isyeriBilgileri;
        return this;
    }

    public void setIsyeriBilgileri(IsyeriBilgileri isyeriBilgileri) {
        this.isyeriBilgileri = isyeriBilgileri;
    }

    public CalisanTipi getCalisanTipi() {
        return calisanTipi;
    }

    public CalisanSayiBilgisi calisanTipi(CalisanTipi calisanTipi) {
        this.calisanTipi = calisanTipi;
        return this;
    }

    public void setCalisanTipi(CalisanTipi calisanTipi) {
        this.calisanTipi = calisanTipi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CalisanSayiBilgisi calisanSayiBilgisi = (CalisanSayiBilgisi) o;
        if (calisanSayiBilgisi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calisanSayiBilgisi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CalisanSayiBilgisi{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", kisiSayisi='" + getKisiSayisi() + "'" +
            "}";
    }
}
