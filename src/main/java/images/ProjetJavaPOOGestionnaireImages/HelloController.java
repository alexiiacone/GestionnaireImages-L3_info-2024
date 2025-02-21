//--------------------------------------//
//---HENAULT ARCHER Noah et IACONE Alexi//
//---------HelloController.java---------//
//--------------------------------------//

//---------package et importation----------//
package images.ProjetJavaPOOGestionnaireImages;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.WritableImage;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import javafx.stage.DirectoryChooser;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javax.imageio.ImageIO;

//---------Déclaration de la classe principale----------//
public class HelloController {

//---------Elements d'interfaces----------//
    @FXML
    private VBox colonneRecherche;

    @FXML
    private Label imageManquante;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField MotDePasseTextField;

    @FXML
    private Label metadonnees;

//---------Variables pour des méthodes----------//
    private String selectedFolderPath;

    private List<String> FiltreUtilise = new ArrayList<>();
    private List<String> TraitementUtilise = new ArrayList<>();

    private GestionLogin gestionLogin = new GestionLogin();

//---------Méthodes----------//
    public void ChangementDeScene(ActionEvent event) throws IOException {
        //Méthode pour changer de scène (de login.fxml à application.fxml)
        String motDePasse = MotDePasseTextField.getText();
        if (gestionLogin.verifierMotDePasse(motDePasse)) {
            gestionLogin.ChangementDeScene(event);
        } else {
            System.out.println("Mot de passe incorrect");
        }
    }

    @FXML
    protected void Cryptage() {
        //Méthode pour appliquer le cryptage à l'image
        imageView.setImage(Filtre.Cryptage(imageView.getImage()));
        FiltreUtilise.add("Crypter");
    }

    @FXML
    protected void Decryptage() throws NoSuchAlgorithmException {
        //Méthode pour appliquer le décryptage à l'image
        byte[] seedBytes = null;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String seed = "aze";
        seedBytes = digest.digest(seed.getBytes());
        imageView.setImage(Filtre.Decryptage(imageView.getImage(), seedBytes));
        FiltreUtilise.add("Decrypter");
    }

    @FXML
    protected void Negatif() {
        //Méthode pour appliquer le filtre négatif à l'image
        imageView.setImage(Filtre.Negatif(imageView.getImage()));
        FiltreUtilise.add("Negative");
    }

    @FXML
    protected void Sepia() {
        //Méthode pour appliquer le filtre sepia à l'image
        imageView.setImage(Filtre.Sepia(imageView.getImage()));
        FiltreUtilise.add("Sepia");
    }

    @FXML
    protected void Gris() {
        //Méthode pour appliquer le filtre gris à l'image
        imageView.setImage(Filtre.Gris(imageView.getImage()));
        FiltreUtilise.add("Gris");
    }

    @FXML
    protected void Sobel() {
        //Méthode pour appliquer le filtre Sobel à l'image
        imageView.setImage(Filtre.Sobel(imageView.getImage()));
        FiltreUtilise.add("Sobel");
    }

    @FXML
    protected void GBR() {
        //Méthode pour appliquer le filtre de conversion RGB en GBR à l'image
        imageView.setImage(Filtre.GBR(imageView.getImage()));
        FiltreUtilise.add("Switch RGB");
    }


    @FXML
    protected void RotationGauche() {
        //Méthode pour appliquer une rotation à gauche à l'image
        Traitement.RotationGauche(imageView);
        TraitementUtilise.add("Rotation gauche");
    }

    @FXML
    protected void RotationDroite() {
        //Méthode pour appliquer une rotation à droite à l'image
        Traitement.RotationDroite(imageView);
        TraitementUtilise.add("Rotation Droite");
    }

    @FXML
    protected void FlipHorizontal() {
        //Méthode pour appliquer un flip horizontal à l'image
        Traitement.FlipHorizontal(imageView);
        TraitementUtilise.add("Flip Horizontal");
    }

    @FXML
    protected void FlipVertical() {
        //Méthode pour appliquer un flip vertical à l'image
        Traitement.FlipVertical(imageView);
        TraitementUtilise.add("Flip Vertical");
    }

