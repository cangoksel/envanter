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
 * A IsyeriBilgileri.
 */
@Entity
@Table(name = "isyeri_bilgileri")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IsyeriBilgileri implements Serializable {

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

    @OneToMany(mappedBy = "isyeriBilgileri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Kurulus> kayitliOlduguKuruluses = new HashSet<>();

    @OneToMany(mappedBy = "isyeriBilgileri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CalisanSayiBilgisi> calisanBilgis = new HashSet<>();

    @OneToMany(mappedBy = "isyeriBilgileri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Belge> kaliteBelgesis = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public IsyeriBilgileri deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public IsyeriBilgileri version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<Kurulus> getKayitliOlduguKuruluses() {
        return kayitliOlduguKuruluses;
    }

    public IsyeriBilgileri kayitliOlduguKuruluses(Set<Kurulus> kuruluses) {
        this.kayitliOlduguKuruluses = kuruluses;
        return this;
    }

    public IsyeriBilgileri addKayitliOlduguKurulus(Kurulus kurulus) {
        this.kayitliOlduguKuruluses.add(kurulus);
        kurulus.setIsyeriBilgileri(this);
        return this;
    }

    public IsyeriBilgileri removeKayitliOlduguKurulus(Kurulus kurulus) {
        this.kayitliOlduguKuruluses.remove(kurulus);
        kurulus.setIsyeriBilgileri(null);
        return this;
    }

    public void setKayitliOlduguKuruluses(Set<Kurulus> kuruluses) {
        this.kayitliOlduguKuruluses = kuruluses;
    }

    public Set<CalisanSayiBilgisi> getCalisanBilgis() {
        return calisanBilgis;
    }

    public IsyeriBilgileri calisanBilgis(Set<CalisanSayiBilgisi> calisanSayiBilgisis) {
        this.calisanBilgis = calisanSayiBilgisis;
        return this;
    }

    public IsyeriBilgileri addCalisanBilgi(CalisanSayiBilgisi calisanSayiBilgisi) {
        this.calisanBilgis.add(calisanSayiBilgisi);
        calisanSayiBilgisi.setIsyeriBilgileri(this);
        return this;
    }

    public IsyeriBilgileri removeCalisanBilgi(CalisanSayiBilgisi calisanSayiBilgisi) {
        this.calisanBilgis.remove(calisanSayiBilgisi);
        calisanSayiBilgisi.setIsyeriBilgileri(null);
        return this;
    }

    public void setCalisanBilgis(Set<CalisanSayiBilgisi> calisanSayiBilgisis) {
        this.calisanBilgis = calisanSayiBilgisis;
    }

    public Set<Belge> getKaliteBelgesis() {
        return kaliteBelgesis;
    }

    public IsyeriBilgileri kaliteBelgesis(Set<Belge> belges) {
        this.kaliteBelgesis = belges;
        return this;
    }

    public IsyeriBilgileri addKaliteBelgesi(Belge belge) {
        this.kaliteBelgesis.add(belge);
        belge.setIsyeriBilgileri(this);
        return this;
    }

    public IsyeriBilgileri removeKaliteBelgesi(Belge belge) {
        this.kaliteBelgesis.remove(belge);
        belge.setIsyeriBilgileri(null);
        return this;
    }

    public void setKaliteBelgesis(Set<Belge> belges) {
        this.kaliteBelgesis = belges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IsyeriBilgileri isyeriBilgileri = (IsyeriBilgileri) o;
        if (isyeriBilgileri.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), isyeriBilgileri.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IsyeriBilgileri{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
