package fxKerho;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import kerho.Kerho;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * Pääohjelma Golfkerho-ohjelman käynnistämiseksi
 * @author Miia Arkko
 * @version 6.3.2023
 *
 */
public class KerhoMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KerhoGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final KerhoGUIController kerhoCtrl = (KerhoGUIController)ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kerho.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Golfkerho");
           
            // Platform.setImplicitExit(false); // jos tämän laittaa, pitää itse sulkea
            
            primaryStage.setOnCloseRequest((event) -> {
                // Kutsutaan voikoSulkea-metodia
                if ( !kerhoCtrl.voikoSulkea() ) event.consume();
            });
            
            Kerho kerho = new Kerho();
            kerhoCtrl.setKerho(kerho);
            
            primaryStage.show();
            
            Application.Parameters params = getParameters();
            if (params.getRaw().size() > 0)
                kerhoCtrl.lueTiedosto(params.getRaw().get(0));
            else 
                if (!kerhoCtrl.avaa()) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Käynnistää käyttöliittymän
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}