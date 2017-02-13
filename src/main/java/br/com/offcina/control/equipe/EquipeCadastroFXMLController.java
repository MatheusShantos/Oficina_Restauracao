/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control.equipe;

import br.com.offcina.control.cargo.CargoCadastroFXMLController;
import br.com.offcina.control.cargo.CargoFluxo;
import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Cargo;
import br.com.offcina.model.Equipe;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author israel
 */
public class EquipeCadastroFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private TextArea textAreaDescricao;

    @FXML
    private TextField textFieldNome;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void handleSalvar(ActionEvent event) {

        try {
            Equipe equipe = new Equipe();

            equipe.setNome(textFieldNome.getText());
            equipe.setDescricao(textAreaDescricao.getText());

            JPAControllerFactory.equipeController().create(equipe);
            
            EquipeFluxo.fluxo().carregaListar();
            
        } catch (IOException ex) {
            Logger.getLogger(CargoCadastroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
