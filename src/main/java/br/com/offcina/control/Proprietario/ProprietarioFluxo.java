
package br.com.offcina.control.Proprietario;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ProprietarioFluxo {
    
    private static Stage stage = null;
    private static ProprietarioFluxo fluxo = null;
    
    public ProprietarioFluxo(){
         if(stage == null){
            stage = new Stage();
        }
    }
    
    public static ProprietarioFluxo fluxo(){
        
        if(fluxo == null){
            fluxo = new ProprietarioFluxo();
        }
        return fluxo;
    }
    
    public void carregaListar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/Proprietario/Listar.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Listagem de Proprietário");
        stage.setScene(scene);
        stage.show();
    }
    
    public void carregaCadastro() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/proprietario/Cadastro.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Cadastro de Proprietário");
        stage.setScene(scene);
        stage.show();
    }
}

    
    


    

