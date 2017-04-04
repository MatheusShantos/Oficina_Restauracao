
package br.com.offcina.control.tipoprojeto;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TipoProjetoFluxo {
    
    private static Stage stage = null;
    private static TipoProjetoFluxo fluxo = null;
    
    public TipoProjetoFluxo(){
         if(stage == null){
            stage = new Stage();
        }
    }
    
    public static TipoProjetoFluxo fluxo(){
        
        if(fluxo == null){
            fluxo = new TipoProjetoFluxo();
        }
        return fluxo;
    }
    
    public void carregaListar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/tipoprojetofluxo/Listar.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Listagem de Tipo de projeto");
        stage.setScene(scene);
        stage.show();
    }
    
    public void carregaCadastro() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/tipoprojetofluxo/Cadastro.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Cadastro de Tipo de projeto");
        stage.setScene(scene);
        stage.show();
    }
}

    
    


    

