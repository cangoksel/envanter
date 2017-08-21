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
 * A OrtaklikBilgileri.
 */
@Entity
@Table(name = "ortaklik_bilgileri")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrtaklikBilgileri implements Serializable {

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
    @Column(name = "isbirligi_sunma_istegi_var_mi", nullable = false)
    private Boolean isbirligiSunmaIstegiVarMi;

    @NotNull
    @Column(name = "isbirligi_talebi_var_mi", nullable = false)
    private Boolean isbirligiTalebiVarMi;

    @Column(name = "isbirligi_konsu")
    private String isbirligiKonsu;

    @OneToMany(mappedBy = "ortaklikBilgileri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IsbirligiFirma> isbirligiYapilanFirmas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public OrtaklikBilgileri deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public OrtaklikBilgileri version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean isIsbirligiSunmaIstegiVarMi() {
        return isbirligiSunmaIstegiVarMi;
    }

    public OrtaklikBilgileri isbirligiSunmaIstegiVarMi(Boolean isbirligiSunmaIstegiVarMi) {
        this.isbirligiSunmaIstegiVarMi = isbirligiSunmaIstegiVarMi;
        return this;
    }

    public void setIsbirligiSunmaIstegiVarMi(Boolean isbirligiSunmaIstegiVarMi) {
        this.isbirligiSunmaIstegiVarMi = isbirligiSunmaIstegiVarMi;
    }

    public Boolean isIsbirligiTalebiVarMi() {
        return isbirligiTalebiVarMi;
    }

    public OrtaklikBilgileri isbirligiTalebiVarMi(Boolean isbirligiTalebiVarMi) {
        this.isbirligiTalebiVarMi = isbirligiTalebiVarMi;
        return this;
    }

    public void setIsbirligiTalebiVarMi(Boolean isbirligiTalebiVarMi) {
        this.isbirligiTalebiVarMi = isbirligiTalebiVarMi;
    }

    public String getIsbirligiKonsu() {
        return isbirligiKonsu;
    }

    public OrtaklikBilgileri isbirligiKonsu(String isbirligiKonsu) {
        this.isbirligiKonsu = isbirligiKonsu;
        return this;
    }

    public void setIsbirligiKonsu(String isbirligiKonsu) {
        this.isbirligiKonsu = isbirligiKonsu;
    }

    public Set<IsbirligiFirma> getIsbirligiYapilanFirmas() {
        return isbirligiYapilanFirmas;
    }

    public OrtaklikBilgileri isbirligiYapilanFirmas(Set<IsbirligiFirma> isbirligiFirmas) {
        this.isbirligiYapilanFirmas = isbirligiFirmas;
        return this;
    }

    public OrtaklikBilgileri addIsbirligiYapilanFirma(IsbirligiFirma isbirligiFirma) {
        this.isbirligiYapilanFirmas.add(isbirligiFirma);
        isbirligiFirma.setOrtaklikBilgileri(this);
        return this;
    }

    public OrtaklikBilgileri removeIsbirligiYapilanFirma(IsbirligiFirma isbirligiFirma) {
        this.isbirligiYapilanFirmas.remove(isbirligiFirma);
        isbirligiFirma.setOrtaklikBilgileri(null);
        return this;
    }

    public void setIsbirligiYapilanFirmas(Set<IsbirligiFirma> isbirligiFirmas) {
        this.isbirligiYapilanFirmas = isbirligiFirmas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrtaklikBilgileri ortaklikBilgileri = (OrtaklikBilgileri) o;
        if (ortaklikBilgileri.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ortaklikBilgileri.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrtaklikBilgileri{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            ", isbirligiSunmaIstegiVarMi='" + isIsbirligiSunmaIstegiVarMi() + "'" +
            ", isbirligiTalebiVarMi='" + isIsbirligiTalebiVarMi() + "'" +
            ", isbirligiKonsu='" + getIsbirligiKonsu() + "'" +
            "}";
    }
}
