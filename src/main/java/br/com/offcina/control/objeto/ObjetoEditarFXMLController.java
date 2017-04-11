
package br.com.offcina.control.objeto;

import br.com.offcina.control.exceptions.NonexistentEntityException;
import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Objeto;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ObjetoEditarFXMLController implements Initializable {
    
    private Objeto objeto;
    
    @FXML
    private TextField textFieldProprietario;
    
    @FXML
    private TextArea textAreaDescricao;
    
    @FXML
    private TextArea textAreaObservacoes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    public Objeto getObjeto(){
        return objeto;
    }
    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
        lerObjeto();
    }
    private void lerObjeto(){
        //textFieldProprietario.setText(objeto.getProprietario());
        textAreaDescricao.setText(objeto.getDescricao());
        textAreaObservacoes.setText(objeto.getObservacoes());
    }
    public void escreveObjeto(){
        //objeto.setProprietario(textFieldProprietario.getText());
        objeto.setDescricao(textAreaDescricao.getText());
        objeto.setObservacoes(textAreaObservacoes.getText());
    }
    @FXML
    public void handleSalvar() {
        try {
            escreveObjeto();
            JPAControllerFactory.objetoController().edit(objeto);
            ObjetoFluxo.fluxo().carregaListar();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ObjetoEditarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ObjetoEditarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
