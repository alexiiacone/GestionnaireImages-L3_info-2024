//--------------------------------------//
//---HENAULT ARCHER Noah et IACONE Alexi//
//------------MetaDonnee.java-----------//
//--------------------------------------//

//---------package et importation----------//
package images.ProjetJavaPOOGestionnaireImages;

import java.util.List;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//---------Déclaration de classe avec ses attributs----------//
public class MetaDonnee {
    public int width;
    public int height;
    public String extension;
    public List<String> filters;
    public List<String> traitements;

//---------Constructeur----------//
    public MetaDonnee(int largeur, int hauteur, String extension, List<String> filtre, List<String> traitement) {
        this.width = largeur;
        this.height = hauteur;
        this.extension = extension;
        this.filters = filtre;
        this.traitements = traitement;
    }

//---------Getteurs et Setteurs----------//
    public int getLargeur() {
        return width;
    }

    public int getHauteur() {
        return height;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<String> getFiltres() {
        return filters;
    }

    public List<String> getTraitements() {
        return traitements;
    }

//---------Méthode----------//
    public void sauvegardeMetadata(File imageFile) throws IOException {
        String nomFichier = imageFile.getName();
        String nomSansExtension = nomFichier;
        int index = nomFichier.lastIndexOf(".");
        if (index > 0) {
            nomSansExtension = nomFichier.substring(0, index);
        }
        //On récupère le nom de l'image et on retire son extension
        String metadataFileName = imageFile.getParent() + File.separator + nomSansExtension + ".json";
        //puis on ajoute un ".json"
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(metadataFileName);
        // et on récupère l'objet JSON pour le stocker dans un string
        String jsonMetadata = gson.toJson(this);
        // et en fin écrit un fichier JSON
        fileWriter.write(jsonMetadata);
        fileWriter.close();
    }
}
