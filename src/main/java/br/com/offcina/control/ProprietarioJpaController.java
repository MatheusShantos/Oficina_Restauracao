/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control;

import br.com.offcina.control.exceptions.IllegalOrphanException;
import br.com.offcina.control.exceptions.NonexistentEntityException;
import br.com.offcina.control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.offcina.model.Objeto;
import br.com.offcina.model.Proprietario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author israel
 */
public class ProprietarioJpaController implements Serializable {

    public ProprietarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proprietario proprietario) throws PreexistingEntityException, Exception {
        if (proprietario.getObjetoList() == null) {
            proprietario.setObjetoList(new ArrayList<Objeto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Objeto> attachedObjetoList = new ArrayList<Objeto>();
            for (Objeto objetoListObjetoToAttach : proprietario.getObjetoList()) {
                objetoListObjetoToAttach = em.getReference(objetoListObjetoToAttach.getClass(), objetoListObjetoToAttach.getId());
                attachedObjetoList.add(objetoListObjetoToAttach);
            }
            proprietario.setObjetoList(attachedObjetoList);
            em.persist(proprietario);
            for (Objeto objetoListObjeto : proprietario.getObjetoList()) {
                Proprietario oldProprietarioOfObjetoListObjeto = objetoListObjeto.getProprietario();
                objetoListObjeto.setProprietario(proprietario);
                objetoListObjeto = em.merge(objetoListObjeto);
                if (oldProprietarioOfObjetoListObjeto != null) {
                    oldProprietarioOfObjetoListObjeto.getObjetoList().remove(objetoListObjeto);
                    oldProprietarioOfObjetoListObjeto = em.merge(oldProprietarioOfObjetoListObjeto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProprietario(proprietario.getCpf()) != null) {
                throw new PreexistingEntityException("Proprietario " + proprietario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proprietario proprietario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proprietario persistentProprietario = em.find(Proprietario.class, proprietario.getCpf());
            List<Objeto> objetoListOld = persistentProprietario.getObjetoList();
            List<Objeto> objetoListNew = proprietario.getObjetoList();
            List<String> illegalOrphanMessages = null;
            for (Objeto objetoListOldObjeto : objetoListOld) {
                if (!objetoListNew.contains(objetoListOldObjeto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Objeto " + objetoListOldObjeto + " since its proprietario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Objeto> attachedObjetoListNew = new ArrayList<Objeto>();
            for (Objeto objetoListNewObjetoToAttach : objetoListNew) {
                objetoListNewObjetoToAttach = em.getReference(objetoListNewObjetoToAttach.getClass(), objetoListNewObjetoToAttach.getId());
                attachedObjetoListNew.add(objetoListNewObjetoToAttach);
            }
            objetoListNew = attachedObjetoListNew;
            proprietario.setObjetoList(objetoListNew);
            proprietario = em.merge(proprietario);
            for (Objeto objetoListNewObjeto : objetoListNew) {
                if (!objetoListOld.contains(objetoListNewObjeto)) {
                    Proprietario oldProprietarioOfObjetoListNewObjeto = objetoListNewObjeto.getProprietario();
                    objetoListNewObjeto.setProprietario(proprietario);
                    objetoListNewObjeto = em.merge(objetoListNewObjeto);
                    if (oldProprietarioOfObjetoListNewObjeto != null && !oldProprietarioOfObjetoListNewObjeto.equals(proprietario)) {
                        oldProprietarioOfObjetoListNewObjeto.getObjetoList().remove(objetoListNewObjeto);
                        oldProprietarioOfObjetoListNewObjeto = em.merge(oldProprietarioOfObjetoListNewObjeto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = proprietario.getCpf();
                if (findProprietario(id) == null) {
                    throw new NonexistentEntityException("The proprietario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proprietario proprietario;
            try {
                proprietario = em.getReference(Proprietario.class, id);
                proprietario.getCpf();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proprietario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Objeto> objetoListOrphanCheck = proprietario.getObjetoList();
            for (Objeto objetoListOrphanCheckObjeto : objetoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proprietario (" + proprietario + ") cannot be destroyed since the Objeto " + objetoListOrphanCheckObjeto + " in its objetoList field has a non-nullable proprietario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(proprietario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proprietario> findProprietarioEntities() {
        return findProprietarioEntities(true, -1, -1);
    }

    public List<Proprietario> findProprietarioEntities(int maxResults, int firstResult) {
        return findProprietarioEntities(false, maxResults, firstResult);
    }

    private List<Proprietario> findProprietarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proprietario.class));
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

    public Proprietario findProprietario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proprietario.class, id);
        } finally {
            em.close();
        }
    }

    public int getProprietarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proprietario> rt = cq.from(Proprietario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
