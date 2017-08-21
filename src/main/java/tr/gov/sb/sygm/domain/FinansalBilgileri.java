package tr.gov.sb.sygm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FinansalBilgileri.
 */
@Entity
@Table(name = "finansal_bilgileri")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FinansalBilgileri implements Serializable {

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

    @NotNull
    @Column(name = "ihracat_var_mi", nullable = false)
    private Boolean ihracatVarMi;

    @OneToMany(mappedBy = "finansalBilgileri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YillikCiro> yillikCiros = new HashSet<>();

    @OneToMany(mappedBy = "finansalBilgileri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ihracat> ihracats = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public FinansalBilgileri deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public FinansalBilgileri version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDate getYil() {
        return yil;
    }

    public FinansalBilgileri yil(LocalDate yil) {
        this.yil = yil;
        return this;
    }

    public void setYil(LocalDate yil) {
        this.yil = yil;
    }

    public Boolean isIhracatVarMi() {
        return ihracatVarMi;
    }

    public FinansalBilgileri ihracatVarMi(Boolean ihracatVarMi) {
        this.ihracatVarMi = ihracatVarMi;
        return this;
    }

    public void setIhracatVarMi(Boolean ihracatVarMi) {
        this.ihracatVarMi = ihracatVarMi;
    }

    public Set<YillikCiro> getYillikCiros() {
        return yillikCiros;
    }

    public FinansalBilgileri yillikCiros(Set<YillikCiro> yillikCiros) {
        this.yillikCiros = yillikCiros;
        return this;
    }

    public FinansalBilgileri addYillikCiro(YillikCiro yillikCiro) {
        this.yillikCiros.add(yillikCiro);
        yillikCiro.setFinansalBilgileri(this);
        return this;
    }

    public FinansalBilgileri removeYillikCiro(YillikCiro yillikCiro) {
        this.yillikCiros.remove(yillikCiro);
        yillikCiro.setFinansalBilgileri(null);
        return this;
    }

    public void setYillikCiros(Set<YillikCiro> yillikCiros) {
        this.yillikCiros = yillikCiros;
    }

    public Set<Ihracat> getIhracats() {
        return ihracats;
    }

    public FinansalBilgileri ihracats(Set<Ihracat> ihracats) {
        this.ihracats = ihracats;
        return this;
    }

    public FinansalBilgileri addIhracat(Ihracat ihracat) {
        this.ihracats.add(ihracat);
        ihracat.setFinansalBilgileri(this);
        return this;
    }

    public FinansalBilgileri removeIhracat(Ihracat ihracat) {
        this.ihracats.remove(ihracat);
        ihracat.setFinansalBilgileri(null);
        return this;
    }

    public void setIhracats(Set<Ihracat> ihracats) {
        this.ihracats = ihracats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FinansalBilgileri finansalBilgileri = (FinansalBilgileri) o;
        if (finansalBilgileri.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), finansalBilgileri.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinansalBilgileri{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", yil='" + getYil() + "'" +
            ", ihracatVarMi='" + isIhracatVarMi() + "'" +
            "}";
    }
}
