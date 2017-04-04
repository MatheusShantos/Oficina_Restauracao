
package br.com.offcina.control.Proprietario;

import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Proprietario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class ProprietarioCadastroFXMLController implements Initializable {
    
    @FXML
    private TextField textFieldNome;
    
    @FXML
    private TextField textFieldSexo;
    
    @FXML
    private TextField textFieldRG;
    
    @FXML
    private TextField textFieldCPF;
    
    @FXML
    private TextField textFieldNascimento;
    
    @FXML
    private TextField textFieldEmail;
    
    @FXML
    private TextField textFieldEstado;
    
    @FXML
    private TextField textFieldCidade;
    
    @FXML
    private TextField textFieldEndereco;
    
    @FXML
    private TextField textFieldTelefone;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    
    void handleSalvar(ActionEvent event) throws Exception {
        
        try {
            Proprietario proprietario = new Proprietario();

            proprietario.setNome(textFieldNome.getText());
            proprietario.setSexo(textFieldSexo.getText());
            //proprietario.setRg(textFieldRG.get());
            proprietario.setCpf(textFieldCPF.getText());
            //proprietario.setNascimento(textFieldNascimento.get);
            proprietario.setEndereco(textFieldEndereco.getText());
            proprietario.setEmails(textFieldEmail.getText());
            proprietario.setCidade(textFieldCidade.getText());
            proprietario.setEstado(textFieldEstado.getText());
            proprietario.setTelefones(textFieldTelefone.getText());
            
           

            JPAControllerFactory.proprietarioController().create(proprietario);
            
            ProprietarioFluxo.fluxo().carregaListar();
            
        } catch (IOException ex) {
            Logger.getLogger(ProprietarioCadastroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
