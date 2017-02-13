/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control.equipe;

import br.com.offcina.control.exceptions.IllegalOrphanException;
import br.com.offcina.control.exceptions.NonexistentEntityException;
import br.com.offcina.model.Equipe;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.offcina.model.Projeto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author israel
 */
public class EquipeJpaController implements Serializable {

    public EquipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipe equipe) {
        if (equipe.getProjetoList() == null) {
            equipe.setProjetoList(new ArrayList<Projeto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Projeto> attachedProjetoList = new ArrayList<Projeto>();
            for (Projeto projetoListProjetoToAttach : equipe.getProjetoList()) {
                projetoListProjetoToAttach = em.getReference(projetoListProjetoToAttach.getClass(), projetoListProjetoToAttach.getId());
                attachedProjetoList.add(projetoListProjetoToAttach);
            }
            equipe.setProjetoList(attachedProjetoList);
            em.persist(equipe);
            for (Projeto projetoListProjeto : equipe.getProjetoList()) {
                Equipe oldEquipeOfProjetoListProjeto = projetoListProjeto.getEquipe();
                projetoListProjeto.setEquipe(equipe);
                projetoListProjeto = em.merge(projetoListProjeto);
                if (oldEquipeOfProjetoListProjeto != null) {
                    oldEquipeOfProjetoListProjeto.getProjetoList().remove(projetoListProjeto);
                    oldEquipeOfProjetoListProjeto = em.merge(oldEquipeOfProjetoListProjeto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipe equipe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe persistentEquipe = em.find(Equipe.class, equipe.getId());
            List<Projeto> projetoListOld = persistentEquipe.getProjetoList();
            List<Projeto> projetoListNew = equipe.getProjetoList();
            List<String> illegalOrphanMessages = null;
            for (Projeto projetoListOldProjeto : projetoListOld) {
                if (!projetoListNew.contains(projetoListOldProjeto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projeto " + projetoListOldProjeto + " since its equipe field is not nullable.");
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
            equipe.setProjetoList(projetoListNew);
            equipe = em.merge(equipe);
            for (Projeto projetoListNewProjeto : projetoListNew) {
                if (!projetoListOld.contains(projetoListNewProjeto)) {
                    Equipe oldEquipeOfProjetoListNewProjeto = projetoListNewProjeto.getEquipe();
                    projetoListNewProjeto.setEquipe(equipe);
                    projetoListNewProjeto = em.merge(projetoListNewProjeto);
                    if (oldEquipeOfProjetoListNewProjeto != null && !oldEquipeOfProjetoListNewProjeto.equals(equipe)) {
                        oldEquipeOfProjetoListNewProjeto.getProjetoList().remove(projetoListNewProjeto);
                        oldEquipeOfProjetoListNewProjeto = em.merge(oldEquipeOfProjetoListNewProjeto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipe.getId();
                if (findEquipe(id) == null) {
                    throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.");
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
            Equipe equipe;
            try {
                equipe = em.getReference(Equipe.class, id);
                equipe.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Projeto> projetoListOrphanCheck = equipe.getProjetoList();
            for (Projeto projetoListOrphanCheckProjeto : projetoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipe (" + equipe + ") cannot be destroyed since the Projeto " + projetoListOrphanCheckProjeto + " in its projetoList field has a non-nullable equipe field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(equipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipe> findEquipeEntities() {
        return findEquipeEntities(true, -1, -1);
    }

    public List<Equipe> findEquipeEntities(int maxResults, int firstResult) {
        return findEquipeEntities(false, maxResults, firstResult);
    }

    private List<Equipe> findEquipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipe.class));
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

    public Equipe findEquipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipe> rt = cq.from(Equipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
