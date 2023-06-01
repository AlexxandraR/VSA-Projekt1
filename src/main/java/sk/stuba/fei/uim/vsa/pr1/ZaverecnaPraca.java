package sk.stuba.fei.uim.vsa.pr1;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ZaverecnaPraca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String feiId;
    private String nazov;
    private String popis;
    private String pracovisko;
    private LocalDate datumZverejnenia;
    private LocalDate datumOdovzdania;
    @Enumerated(EnumType.STRING)
    private Typ typ;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedagog veduci;
    @OneToOne
    @JoinColumn(nullable = true)
    private Student riesitel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeiId() {
        return feiId;
    }

    public void setFeiId(String feiId) {
        this.feiId = feiId;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getPracovisko() {
        return pracovisko;
    }

    public void setPracovisko(String pracovisko) {
        this.pracovisko = pracovisko;
    }

    public LocalDate getDatumZverejnenia() {
        return datumZverejnenia;
    }

    public void setDatumZverejnenia(LocalDate datumZverejnenia) {
        this.datumZverejnenia = datumZverejnenia;
    }

    public LocalDate getDatumOdovzdania() {
        return datumOdovzdania;
    }

    public void setDatumOdovzdania(LocalDate datumOdovzdania) {
        this.datumOdovzdania = datumOdovzdania;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Pedagog getVeduci() {
        return veduci;
    }

    public void setVeduci(Pedagog veduci) {
        this.veduci = veduci;
    }

    public Student getRiesitel() {
        return riesitel;
    }

    public void setRiesitel(Student riesitel) {
        this.riesitel = riesitel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZaverecnaPraca)) {
            return false;
        }
        ZaverecnaPraca other = (ZaverecnaPraca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sk.stuba.fei.uim.vsa.pr1.ZaverecnaPraca[ id=" + id + " ]";
    }
    
}
