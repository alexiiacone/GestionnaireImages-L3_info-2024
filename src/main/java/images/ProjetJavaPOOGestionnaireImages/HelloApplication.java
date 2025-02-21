//--------------------------------------//
//---HENAULT ARCHER Noah et IACONE Alexi//
//---------HelloApplication.java--------//
//--------------------------------------//

//---------package et importation----------//
package images.ProjetJavaPOOGestionnaireImages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

//---------Classe principale du main----------//
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        int TailleX = 1245;
        int TailleY = 670;
        Scene scene = new Scene(root, TailleX, TailleY);
        stage.setMinWidth(TailleX);
        stage.setMinHeight(TailleY);
        stage.setMaxWidth(TailleX);
        stage.setMaxHeight(TailleY);
        stage.setTitle("Gestionnaire d'image!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
