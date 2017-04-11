
package br.com.offcina.control.Proprietario;

import br.com.offcina.control.exceptions.NonexistentEntityException;
import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Proprietario;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class ProprietarioEditarFXMLController implements Initializable {
    
    private Proprietario proprietario;
     
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
    }  
    
    public Proprietario getProprietario(){
        return proprietario;
    }
    
    public Proprietario setProprietario(Proprietario proprietario){
        this.proprietario = proprietario;
        lerProprietario();
    }
    
    private void lerProprietario() {
        textFieldNome.setText(proprietario.getNome());
        textFieldRG.setText(proprietario.getRg());
        textFieldCPF.setText(proprietario.getCpf());
        textFieldNascimento.setText(proprietario.getNascimento());
        textFieldEmail.setText(proprietario.getEmails());
        textFieldEstado.setText(proprietario.getEstado());
        textFieldEndereco.setText(proprietario.getEndereco());
        textFieldSexo.setText(proprietario.getSexo());
        textFieldTelefone.setText(proprietario.getTelefones());
       
       }
    public void escreveProprietario() {
        proprietario.setNome(textFieldNome.getText());
        proprietario.setRg(textFieldRG.getText());
        proprietario.setCpf(textFieldCPF.getText());
        proprietario.setNascimento(textFieldNascimento.getText());
        proprietario.setEmails(textFieldEmail.getText());
        proprietario.setEstado(textFieldEstado.getText());
        proprietario.setCidade(textFieldCidade.getText());
        proprietario.setEndereco(textFieldEndereco.getText());
        proprietario.setSexo(textFieldSexo.getText());
        proprietario.setTelefones(textFieldTelefone.getText());
    }
     @FXML
    public void handleSalvar() {
        try {
            escreveProprietario();
            JPAControllerFactory.projetoController().edit(proprietario);
            ProprietarioFluxo.fluxo().carregaListar();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProprietarioEditarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProprietarioEditarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    

