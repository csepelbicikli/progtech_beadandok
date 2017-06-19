
package konyvtar.controller;

import konyvtar.controller.exc.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import konyvtar.model.entity.Kolcsonzes;
import konyvtar.model.entity.Tag;
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
public class TagController implements Serializable, EntityController<Tag> {

    private static TagController tc;
    private EntityManagerFactory emf = null;
    public static TagController getInstance() {
        if(tc == null) tc = new TagController();
        return tc;
    }

    private TagController() {
        emf = Persistence.createEntityManagerFactory("beadandoPU");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tag tag) {
        if (tag.getKolcsonzesCollection() == null) {
            tag.setKolcsonzesCollection(new ArrayList<Kolcsonzes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kolcsonzes> attachedKolcsonzesCollection = new ArrayList<Kolcsonzes>();
            for (Kolcsonzes kolcsonzesCollectionKolcsonzesToAttach : tag.getKolcsonzesCollection()) {
                kolcsonzesCollectionKolcsonzesToAttach = em.getReference(kolcsonzesCollectionKolcsonzesToAttach.getClass(), kolcsonzesCollectionKolcsonzesToAttach.getKolcsonzesId());
                attachedKolcsonzesCollection.add(kolcsonzesCollectionKolcsonzesToAttach);
            }
            tag.setKolcsonzesCollection(attachedKolcsonzesCollection);
            em.persist(tag);
            for (Kolcsonzes kolcsonzesCollectionKolcsonzes : tag.getKolcsonzesCollection()) {
                Tag oldKonyvtarjegyOfKolcsonzesCollectionKolcsonzes = kolcsonzesCollectionKolcsonzes.getKonyvtarjegy();
                kolcsonzesCollectionKolcsonzes.setKonyvtarjegy(tag);
                kolcsonzesCollectionKolcsonzes = em.merge(kolcsonzesCollectionKolcsonzes);
                if (oldKonyvtarjegyOfKolcsonzesCollectionKolcsonzes != null) {
                    oldKonyvtarjegyOfKolcsonzesCollectionKolcsonzes.getKolcsonzesCollection().remove(kolcsonzesCollectionKolcsonzes);
                    oldKonyvtarjegyOfKolcsonzesCollectionKolcsonzes = em.merge(oldKonyvtarjegyOfKolcsonzesCollectionKolcsonzes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tag tag) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag persistentTag = em.find(Tag.class, tag.getKonyvtarjegy());
            Collection<Kolcsonzes> kolcsonzesCollectionOld = persistentTag.getKolcsonzesCollection();
            Collection<Kolcsonzes> kolcsonzesCollectionNew = tag.getKolcsonzesCollection();
            Collection<Kolcsonzes> attachedKolcsonzesCollectionNew = new ArrayList<Kolcsonzes>();
            for (Kolcsonzes kolcsonzesCollectionNewKolcsonzesToAttach : kolcsonzesCollectionNew) {
                kolcsonzesCollectionNewKolcsonzesToAttach = em.getReference(kolcsonzesCollectionNewKolcsonzesToAttach.getClass(), kolcsonzesCollectionNewKolcsonzesToAttach.getKolcsonzesId());
                attachedKolcsonzesCollectionNew.add(kolcsonzesCollectionNewKolcsonzesToAttach);
            }
            kolcsonzesCollectionNew = attachedKolcsonzesCollectionNew;
            tag.setKolcsonzesCollection(kolcsonzesCollectionNew);
            tag = em.merge(tag);
            for (Kolcsonzes kolcsonzesCollectionOldKolcsonzes : kolcsonzesCollectionOld) {
                if (!kolcsonzesCollectionNew.contains(kolcsonzesCollectionOldKolcsonzes)) {
                    kolcsonzesCollectionOldKolcsonzes.setKonyvtarjegy(null);
                    kolcsonzesCollectionOldKolcsonzes = em.merge(kolcsonzesCollectionOldKolcsonzes);
                }
            }
            for (Kolcsonzes kolcsonzesCollectionNewKolcsonzes : kolcsonzesCollectionNew) {
                if (!kolcsonzesCollectionOld.contains(kolcsonzesCollectionNewKolcsonzes)) {
                    Tag oldKonyvtarjegyOfKolcsonzesCollectionNewKolcsonzes = kolcsonzesCollectionNewKolcsonzes.getKonyvtarjegy();
                    kolcsonzesCollectionNewKolcsonzes.setKonyvtarjegy(tag);
                    kolcsonzesCollectionNewKolcsonzes = em.merge(kolcsonzesCollectionNewKolcsonzes);
                    if (oldKonyvtarjegyOfKolcsonzesCollectionNewKolcsonzes != null && !oldKonyvtarjegyOfKolcsonzesCollectionNewKolcsonzes.equals(tag)) {
                        oldKonyvtarjegyOfKolcsonzesCollectionNewKolcsonzes.getKolcsonzesCollection().remove(kolcsonzesCollectionNewKolcsonzes);
                        oldKonyvtarjegyOfKolcsonzesCollectionNewKolcsonzes = em.merge(oldKonyvtarjegyOfKolcsonzesCollectionNewKolcsonzes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tag.getKonyvtarjegy();
                if (findTag(id) == null) {
                    throw new NonexistentEntityException("The tag with id " + id + " no longer exists.");
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
            Tag tag;
            try {
                tag = em.getReference(Tag.class, id);
                tag.getKonyvtarjegy();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tag with id " + id + " no longer exists.", enfe);
            }
            Collection<Kolcsonzes> kolcsonzesCollection = tag.getKolcsonzesCollection();
            for (Kolcsonzes kolcsonzesCollectionKolcsonzes : kolcsonzesCollection) {
                //kolcsonzesCollectionKolcsonzes.setKonyvtarjegy(null);
                //kolcsonzesCollectionKolcsonzes = em.merge(kolcsonzesCollectionKolcsonzes);
                //SAJAT MEGOLDAS:
                em.remove(kolcsonzesCollectionKolcsonzes);
                //SAJAT MO. VEGE
            }
            em.remove(tag);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tag> findTagEntities() {
        return findTagEntities(true, -1, -1);
    }

    public List<Tag> findTagEntities(int maxResults, int firstResult) {
        return findTagEntities(false, maxResults, firstResult);
    }

    private List<Tag> findTagEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tag.class));
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

    public Tag findTag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tag.class, id);
        } finally {
            em.close();
        }
    }

    public int getTagCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tag> rt = cq.from(Tag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Tag> getEntities() {
        return findTagEntities();
    }

    @Override
    public int getEntityCount() {
        return getTagCount();
    }
    
    @Override
    public Tag getEntityById(int entityId) {
        return findTag(entityId);
    }
    
    @Override
    public Tag getEntityByRowIndex(int rowIndex) {
        return findTagEntities(1, rowIndex).get(0);
    }
    
    @Override
    public void addEntity(Tag entity)  {
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
    public void updateEntity(Tag entity) throws Exception {
        edit(entity);
    }

}
