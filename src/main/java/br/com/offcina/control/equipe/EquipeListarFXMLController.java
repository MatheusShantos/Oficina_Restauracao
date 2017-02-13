/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control.equipe;

import br.com.offcina.control.cargo.CargoFluxo;
import br.com.offcina.control.cargo.CargoListFXMLController;
import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Equipe;
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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author israel
 */
public class EquipeListarFXMLController implements Initializable {
    
      @FXML
    private TableView<Equipe> tableViewEquipe;

    
     @FXML
    private TableColumn<Equipe, Integer> tableColumnID;

    @FXML
    private TableColumn<Equipe, String> tableColumnNome;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleAtualizaTabela();
        
    }    
     @FXML
    public void handleAtualizaTabela() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory("nome"));

        tableViewEquipe.setItems(FXCollections.observableList(JPAControllerFactory.equipeController().findEquipeEntities()));
    }
    
    @FXML
    public void handleNovo(){
          try {
              EquipeFluxo.fluxo().carregaCadastro();
          } catch (IOException ex) {
              Logger.getLogger(EquipeListarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
}
