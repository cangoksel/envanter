package tr.gov.sb.sygm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Firma.
 */
@Entity
@Table(name = "firma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Firma implements Serializable {

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
    @Column(name = "firma_yetkinlik_calismasi_yepildi_mi", nullable = false)
    private Boolean firmaYetkinlikCalismasiYepildiMi;

    @OneToOne
    @JoinColumn(unique = true)
    private GenelFirmaBilgileri genelBilgiler;

    @OneToOne
    @JoinColumn(unique = true)
    private IsyeriBilgileri isyeriBilgileri;

    @OneToOne
    @JoinColumn(unique = true)
    private OrtaklikBilgileri ortaklikBilgileri;

    @OneToOne
    @JoinColumn(unique = true)
    private FinansalBilgileri finansalBilgileri;

    @OneToMany(mappedBy = "firma")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjeBilgisi> projeBilgis = new HashSet<>();

    @OneToMany(mappedBy = "firma")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TesisBilgisi> tesisBilgisis = new HashSet<>();

    @OneToMany(mappedBy = "firma")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UretimBilgisi> uretimBilgisis = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Firma deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public Firma version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean isFirmaYetkinlikCalismasiYepildiMi() {
        return firmaYetkinlikCalismasiYepildiMi;
    }

    public Firma firmaYetkinlikCalismasiYepildiMi(Boolean firmaYetkinlikCalismasiYepildiMi) {
        this.firmaYetkinlikCalismasiYepildiMi = firmaYetkinlikCalismasiYepildiMi;
        return this;
    }

    public void setFirmaYetkinlikCalismasiYepildiMi(Boolean firmaYetkinlikCalismasiYepildiMi) {
        this.firmaYetkinlikCalismasiYepildiMi = firmaYetkinlikCalismasiYepildiMi;
    }

    public GenelFirmaBilgileri getGenelBilgiler() {
        return genelBilgiler;
    }

    public Firma genelBilgiler(GenelFirmaBilgileri genelFirmaBilgileri) {
        this.genelBilgiler = genelFirmaBilgileri;
        return this;
    }

    public void setGenelBilgiler(GenelFirmaBilgileri genelFirmaBilgileri) {
        this.genelBilgiler = genelFirmaBilgileri;
    }

    public IsyeriBilgileri getIsyeriBilgileri() {
        return isyeriBilgileri;
    }

    public Firma isyeriBilgileri(IsyeriBilgileri isyeriBilgileri) {
        this.isyeriBilgileri = isyeriBilgileri;
        return this;
    }

    public void setIsyeriBilgileri(IsyeriBilgileri isyeriBilgileri) {
        this.isyeriBilgileri = isyeriBilgileri;
    }

    public OrtaklikBilgileri getOrtaklikBilgileri() {
        return ortaklikBilgileri;
    }

    public Firma ortaklikBilgileri(OrtaklikBilgileri ortaklikBilgileri) {
        this.ortaklikBilgileri = ortaklikBilgileri;
        return this;
    }

    public void setOrtaklikBilgileri(OrtaklikBilgileri ortaklikBilgileri) {
        this.ortaklikBilgileri = ortaklikBilgileri;
    }

    public FinansalBilgileri getFinansalBilgileri() {
        return finansalBilgileri;
    }

    public Firma finansalBilgileri(FinansalBilgileri finansalBilgileri) {
        this.finansalBilgileri = finansalBilgileri;
        return this;
    }

    public void setFinansalBilgileri(FinansalBilgileri finansalBilgileri) {
        this.finansalBilgileri = finansalBilgileri;
    }

    public Set<ProjeBilgisi> getProjeBilgis() {
        return projeBilgis;
    }

    public Firma projeBilgis(Set<ProjeBilgisi> projeBilgisis) {
        this.projeBilgis = projeBilgisis;
        return this;
    }

    public Firma addProjeBilgi(ProjeBilgisi projeBilgisi) {
        this.projeBilgis.add(projeBilgisi);
        projeBilgisi.setFirma(this);
        return this;
    }

    public Firma removeProjeBilgi(ProjeBilgisi projeBilgisi) {
        this.projeBilgis.remove(projeBilgisi);
        projeBilgisi.setFirma(null);
        return this;
    }

    public void setProjeBilgis(Set<ProjeBilgisi> projeBilgisis) {
        this.projeBilgis = projeBilgisis;
    }

    public Set<TesisBilgisi> getTesisBilgisis() {
        return tesisBilgisis;
    }

    public Firma tesisBilgisis(Set<TesisBilgisi> tesisBilgisis) {
        this.tesisBilgisis = tesisBilgisis;
        return this;
    }

    public Firma addTesisBilgisi(TesisBilgisi tesisBilgisi) {
        this.tesisBilgisis.add(tesisBilgisi);
        tesisBilgisi.setFirma(this);
        return this;
    }

    public Firma removeTesisBilgisi(TesisBilgisi tesisBilgisi) {
        this.tesisBilgisis.remove(tesisBilgisi);
        tesisBilgisi.setFirma(null);
        return this;
    }

    public void setTesisBilgisis(Set<TesisBilgisi> tesisBilgisis) {
        this.tesisBilgisis = tesisBilgisis;
    }

    public Set<UretimBilgisi> getUretimBilgisis() {
        return uretimBilgisis;
    }

    public Firma uretimBilgisis(Set<UretimBilgisi> uretimBilgisis) {
        this.uretimBilgisis = uretimBilgisis;
        return this;
    }

    public Firma addUretimBilgisi(UretimBilgisi uretimBilgisi) {
        this.uretimBilgisis.add(uretimBilgisi);
        uretimBilgisi.setFirma(this);
        return this;
    }

    public Firma removeUretimBilgisi(UretimBilgisi uretimBilgisi) {
        this.uretimBilgisis.remove(uretimBilgisi);
        uretimBilgisi.setFirma(null);
        return this;
    }

    public void setUretimBilgisis(Set<UretimBilgisi> uretimBilgisis) {
        this.uretimBilgisis = uretimBilgisis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Firma firma = (Firma) o;
        if (firma.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), firma.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Firma{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", firmaYetkinlikCalismasiYepildiMi='" + isFirmaYetkinlikCalismasiYepildiMi() + "'" +
            "}";
    }
}
