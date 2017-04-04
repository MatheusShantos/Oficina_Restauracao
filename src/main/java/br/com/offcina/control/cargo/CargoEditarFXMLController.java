/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control.cargo;

import br.com.offcina.control.exceptions.NonexistentEntityException;
import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Cargo;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class CargoEditarFXMLController implements Initializable {

    private Cargo cargo;

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextArea textAreaDescricao;

    @FXML
    private Label labelId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
        lerCargo();
    }

    private void lerCargo() {
        textFieldNome.setText(cargo.getNome());
        textAreaDescricao.setText(cargo.getDescricao());
        labelId.setText(String.valueOf(cargo.getId()));
    }

    public void escreveCargo() {
        cargo.setNome(textFieldNome.getText());
        cargo.setDescricao(textAreaDescricao.getText());
    }

    @FXML
    public void handleSalvar() {
        try {
            escreveCargo();
            JPAControllerFactory.cargoController().edit(cargo);
            CargoFluxo.fluxo().carregaListar();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CargoEditarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CargoEditarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
