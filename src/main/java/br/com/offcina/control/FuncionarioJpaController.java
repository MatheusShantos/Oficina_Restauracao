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
import br.com.offcina.model.Cargo;
import br.com.offcina.model.Funcionario;
import br.com.offcina.model.FuncionarioEquipe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author israel
 */
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) throws PreexistingEntityException, Exception {
        if (funcionario.getFuncionarioEquipeList() == null) {
            funcionario.setFuncionarioEquipeList(new ArrayList<FuncionarioEquipe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo cargo = funcionario.getCargo();
            if (cargo != null) {
                cargo = em.getReference(cargo.getClass(), cargo.getId());
                funcionario.setCargo(cargo);
            }
            List<FuncionarioEquipe> attachedFuncionarioEquipeList = new ArrayList<FuncionarioEquipe>();
            for (FuncionarioEquipe funcionarioEquipeListFuncionarioEquipeToAttach : funcionario.getFuncionarioEquipeList()) {
                funcionarioEquipeListFuncionarioEquipeToAttach = em.getReference(funcionarioEquipeListFuncionarioEquipeToAttach.getClass(), funcionarioEquipeListFuncionarioEquipeToAttach.getFuncionarioEquipePK());
                attachedFuncionarioEquipeList.add(funcionarioEquipeListFuncionarioEquipeToAttach);
            }
            funcionario.setFuncionarioEquipeList(attachedFuncionarioEquipeList);
            em.persist(funcionario);
            if (cargo != null) {
                cargo.getFuncionarioList().add(funcionario);
                cargo = em.merge(cargo);
            }
            for (FuncionarioEquipe funcionarioEquipeListFuncionarioEquipe : funcionario.getFuncionarioEquipeList()) {
                Funcionario oldFuncionario1OfFuncionarioEquipeListFuncionarioEquipe = funcionarioEquipeListFuncionarioEquipe.getFuncionario1();
                funcionarioEquipeListFuncionarioEquipe.setFuncionario1(funcionario);
                funcionarioEquipeListFuncionarioEquipe = em.merge(funcionarioEquipeListFuncionarioEquipe);
                if (oldFuncionario1OfFuncionarioEquipeListFuncionarioEquipe != null) {
                    oldFuncionario1OfFuncionarioEquipeListFuncionarioEquipe.getFuncionarioEquipeList().remove(funcionarioEquipeListFuncionarioEquipe);
                    oldFuncionario1OfFuncionarioEquipeListFuncionarioEquipe = em.merge(oldFuncionario1OfFuncionarioEquipeListFuncionarioEquipe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionario(funcionario.getCpf()) != null) {
                throw new PreexistingEntityException("Funcionario " + funcionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getCpf());
            Cargo cargoOld = persistentFuncionario.getCargo();
            Cargo cargoNew = funcionario.getCargo();
            List<FuncionarioEquipe> funcionarioEquipeListOld = persistentFuncionario.getFuncionarioEquipeList();
            List<FuncionarioEquipe> funcionarioEquipeListNew = funcionario.getFuncionarioEquipeList();
            List<String> illegalOrphanMessages = null;
            for (FuncionarioEquipe funcionarioEquipeListOldFuncionarioEquipe : funcionarioEquipeListOld) {
                if (!funcionarioEquipeListNew.contains(funcionarioEquipeListOldFuncionarioEquipe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FuncionarioEquipe " + funcionarioEquipeListOldFuncionarioEquipe + " since its funcionario1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cargoNew != null) {
                cargoNew = em.getReference(cargoNew.getClass(), cargoNew.getId());
                funcionario.setCargo(cargoNew);
            }
            List<FuncionarioEquipe> attachedFuncionarioEquipeListNew = new ArrayList<FuncionarioEquipe>();
            for (FuncionarioEquipe funcionarioEquipeListNewFuncionarioEquipeToAttach : funcionarioEquipeListNew) {
                funcionarioEquipeListNewFuncionarioEquipeToAttach = em.getReference(funcionarioEquipeListNewFuncionarioEquipeToAttach.getClass(), funcionarioEquipeListNewFuncionarioEquipeToAttach.getFuncionarioEquipePK());
                attachedFuncionarioEquipeListNew.add(funcionarioEquipeListNewFuncionarioEquipeToAttach);
            }
            funcionarioEquipeListNew = attachedFuncionarioEquipeListNew;
            funcionario.setFuncionarioEquipeList(funcionarioEquipeListNew);
            funcionario = em.merge(funcionario);
            if (cargoOld != null && !cargoOld.equals(cargoNew)) {
                cargoOld.getFuncionarioList().remove(funcionario);
                cargoOld = em.merge(cargoOld);
            }
            if (cargoNew != null && !cargoNew.equals(cargoOld)) {
                cargoNew.getFuncionarioList().add(funcionario);
                cargoNew = em.merge(cargoNew);
            }
            for (FuncionarioEquipe funcionarioEquipeListNewFuncionarioEquipe : funcionarioEquipeListNew) {
                if (!funcionarioEquipeListOld.contains(funcionarioEquipeListNewFuncionarioEquipe)) {
                    Funcionario oldFuncionario1OfFuncionarioEquipeListNewFuncionarioEquipe = funcionarioEquipeListNewFuncionarioEquipe.getFuncionario1();
                    funcionarioEquipeListNewFuncionarioEquipe.setFuncionario1(funcionario);
                    funcionarioEquipeListNewFuncionarioEquipe = em.merge(funcionarioEquipeListNewFuncionarioEquipe);
                    if (oldFuncionario1OfFuncionarioEquipeListNewFuncionarioEquipe != null && !oldFuncionario1OfFuncionarioEquipeListNewFuncionarioEquipe.equals(funcionario)) {
                        oldFuncionario1OfFuncionarioEquipeListNewFuncionarioEquipe.getFuncionarioEquipeList().remove(funcionarioEquipeListNewFuncionarioEquipe);
                        oldFuncionario1OfFuncionarioEquipeListNewFuncionarioEquipe = em.merge(oldFuncionario1OfFuncionarioEquipeListNewFuncionarioEquipe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = funcionario.getCpf();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getCpf();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<FuncionarioEquipe> funcionarioEquipeListOrphanCheck = funcionario.getFuncionarioEquipeList();
            for (FuncionarioEquipe funcionarioEquipeListOrphanCheckFuncionarioEquipe : funcionarioEquipeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the FuncionarioEquipe " + funcionarioEquipeListOrphanCheckFuncionarioEquipe + " in its funcionarioEquipeList field has a non-nullable funcionario1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cargo cargo = funcionario.getCargo();
            if (cargo != null) {
                cargo.getFuncionarioList().remove(funcionario);
                cargo = em.merge(cargo);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
