package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Kurulus.
 */
@Entity
@Table(name = "kurulus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Kurulus implements Serializable {

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
    @Column(name = "adi", nullable = false)
    private String adi;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "telefon_2")
    private String telefon2;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "web_sitesi")
    private String webSitesi;

    @Column(name = "uye_sayisi")
    private Long uyeSayisi;

    @ManyToOne
    private IsyeriBilgileri isyeriBilgileri;

    @OneToOne
    @JoinColumn(unique = true)
    private KurulusTipi kurulusTipi;

    @OneToOne
    @JoinColumn(unique = true)
    private FaaliyetAlani faaliyetAlani;

    @OneToOne
    @JoinColumn(unique = true)
    private UrunGrubu urunGrubu;

    @OneToOne
    @JoinColumn(unique = true)
    private Kisi yetkiliKisi;

    @OneToOne
    @JoinColumn(unique = true)
    private Adres adres;

    @OneToOne
    @JoinColumn(unique = true)
    private Il bulunduguIl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Kurulus deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public Kurulus version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getAdi() {
        return adi;
    }

    public Kurulus adi(String adi) {
        this.adi = adi;
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getTelefon() {
        return telefon;
    }

    public Kurulus telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getTelefon2() {
        return telefon2;
    }

    public Kurulus telefon2(String telefon2) {
        this.telefon2 = telefon2;
        return this;
    }

    public void setTelefon2(String telefon2) {
        this.telefon2 = telefon2;
    }

    public String getFax() {
        return fax;
    }

    public Kurulus fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public Kurulus email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSitesi() {
        return webSitesi;
    }

    public Kurulus webSitesi(String webSitesi) {
        this.webSitesi = webSitesi;
        return this;
    }

    public void setWebSitesi(String webSitesi) {
        this.webSitesi = webSitesi;
    }

    public Long getUyeSayisi() {
        return uyeSayisi;
    }

    public Kurulus uyeSayisi(Long uyeSayisi) {
        this.uyeSayisi = uyeSayisi;
        return this;
    }

    public void setUyeSayisi(Long uyeSayisi) {
        this.uyeSayisi = uyeSayisi;
    }

    public IsyeriBilgileri getIsyeriBilgileri() {
        return isyeriBilgileri;
    }

    public Kurulus isyeriBilgileri(IsyeriBilgileri isyeriBilgileri) {
        this.isyeriBilgileri = isyeriBilgileri;
        return this;
    }

    public void setIsyeriBilgileri(IsyeriBilgileri isyeriBilgileri) {
        this.isyeriBilgileri = isyeriBilgileri;
    }

    public KurulusTipi getKurulusTipi() {
        return kurulusTipi;
    }

    public Kurulus kurulusTipi(KurulusTipi kurulusTipi) {
        this.kurulusTipi = kurulusTipi;
        return this;
    }

    public void setKurulusTipi(KurulusTipi kurulusTipi) {
        this.kurulusTipi = kurulusTipi;
    }

    public FaaliyetAlani getFaaliyetAlani() {
        return faaliyetAlani;
    }

    public Kurulus faaliyetAlani(FaaliyetAlani faaliyetAlani) {
        this.faaliyetAlani = faaliyetAlani;
        return this;
    }

    public void setFaaliyetAlani(FaaliyetAlani faaliyetAlani) {
        this.faaliyetAlani = faaliyetAlani;
    }

    public UrunGrubu getUrunGrubu() {
        return urunGrubu;
    }

    public Kurulus urunGrubu(UrunGrubu urunGrubu) {
        this.urunGrubu = urunGrubu;
        return this;
    }

    public void setUrunGrubu(UrunGrubu urunGrubu) {
        this.urunGrubu = urunGrubu;
    }

    public Kisi getYetkiliKisi() {
        return yetkiliKisi;
    }

    public Kurulus yetkiliKisi(Kisi kisi) {
        this.yetkiliKisi = kisi;
        return this;
    }

    public void setYetkiliKisi(Kisi kisi) {
        this.yetkiliKisi = kisi;
    }

    public Adres getAdres() {
        return adres;
    }

    public Kurulus adres(Adres adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Il getBulunduguIl() {
        return bulunduguIl;
    }

    public Kurulus bulunduguIl(Il il) {
        this.bulunduguIl = il;
        return this;
    }

    public void setBulunduguIl(Il il) {
        this.bulunduguIl = il;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kurulus kurulus = (Kurulus) o;
        if (kurulus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kurulus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kurulus{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", adi='" + getAdi() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", telefon2='" + getTelefon2() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", webSitesi='" + getWebSitesi() + "'" +
            ", uyeSayisi='" + getUyeSayisi() + "'" +
            "}";
    }
}
