
package br.com.offcina.control.projeto;

import br.com.offcina.control.equipe.EquipeCadastroFXMLController;
import br.com.offcina.control.equipe.EquipeFluxo;
import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Projeto;
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


public class ProjetoCadastroFXMLController implements Initializable {
    
    @FXML
    private TextField textFieldTipoProjeto;
    
    @FXML
    private TextField textFieldStatus;
    
    @FXML
    private TextField textFieldEquipe;
    
    @FXML
    private TextField textFieldObjeto;
    
    @FXML
    private TextArea textAreaObservacoes;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    @FXML
    void handleSalvar(ActionEvent event) {
        
         try {
            Projeto projeto = new Projeto();
            
          
            JPAControllerFactory.projetoController().create(projeto);
            
            EquipeFluxo.fluxo().carregaListar();
            
        } catch (IOException ex) {
            Logger.getLogger(EquipeCadastroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
