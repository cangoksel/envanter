package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Belge.
 */
@Entity
@Table(name = "belge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Belge implements Serializable {

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
    @Column(name = "belge_adi", nullable = false)
    private String belgeAdi;

    @NotNull
    @Column(name = "belge_boyutu", nullable = false)
    private Long belgeBoyutu;

    @NotNull
    @Column(name = "olusturma_zamani", nullable = false)
    private LocalDate olusturmaZamani;

    @NotNull
    @Column(name = "silinebilir_mi", nullable = false)
    private Boolean silinebilirMi;

    @Column(name = "aciklama")
    private String aciklama;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "content_content_type", nullable = false)
    private String contentContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private BelgeTipi belgeTipi;

    @ManyToOne
    private Urun urun;

    @ManyToOne
    private IsyeriBilgileri isyeriBilgileri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Belge deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public Belge version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getBelgeAdi() {
        return belgeAdi;
    }

    public Belge belgeAdi(String belgeAdi) {
        this.belgeAdi = belgeAdi;
        return this;
    }

    public void setBelgeAdi(String belgeAdi) {
        this.belgeAdi = belgeAdi;
    }

    public Long getBelgeBoyutu() {
        return belgeBoyutu;
    }

    public Belge belgeBoyutu(Long belgeBoyutu) {
        this.belgeBoyutu = belgeBoyutu;
        return this;
    }

    public void setBelgeBoyutu(Long belgeBoyutu) {
        this.belgeBoyutu = belgeBoyutu;
    }

    public LocalDate getOlusturmaZamani() {
        return olusturmaZamani;
    }

    public Belge olusturmaZamani(LocalDate olusturmaZamani) {
        this.olusturmaZamani = olusturmaZamani;
        return this;
    }

    public void setOlusturmaZamani(LocalDate olusturmaZamani) {
        this.olusturmaZamani = olusturmaZamani;
    }

    public Boolean isSilinebilirMi() {
        return silinebilirMi;
    }

    public Belge silinebilirMi(Boolean silinebilirMi) {
        this.silinebilirMi = silinebilirMi;
        return this;
    }

    public void setSilinebilirMi(Boolean silinebilirMi) {
        this.silinebilirMi = silinebilirMi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Belge aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public byte[] getContent() {
        return content;
    }

    public Belge content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public Belge contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public BelgeTipi getBelgeTipi() {
        return belgeTipi;
    }

    public Belge belgeTipi(BelgeTipi belgeTipi) {
        this.belgeTipi = belgeTipi;
        return this;
    }

    public void setBelgeTipi(BelgeTipi belgeTipi) {
        this.belgeTipi = belgeTipi;
    }

    public Urun getUrun() {
        return urun;
    }

    public Belge urun(Urun urun) {
        this.urun = urun;
        return this;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    public IsyeriBilgileri getIsyeriBilgileri() {
        return isyeriBilgileri;
    }

    public Belge isyeriBilgileri(IsyeriBilgileri isyeriBilgileri) {
        this.isyeriBilgileri = isyeriBilgileri;
        return this;
    }

    public void setIsyeriBilgileri(IsyeriBilgileri isyeriBilgileri) {
        this.isyeriBilgileri = isyeriBilgileri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Belge belge = (Belge) o;
        if (belge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), belge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Belge{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", belgeAdi='" + getBelgeAdi() + "'" +
            ", belgeBoyutu='" + getBelgeBoyutu() + "'" +
            ", olusturmaZamani='" + getOlusturmaZamani() + "'" +
            ", silinebilirMi='" + isSilinebilirMi() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + contentContentType + "'" +
            "}";
    }
}
