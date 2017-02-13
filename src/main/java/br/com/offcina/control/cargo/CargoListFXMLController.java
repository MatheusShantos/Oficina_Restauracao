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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author israel
 */
public class CargoListFXMLController implements Initializable {

    @FXML
    private TableView<Cargo> tableViewCargo;

    @FXML
    private TableColumn<Cargo, Integer> tableColumnCargoID;

    @FXML
    private TableColumn<Cargo, String> tableColumnCargoNome;

    @FXML
    private TextField textFieldPesquisa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleAtualizaTabela();
    }

    @FXML
    public void handleAtualizaTabela() {
        tableColumnCargoID.setCellValueFactory(new PropertyValueFactory("id"));
        tableColumnCargoNome.setCellValueFactory(new PropertyValueFactory("nome"));

        tableViewCargo.setItems(FXCollections.observableList(JPAControllerFactory.cargoController().findCargoEntities()));
    }

    @FXML
    public void handleEditar() {
        try {
            Cargo cargo = tableViewCargo.getSelectionModel().getSelectedItem();
            if (cargo != null) {
                CargoFluxo.fluxo().carregaEditar(cargo);
            }
        } catch (IOException ex) {
            Logger.getLogger(CargoListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @FXML
    public void handleNovo(){
        try {
            CargoFluxo.fluxo().carregaCadastro();
        } catch (IOException ex) {
            Logger.getLogger(CargoListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
