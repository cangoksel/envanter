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
 * A Ihracat.
 */
@Entity
@Table(name = "ihracat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ihracat implements Serializable {

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

    @Column(name = "ithalat_tutar", precision=10, scale=2)
    private BigDecimal ithalatTutar;

    @Column(name = "ithalat_pb")
    private String ithalatPB;

    @Column(name = "miktar")
    private Long miktar;

    @NotNull
    @Column(name = "yil", nullable = false)
    private LocalDate yil;

    @ManyToOne
    private FinansalBilgileri finansalBilgileri;

    @OneToOne
    @JoinColumn(unique = true)
    private Ulke yapilanUlke;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Ihracat deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public Ihracat version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public BigDecimal getIthalatTutar() {
        return ithalatTutar;
    }

    public Ihracat ithalatTutar(BigDecimal ithalatTutar) {
        this.ithalatTutar = ithalatTutar;
        return this;
    }

    public void setIthalatTutar(BigDecimal ithalatTutar) {
        this.ithalatTutar = ithalatTutar;
    }

    public String getIthalatPB() {
        return ithalatPB;
    }

    public Ihracat ithalatPB(String ithalatPB) {
        this.ithalatPB = ithalatPB;
        return this;
    }

    public void setIthalatPB(String ithalatPB) {
        this.ithalatPB = ithalatPB;
    }

    public Long getMiktar() {
        return miktar;
    }

    public Ihracat miktar(Long miktar) {
        this.miktar = miktar;
        return this;
    }

    public void setMiktar(Long miktar) {
        this.miktar = miktar;
    }

    public LocalDate getYil() {
        return yil;
    }

    public Ihracat yil(LocalDate yil) {
        this.yil = yil;
        return this;
    }

    public void setYil(LocalDate yil) {
        this.yil = yil;
    }

    public FinansalBilgileri getFinansalBilgileri() {
        return finansalBilgileri;
    }

    public Ihracat finansalBilgileri(FinansalBilgileri finansalBilgileri) {
        this.finansalBilgileri = finansalBilgileri;
        return this;
    }

    public void setFinansalBilgileri(FinansalBilgileri finansalBilgileri) {
        this.finansalBilgileri = finansalBilgileri;
    }

    public Ulke getYapilanUlke() {
        return yapilanUlke;
    }

    public Ihracat yapilanUlke(Ulke ulke) {
        this.yapilanUlke = ulke;
        return this;
    }

    public void setYapilanUlke(Ulke ulke) {
        this.yapilanUlke = ulke;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ihracat ihracat = (Ihracat) o;
        if (ihracat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ihracat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ihracat{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", ithalatTutar='" + getIthalatTutar() + "'" +
            ", ithalatPB='" + getIthalatPB() + "'" +
            ", miktar='" + getMiktar() + "'" +
            ", yil='" + getYil() + "'" +
            "}";
    }
}
