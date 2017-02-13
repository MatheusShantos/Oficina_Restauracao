/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author israel
 */
@Entity
@Table(name = "tipo_projeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProjeto.findAll", query = "SELECT t FROM TipoProjeto t")
    , @NamedQuery(name = "TipoProjeto.findById", query = "SELECT t FROM TipoProjeto t WHERE t.id = :id")
    , @NamedQuery(name = "TipoProjeto.findByDescricao", query = "SELECT t FROM TipoProjeto t WHERE t.descricao = :descricao")
    , @NamedQuery(name = "TipoProjeto.findByNome", query = "SELECT t FROM TipoProjeto t WHERE t.nome = :nome")
    , @NamedQuery(name = "TipoProjeto.findByValor", query = "SELECT t FROM TipoProjeto t WHERE t.valor = :valor")})
public class TipoProjeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoProjeto")
    private List<Projeto> projetoList;

    public TipoProjeto() {
    }

    public TipoProjeto(Integer id) {
        this.id = id;
    }

    public TipoProjeto(Integer id, String descricao, String nome, double valor) {
        this.id = id;
        this.descricao = descricao;
        this.nome = nome;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @XmlTransient
    public List<Projeto> getProjetoList() {
        return projetoList;
    }

    public void setProjetoList(List<Projeto> projetoList) {
        this.projetoList = projetoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProjeto)) {
            return false;
        }
        TipoProjeto other = (TipoProjeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.offcina.model.TipoProjeto[ id=" + id + " ]";
    }
    
}
