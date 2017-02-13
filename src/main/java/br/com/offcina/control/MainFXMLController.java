/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control;

import br.com.offcina.control.cargo.CargoFluxo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author israel
 */
public class MainFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void handleCargoListar() {
        try {
            CargoFluxo.fluxo().carregaListar();
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleCargoCadastrar() {
        try {
            CargoFluxo.fluxo().carregaCadastro();
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Finaliza os fluxos das janelas para que a aplicaçao possa ser fechada
     */
    public void finalizar() {
        CargoFluxo.fluxo().fechar();
    }

}
