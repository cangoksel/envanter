package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SayisalVeri.
 */
@Entity
@Table(name = "sayisal_veri")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SayisalVeri implements Serializable {

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
    @Column(name = "yil", nullable = false)
    private LocalDate yil;

    @Column(name = "ithalat_miktari")
    private Long ithalatMiktari;

    @Column(name = "ihtiyac_miktari")
    private Long ihtiyacMiktari;

    @Column(name = "birim_fiyat_tutar", precision=10, scale=2)
    private BigDecimal birimFiyatTutar;

    @Column(name = "birim_fiyat_pb")
    private String birimFiyatPB;

    @Column(name = "dunya_pazari_tutar", precision=10, scale=2)
    private BigDecimal dunyaPazariTutar;

    @Column(name = "dunya_pazari_pb")
    private String dunyaPazariPB;

    @Column(name = "turkiye_pazari_tutar", precision=10, scale=2)
    private BigDecimal turkiyePazariTutar;

    @Column(name = "turkiye_pazari_pb")
    private String turkiyePazariPB;

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

    public SayisalVeri deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public SayisalVeri version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDate getYil() {
        return yil;
    }

    public SayisalVeri yil(LocalDate yil) {
        this.yil = yil;
        return this;
    }

    public void setYil(LocalDate yil) {
        this.yil = yil;
    }

    public Long getIthalatMiktari() {
        return ithalatMiktari;
    }

    public SayisalVeri ithalatMiktari(Long ithalatMiktari) {
        this.ithalatMiktari = ithalatMiktari;
        return this;
    }

    public void setIthalatMiktari(Long ithalatMiktari) {
        this.ithalatMiktari = ithalatMiktari;
    }

    public Long getIhtiyacMiktari() {
        return ihtiyacMiktari;
    }

    public SayisalVeri ihtiyacMiktari(Long ihtiyacMiktari) {
        this.ihtiyacMiktari = ihtiyacMiktari;
        return this;
    }

    public void setIhtiyacMiktari(Long ihtiyacMiktari) {
        this.ihtiyacMiktari = ihtiyacMiktari;
    }

    public BigDecimal getBirimFiyatTutar() {
        return birimFiyatTutar;
    }

    public SayisalVeri birimFiyatTutar(BigDecimal birimFiyatTutar) {
        this.birimFiyatTutar = birimFiyatTutar;
        return this;
    }

    public void setBirimFiyatTutar(BigDecimal birimFiyatTutar) {
        this.birimFiyatTutar = birimFiyatTutar;
    }

    public String getBirimFiyatPB() {
        return birimFiyatPB;
    }

    public SayisalVeri birimFiyatPB(String birimFiyatPB) {
        this.birimFiyatPB = birimFiyatPB;
        return this;
    }

    public void setBirimFiyatPB(String birimFiyatPB) {
        this.birimFiyatPB = birimFiyatPB;
    }

    public BigDecimal getDunyaPazariTutar() {
        return dunyaPazariTutar;
    }

    public SayisalVeri dunyaPazariTutar(BigDecimal dunyaPazariTutar) {
        this.dunyaPazariTutar = dunyaPazariTutar;
        return this;
    }

    public void setDunyaPazariTutar(BigDecimal dunyaPazariTutar) {
        this.dunyaPazariTutar = dunyaPazariTutar;
    }

    public String getDunyaPazariPB() {
        return dunyaPazariPB;
    }

    public SayisalVeri dunyaPazariPB(String dunyaPazariPB) {
        this.dunyaPazariPB = dunyaPazariPB;
        return this;
    }

    public void setDunyaPazariPB(String dunyaPazariPB) {
        this.dunyaPazariPB = dunyaPazariPB;
    }

    public BigDecimal getTurkiyePazariTutar() {
        return turkiyePazariTutar;
    }

    public SayisalVeri turkiyePazariTutar(BigDecimal turkiyePazariTutar) {
        this.turkiyePazariTutar = turkiyePazariTutar;
        return this;
    }

    public void setTurkiyePazariTutar(BigDecimal turkiyePazariTutar) {
        this.turkiyePazariTutar = turkiyePazariTutar;
    }

    public String getTurkiyePazariPB() {
        return turkiyePazariPB;
    }

    public SayisalVeri turkiyePazariPB(String turkiyePazariPB) {
        this.turkiyePazariPB = turkiyePazariPB;
        return this;
    }

    public void setTurkiyePazariPB(String turkiyePazariPB) {
        this.turkiyePazariPB = turkiyePazariPB;
    }

    public Urun getUrun() {
        return urun;
    }

    public SayisalVeri urun(Urun urun) {
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
        SayisalVeri sayisalVeri = (SayisalVeri) o;
        if (sayisalVeri.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sayisalVeri.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SayisalVeri{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", yil='" + getYil() + "'" +
            ", ithalatMiktari='" + getIthalatMiktari() + "'" +
            ", ihtiyacMiktari='" + getIhtiyacMiktari() + "'" +
            ", birimFiyatTutar='" + getBirimFiyatTutar() + "'" +
            ", birimFiyatPB='" + getBirimFiyatPB() + "'" +
            ", dunyaPazariTutar='" + getDunyaPazariTutar() + "'" +
            ", dunyaPazariPB='" + getDunyaPazariPB() + "'" +
            ", turkiyePazariTutar='" + getTurkiyePazariTutar() + "'" +
            ", turkiyePazariPB='" + getTurkiyePazariPB() + "'" +
            "}";
    }
}
