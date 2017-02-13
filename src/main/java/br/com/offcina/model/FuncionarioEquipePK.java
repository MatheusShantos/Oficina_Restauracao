/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author israel
 */
@Embeddable
public class FuncionarioEquipePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "funcionario")
    private String funcionario;
    @Basic(optional = false)
    @Column(name = "equipe")
    private int equipe;

    public FuncionarioEquipePK() {
    }

    public FuncionarioEquipePK(String funcionario, int equipe) {
        this.funcionario = funcionario;
        this.equipe = equipe;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public int getEquipe() {
        return equipe;
    }

    public void setEquipe(int equipe) {
        this.equipe = equipe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funcionario != null ? funcionario.hashCode() : 0);
        hash += (int) equipe;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuncionarioEquipePK)) {
            return false;
        }
        FuncionarioEquipePK other = (FuncionarioEquipePK) object;
        if ((this.funcionario == null && other.funcionario != null) || (this.funcionario != null && !this.funcionario.equals(other.funcionario))) {
            return false;
        }
        if (this.equipe != other.equipe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.offcina.model.FuncionarioEquipePK[ funcionario=" + funcionario + ", equipe=" + equipe + " ]";
    }
    
}
