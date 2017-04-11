
package br.com.offcina.control.util;

import br.com.offcina.control.FuncionarioJpaController;
import br.com.offcina.control.ObjetoJpaController;
import br.com.offcina.control.ProjetoJpaController;
import br.com.offcina.control.ProprietarioJpaController;
import br.com.offcina.control.TipoProjetoJpaController;
import br.com.offcina.control.cargo.CargoJpaController;
import br.com.offcina.control.equipe.EquipeJpaController;



public class JPAControllerFactory {

    private static CargoJpaController cargoController = null;
    private static EquipeJpaController equipeController = null;
    private static ProprietarioJpaController proprietarioController = null;
    private static ObjetoJpaController objetoController = null;
    private static ProjetoJpaController projetoController = null;
    private static TipoProjetoJpaController tipoProjetoController = null;
    private static FuncionarioJpaController funcionarioController = null;

    public static CargoJpaController cargoController() {
        if (cargoController == null) {
            cargoController = new CargoJpaController(JPAUtil.entityManagerFactory());
        }
        return cargoController;
    }
    public static EquipeJpaController equipeController(){
        if(equipeController == null){
            equipeController = new EquipeJpaController(JPAUtil.entityManagerFactory());
            
        }
        return equipeController;
    }
    public static ProprietarioJpaController proprietarioController(){
        if(proprietarioController == null){
            proprietarioController = new ProprietarioJpaController(JPAUtil.entityManagerFactory());
            
        }
        return proprietarioController;
    }
    public static ObjetoJpaController objetoController(){
        if(objetoController == null){
            objetoController = new ObjetoJpaController(JPAUtil.entityManagerFactory());
            
        }
        return objetoController;
    }
    public static ProjetoJpaController projetoController(){
        if(projetoController == null){
            projetoController = new ProjetoJpaController(JPAUtil.entityManagerFactory());
        }
        return projetoController;
    }
    public static TipoProjetoJpaController tipoProjetoController(){
        if(tipoProjetoController == null){
            tipoProjetoController = new TipoProjetoJpaController(JPAUtil.entityManagerFactory());
        }
        return tipoProjetoController;
    }
    public static FuncionarioJpaController funcionarioController(){
        if(funcionarioController == null){
            funcionarioController = new FuncionarioJpaController(JPAUtil.entityManagerFactory());
        }
        return funcionarioController;
    }

    public static Object orcamentoController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
