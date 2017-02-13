/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control;

import br.com.offcina.control.exceptions.IllegalOrphanException;
import br.com.offcina.control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.offcina.model.Projeto;
import br.com.offcina.model.TipoProjeto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author israel
 */
public class TipoProjetoJpaController implements Serializable {

    public TipoProjetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProjeto tipoProjeto) {
        if (tipoProjeto.getProjetoList() == null) {
            tipoProjeto.setProjetoList(new ArrayList<Projeto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Projeto> attachedProjetoList = new ArrayList<Projeto>();
            for (Projeto projetoListProjetoToAttach : tipoProjeto.getProjetoList()) {
                projetoListProjetoToAttach = em.getReference(projetoListProjetoToAttach.getClass(), projetoListProjetoToAttach.getId());
                attachedProjetoList.add(projetoListProjetoToAttach);
            }
            tipoProjeto.setProjetoList(attachedProjetoList);
            em.persist(tipoProjeto);
            for (Projeto projetoListProjeto : tipoProjeto.getProjetoList()) {
                TipoProjeto oldTipoProjetoOfProjetoListProjeto = projetoListProjeto.getTipoProjeto();
                projetoListProjeto.setTipoProjeto(tipoProjeto);
                projetoListProjeto = em.merge(projetoListProjeto);
                if (oldTipoProjetoOfProjetoListProjeto != null) {
                    oldTipoProjetoOfProjetoListProjeto.getProjetoList().remove(projetoListProjeto);
                    oldTipoProjetoOfProjetoListProjeto = em.merge(oldTipoProjetoOfProjetoListProjeto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoProjeto tipoProjeto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoProjeto persistentTipoProjeto = em.find(TipoProjeto.class, tipoProjeto.getId());
            List<Projeto> projetoListOld = persistentTipoProjeto.getProjetoList();
            List<Projeto> projetoListNew = tipoProjeto.getProjetoList();
            List<String> illegalOrphanMessages = null;
            for (Projeto projetoListOldProjeto : projetoListOld) {
                if (!projetoListNew.contains(projetoListOldProjeto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projeto " + projetoListOldProjeto + " since its tipoProjeto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Projeto> attachedProjetoListNew = new ArrayList<Projeto>();
            for (Projeto projetoListNewProjetoToAttach : projetoListNew) {
                projetoListNewProjetoToAttach = em.getReference(projetoListNewProjetoToAttach.getClass(), projetoListNewProjetoToAttach.getId());
                attachedProjetoListNew.add(projetoListNewProjetoToAttach);
            }
            projetoListNew = attachedProjetoListNew;
            tipoProjeto.setProjetoList(projetoListNew);
            tipoProjeto = em.merge(tipoProjeto);
            for (Projeto projetoListNewProjeto : projetoListNew) {
                if (!projetoListOld.contains(projetoListNewProjeto)) {
                    TipoProjeto oldTipoProjetoOfProjetoListNewProjeto = projetoListNewProjeto.getTipoProjeto();
                    projetoListNewProjeto.setTipoProjeto(tipoProjeto);
                    projetoListNewProjeto = em.merge(projetoListNewProjeto);
                    if (oldTipoProjetoOfProjetoListNewProjeto != null && !oldTipoProjetoOfProjetoListNewProjeto.equals(tipoProjeto)) {
                        oldTipoProjetoOfProjetoListNewProjeto.getProjetoList().remove(projetoListNewProjeto);
                        oldTipoProjetoOfProjetoListNewProjeto = em.merge(oldTipoProjetoOfProjetoListNewProjeto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoProjeto.getId();
                if (findTipoProjeto(id) == null) {
                    throw new NonexistentEntityException("The tipoProjeto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoProjeto tipoProjeto;
            try {
                tipoProjeto = em.getReference(TipoProjeto.class, id);
                tipoProjeto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProjeto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Projeto> projetoListOrphanCheck = tipoProjeto.getProjetoList();
            for (Projeto projetoListOrphanCheckProjeto : projetoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoProjeto (" + tipoProjeto + ") cannot be destroyed since the Projeto " + projetoListOrphanCheckProjeto + " in its projetoList field has a non-nullable tipoProjeto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoProjeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoProjeto> findTipoProjetoEntities() {
        return findTipoProjetoEntities(true, -1, -1);
    }

    public List<TipoProjeto> findTipoProjetoEntities(int maxResults, int firstResult) {
        return findTipoProjetoEntities(false, maxResults, firstResult);
    }

    private List<TipoProjeto> findTipoProjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProjeto.class));
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

    public TipoProjeto findTipoProjeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProjeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProjeto> rt = cq.from(TipoProjeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
