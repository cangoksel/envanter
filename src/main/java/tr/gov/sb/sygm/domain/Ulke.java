package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ulke.
 */
@Entity
@Table(name = "ulke")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ulke implements Serializable {

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
    @Column(name = "jhi_label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "harf_kodu", nullable = false)
    private String harfKodu;

    @NotNull
    @Column(name = "kodu", nullable = false)
    private String kodu;

    @NotNull
    @Column(name = "english_name", nullable = false)
    private String englishName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Ulke deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public Ulke version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getLabel() {
        return label;
    }

    public Ulke label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHarfKodu() {
        return harfKodu;
    }

    public Ulke harfKodu(String harfKodu) {
        this.harfKodu = harfKodu;
        return this;
    }

    public void setHarfKodu(String harfKodu) {
        this.harfKodu = harfKodu;
    }

    public String getKodu() {
        return kodu;
    }

    public Ulke kodu(String kodu) {
        this.kodu = kodu;
        return this;
    }

    public void setKodu(String kodu) {
        this.kodu = kodu;
    }

    public String getEnglishName() {
        return englishName;
    }

    public Ulke englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ulke ulke = (Ulke) o;
        if (ulke.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ulke.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ulke{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", label='" + getLabel() + "'" +
            ", harfKodu='" + getHarfKodu() + "'" +
            ", kodu='" + getKodu() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            "}";
    }
}