    @FXML
    protected void SymetrieVerticale() {
        //Méthode pour appliquer une symétrie verticale à l'image
        Traitement.SymetrieVerticale(imageView);
        TraitementUtilise.add("Symetrie Verticale");
    }

    @FXML
    protected void SymetrieHorizontale() {
        //Méthode pour appliquer une symétrie horizontale à l'image
        Traitement.SymetrieHorizontale(imageView);
        TraitementUtilise.add("Symetrie Horizontale");
    }


    private void RecupererImages(File folder, List<String> imageNames) {
        //Méthode pour récupérer toutes les images dans un dossier (y compris dans les sous dossiers)
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png")){
                        //on vérifie si l'image est un jpg ou un png
                        imageNames.add(file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    //sinon, si c'est un fichier, on appelle récursivement cette méthode dans le sous dossier
                    RecupererImages(file, imageNames);
                }
            }
        }
    }


    private void printJSONContent(File FichierJSON) throws FileNotFoundException {
        //Méthode pour récupérer un fichier JSON
        Reader fichierJSON = new FileReader(FichierJSON);
            Gson gson = new Gson();
            Object jsonObject = gson.fromJson(fichierJSON, Object.class);
    }

    @FXML
    private void sauvegardeImage() {
        // Méthode pour sauvegarder une image et son fichier JSON
        WritableImage writableImage = imageView.snapshot(null, null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarde d'image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                MetaDonnee metaDonnee = new MetaDonnee((int) writableImage.getWidth(), (int) writableImage.getHeight(), "png", FiltreUtilise, TraitementUtilise);
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                metaDonnee.sauvegardeMetadata(file);
                // Obtient le chemin complet du fichier sans son extension
                String cheminSansExtension = getCheminSansExtension(file);
                // Charge les métadonnées du fichier
                chargerMetadonnee(cheminSansExtension);
                RecupererImageDepuisFichier(selectedFolderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour obtenir le chemin complet du fichier sans extension
    private String getCheminSansExtension(File file) {
        String nomFichierSansExtension = getNomFichierSansExtension(file.getAbsolutePath());
        String cheminComplet = file.getParent();
        return cheminComplet + File.separator + nomFichierSansExtension;
    }

    // Méthode pour obtenir le nom du fichier sans extension
    private String getNomFichierSansExtension(String cheminFichier) {
        File fichier = new File(cheminFichier);
        String nomFichier = fichier.getName();
        int indexDernierPoint = nomFichier.lastIndexOf('.');
        if (indexDernierPoint > 0) {
            return nomFichier.substring(0, indexDernierPoint);
        }
        return nomFichier;
    }






    @FXML
    protected void RecupererImage() {
        //Méthode pour récupérer les images dans un dossier, et afficher les images lorsqu'on clique sur leurs noms respectif
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedFolder = directoryChooser.showDialog(colonneRecherche.getScene().getWindow());
        if (selectedFolder != null && selectedFolder.isDirectory()) {
            selectedFolderPath = selectedFolder.getAbsolutePath();
            List<String> imagePaths = new ArrayList<>();
            RecupererImages(selectedFolder, imagePaths);
            //On utilise RecupererImages pour stocker tout les chemins des images dans une liste de String
            colonneRecherche.getChildren().clear();
            // On clear le contenue de l'élément "colonneRecherche" pour supprimé
            EventHandler<MouseEvent> labelClickHandler = event -> {
                //On créer un objet MouseEvent pour pouvoir
                Label clickedLabel = (Label) event.getSource();
                String imageName = clickedLabel.getText();
                String imagePath = getImagePath(selectedFolder, imageName);
                Image image = new Image(new File(imagePath).toURI().toString());
                //On récupère le chemin complet de l'image sur laquelle on a cliqué, puis on l'affiche
                imageView.setImage(image);
                chargerMetadonnee(imagePath.replaceFirst("[.][^.]+$", ""));
                //Puis on charge les métadonnées
            };
            //On ajoute a l'élément colonneRecherche un label pour chaque nom d'image
            for (String imagePath : imagePaths) {
                String imageName = new File(imagePath).getName();
                Label label = new Label(imageName);
                label.setOnMouseClicked(labelClickHandler); // Attach click handler
                colonneRecherche.getChildren().add(label);
            }
        }
    }

    protected void RecupererImageDepuisFichier(String folderPath) {
        //méthode pour récupérer les images a partir d'un dossier et affiche leurs noms
        File selectedFolder = new File(folderPath);
        if (selectedFolder.exists() && selectedFolder.isDirectory()) {
            //On vérifie si le dossier existe et si c'est un répertoire
            List<String> chemin = new ArrayList<>();
            RecupererImages(selectedFolder, chemin);
            //on appelle la méthode pour récupérer toutes les images
            colonneRecherche.getChildren().clear();
            //on clear le contenue de colonneRecherche
            EventHandler<MouseEvent> labelClickHandler = event -> {
                Label label = (Label) event.getSource();
                String nomDImage = label.getText();
                String Chemin = getImagePath(selectedFolder, nomDImage);
                Image image = new Image(new File(Chemin).toURI().toString());
                imageView.setImage(image);
            };

            for (String imagePath : chemin) {
                //on parcours
                String imageName = new File(imagePath).getName();
                Label label = new Label(imageName);
                label.setOnMouseClicked(labelClickHandler);
                //on lie le nom au label
                int lastDotIndex = imageName.lastIndexOf('.');
                if (lastDotIndex != -1) {
                    colonneRecherche.getChildren().add(label);
                }
            }
        }
    }

    public void chargerMetadonnee(String jsonFilePath) {
        //Méthode pour charger les métadonnées d'une image depuis le chemin + nom d'une image
        try (Reader reader = new FileReader(jsonFilePath + ".json")) {
            //On ajoute .json et on lit le fichier
            Gson gson = new Gson();
            MetaDonnee loadedMetadata = gson.fromJson(reader, MetaDonnee.class);
            String metadataText = "Largeur: " + loadedMetadata.getLargeur() + "px" +
                    "\nHauteur: " + loadedMetadata.getHauteur() + "px" +
                    "\nFormat: " + loadedMetadata.getExtension();
            if (!loadedMetadata.getFiltres().isEmpty()) { //si filtre n'est pas vide
                metadataText += "\nFiltre: " + String.join(", ", loadedMetadata.getFiltres());
            } else {//sinon on affiche aucun
                metadataText += "\nFiltre: Aucun";
            }
            if (!loadedMetadata.getTraitements().isEmpty()) { //si traitement n'est pas vide
                metadataText += "\nTraitement: " + String.join(", ", loadedMetadata.getTraitements());
            } else { //sinon on affiche aucun
                metadataText += "\nTraitement: Aucun";
            }
            // Puis on crée une chaine de metadonnée qui sera affiché avec le contenue du fichier JSON
            metadonnees.setText(metadataText);
            //On ajoute cette chaine au label "metadonnees"
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getImagePath(File selectedFolder, String imageName) {
        //Méthode pour récupérer le chemin des images
        String imagePath = selectedFolder.getAbsolutePath() + File.separator + imageName;
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            // Et si aucune image est trouvé, on continue dans les sous fichiers
            List<File> subfolders = new ArrayList<>();
            getSubfolders(selectedFolder, subfolders);
            for (File folder : subfolders) {
                String subfolderImagePath = folder.getAbsolutePath() + File.separator + imageName;
                if (new File(subfolderImagePath).exists()) {
                    return subfolderImagePath;
                }
            }
        }
        return imagePath;
    }

    private void getSubfolders(File folder, List<File> subfolders) {
        //Méthode pour passer dans des sous fichiers depuis un fichier en entrée récursivement
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    subfolders.add(file);
                    getSubfolders(file, subfolders);
                }
            }
        }
    }


    @FXML
    protected void ParcourirUneImage() throws FileNotFoundException {
        //Méthode pour choisir qu'une seule image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageManquante.setVisible(false);
            //On masque le label si une image est affiché
            File jsonFile = new File(file.getParent(), "metadata.json");
            //Et on affiche le fichier JSON correspondant
            printJSONContent(jsonFile);
        } else {
            imageView.setImage(null);
            imageManquante.setVisible(true);
            //Si aucune image est là, on affiche le label d'image manquante
        }
    }
}
