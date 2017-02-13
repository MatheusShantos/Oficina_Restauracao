/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author israel
 */
@Entity
@Table(name = "proprietario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proprietario.findAll", query = "SELECT p FROM Proprietario p")
    , @NamedQuery(name = "Proprietario.findByCpf", query = "SELECT p FROM Proprietario p WHERE p.cpf = :cpf")
    , @NamedQuery(name = "Proprietario.findByNome", query = "SELECT p FROM Proprietario p WHERE p.nome = :nome")
    , @NamedQuery(name = "Proprietario.findByRg", query = "SELECT p FROM Proprietario p WHERE p.rg = :rg")
    , @NamedQuery(name = "Proprietario.findByNascimento", query = "SELECT p FROM Proprietario p WHERE p.nascimento = :nascimento")
    , @NamedQuery(name = "Proprietario.findByEmails", query = "SELECT p FROM Proprietario p WHERE p.emails = :emails")
    , @NamedQuery(name = "Proprietario.findByEstado", query = "SELECT p FROM Proprietario p WHERE p.estado = :estado")
    , @NamedQuery(name = "Proprietario.findByCidade", query = "SELECT p FROM Proprietario p WHERE p.cidade = :cidade")
    , @NamedQuery(name = "Proprietario.findByEndereco", query = "SELECT p FROM Proprietario p WHERE p.endereco = :endereco")
    , @NamedQuery(name = "Proprietario.findBySexo", query = "SELECT p FROM Proprietario p WHERE p.sexo = :sexo")
    , @NamedQuery(name = "Proprietario.findByTelefones", query = "SELECT p FROM Proprietario p WHERE p.telefones = :telefones")})
public class Proprietario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cpf")
    private String cpf;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "rg")
    private String rg;
    @Basic(optional = false)
    @Column(name = "nascimento")
    @Temporal(TemporalType.DATE)
    private Date nascimento;
    @Basic(optional = false)
    @Column(name = "emails")
    private String emails;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "cidade")
    private String cidade;
    @Basic(optional = false)
    @Column(name = "endereco")
    private String endereco;
    @Basic(optional = false)
    @Column(name = "sexo")
    private String sexo;
    @Basic(optional = false)
    @Column(name = "telefones")
    private String telefones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proprietario")
    private List<Objeto> objetoList;

    public Proprietario() {
    }

    public Proprietario(String cpf) {
        this.cpf = cpf;
    }

    public Proprietario(String cpf, String nome, String rg, Date nascimento, String emails, String estado, String cidade, String endereco, String sexo, String telefones) {
        this.cpf = cpf;
        this.nome = nome;
        this.rg = rg;
        this.nascimento = nascimento;
        this.emails = emails;
        this.estado = estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.sexo = sexo;
        this.telefones = telefones;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefones() {
        return telefones;
    }

    public void setTelefones(String telefones) {
        this.telefones = telefones;
    }

    @XmlTransient
    public List<Objeto> getObjetoList() {
        return objetoList;
    }

    public void setObjetoList(List<Objeto> objetoList) {
        this.objetoList = objetoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpf != null ? cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proprietario)) {
            return false;
        }
        Proprietario other = (Proprietario) object;
        if ((this.cpf == null && other.cpf != null) || (this.cpf != null && !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.offcina.model.Proprietario[ cpf=" + cpf + " ]";
    }
    
}
