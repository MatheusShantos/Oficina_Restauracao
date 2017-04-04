
package br.com.offcina.control.tipoprojeto;

import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.TipoProjeto;
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


public class TipoProjetoCadastroFXMLController implements Initializable {
    
    @FXML
    private TextField textFieldNome;
    
    @FXML
    private TextField textFieldValor;
    
    @FXML
    private TextArea textAreaDescricao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    @FXML
    void handleSalvar(ActionEvent event) {
              
        try {
            TipoProjeto tipoProjeto = new TipoProjeto();
            
            tipoProjeto.setNome(textFieldNome.getText());
            //tipoProjeto.setValor(textFieldValor.get);
            tipoProjeto.setDescricao(textAreaDescricao.getText());
            
            JPAControllerFactory.tipoProjetoController().create(tipoProjeto);
            
            TipoProjetoFluxo.fluxo().carregaListar();
            
            } catch (IOException ex) {
            Logger.getLogger(TipoProjetoCadastroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        
    }
            

}
