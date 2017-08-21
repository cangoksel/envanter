package tr.gov.sb.sygm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IsbirligiFirma.
 */
@Entity
@Table(name = "isbirligi_firma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IsbirligiFirma implements Serializable {

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
    @Column(name = "firma_adi", nullable = false)
    private String firmaAdi;

    @Column(name = "isbirligi_konsu")
    private String isbirligiKonsu;

    @ManyToOne
    private OrtaklikBilgileri ortaklikBilgileri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public IsbirligiFirma deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public IsbirligiFirma version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getFirmaAdi() {
        return firmaAdi;
    }

    public IsbirligiFirma firmaAdi(String firmaAdi) {
        this.firmaAdi = firmaAdi;
        return this;
    }

    public void setFirmaAdi(String firmaAdi) {
        this.firmaAdi = firmaAdi;
    }

    public String getIsbirligiKonsu() {
        return isbirligiKonsu;
    }

    public IsbirligiFirma isbirligiKonsu(String isbirligiKonsu) {
        this.isbirligiKonsu = isbirligiKonsu;
        return this;
    }

    public void setIsbirligiKonsu(String isbirligiKonsu) {
        this.isbirligiKonsu = isbirligiKonsu;
    }

    public OrtaklikBilgileri getOrtaklikBilgileri() {
        return ortaklikBilgileri;
    }

    public IsbirligiFirma ortaklikBilgileri(OrtaklikBilgileri ortaklikBilgileri) {
        this.ortaklikBilgileri = ortaklikBilgileri;
        return this;
    }

    public void setOrtaklikBilgileri(OrtaklikBilgileri ortaklikBilgileri) {
        this.ortaklikBilgileri = ortaklikBilgileri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IsbirligiFirma isbirligiFirma = (IsbirligiFirma) o;
        if (isbirligiFirma.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), isbirligiFirma.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IsbirligiFirma{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", firmaAdi='" + getFirmaAdi() + "'" +
            ", isbirligiKonsu='" + getIsbirligiKonsu() + "'" +
            "}";
    }
}
