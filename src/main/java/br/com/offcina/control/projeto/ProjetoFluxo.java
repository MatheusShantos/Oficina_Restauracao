
package br.com.offcina.control.projeto;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ProjetoFluxo {
    
    private static Stage stage = null;
    private static ProjetoFluxo fluxo = null;
    
    public ProjetoFluxo(){
        if(stage == null){
            stage = new Stage();
        }
    }
    
    public static ProjetoFluxo fluxo(){
        if(fluxo == null){
            fluxo = new ProjetoFluxo();
        }
        return fluxo;
    }
    
        
        public void carregaListar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/projeto/Listar.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Listagem de Projetos");
        stage.setScene(scene);
        stage.show();
    }
    
    public void carregaCadastro() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/projetos/Cadastro.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Cadastro de Projetos");
        stage.setScene(scene);
        stage.show();
    }
}

    
    
    
  

 
    
    
        
    
    

