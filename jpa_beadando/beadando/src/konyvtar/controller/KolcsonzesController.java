
package konyvtar.controller;

import konyvtar.controller.exc.NonexistentEntityException;
import konyvtar.model.entity.Kolcsonzes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import konyvtar.model.entity.Konyv;
import konyvtar.model.entity.Tag;
import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nemeth.peter
 */
public class KolcsonzesController implements Serializable, EntityController<Kolcsonzes> {

    private static KolcsonzesController kc;
    private EntityManagerFactory emf = null;
    public static KolcsonzesController getInstance() {
        if(kc == null) kc = new KolcsonzesController();
        return kc;
    }

    private KolcsonzesController() {
        emf = Persistence.createEntityManagerFactory("beadandoPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kolcsonzes kolcsonzes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Konyv konyvId = kolcsonzes.getKonyvId();
            if (konyvId != null) {
                konyvId = em.getReference(konyvId.getClass(), konyvId.getKonyvId());
                kolcsonzes.setKonyvId(konyvId);
            }
            Tag konyvtarjegy = kolcsonzes.getKonyvtarjegy();
            if (konyvtarjegy != null) {
                konyvtarjegy = em.getReference(konyvtarjegy.getClass(), konyvtarjegy.getKonyvtarjegy());
                kolcsonzes.setKonyvtarjegy(konyvtarjegy);
            }
            em.persist(kolcsonzes);
            if (konyvId != null) {
                konyvId.getKolcsonzesCollection().add(kolcsonzes);
                konyvId = em.merge(konyvId);
            }
            if (konyvtarjegy != null) {
                konyvtarjegy.getKolcsonzesCollection().add(kolcsonzes);
                konyvtarjegy = em.merge(konyvtarjegy);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kolcsonzes kolcsonzes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kolcsonzes persistentKolcsonzes = em.find(Kolcsonzes.class, kolcsonzes.getKolcsonzesId());
            Konyv konyvIdOld = persistentKolcsonzes.getKonyvId();
            Konyv konyvIdNew = kolcsonzes.getKonyvId();
            Tag konyvtarjegyOld = persistentKolcsonzes.getKonyvtarjegy();
            Tag konyvtarjegyNew = kolcsonzes.getKonyvtarjegy();
            if (konyvIdNew != null) {
                konyvIdNew = em.getReference(konyvIdNew.getClass(), konyvIdNew.getKonyvId());
                kolcsonzes.setKonyvId(konyvIdNew);
            }
            if (konyvtarjegyNew != null) {
                konyvtarjegyNew = em.getReference(konyvtarjegyNew.getClass(), konyvtarjegyNew.getKonyvtarjegy());
                kolcsonzes.setKonyvtarjegy(konyvtarjegyNew);
            }
            kolcsonzes = em.merge(kolcsonzes);
            if (konyvIdOld != null && !konyvIdOld.equals(konyvIdNew)) {
                konyvIdOld.getKolcsonzesCollection().remove(kolcsonzes);
                konyvIdOld = em.merge(konyvIdOld);
            }
            if (konyvIdNew != null && !konyvIdNew.equals(konyvIdOld)) {
                konyvIdNew.getKolcsonzesCollection().add(kolcsonzes);
                konyvIdNew = em.merge(konyvIdNew);
            }
            if (konyvtarjegyOld != null && !konyvtarjegyOld.equals(konyvtarjegyNew)) {
                konyvtarjegyOld.getKolcsonzesCollection().remove(kolcsonzes);
                konyvtarjegyOld = em.merge(konyvtarjegyOld);
            }
            if (konyvtarjegyNew != null && !konyvtarjegyNew.equals(konyvtarjegyOld)) {
                konyvtarjegyNew.getKolcsonzesCollection().add(kolcsonzes);
                konyvtarjegyNew = em.merge(konyvtarjegyNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kolcsonzes.getKolcsonzesId();
                if (findKolcsonzes(id) == null) {
                    throw new NonexistentEntityException("The kolcsonzes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kolcsonzes kolcsonzes;
            try {
                kolcsonzes = em.getReference(Kolcsonzes.class, id);
                kolcsonzes.getKolcsonzesId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kolcsonzes with id " + id + " no longer exists.", enfe);
            }
            Konyv konyvId = kolcsonzes.getKonyvId();
            if (konyvId != null) {
                konyvId.getKolcsonzesCollection().remove(kolcsonzes);
                konyvId = em.merge(konyvId);
            }
            Tag konyvtarjegy = kolcsonzes.getKonyvtarjegy();
            if (konyvtarjegy != null) {
                konyvtarjegy.getKolcsonzesCollection().remove(kolcsonzes);
                konyvtarjegy = em.merge(konyvtarjegy);
            }
            em.remove(kolcsonzes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kolcsonzes> findKolcsonzesEntities() {
        return findKolcsonzesEntities(true, -1, -1);
    }

    public List<Kolcsonzes> findKolcsonzesEntities(int maxResults, int firstResult) {
        return findKolcsonzesEntities(false, maxResults, firstResult);
    }

    private List<Kolcsonzes> findKolcsonzesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kolcsonzes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Kolcsonzes findKolcsonzes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kolcsonzes.class, id);
        } finally {
            em.close();
        }
    }

    public int getKolcsonzesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kolcsonzes> rt = cq.from(Kolcsonzes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    @Override
    public List<Kolcsonzes> getEntities() {
        return findKolcsonzesEntities();
    }
   

    @Override
    public int getEntityCount() {
        return getKolcsonzesCount();
    }

   

    @Override
    public Kolcsonzes getEntityById(int entityId) {
        return findKolcsonzes(entityId);
    }

    @Override
    public Kolcsonzes getEntityByRowIndex(int rowIndex) {
        return findKolcsonzesEntities(1, rowIndex).get(0);
    }

    @Override
    public void addEntity(Kolcsonzes entity) {
        try{
            create(entity);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteEntity(int rowIndex) throws hu.elte.inf.prt.db.jpa.controllers.exceptions.NonexistentEntityException {
        try{
            destroy((Integer) getEntityByRowIndex(rowIndex).getId());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void updateEntity(Kolcsonzes entity) throws Exception {
        edit(entity);
    }

}
