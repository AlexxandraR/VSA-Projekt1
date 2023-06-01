package sk.stuba.fei.uim.vsa.pr1;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long aisId;
    @Column(unique = true)
    private String email;
    private String meno;
    private String programStudia;
    private Integer rocnik;
    private Integer semester;
    @OneToOne(mappedBy = "riesitel")
    @JoinColumn(nullable = true)
    private ZaverecnaPraca praca;


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

    public String getProgramStudia() {
        return programStudia;
    }

    public void setProgramStudia(String programStudia) {
        this.programStudia = programStudia;
    }

    public Integer getRocnik() {
        return rocnik;
    }

    public void setRocnik(Integer rocnik) {
        this.rocnik = rocnik;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public ZaverecnaPraca getPraca() {
        return praca;
    }

    public void setPraca(ZaverecnaPraca praca) {
        this.praca = praca;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.aisId == null && other.aisId != null) || (this.aisId != null && !this.aisId.equals(other.aisId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sk.stuba.fei.uim.vsa.pr1.Student[ id=" + aisId + " ]";
    }
    
}
