/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control;

import br.com.offcina.control.exceptions.IllegalOrphanException;
import br.com.offcina.control.exceptions.NonexistentEntityException;
import br.com.offcina.model.Objeto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.offcina.model.Proprietario;
import br.com.offcina.model.Projeto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author israel
 */
public class ObjetoJpaController implements Serializable {

    public ObjetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Objeto objeto) {
        if (objeto.getProjetoList() == null) {
            objeto.setProjetoList(new ArrayList<Projeto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proprietario proprietario = objeto.getProprietario();
            if (proprietario != null) {
                proprietario = em.getReference(proprietario.getClass(), proprietario.getCpf());
                objeto.setProprietario(proprietario);
            }
            List<Projeto> attachedProjetoList = new ArrayList<Projeto>();
            for (Projeto projetoListProjetoToAttach : objeto.getProjetoList()) {
                projetoListProjetoToAttach = em.getReference(projetoListProjetoToAttach.getClass(), projetoListProjetoToAttach.getId());
                attachedProjetoList.add(projetoListProjetoToAttach);
            }
            objeto.setProjetoList(attachedProjetoList);
            em.persist(objeto);
            if (proprietario != null) {
                proprietario.getObjetoList().add(objeto);
                proprietario = em.merge(proprietario);
            }
            for (Projeto projetoListProjeto : objeto.getProjetoList()) {
                Objeto oldObjetoOfProjetoListProjeto = projetoListProjeto.getObjeto();
                projetoListProjeto.setObjeto(objeto);
                projetoListProjeto = em.merge(projetoListProjeto);
                if (oldObjetoOfProjetoListProjeto != null) {
                    oldObjetoOfProjetoListProjeto.getProjetoList().remove(projetoListProjeto);
                    oldObjetoOfProjetoListProjeto = em.merge(oldObjetoOfProjetoListProjeto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Objeto objeto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objeto persistentObjeto = em.find(Objeto.class, objeto.getId());
            Proprietario proprietarioOld = persistentObjeto.getProprietario();
            Proprietario proprietarioNew = objeto.getProprietario();
            List<Projeto> projetoListOld = persistentObjeto.getProjetoList();
            List<Projeto> projetoListNew = objeto.getProjetoList();
            List<String> illegalOrphanMessages = null;
            for (Projeto projetoListOldProjeto : projetoListOld) {
                if (!projetoListNew.contains(projetoListOldProjeto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Projeto " + projetoListOldProjeto + " since its objeto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (proprietarioNew != null) {
                proprietarioNew = em.getReference(proprietarioNew.getClass(), proprietarioNew.getCpf());
                objeto.setProprietario(proprietarioNew);
            }
            List<Projeto> attachedProjetoListNew = new ArrayList<Projeto>();
            for (Projeto projetoListNewProjetoToAttach : projetoListNew) {
                projetoListNewProjetoToAttach = em.getReference(projetoListNewProjetoToAttach.getClass(), projetoListNewProjetoToAttach.getId());
                attachedProjetoListNew.add(projetoListNewProjetoToAttach);
            }
            projetoListNew = attachedProjetoListNew;
            objeto.setProjetoList(projetoListNew);
            objeto = em.merge(objeto);
            if (proprietarioOld != null && !proprietarioOld.equals(proprietarioNew)) {
                proprietarioOld.getObjetoList().remove(objeto);
                proprietarioOld = em.merge(proprietarioOld);
            }
            if (proprietarioNew != null && !proprietarioNew.equals(proprietarioOld)) {
                proprietarioNew.getObjetoList().add(objeto);
                proprietarioNew = em.merge(proprietarioNew);
            }
            for (Projeto projetoListNewProjeto : projetoListNew) {
                if (!projetoListOld.contains(projetoListNewProjeto)) {
                    Objeto oldObjetoOfProjetoListNewProjeto = projetoListNewProjeto.getObjeto();
                    projetoListNewProjeto.setObjeto(objeto);
                    projetoListNewProjeto = em.merge(projetoListNewProjeto);
                    if (oldObjetoOfProjetoListNewProjeto != null && !oldObjetoOfProjetoListNewProjeto.equals(objeto)) {
                        oldObjetoOfProjetoListNewProjeto.getProjetoList().remove(projetoListNewProjeto);
                        oldObjetoOfProjetoListNewProjeto = em.merge(oldObjetoOfProjetoListNewProjeto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = objeto.getId();
                if (findObjeto(id) == null) {
                    throw new NonexistentEntityException("The objeto with id " + id + " no longer exists.");
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
            Objeto objeto;
            try {
                objeto = em.getReference(Objeto.class, id);
                objeto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objeto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Projeto> projetoListOrphanCheck = objeto.getProjetoList();
            for (Projeto projetoListOrphanCheckProjeto : projetoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Objeto (" + objeto + ") cannot be destroyed since the Projeto " + projetoListOrphanCheckProjeto + " in its projetoList field has a non-nullable objeto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proprietario proprietario = objeto.getProprietario();
            if (proprietario != null) {
                proprietario.getObjetoList().remove(objeto);
                proprietario = em.merge(proprietario);
            }
            em.remove(objeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Objeto> findObjetoEntities() {
        return findObjetoEntities(true, -1, -1);
    }

    public List<Objeto> findObjetoEntities(int maxResults, int firstResult) {
        return findObjetoEntities(false, maxResults, firstResult);
    }

    private List<Objeto> findObjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Objeto.class));
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

    public Objeto findObjeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Objeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Objeto> rt = cq.from(Objeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
