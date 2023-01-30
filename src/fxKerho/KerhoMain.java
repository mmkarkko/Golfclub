package fxKerho;
import fi.jyu.mit.fxgui.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Pääohjelma Golfkerho-ohjelman käynnistämiseksi
 * @author miiaa
 * @version 25.1.2023
 *
 */
public class KerhoMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KerhoGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final KerhoGUIController kerhoCtrl = (KerhoGUIController)ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kerho.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kerho");
            
            // Platform.setImplicitExit(false); // jos tämän laittaa, pitää itse sulkea
            
            primaryStage.setOnCloseRequest((event) -> {
                // Kutsutaan voikoSulkea-metodia
                if ( !kerhoCtrl.voikoSulkea() ) event.consume();
            });
            
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Käynnistää käyttöliittymän
     * 
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}