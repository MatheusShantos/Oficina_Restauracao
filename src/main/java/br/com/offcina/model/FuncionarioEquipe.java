/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author israel
 */
@Entity
@Table(name = "funcionario_equipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FuncionarioEquipe.findAll", query = "SELECT f FROM FuncionarioEquipe f")
    , @NamedQuery(name = "FuncionarioEquipe.findByFuncionario", query = "SELECT f FROM FuncionarioEquipe f WHERE f.funcionarioEquipePK.funcionario = :funcionario")
    , @NamedQuery(name = "FuncionarioEquipe.findByEquipe", query = "SELECT f FROM FuncionarioEquipe f WHERE f.funcionarioEquipePK.equipe = :equipe")})
public class FuncionarioEquipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FuncionarioEquipePK funcionarioEquipePK;
    @JoinColumn(name = "funcionario", referencedColumnName = "cpf", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Funcionario funcionario1;

    public FuncionarioEquipe() {
    }

    public FuncionarioEquipe(FuncionarioEquipePK funcionarioEquipePK) {
        this.funcionarioEquipePK = funcionarioEquipePK;
    }

    public FuncionarioEquipe(String funcionario, int equipe) {
        this.funcionarioEquipePK = new FuncionarioEquipePK(funcionario, equipe);
    }

    public FuncionarioEquipePK getFuncionarioEquipePK() {
        return funcionarioEquipePK;
    }

    public void setFuncionarioEquipePK(FuncionarioEquipePK funcionarioEquipePK) {
        this.funcionarioEquipePK = funcionarioEquipePK;
    }

    public Funcionario getFuncionario1() {
        return funcionario1;
    }

    public void setFuncionario1(Funcionario funcionario1) {
        this.funcionario1 = funcionario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funcionarioEquipePK != null ? funcionarioEquipePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuncionarioEquipe)) {
            return false;
        }
        FuncionarioEquipe other = (FuncionarioEquipe) object;
        if ((this.funcionarioEquipePK == null && other.funcionarioEquipePK != null) || (this.funcionarioEquipePK != null && !this.funcionarioEquipePK.equals(other.funcionarioEquipePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.offcina.model.FuncionarioEquipe[ funcionarioEquipePK=" + funcionarioEquipePK + " ]";
    }
    
}
