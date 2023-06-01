package sk.stuba.fei.uim.vsa.pr1;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Pedagog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long aisId;
    @Column(unique = true)
    private String email;
    private String meno;
    private String pracovisko;
    private String oddelenie;
    @OneToMany(mappedBy = "veduci", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(nullable = true)
    private List<ZaverecnaPraca> zaverecnePrace;

    public Long getAisId() {
        return aisId;
    }

    public void setAisId(Long aisId) {
        this.aisId = aisId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPracovisko() {
        return pracovisko;
    }

    public void setPracovisko(String pracovisko) {
        this.pracovisko = pracovisko;
    }

    public String getOddelenie() {
        return oddelenie;
    }

    public void setOddelenie(String oddelenie) {
        this.oddelenie = oddelenie;
    }

    public List<ZaverecnaPraca> getZaverecnePrace() {
        return zaverecnePrace;
    }

    public void setZaverecnePrace(List<ZaverecnaPraca> zaverecnePrace) {
        this.zaverecnePrace = zaverecnePrace;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aisId != null ? aisId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the aisId fields are not set
        if (!(object instanceof Pedagog)) {
            return false;
        }
        Pedagog other = (Pedagog) object;
        if ((this.aisId == null && other.aisId != null) || (this.aisId != null && !this.aisId.equals(other.aisId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sk.stuba.fei.uim.vsa.pr1.Pedagog[ id=" + aisId + " ]";
    }
    
}
