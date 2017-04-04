
package br.com.offcina.funcionario;


import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FuncionarioFluxo {
    
    private static Stage stage = null;
    private static FuncionarioFluxo fluxo = null;

    public FuncionarioFluxo() {
        if(stage == null){
            stage = new Stage();
        }
    }
        
    
    public static FuncionarioFluxo fluxo(){
        
        if(fluxo == null){
            fluxo = new FuncionarioFluxo();
        }
        return fluxo;
    }
    
    public void carregaListar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/Funcionario/Listar.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Listagem de Funcionario");
        stage.setScene(scene);
        stage.show();
    }
    
    public void carregaCadastro() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/funcionario/Cadastro.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Cadastro de funcionario");
        stage.setScene(scene);
        stage.show();
    }
}

    

