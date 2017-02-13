
package br.com.offcina.control.equipe;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author israel
 */
public class EquipeFluxo {
    
    private static Stage stage = null;
    private static EquipeFluxo fluxo = null;

    public EquipeFluxo() {
        if(stage == null){
            stage = new Stage();
        }
    }
        
    
    public static EquipeFluxo fluxo(){
        
        if(fluxo == null){
            fluxo = new EquipeFluxo();
        }
        return fluxo;
    }
    
    public void carregaListar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/equipe/Listar.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Listagem de Equipes");
        stage.setScene(scene);
        stage.show();
    }
    
    public void carregaCadastro() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/equipe/Cadastro.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Cadastro de Equipes");
        stage.setScene(scene);
        stage.show();
    }
}
