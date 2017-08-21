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
 * A YillikCiro.
 */
@Entity
@Table(name = "yillik_ciro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YillikCiro implements Serializable {

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

    @Column(name = "ciro_tutar", precision=10, scale=2)
    private BigDecimal ciroTutar;

    @Column(name = "ciro_pb")
    private String ciroPB;

    @ManyToOne
    private FinansalBilgileri finansalBilgileri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public YillikCiro deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public YillikCiro version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDate getYil() {
        return yil;
    }

    public YillikCiro yil(LocalDate yil) {
        this.yil = yil;
        return this;
    }

    public void setYil(LocalDate yil) {
        this.yil = yil;
    }

    public BigDecimal getCiroTutar() {
        return ciroTutar;
    }

    public YillikCiro ciroTutar(BigDecimal ciroTutar) {
        this.ciroTutar = ciroTutar;
        return this;
    }

    public void setCiroTutar(BigDecimal ciroTutar) {
        this.ciroTutar = ciroTutar;
    }

    public String getCiroPB() {
        return ciroPB;
    }

    public YillikCiro ciroPB(String ciroPB) {
        this.ciroPB = ciroPB;
        return this;
    }

    public void setCiroPB(String ciroPB) {
        this.ciroPB = ciroPB;
    }

    public FinansalBilgileri getFinansalBilgileri() {
        return finansalBilgileri;
    }

    public YillikCiro finansalBilgileri(FinansalBilgileri finansalBilgileri) {
        this.finansalBilgileri = finansalBilgileri;
        return this;
    }

    public void setFinansalBilgileri(FinansalBilgileri finansalBilgileri) {
        this.finansalBilgileri = finansalBilgileri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YillikCiro yillikCiro = (YillikCiro) o;
        if (yillikCiro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), yillikCiro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "YillikCiro{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", yil='" + getYil() + "'" +
            ", ciroTutar='" + getCiroTutar() + "'" +
            ", ciroPB='" + getCiroPB() + "'" +
            "}";
    }
}
