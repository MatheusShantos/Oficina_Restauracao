
package br.com.offcina.control.objeto;

import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Objeto;
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


public class ObjetoCadastroFXMLController implements Initializable {
    
    @FXML
    private TextField textFieldProprietario;
    
    @FXML
    private TextArea textAreaDescricao;
    
    @FXML
    private TextArea textAreaObservacoes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
        void handleSalvar(ActionEvent event) {

        try {
            Objeto objeto = new Objeto();

            //objeto.setProprietario(textFieldProprietario.getText());
            objeto.setDescricao(textAreaDescricao.getText());
            objeto.setObservacoes(textAreaObservacoes.getText());

            JPAControllerFactory.objetoController().create(objeto);
            
            ObjetoFluxo.fluxo().carregaListar();
            
        } catch (IOException ex) {
            Logger.getLogger(ObjetoCadastroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
    

