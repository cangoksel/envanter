package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A GenelFirmaBilgileri.
 */
@Entity
@Table(name = "genel_firma_bilgileri")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GenelFirmaBilgileri implements Serializable {

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
    @Column(name = "firma_unvan", nullable = false)
    private String firmaUnvan;

    @Column(name = "kurulus_tarihi")
    private LocalDate kurulusTarihi;

    @NotNull
    @Column(name = "saglik_sektorunde_mi", nullable = false)
    private Boolean saglikSektorundeMi;

    @Column(name = "sektor_bilgisi")
    private String sektorBilgisi;

    @Column(name = "arge_birimi_var_mi")
    private Boolean argeBirimiVarMi;

    @Column(name = "ticaret_sicil_no")
    private String ticaretSicilNo;

    @Column(name = "vergi_no")
    private String vergiNo;

    @Column(name = "acik_adress")
    private String acikAdress;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "telefon_2")
    private String telefon2;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "web_adresi")
    private String webAdresi;

    @OneToOne
    @JoinColumn(unique = true)
    private Il il;

    @OneToOne
    @JoinColumn(unique = true)
    private Ulke ulke;

    @OneToOne
    @JoinColumn(unique = true)
    private Kisi kaydiOlusturanKisi;

    @OneToOne
    @JoinColumn(unique = true)
    private Kisi yetkiliKisi;

    @OneToOne
    @JoinColumn(unique = true)
    private FaaliyetAlani faaliyetAlani;

    @OneToOne
    @JoinColumn(unique = true)
    private UrunGrubu urunGrubu;

    @OneToOne
    @JoinColumn(unique = true)
    private FaaliyetKodu faaliyetKodu;

    @OneToOne
    @JoinColumn(unique = true)
    private Adres adres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public GenelFirmaBilgileri deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public GenelFirmaBilgileri version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getFirmaUnvan() {
        return firmaUnvan;
    }

    public GenelFirmaBilgileri firmaUnvan(String firmaUnvan) {
        this.firmaUnvan = firmaUnvan;
        return this;
    }

    public void setFirmaUnvan(String firmaUnvan) {
        this.firmaUnvan = firmaUnvan;
    }

    public LocalDate getKurulusTarihi() {
        return kurulusTarihi;
    }

    public GenelFirmaBilgileri kurulusTarihi(LocalDate kurulusTarihi) {
        this.kurulusTarihi = kurulusTarihi;
        return this;
    }

    public void setKurulusTarihi(LocalDate kurulusTarihi) {
        this.kurulusTarihi = kurulusTarihi;
    }

    public Boolean isSaglikSektorundeMi() {
        return saglikSektorundeMi;
    }

    public GenelFirmaBilgileri saglikSektorundeMi(Boolean saglikSektorundeMi) {
        this.saglikSektorundeMi = saglikSektorundeMi;
        return this;
    }

    public void setSaglikSektorundeMi(Boolean saglikSektorundeMi) {
        this.saglikSektorundeMi = saglikSektorundeMi;
    }

    public String getSektorBilgisi() {
        return sektorBilgisi;
    }

    public GenelFirmaBilgileri sektorBilgisi(String sektorBilgisi) {
        this.sektorBilgisi = sektorBilgisi;
        return this;
    }

    public void setSektorBilgisi(String sektorBilgisi) {
        this.sektorBilgisi = sektorBilgisi;
    }

    public Boolean isArgeBirimiVarMi() {
        return argeBirimiVarMi;
    }

    public GenelFirmaBilgileri argeBirimiVarMi(Boolean argeBirimiVarMi) {
        this.argeBirimiVarMi = argeBirimiVarMi;
        return this;
    }

    public void setArgeBirimiVarMi(Boolean argeBirimiVarMi) {
        this.argeBirimiVarMi = argeBirimiVarMi;
    }

    public String getTicaretSicilNo() {
        return ticaretSicilNo;
    }

    public GenelFirmaBilgileri ticaretSicilNo(String ticaretSicilNo) {
        this.ticaretSicilNo = ticaretSicilNo;
        return this;
    }

    public void setTicaretSicilNo(String ticaretSicilNo) {
        this.ticaretSicilNo = ticaretSicilNo;
    }

    public String getVergiNo() {
        return vergiNo;
    }

    public GenelFirmaBilgileri vergiNo(String vergiNo) {
        this.vergiNo = vergiNo;
        return this;
    }

    public void setVergiNo(String vergiNo) {
        this.vergiNo = vergiNo;
    }

    public String getAcikAdress() {
        return acikAdress;
    }

    public GenelFirmaBilgileri acikAdress(String acikAdress) {
        this.acikAdress = acikAdress;
        return this;
    }

    public void setAcikAdress(String acikAdress) {
        this.acikAdress = acikAdress;
    }

    public String getTelefon() {
        return telefon;
    }

    public GenelFirmaBilgileri telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getTelefon2() {
        return telefon2;
    }

    public GenelFirmaBilgileri telefon2(String telefon2) {
        this.telefon2 = telefon2;
        return this;
    }

    public void setTelefon2(String telefon2) {
        this.telefon2 = telefon2;
    }

    public String getFax() {
        return fax;
    }

    public GenelFirmaBilgileri fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public GenelFirmaBilgileri email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebAdresi() {
        return webAdresi;
    }

    public GenelFirmaBilgileri webAdresi(String webAdresi) {
        this.webAdresi = webAdresi;
        return this;
    }

    public void setWebAdresi(String webAdresi) {
        this.webAdresi = webAdresi;
    }

    public Il getIl() {
        return il;
    }

    public GenelFirmaBilgileri il(Il il) {
        this.il = il;
        return this;
    }

    public void setIl(Il il) {
        this.il = il;
    }

    public Ulke getUlke() {
        return ulke;
    }

    public GenelFirmaBilgileri ulke(Ulke ulke) {
        this.ulke = ulke;
        return this;
    }

    public void setUlke(Ulke ulke) {
        this.ulke = ulke;
    }

    public Kisi getKaydiOlusturanKisi() {
        return kaydiOlusturanKisi;
    }

    public GenelFirmaBilgileri kaydiOlusturanKisi(Kisi kisi) {
        this.kaydiOlusturanKisi = kisi;
        return this;
    }

    public void setKaydiOlusturanKisi(Kisi kisi) {
        this.kaydiOlusturanKisi = kisi;
    }

    public Kisi getYetkiliKisi() {
        return yetkiliKisi;
    }

    public GenelFirmaBilgileri yetkiliKisi(Kisi kisi) {
        this.yetkiliKisi = kisi;
        return this;
    }

    public void setYetkiliKisi(Kisi kisi) {
        this.yetkiliKisi = kisi;
    }

    public FaaliyetAlani getFaaliyetAlani() {
        return faaliyetAlani;
    }

    public GenelFirmaBilgileri faaliyetAlani(FaaliyetAlani faaliyetAlani) {
        this.faaliyetAlani = faaliyetAlani;
        return this;
    }

    public void setFaaliyetAlani(FaaliyetAlani faaliyetAlani) {
        this.faaliyetAlani = faaliyetAlani;
    }

    public UrunGrubu getUrunGrubu() {
        return urunGrubu;
    }

    public GenelFirmaBilgileri urunGrubu(UrunGrubu urunGrubu) {
        this.urunGrubu = urunGrubu;
        return this;
    }

    public void setUrunGrubu(UrunGrubu urunGrubu) {
        this.urunGrubu = urunGrubu;
    }

    public FaaliyetKodu getFaaliyetKodu() {
        return faaliyetKodu;
    }

    public GenelFirmaBilgileri faaliyetKodu(FaaliyetKodu faaliyetKodu) {
        this.faaliyetKodu = faaliyetKodu;
        return this;
    }

    public void setFaaliyetKodu(FaaliyetKodu faaliyetKodu) {
        this.faaliyetKodu = faaliyetKodu;
    }

    public Adres getAdres() {
        return adres;
    }

    public GenelFirmaBilgileri adres(Adres adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenelFirmaBilgileri genelFirmaBilgileri = (GenelFirmaBilgileri) o;
        if (genelFirmaBilgileri.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genelFirmaBilgileri.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GenelFirmaBilgileri{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", firmaUnvan='" + getFirmaUnvan() + "'" +
            ", kurulusTarihi='" + getKurulusTarihi() + "'" +
            ", saglikSektorundeMi='" + isSaglikSektorundeMi() + "'" +
            ", sektorBilgisi='" + getSektorBilgisi() + "'" +
            ", argeBirimiVarMi='" + isArgeBirimiVarMi() + "'" +
            ", ticaretSicilNo='" + getTicaretSicilNo() + "'" +
            ", vergiNo='" + getVergiNo() + "'" +
            ", acikAdress='" + getAcikAdress() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", telefon2='" + getTelefon2() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", webAdresi='" + getWebAdresi() + "'" +
            "}";
    }
}
