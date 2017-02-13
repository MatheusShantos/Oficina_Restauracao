/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control;

import br.com.offcina.control.exceptions.NonexistentEntityException;
import br.com.offcina.model.Orcamento;
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
public class OrcamentoJpaController implements Serializable {

    public OrcamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orcamento orcamento) {
        if (orcamento.getProjetoList() == null) {
            orcamento.setProjetoList(new ArrayList<Projeto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Projeto> attachedProjetoList = new ArrayList<Projeto>();
            for (Projeto projetoListProjetoToAttach : orcamento.getProjetoList()) {
                projetoListProjetoToAttach = em.getReference(projetoListProjetoToAttach.getClass(), projetoListProjetoToAttach.getId());
                attachedProjetoList.add(projetoListProjetoToAttach);
            }
            orcamento.setProjetoList(attachedProjetoList);
            em.persist(orcamento);
            for (Projeto projetoListProjeto : orcamento.getProjetoList()) {
                projetoListProjeto.getOrcamentoList().add(orcamento);
                projetoListProjeto = em.merge(projetoListProjeto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orcamento orcamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orcamento persistentOrcamento = em.find(Orcamento.class, orcamento.getId());
            List<Projeto> projetoListOld = persistentOrcamento.getProjetoList();
            List<Projeto> projetoListNew = orcamento.getProjetoList();
            List<Projeto> attachedProjetoListNew = new ArrayList<Projeto>();
            for (Projeto projetoListNewProjetoToAttach : projetoListNew) {
                projetoListNewProjetoToAttach = em.getReference(projetoListNewProjetoToAttach.getClass(), projetoListNewProjetoToAttach.getId());
                attachedProjetoListNew.add(projetoListNewProjetoToAttach);
            }
            projetoListNew = attachedProjetoListNew;
            orcamento.setProjetoList(projetoListNew);
            orcamento = em.merge(orcamento);
            for (Projeto projetoListOldProjeto : projetoListOld) {
                if (!projetoListNew.contains(projetoListOldProjeto)) {
                    projetoListOldProjeto.getOrcamentoList().remove(orcamento);
                    projetoListOldProjeto = em.merge(projetoListOldProjeto);
                }
            }
            for (Projeto projetoListNewProjeto : projetoListNew) {
                if (!projetoListOld.contains(projetoListNewProjeto)) {
                    projetoListNewProjeto.getOrcamentoList().add(orcamento);
                    projetoListNewProjeto = em.merge(projetoListNewProjeto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orcamento.getId();
                if (findOrcamento(id) == null) {
                    throw new NonexistentEntityException("The orcamento with id " + id + " no longer exists.");
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
            Orcamento orcamento;
            try {
                orcamento = em.getReference(Orcamento.class, id);
                orcamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orcamento with id " + id + " no longer exists.", enfe);
            }
            List<Projeto> projetoList = orcamento.getProjetoList();
            for (Projeto projetoListProjeto : projetoList) {
                projetoListProjeto.getOrcamentoList().remove(orcamento);
                projetoListProjeto = em.merge(projetoListProjeto);
            }
            em.remove(orcamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orcamento> findOrcamentoEntities() {
        return findOrcamentoEntities(true, -1, -1);
    }

    public List<Orcamento> findOrcamentoEntities(int maxResults, int firstResult) {
        return findOrcamentoEntities(false, maxResults, firstResult);
    }

    private List<Orcamento> findOrcamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orcamento.class));
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

    public Orcamento findOrcamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orcamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrcamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orcamento> rt = cq.from(Orcamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
