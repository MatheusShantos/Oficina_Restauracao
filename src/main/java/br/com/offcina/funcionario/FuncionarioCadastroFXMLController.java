
package br.com.offcina.funcionario;

import br.com.offcina.control.util.JPAControllerFactory;
import br.com.offcina.model.Funcionario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class FuncionarioCadastroFXMLController implements Initializable {

    @FXML
    private TextField textFieldNome;
    
    @FXML
    private TextField textFieldSexo;
    
    @FXML
    private TextField textFieldRG;
    
    @FXML
    private TextField textFieldNascimento;
    
    @FXML
    private TextField textFieldCPF;
    
    @FXML
    private TextField textFieldCarteira;
    
    @FXML
    private TextField textFieldCargo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
     @FXML
    void handleSalvar(ActionEvent event) throws Exception {

        try {
            Funcionario funcionario = new Funcionario();

            funcionario.setNome(textFieldNome.getText());
            //funcionario.setCargo(textFieldCargo.getText());
            funcionario.setCpf(textFieldCPF.getText());
            funcionario.setCarteira(textFieldCarteira.getText());
            //funcionario.setNascimento(textFieldNascimento.getText());
            funcionario.setRg(textFieldRG.getText());
            funcionario.setSexo(textFieldSexo.getText());
            

            JPAControllerFactory.funcionarioController().create(funcionario);
            
            FuncionarioFluxo.fluxo().carregaListar();
            
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioCadastroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}    
    

