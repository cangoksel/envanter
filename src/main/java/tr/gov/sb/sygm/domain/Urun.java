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
 * A Urun.
 */
@Entity
@Table(name = "urun")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Urun implements Serializable {

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

    @Column(name = "tanimi")
    private String tanimi;

    @Column(name = "kullanim_alanlari")
    private String kullanimAlanlari;

    @Column(name = "cesitleri")
    private String cesitleri;

    @Column(name = "endikasyonlari")
    private String endikasyonlari;

    @Column(name = "formlari")
    private String formlari;

    @OneToOne
    @JoinColumn(unique = true)
    private UrunAltKodu urunAltKodu;

    @OneToOne
    @JoinColumn(unique = true)
    private ProdkomKodu prodkomKodu;

    @OneToOne
    @JoinColumn(unique = true)
    private GtipKodu gtipKodu;

    @OneToOne
    @JoinColumn(unique = true)
    private MkysKodu mkysKodu;

    @OneToOne
    @JoinColumn(unique = true)
    private MedikalTurKodu medikalTurKodu;

    @OneToOne
    @JoinColumn(unique = true)
    private NaceKodu naceKodu;

    @OneToOne
    @JoinColumn(unique = true)
    private ActKodu actKodu;

    @OneToOne
    @JoinColumn(unique = true)
    private TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi;

    @OneToMany(mappedBy = "urun")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SayisalVeri> sayisalVeris = new HashSet<>();

    @OneToMany(mappedBy = "urun")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Belge> belges = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Urun deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public Urun version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTanimi() {
        return tanimi;
    }

    public Urun tanimi(String tanimi) {
        this.tanimi = tanimi;
        return this;
    }

    public void setTanimi(String tanimi) {
        this.tanimi = tanimi;
    }

    public String getKullanimAlanlari() {
        return kullanimAlanlari;
    }

    public Urun kullanimAlanlari(String kullanimAlanlari) {
        this.kullanimAlanlari = kullanimAlanlari;
        return this;
    }

    public void setKullanimAlanlari(String kullanimAlanlari) {
        this.kullanimAlanlari = kullanimAlanlari;
    }

    public String getCesitleri() {
        return cesitleri;
    }

    public Urun cesitleri(String cesitleri) {
        this.cesitleri = cesitleri;
        return this;
    }

    public void setCesitleri(String cesitleri) {
        this.cesitleri = cesitleri;
    }

    public String getEndikasyonlari() {
        return endikasyonlari;
    }

    public Urun endikasyonlari(String endikasyonlari) {
        this.endikasyonlari = endikasyonlari;
        return this;
    }

    public void setEndikasyonlari(String endikasyonlari) {
        this.endikasyonlari = endikasyonlari;
    }

    public String getFormlari() {
        return formlari;
    }

    public Urun formlari(String formlari) {
        this.formlari = formlari;
        return this;
    }

    public void setFormlari(String formlari) {
        this.formlari = formlari;
    }

    public UrunAltKodu getUrunAltKodu() {
        return urunAltKodu;
    }

    public Urun urunAltKodu(UrunAltKodu urunAltKodu) {
        this.urunAltKodu = urunAltKodu;
        return this;
    }

    public void setUrunAltKodu(UrunAltKodu urunAltKodu) {
        this.urunAltKodu = urunAltKodu;
    }

    public ProdkomKodu getProdkomKodu() {
        return prodkomKodu;
    }

    public Urun prodkomKodu(ProdkomKodu prodkomKodu) {
        this.prodkomKodu = prodkomKodu;
        return this;
    }

    public void setProdkomKodu(ProdkomKodu prodkomKodu) {
        this.prodkomKodu = prodkomKodu;
    }

    public GtipKodu getGtipKodu() {
        return gtipKodu;
    }

    public Urun gtipKodu(GtipKodu gtipKodu) {
        this.gtipKodu = gtipKodu;
        return this;
    }

    public void setGtipKodu(GtipKodu gtipKodu) {
        this.gtipKodu = gtipKodu;
    }

    public MkysKodu getMkysKodu() {
        return mkysKodu;
    }

    public Urun mkysKodu(MkysKodu mkysKodu) {
        this.mkysKodu = mkysKodu;
        return this;
    }

    public void setMkysKodu(MkysKodu mkysKodu) {
        this.mkysKodu = mkysKodu;
    }

    public MedikalTurKodu getMedikalTurKodu() {
        return medikalTurKodu;
    }

    public Urun medikalTurKodu(MedikalTurKodu medikalTurKodu) {
        this.medikalTurKodu = medikalTurKodu;
        return this;
    }

    public void setMedikalTurKodu(MedikalTurKodu medikalTurKodu) {
        this.medikalTurKodu = medikalTurKodu;
    }

    public NaceKodu getNaceKodu() {
        return naceKodu;
    }

    public Urun naceKodu(NaceKodu naceKodu) {
        this.naceKodu = naceKodu;
        return this;
    }

    public void setNaceKodu(NaceKodu naceKodu) {
        this.naceKodu = naceKodu;
    }

    public ActKodu getActKodu() {
        return actKodu;
    }

    public Urun actKodu(ActKodu actKodu) {
        this.actKodu = actKodu;
        return this;
    }

    public void setActKodu(ActKodu actKodu) {
        this.actKodu = actKodu;
    }

    public TibbiCihazTehlikeSinifi getTibbiCihazTehlikeSinifi() {
        return tibbiCihazTehlikeSinifi;
    }

    public Urun tibbiCihazTehlikeSinifi(TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi) {
        this.tibbiCihazTehlikeSinifi = tibbiCihazTehlikeSinifi;
        return this;
    }

    public void setTibbiCihazTehlikeSinifi(TibbiCihazTehlikeSinifi tibbiCihazTehlikeSinifi) {
        this.tibbiCihazTehlikeSinifi = tibbiCihazTehlikeSinifi;
    }

    public Set<SayisalVeri> getSayisalVeris() {
        return sayisalVeris;
    }

    public Urun sayisalVeris(Set<SayisalVeri> sayisalVeris) {
        this.sayisalVeris = sayisalVeris;
        return this;
    }

    public Urun addSayisalVeri(SayisalVeri sayisalVeri) {
        this.sayisalVeris.add(sayisalVeri);
        sayisalVeri.setUrun(this);
        return this;
    }

    public Urun removeSayisalVeri(SayisalVeri sayisalVeri) {
        this.sayisalVeris.remove(sayisalVeri);
        sayisalVeri.setUrun(null);
        return this;
    }

    public void setSayisalVeris(Set<SayisalVeri> sayisalVeris) {
        this.sayisalVeris = sayisalVeris;
    }

    public Set<Belge> getBelges() {
        return belges;
    }

    public Urun belges(Set<Belge> belges) {
        this.belges = belges;
        return this;
    }

    public Urun addBelge(Belge belge) {
        this.belges.add(belge);
        belge.setUrun(this);
        return this;
    }

    public Urun removeBelge(Belge belge) {
        this.belges.remove(belge);
        belge.setUrun(null);
        return this;
    }

    public void setBelges(Set<Belge> belges) {
        this.belges = belges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Urun urun = (Urun) o;
        if (urun.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), urun.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Urun{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", tanimi='" + getTanimi() + "'" +
            ", kullanimAlanlari='" + getKullanimAlanlari() + "'" +
            ", cesitleri='" + getCesitleri() + "'" +
            ", endikasyonlari='" + getEndikasyonlari() + "'" +
            ", formlari='" + getFormlari() + "'" +
            "}";
    }
}
