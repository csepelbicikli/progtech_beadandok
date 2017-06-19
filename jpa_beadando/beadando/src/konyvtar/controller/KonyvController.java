

package konyvtar.controller;

import konyvtar.controller.exc.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import konyvtar.model.entity.Kolcsonzes;
import konyvtar.model.entity.Konyv;
import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author nemeth.peter
 */
public class KonyvController implements Serializable, EntityController<Konyv> {

    private static KonyvController kc;
    private EntityManagerFactory emf = null;
    public static KonyvController getInstance() {
        if(kc == null) kc = new KonyvController();
        return kc;
    }

    private KonyvController() {
        emf = Persistence.createEntityManagerFactory("beadandoPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Konyv konyv) {
        if (konyv.getKolcsonzesCollection() == null) {
            konyv.setKolcsonzesCollection(new ArrayList<Kolcsonzes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kolcsonzes> attachedKolcsonzesCollection = new ArrayList<Kolcsonzes>();
            for (Kolcsonzes kolcsonzesCollectionKolcsonzesToAttach : konyv.getKolcsonzesCollection()) {
                kolcsonzesCollectionKolcsonzesToAttach = em.getReference(kolcsonzesCollectionKolcsonzesToAttach.getClass(), kolcsonzesCollectionKolcsonzesToAttach.getKolcsonzesId());
                attachedKolcsonzesCollection.add(kolcsonzesCollectionKolcsonzesToAttach);
            }
            konyv.setKolcsonzesCollection(attachedKolcsonzesCollection);
            em.persist(konyv);
            for (Kolcsonzes kolcsonzesCollectionKolcsonzes : konyv.getKolcsonzesCollection()) {
                Konyv oldKonyvIdOfKolcsonzesCollectionKolcsonzes = kolcsonzesCollectionKolcsonzes.getKonyvId();
                kolcsonzesCollectionKolcsonzes.setKonyvId(konyv);
                kolcsonzesCollectionKolcsonzes = em.merge(kolcsonzesCollectionKolcsonzes);
                if (oldKonyvIdOfKolcsonzesCollectionKolcsonzes != null) {
                    oldKonyvIdOfKolcsonzesCollectionKolcsonzes.getKolcsonzesCollection().remove(kolcsonzesCollectionKolcsonzes);
                    oldKonyvIdOfKolcsonzesCollectionKolcsonzes = em.merge(oldKonyvIdOfKolcsonzesCollectionKolcsonzes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Konyv konyv) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Konyv persistentKonyv = em.find(Konyv.class, konyv.getKonyvId());
            Collection<Kolcsonzes> kolcsonzesCollectionOld = persistentKonyv.getKolcsonzesCollection();
            Collection<Kolcsonzes> kolcsonzesCollectionNew = konyv.getKolcsonzesCollection();
            Collection<Kolcsonzes> attachedKolcsonzesCollectionNew = new ArrayList<Kolcsonzes>();
            for (Kolcsonzes kolcsonzesCollectionNewKolcsonzesToAttach : kolcsonzesCollectionNew) {
                kolcsonzesCollectionNewKolcsonzesToAttach = em.getReference(kolcsonzesCollectionNewKolcsonzesToAttach.getClass(), kolcsonzesCollectionNewKolcsonzesToAttach.getKolcsonzesId());
                attachedKolcsonzesCollectionNew.add(kolcsonzesCollectionNewKolcsonzesToAttach);
            }
            kolcsonzesCollectionNew = attachedKolcsonzesCollectionNew;
            konyv.setKolcsonzesCollection(kolcsonzesCollectionNew);
            konyv = em.merge(konyv);
            for (Kolcsonzes kolcsonzesCollectionOldKolcsonzes : kolcsonzesCollectionOld) {
                if (!kolcsonzesCollectionNew.contains(kolcsonzesCollectionOldKolcsonzes)) {
                    kolcsonzesCollectionOldKolcsonzes.setKonyvId(null);
                    kolcsonzesCollectionOldKolcsonzes = em.merge(kolcsonzesCollectionOldKolcsonzes);
                }
            }
            for (Kolcsonzes kolcsonzesCollectionNewKolcsonzes : kolcsonzesCollectionNew) {
                if (!kolcsonzesCollectionOld.contains(kolcsonzesCollectionNewKolcsonzes)) {
                    Konyv oldKonyvIdOfKolcsonzesCollectionNewKolcsonzes = kolcsonzesCollectionNewKolcsonzes.getKonyvId();
                    kolcsonzesCollectionNewKolcsonzes.setKonyvId(konyv);
                    kolcsonzesCollectionNewKolcsonzes = em.merge(kolcsonzesCollectionNewKolcsonzes);
                    if (oldKonyvIdOfKolcsonzesCollectionNewKolcsonzes != null && !oldKonyvIdOfKolcsonzesCollectionNewKolcsonzes.equals(konyv)) {
                        oldKonyvIdOfKolcsonzesCollectionNewKolcsonzes.getKolcsonzesCollection().remove(kolcsonzesCollectionNewKolcsonzes);
                        oldKonyvIdOfKolcsonzesCollectionNewKolcsonzes = em.merge(oldKonyvIdOfKolcsonzesCollectionNewKolcsonzes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = konyv.getKonyvId();
                if (findKonyv(id) == null) {
                    throw new NonexistentEntityException("The konyv with id " + id + " no longer exists.");
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
            Konyv konyv;
            try {
                konyv = em.getReference(Konyv.class, id);
                konyv.getKonyvId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The konyv with id " + id + " no longer exists.", enfe);
            }
            Collection<Kolcsonzes> kolcsonzesCollection = konyv.getKolcsonzesCollection();
            for (Kolcsonzes kolcsonzesCollectionKolcsonzes : kolcsonzesCollection) {
                //kolcsonzesCollectionKolcsonzes.setKonyvId(null);
                //kolcsonzesCollectionKolcsonzes = em.merge(kolcsonzesCollectionKolcsonzes);
                //SAJAT MEGOLDAS
                em.remove(kolcsonzesCollectionKolcsonzes);
                //SAJAT MO. VEGE
            }
            em.remove(konyv);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Konyv> findKonyvEntities() {
        return findKonyvEntities(true, -1, -1);
    }

    public List<Konyv> findKonyvEntities(int maxResults, int firstResult) {
        return findKonyvEntities(false, maxResults, firstResult);
    }

    private List<Konyv> findKonyvEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Konyv.class));
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

    public Konyv findKonyv(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Konyv.class, id);
        } finally {
            em.close();
        }
    }

    public int getKonyvCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Konyv> rt = cq.from(Konyv.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
    @Override
    public List<Konyv> getEntities() {
        return findKonyvEntities();
    }

    @Override
    public int getEntityCount() {
        return getKonyvCount();
    }

    @Override
    public Konyv getEntityById(int entityId) {
        return findKonyv(entityId);
    }

    @Override
    public Konyv getEntityByRowIndex(int rowIndex) {
        return findKonyvEntities(1, rowIndex).get(0);
    }

    @Override
    public void addEntity(Konyv entity) {
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
    public void updateEntity(Konyv entity) throws Exception {
        edit(entity);
    }
}
