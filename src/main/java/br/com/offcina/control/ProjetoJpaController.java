/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control;

import br.com.offcina.control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.offcina.model.Equipe;
import br.com.offcina.model.Objeto;
import br.com.offcina.model.TipoProjeto;
import br.com.offcina.model.Orcamento;
import br.com.offcina.model.Projeto;
import br.com.offcina.model.Proprietario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author israel
 */
public class ProjetoJpaController implements Serializable {

    public ProjetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Projeto projeto) {
        if (projeto.getOrcamentoList() == null) {
            projeto.setOrcamentoList(new ArrayList<Orcamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe equipe = projeto.getEquipe();
            if (equipe != null) {
                equipe = em.getReference(equipe.getClass(), equipe.getId());
                projeto.setEquipe(equipe);
            }
            Objeto objeto = projeto.getObjeto();
            if (objeto != null) {
                objeto = em.getReference(objeto.getClass(), objeto.getId());
                projeto.setObjeto(objeto);
            }
            TipoProjeto tipoProjeto = projeto.getTipoProjeto();
            if (tipoProjeto != null) {
                tipoProjeto = em.getReference(tipoProjeto.getClass(), tipoProjeto.getId());
                projeto.setTipoProjeto(tipoProjeto);
            }
            List<Orcamento> attachedOrcamentoList = new ArrayList<Orcamento>();
            for (Orcamento orcamentoListOrcamentoToAttach : projeto.getOrcamentoList()) {
                orcamentoListOrcamentoToAttach = em.getReference(orcamentoListOrcamentoToAttach.getClass(), orcamentoListOrcamentoToAttach.getId());
                attachedOrcamentoList.add(orcamentoListOrcamentoToAttach);
            }
            projeto.setOrcamentoList(attachedOrcamentoList);
            em.persist(projeto);
            if (equipe != null) {
                equipe.getProjetoList().add(projeto);
                equipe = em.merge(equipe);
            }
            if (objeto != null) {
                objeto.getProjetoList().add(projeto);
                objeto = em.merge(objeto);
            }
            if (tipoProjeto != null) {
                tipoProjeto.getProjetoList().add(projeto);
                tipoProjeto = em.merge(tipoProjeto);
            }
            for (Orcamento orcamentoListOrcamento : projeto.getOrcamentoList()) {
                orcamentoListOrcamento.getProjetoList().add(projeto);
                orcamentoListOrcamento = em.merge(orcamentoListOrcamento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Projeto projeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projeto persistentProjeto = em.find(Projeto.class, projeto.getId());
            Equipe equipeOld = persistentProjeto.getEquipe();
            Equipe equipeNew = projeto.getEquipe();
            Objeto objetoOld = persistentProjeto.getObjeto();
            Objeto objetoNew = projeto.getObjeto();
            TipoProjeto tipoProjetoOld = persistentProjeto.getTipoProjeto();
            TipoProjeto tipoProjetoNew = projeto.getTipoProjeto();
            List<Orcamento> orcamentoListOld = persistentProjeto.getOrcamentoList();
            List<Orcamento> orcamentoListNew = projeto.getOrcamentoList();
            if (equipeNew != null) {
                equipeNew = em.getReference(equipeNew.getClass(), equipeNew.getId());
                projeto.setEquipe(equipeNew);
            }
            if (objetoNew != null) {
                objetoNew = em.getReference(objetoNew.getClass(), objetoNew.getId());
                projeto.setObjeto(objetoNew);
            }
            if (tipoProjetoNew != null) {
                tipoProjetoNew = em.getReference(tipoProjetoNew.getClass(), tipoProjetoNew.getId());
                projeto.setTipoProjeto(tipoProjetoNew);
            }
            List<Orcamento> attachedOrcamentoListNew = new ArrayList<Orcamento>();
            for (Orcamento orcamentoListNewOrcamentoToAttach : orcamentoListNew) {
                orcamentoListNewOrcamentoToAttach = em.getReference(orcamentoListNewOrcamentoToAttach.getClass(), orcamentoListNewOrcamentoToAttach.getId());
                attachedOrcamentoListNew.add(orcamentoListNewOrcamentoToAttach);
            }
            orcamentoListNew = attachedOrcamentoListNew;
            projeto.setOrcamentoList(orcamentoListNew);
            projeto = em.merge(projeto);
            if (equipeOld != null && !equipeOld.equals(equipeNew)) {
                equipeOld.getProjetoList().remove(projeto);
                equipeOld = em.merge(equipeOld);
            }
            if (equipeNew != null && !equipeNew.equals(equipeOld)) {
                equipeNew.getProjetoList().add(projeto);
                equipeNew = em.merge(equipeNew);
            }
            if (objetoOld != null && !objetoOld.equals(objetoNew)) {
                objetoOld.getProjetoList().remove(projeto);
                objetoOld = em.merge(objetoOld);
            }
            if (objetoNew != null && !objetoNew.equals(objetoOld)) {
                objetoNew.getProjetoList().add(projeto);
                objetoNew = em.merge(objetoNew);
            }
            if (tipoProjetoOld != null && !tipoProjetoOld.equals(tipoProjetoNew)) {
                tipoProjetoOld.getProjetoList().remove(projeto);
                tipoProjetoOld = em.merge(tipoProjetoOld);
            }
            if (tipoProjetoNew != null && !tipoProjetoNew.equals(tipoProjetoOld)) {
                tipoProjetoNew.getProjetoList().add(projeto);
                tipoProjetoNew = em.merge(tipoProjetoNew);
            }
            for (Orcamento orcamentoListOldOrcamento : orcamentoListOld) {
                if (!orcamentoListNew.contains(orcamentoListOldOrcamento)) {
                    orcamentoListOldOrcamento.getProjetoList().remove(projeto);
                    orcamentoListOldOrcamento = em.merge(orcamentoListOldOrcamento);
                }
            }
            for (Orcamento orcamentoListNewOrcamento : orcamentoListNew) {
                if (!orcamentoListOld.contains(orcamentoListNewOrcamento)) {
                    orcamentoListNewOrcamento.getProjetoList().add(projeto);
                    orcamentoListNewOrcamento = em.merge(orcamentoListNewOrcamento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = projeto.getId();
                if (findProjeto(id) == null) {
                    throw new NonexistentEntityException("The projeto with id " + id + " no longer exists.");
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
            Projeto projeto;
            try {
                projeto = em.getReference(Projeto.class, id);
                projeto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projeto with id " + id + " no longer exists.", enfe);
            }
            Equipe equipe = projeto.getEquipe();
            if (equipe != null) {
                equipe.getProjetoList().remove(projeto);
                equipe = em.merge(equipe);
            }
            Objeto objeto = projeto.getObjeto();
            if (objeto != null) {
                objeto.getProjetoList().remove(projeto);
                objeto = em.merge(objeto);
            }
            TipoProjeto tipoProjeto = projeto.getTipoProjeto();
            if (tipoProjeto != null) {
                tipoProjeto.getProjetoList().remove(projeto);
                tipoProjeto = em.merge(tipoProjeto);
            }
            List<Orcamento> orcamentoList = projeto.getOrcamentoList();
            for (Orcamento orcamentoListOrcamento : orcamentoList) {
                orcamentoListOrcamento.getProjetoList().remove(projeto);
                orcamentoListOrcamento = em.merge(orcamentoListOrcamento);
            }
            em.remove(projeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Projeto> findProjetoEntities() {
        return findProjetoEntities(true, -1, -1);
    }

    public List<Projeto> findProjetoEntities(int maxResults, int firstResult) {
        return findProjetoEntities(false, maxResults, firstResult);
    }

    private List<Projeto> findProjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projeto.class));
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

    public Projeto findProjeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projeto> rt = cq.from(Projeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void edit(Proprietario proprietario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
