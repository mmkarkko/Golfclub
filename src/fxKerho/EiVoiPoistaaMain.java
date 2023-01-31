package fxKerho;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Miia Arkko
 * @version 31.1.2023
 *
 */
public class EiVoiPoistaaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("EiVoiPoistaaGUIView.fxml"));
            final Pane root = ldr.load();
            //final EiVoiPoistaaGUIController eivoipoistaaCtrl = (EiVoiPoistaaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("eivoipoistaa.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("EiVoiPoistaa");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}