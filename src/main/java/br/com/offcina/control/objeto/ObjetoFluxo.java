
package br.com.offcina.control.objeto;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ObjetoFluxo {
    
    private static Stage stage = null;
    private static ObjetoFluxo fluxo = null;
    
    public ObjetoFluxo(){
         if(stage == null){
            stage = new Stage();
        }
    }
    
    public static ObjetoFluxo fluxo(){
        
        if(fluxo == null){
            fluxo = new ObjetoFluxo();
        }
        return fluxo;
    }
    
    public void carregaListar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/objeto/Listar.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Listagem de Objetos");
        stage.setScene(scene);
        stage.show();
    }
    
    public void carregaCadastro() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/objeto/Cadastro.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Cadastro de Objetos");
        stage.setScene(scene);
        stage.show();
    }
}

    
    

