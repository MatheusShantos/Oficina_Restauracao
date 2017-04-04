
package br.com.offcina.control.orcamento;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class OrcamentoFluxo {
    
    private static Stage stage = null;
    private static OrcamentoFluxo fluxo = null;
    
    public OrcamentoFluxo(){
        if(stage == null){
            stage = new Stage();
        }
    }
    
    public static OrcamentoFluxo fluxo(){
        
        if(fluxo == null){
            fluxo = new OrcamentoFluxo();
        }
        return fluxo;
    }
    
    public void carregaListar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/orcamento/Listar.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Listagem de Orçamentos");
        stage.setScene(scene);
        stage.show();
    }
    
    public void carregaCadastro() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/orcamento/Cadastro.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Cadastro de Orçamentos");
        stage.setScene(scene);
        stage.show();
    }
    
}
