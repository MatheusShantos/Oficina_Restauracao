/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control.cargo;

import br.com.offcina.model.Cargo;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author israel
 */
public class CargoFluxo {

    private static Stage stage = null;
    private static CargoFluxo fluxo = null;

    public CargoFluxo() {
        if (stage == null) {
            stage = new Stage();
        }
    }

    public static CargoFluxo fluxo() {
        if (fluxo == null) {
            fluxo = new CargoFluxo();
        }
        return fluxo;
    }

    public void carregaVisualizar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/cargo/Visualizar.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Visualizaçao de Cargos");
        stage.setScene(scene);
        stage.show();
    }

    public void carregaCadastro() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/cargo/Cadastro.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Cadastro de Cargos");
        stage.setScene(scene);
        stage.show();
    }

    public void carregaEditar(Cargo cargo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/offcina/view/cargo/Editar.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        CargoEditarFXMLController controller = loader.<CargoEditarFXMLController>getController();
        controller.setCargo(cargo);

        stage.setTitle("Ediçao de Cargos");
        stage.setScene(scene);
        stage.show();
    }

    public void carregaListar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/offcina/view/cargo/Lista.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Listagem de Cargos");
        stage.setScene(scene);
        stage.show();
    }

    public void fechar() {
        stage.close();
    }

}
