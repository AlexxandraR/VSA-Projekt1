package sk.stuba.fei.uim.vsa.pr1;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import sk.stuba.fei.uim.vsa.pr1.bonus.Page;
import sk.stuba.fei.uim.vsa.pr1.bonus.Page1;
import sk.stuba.fei.uim.vsa.pr1.bonus.Pageable;
import sk.stuba.fei.uim.vsa.pr1.bonus.Pageable1;
import sk.stuba.fei.uim.vsa.pr1.bonus.PageableThesisService;

public class Service extends AbstractThesisService<Student, Pedagog, ZaverecnaPraca> implements PageableThesisService<Student, Pedagog, ZaverecnaPraca> {

    public Service() {
        super();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public Student createStudent(Long aisId, String name, String email) {
        Student s = new Student();

        EntityManager em = emf.createEntityManager();

        s.setAisId(aisId);
        s.setMeno(name);
        s.setEmail(email);

        em.getTransaction().begin();
        try {
            em.persist(s);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }

        return s;
    }

    @Override
    public Student getStudent(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();

        Student s;

        em.getTransaction().begin();
        try {
            TypedQuery<Student> s1 = em.createQuery("select s from Student s where s.aisId = :aisId", Student.class);
            s1.setParameter("aisId", id);
            s = s1.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            s = null;
        } finally {
            em.close();
        }
        return s;
    }

    @Override
    public Student updateStudent(Student student) {
        if (student == null || student.getAisId() == null) {
            throw new IllegalArgumentException();
        }
        Student s = this.getStudent(student.getAisId());

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        try {
            if (s != null) {
                s.setEmail(student.getEmail());
                s.setMeno(student.getMeno());
                s.setPraca(student.getPraca());
                s.setProgramStudia(student.getProgramStudia());
                s.setRocnik(student.getRocnik());
                s.setSemester(student.getSemester());

                s = em.merge(s);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
        return s;
    }

    @Override
    public List<Student> getStudents() {
        EntityManager em = emf.createEntityManager();

        List<Student> sl = new ArrayList<>();

        try {
            TypedQuery<Student> s1 = em.createQuery("select s from Student s", Student.class);
            sl = s1.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return sl;
    }

    @Override
    public Student deleteStudent(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();

        Student s;

        em.getTransaction().begin();
        try {
            TypedQuery<Student> s1 = em.createQuery("select s from Student s where s.aisId = :aisId", Student.class);
            s1.setParameter("aisId", id);
            s = s1.getSingleResult();

            TypedQuery<ZaverecnaPraca> zp1 = em.createQuery("select z from ZaverecnaPraca z where z.riesitel.aisId = :aisId", ZaverecnaPraca.class);
            zp1.setParameter("aisId", s.getAisId());
            List<ZaverecnaPraca> zpl = zp1.getResultList();
            for (ZaverecnaPraca zp : zpl) {
                if (zp.getStatus() == Status.ROZPRACOVANA) {
                    zp.setStatus(Status.VOLNA);
                }
                zp.setRiesitel(null);
                em.merge(zp);
            }
            em.remove(s);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
        return s;
    }

    @Override
    public Pedagog createTeacher(Long aisId, String name, String email, String department) {
        Pedagog p = new Pedagog();
        p.setAisId(aisId);
        p.setMeno(name);
        p.setEmail(email);
        p.setOddelenie(department);
        p.setPracovisko(department);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
        return p;
    }

    @Override
    public Pedagog getTeacher(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();

        Pedagog p = new Pedagog();

        try {
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p where p.aisId = :aisId", Pedagog.class);
            p1.setParameter("aisId", id);
            p = p1.getSingleResult();
        } catch (Exception e) {
            p = null;
        } finally {
            em.close();
        }
        return p;
    }

    @Override
    public Pedagog updateTeacher(Pedagog teacher) {
        if (teacher == null || teacher.getAisId() == null) {
            throw new IllegalArgumentException();
        }
        Pedagog p = this.getTeacher(teacher.getAisId());

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        try {
            if (p != null) {
                p.setEmail(teacher.getEmail());
                p.setMeno(teacher.getMeno());
                p.setZaverecnePrace(teacher.getZaverecnePrace());
                p.setOddelenie(teacher.getOddelenie());
                p.setPracovisko(teacher.getPracovisko());

                p = em.merge(p);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
        return p;
    }

    @Override
    public List<Pedagog> getTeachers() {
        EntityManager em = emf.createEntityManager();

        List<Pedagog> pl = new ArrayList<>();

        try {
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p", Pedagog.class);
            pl = p1.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return pl;
    }

    @Override
    public Pedagog deleteTeacher(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();

        Pedagog p;

        em.getTransaction().begin();
        try {
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p where p.aisId = :aisId", Pedagog.class);
            p1.setParameter("aisId", id);
            p = p1.getSingleResult();

            for (ZaverecnaPraca zp : p.getZaverecnePrace()) {
                if (zp.getRiesitel() != null) {
                    zp.getRiesitel().setPraca(null);
                }
            }
            em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
        return p;
    }

    @Override
    public ZaverecnaPraca makeThesisAssignment(Long supervisor, String title, String type, String description) {
        if (supervisor == null) {
            throw new IllegalArgumentException();
        }
        ZaverecnaPraca zp = null;

        EntityManager em = emf.createEntityManager();

        Pedagog p;

        em.getTransaction().begin();
        try {
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p where p.aisId = :aisId", Pedagog.class);
            p1.setParameter("aisId", supervisor);
            p = p1.getSingleResult();

            zp = new ZaverecnaPraca();
            zp.setNazov(title);
            zp.setTyp(Typ.valueOf(type));
            zp.setPopis(description);
            LocalDate datum = LocalDate.now();
            zp.setDatumZverejnenia(datum);
            zp.setDatumOdovzdania(datum.plusMonths(3L));
            zp.setPracovisko(p.getPracovisko());
            zp.setStatus(Status.VOLNA);
            p.getZaverecnePrace().add(zp);
            zp.setVeduci(p);

            em.persist(zp);
            em.flush();
            zp.setFeiId("FEI-" + zp.getId());
            zp = em.merge(zp);

            em.merge(p);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            zp = null;
        } finally {
            em.close();
        }

        return zp;
    }

    @Override
    public ZaverecnaPraca assignThesis(Long thesisId, Long studentId) {
        if (thesisId == null || studentId == null) {
            throw new IllegalArgumentException();
        }

        EntityManager em = emf.createEntityManager();

        Student s;
        ZaverecnaPraca zp;

        try {
            TypedQuery<Student> s1 = em.createQuery("select s from Student s where s.aisId = :aisId", Student.class);
            s1.setParameter("aisId", studentId);
            s = s1.getSingleResult();

            TypedQuery<ZaverecnaPraca> zp1 = em.createQuery("select z from ZaverecnaPraca z where z.id = :aisId", ZaverecnaPraca.class);
            zp1.setParameter("aisId", thesisId);
            zp = zp1.getSingleResult();
        } catch (Exception e) {
            em.close();
            return null;
        }

        if (!zp.getStatus().equals(Status.VOLNA)  || zp.getDatumOdovzdania().compareTo(LocalDate.now()) <= 0) {
            throw new IllegalStateException();
        }

        if (s.getPraca() != null) {
            return null;
        }

        em.getTransaction().begin();
        try {
            zp.setRiesitel(s);
            zp.setStatus(Status.ROZPRACOVANA);
            s.setPraca(zp);

            zp = em.merge(zp);
            s = em.merge(s);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            zp = null;
        } finally {
            em.close();
        }
        return zp;
    }

    @Override
    public ZaverecnaPraca submitThesis(Long thesisId) {
        if (thesisId == null) {
            throw new IllegalArgumentException();
        }

        EntityManager em = emf.createEntityManager();

        ZaverecnaPraca zp;

        try {
            TypedQuery<ZaverecnaPraca> zp1 = em.createQuery("select z from ZaverecnaPraca z where z.id = :aisId", ZaverecnaPraca.class);
            zp1.setParameter("aisId", thesisId);
            zp = zp1.getSingleResult();
        } catch (Exception e) {
            em.close();
            return null;
        }

        if (zp.getStatus().equals(Status.ODOVZDANA)  || zp.getStatus().equals(Status.VOLNA) || zp.getDatumOdovzdania().compareTo(LocalDate.now()) <= 0 || zp.getRiesitel() == null) {
            throw new IllegalStateException();
        }

        em.getTransaction().begin();
        try {
            zp.setStatus(Status.ODOVZDANA);

            zp = em.merge(zp);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            zp = null;
        } finally {
            em.close();
        }
        return zp;
    }

    @Override
    public ZaverecnaPraca deleteThesis(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();

        ZaverecnaPraca zp;

        em.getTransaction().begin();
        try {
            TypedQuery<ZaverecnaPraca> zp1 = em.createQuery("select z from ZaverecnaPraca z where z.id = :aisId", ZaverecnaPraca.class);
            zp1.setParameter("aisId", id);
            zp = zp1.getSingleResult();
            em.remove(zp);
            em.getTransaction().commit();
            em.refresh(zp.getVeduci());
            if (zp.getRiesitel() != null) {
                em.refresh(zp.getRiesitel());
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
        return zp;
    }

    @Override
    public List<ZaverecnaPraca> getTheses() {
        EntityManager em = emf.createEntityManager();

        List<ZaverecnaPraca> zpl = new ArrayList<>();

        try {
            TypedQuery<ZaverecnaPraca> zp1 = em.createQuery("select z from ZaverecnaPraca z", ZaverecnaPraca.class);
            zpl = zp1.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return zpl;
    }

    @Override
    public List<ZaverecnaPraca> getThesesByTeacher(Long teacherId) {
        EntityManager em = emf.createEntityManager();

        Pedagog p = new Pedagog();

        try {
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p where p.aisId = :aisId", Pedagog.class);
            p1.setParameter("aisId", teacherId);
            p = p1.getSingleResult();
        } catch (Exception e) {
            return new ArrayList<ZaverecnaPraca>();
        } finally {
            em.close();
        }
        return p.getZaverecnePrace();
    }

    @Override
    public ZaverecnaPraca getThesisByStudent(Long studentId) {
        EntityManager em = emf.createEntityManager();

        Student s = new Student();

        try {
            TypedQuery<Student> s1 = em.createQuery("select s from Student s where s.aisId = :aisId", Student.class);
            s1.setParameter("aisId", studentId);
            s = s1.getSingleResult();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return s.getPraca();
    }

    @Override
    public ZaverecnaPraca getThesis(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();

        ZaverecnaPraca zp = new ZaverecnaPraca();

        try {
            TypedQuery<ZaverecnaPraca> zp1 = em.createQuery("select z from ZaverecnaPraca z where z.id = :aisId", ZaverecnaPraca.class);
            zp1.setParameter("aisId", id);
            zp = zp1.getSingleResult();
        } catch (Exception e) {
            zp = null;
        } finally {
            em.close();
        }
        return zp;
    }

    @Override
    public ZaverecnaPraca updateThesis(ZaverecnaPraca thesis) {
        if (thesis == null || thesis.getId() == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = emf.createEntityManager();

        ZaverecnaPraca zp = new ZaverecnaPraca();

        em.getTransaction().begin();
        try {
            TypedQuery<ZaverecnaPraca> zp1 = em.createQuery("select z from ZaverecnaPraca z where z.id = :id", ZaverecnaPraca.class);
            zp1.setParameter("id", thesis.getId());
            zp = zp1.getSingleResult();
            zp.setNazov(thesis.getNazov());
            zp.setTyp(thesis.getTyp());
            zp.setPopis(thesis.getPopis());
            zp.setDatumZverejnenia(thesis.getDatumZverejnenia());
            zp.setDatumOdovzdania(thesis.getDatumOdovzdania());
            zp.setPracovisko(thesis.getPracovisko());
            zp.setStatus(thesis.getStatus());
            zp.setRiesitel(thesis.getRiesitel());
            zp.setVeduci(thesis.getVeduci());
            em.persist(zp);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            zp = null;
        } finally {
            em.close();
        }
        return zp;
    }

    @Override
    public Page<Student> findStudents(Optional<String> name, Optional<String> year, Pageable pageable) {
        List<Student> s = new ArrayList<>();
        if (name.isPresent() && year.isPresent()) {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Student> s1 = em.createQuery("select s from Student s where s.meno = :name AND s.rocnik = :year", Student.class);
            s1.setParameter("name", name.get());
            s1.setParameter("year", year.get());
            s = s1.getResultList();
        } else if (name.isPresent()) {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Student> s1 = em.createQuery("select s from Student s where s.meno = :name", Student.class);
            s1.setParameter("name", name.get());
            s = s1.getResultList();
        } else if (year.isPresent()) {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Student> s1 = em.createQuery("select s from Student s where s.rocnik = :year", Student.class);
            s1.setParameter("year", new Integer(year.get()));
            s = s1.getResultList();
        }else{
            EntityManager em = emf.createEntityManager();
            TypedQuery<Student> s1 = em.createQuery("select s from Student s", Student.class);
            s = s1.getResultList();
        }

        int velkostStrany = pageable.getPageSize();
        int aktualnaStrana = pageable.getPageNumber();
        int prvyPrvok = aktualnaStrana * velkostStrany;

        List<Student> result = new ArrayList<>();
        for (int i = prvyPrvok; i < prvyPrvok + velkostStrany; i++) {
            if (i > s.size() - 1) {
                break;
            }
            result.add(s.get(i));
        }

        Pageable1 pa = new Pageable1(aktualnaStrana, velkostStrany);

        Page1 p = new Page1(result, pa, new Long(s.size()));

        return p;
    }

    @Override
    public Page<Pedagog> findTeachers(Optional<String> name, Optional<String> institute, Pageable pageable) {
        List<Pedagog> p = new ArrayList<>();
        if (name.isPresent() && institute.isPresent()) {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p where p.meno = :name AND p.pracovisko = :institute", Pedagog.class);
            p1.setParameter("name", name.get());
            p1.setParameter("institute", institute.get());
            p = p1.getResultList();
        } else if (name.isPresent()) {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p where p.meno = name", Pedagog.class);
            p1.setParameter("name", name.get());
            p = p1.getResultList();
        } else if (institute.isPresent()) {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p where p.pracovisko = :institute", Pedagog.class);
            p1.setParameter("institute", institute.get());
            p = p1.getResultList();
        }else {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Pedagog> p1 = em.createQuery("select p from Pedagog p", Pedagog.class);
            p = p1.getResultList();
        }

        int velkostStrany = pageable.getPageSize();
        int aktualnaStrana = pageable.getPageNumber();
        int prvyPrvok = aktualnaStrana * velkostStrany;

        List<Pedagog> result = new ArrayList<>();
        for (int i = prvyPrvok; i < prvyPrvok + velkostStrany; i++) {
            if (i > p.size() - 1) {
                break;
            }
            result.add(p.get(i));
        }

        Pageable1 pa = new Pageable1(aktualnaStrana, velkostStrany);

        Page1 pg = new Page1(result, pa, new Long(p.size()));

        return pg;
    }

    @Override
    public Page<ZaverecnaPraca> findTheses(Optional<String> department, Optional<Date> publishedOn, Optional<String> type, Optional<String> status, Pageable pageable) {
        String query = "select z from ZaverecnaPraca z where 1 = 1";
        if (department.isPresent()) {
            query += " AND z.pracovisko = :department";
        }
        if (publishedOn.isPresent()) {
            query += " AND z.datumZverejnenia = :publishedOn";
        }
        if (type.isPresent()) {
            query += " AND z.typ = :type";
        }
        if (status.isPresent()) {
            query += " AND z.status = :status";
        }
        
        EntityManager em = emf.createEntityManager();
        TypedQuery<ZaverecnaPraca> zp1 = em.createQuery(query, ZaverecnaPraca.class);
        if (department.isPresent()) {
            zp1.setParameter("department", department.get());
        }
        if (publishedOn.isPresent()) {
            zp1.setParameter("publishedOn", publishedOn.get().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        if (type.isPresent()) {
            zp1.setParameter("type", Typ.valueOf(type.get().toUpperCase()));
        }
        if (status.isPresent()) {
            zp1.setParameter("status", Status.valueOf(status.get().toUpperCase()));
        }
        List<ZaverecnaPraca> zp = zp1.getResultList();
        
        int velkostStrany = pageable.getPageSize();
        int aktualnaStrana = pageable.getPageNumber();
        int prvyPrvok = aktualnaStrana * velkostStrany;

        List<ZaverecnaPraca> result = new ArrayList<>();
        for (int i = prvyPrvok; i < prvyPrvok + velkostStrany; i++) {
            if (i > zp.size() - 1) {
                break;
            }
            result.add(zp.get(i));
        }

        Pageable1 pa = new Pageable1(aktualnaStrana, velkostStrany);

        Page1 pg = new Page1(result, pa, new Long(zp.size()));

        return pg;
        
    }

}
