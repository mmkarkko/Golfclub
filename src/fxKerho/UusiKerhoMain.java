package fxKerho;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author miiaa
 * @version 31.1.2023
 *
 */
public class UusiKerhoMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("UusiKerhoGUIView.fxml"));
            final Pane root = ldr.load();
            //final UusiKerhoGUIController uusikerhoCtrl = (UusiKerhoGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("uusikerho.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("UusiKerho");
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