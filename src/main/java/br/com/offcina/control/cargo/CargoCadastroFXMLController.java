/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control.cargo;

import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Cargo;
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
public class CargoCadastroFXMLController implements Initializable {

    @FXML
    private TextArea textAreaDescricao;

    @FXML
    private TextField textFieldNome;

    @FXML
    void handleSalvar(ActionEvent event) {

        try {
            Cargo cargo = new Cargo();

            cargo.setNome(textFieldNome.getText());
            cargo.setDescricao(textAreaDescricao.getText());

            JPAControllerFactory.cargoController().create(cargo);
            
            CargoFluxo.fluxo().carregaListar();
            
        } catch (IOException ex) {
            Logger.getLogger(CargoCadastroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
