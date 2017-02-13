/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control;

import br.com.offcina.control.exceptions.NonexistentEntityException;
import br.com.offcina.control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.offcina.model.Funcionario;
import br.com.offcina.model.FuncionarioEquipe;
import br.com.offcina.model.FuncionarioEquipePK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author israel
 */
public class FuncionarioEquipeJpaController implements Serializable {

    public FuncionarioEquipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FuncionarioEquipe funcionarioEquipe) throws PreexistingEntityException, Exception {
        if (funcionarioEquipe.getFuncionarioEquipePK() == null) {
            funcionarioEquipe.setFuncionarioEquipePK(new FuncionarioEquipePK());
        }
        funcionarioEquipe.getFuncionarioEquipePK().setFuncionario(funcionarioEquipe.getFuncionario1().getCpf());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario funcionario1 = funcionarioEquipe.getFuncionario1();
            if (funcionario1 != null) {
                funcionario1 = em.getReference(funcionario1.getClass(), funcionario1.getCpf());
                funcionarioEquipe.setFuncionario1(funcionario1);
            }
            em.persist(funcionarioEquipe);
            if (funcionario1 != null) {
                funcionario1.getFuncionarioEquipeList().add(funcionarioEquipe);
                funcionario1 = em.merge(funcionario1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionarioEquipe(funcionarioEquipe.getFuncionarioEquipePK()) != null) {
                throw new PreexistingEntityException("FuncionarioEquipe " + funcionarioEquipe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FuncionarioEquipe funcionarioEquipe) throws NonexistentEntityException, Exception {
        funcionarioEquipe.getFuncionarioEquipePK().setFuncionario(funcionarioEquipe.getFuncionario1().getCpf());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FuncionarioEquipe persistentFuncionarioEquipe = em.find(FuncionarioEquipe.class, funcionarioEquipe.getFuncionarioEquipePK());
            Funcionario funcionario1Old = persistentFuncionarioEquipe.getFuncionario1();
            Funcionario funcionario1New = funcionarioEquipe.getFuncionario1();
            if (funcionario1New != null) {
                funcionario1New = em.getReference(funcionario1New.getClass(), funcionario1New.getCpf());
                funcionarioEquipe.setFuncionario1(funcionario1New);
            }
            funcionarioEquipe = em.merge(funcionarioEquipe);
            if (funcionario1Old != null && !funcionario1Old.equals(funcionario1New)) {
                funcionario1Old.getFuncionarioEquipeList().remove(funcionarioEquipe);
                funcionario1Old = em.merge(funcionario1Old);
            }
            if (funcionario1New != null && !funcionario1New.equals(funcionario1Old)) {
                funcionario1New.getFuncionarioEquipeList().add(funcionarioEquipe);
                funcionario1New = em.merge(funcionario1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FuncionarioEquipePK id = funcionarioEquipe.getFuncionarioEquipePK();
                if (findFuncionarioEquipe(id) == null) {
                    throw new NonexistentEntityException("The funcionarioEquipe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FuncionarioEquipePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FuncionarioEquipe funcionarioEquipe;
            try {
                funcionarioEquipe = em.getReference(FuncionarioEquipe.class, id);
                funcionarioEquipe.getFuncionarioEquipePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionarioEquipe with id " + id + " no longer exists.", enfe);
            }
            Funcionario funcionario1 = funcionarioEquipe.getFuncionario1();
            if (funcionario1 != null) {
                funcionario1.getFuncionarioEquipeList().remove(funcionarioEquipe);
                funcionario1 = em.merge(funcionario1);
            }
            em.remove(funcionarioEquipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FuncionarioEquipe> findFuncionarioEquipeEntities() {
        return findFuncionarioEquipeEntities(true, -1, -1);
    }

    public List<FuncionarioEquipe> findFuncionarioEquipeEntities(int maxResults, int firstResult) {
        return findFuncionarioEquipeEntities(false, maxResults, firstResult);
    }

    private List<FuncionarioEquipe> findFuncionarioEquipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FuncionarioEquipe.class));
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

    public FuncionarioEquipe findFuncionarioEquipe(FuncionarioEquipePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FuncionarioEquipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioEquipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FuncionarioEquipe> rt = cq.from(FuncionarioEquipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
