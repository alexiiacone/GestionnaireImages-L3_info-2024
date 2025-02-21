//--------------------------------------//
//---HENAULT ARCHER Noah et IACONE Alexi//
//----------GestionLogin.java-----------//
//--------------------------------------//

//---------package et importation----------//
package images.ProjetJavaPOOGestionnaireImages;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//---------Déclaration de classe avec son attribut----------//
public class GestionLogin {
    private final String motDePasse = "aze";

//---------Méthodes----------//
    public boolean verifierMotDePasse(String essaiMotDePasse) {
        //Méthode pour vérifier si on rentre le bon mot de passe
        return essaiMotDePasse.equals(motDePasse);
    }

    public void ChangementDeScene(ActionEvent event) throws IOException {
        //Méthode pour effectuer un changement de scène entre login.fxml à application.fxml
        Parent root = FXMLLoader.load(getClass().getResource("application.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
