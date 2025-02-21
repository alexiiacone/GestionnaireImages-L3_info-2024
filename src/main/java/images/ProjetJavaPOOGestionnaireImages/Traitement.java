//--------------------------------------//
//---HENAULT ARCHER Noah et IACONE Alexi//
//-----------Traitement.java------------//
//--------------------------------------//


//---------package et importation----------//
package images.ProjetJavaPOOGestionnaireImages;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

//---------Déclaration de classe----------//
public class Traitement {

//---------Méthodes----------//
    public static void RotationGauche(ImageView imageView) {
        //Méthode pour tourner une image de -90°
        imageView.setRotate(imageView.getRotate() - 90);
        AdapterTailleImage(imageView);
    }

    public static void RotationDroite(ImageView imageView) {
        //Méthode pour tourner une image de +90°
        imageView.setRotate(imageView.getRotate() + 90);
        AdapterTailleImage(imageView);
    }


    private static void AdapterTailleImage(ImageView imageView) {
        //Méthode pour adapter une image à son conteneur, appelé lorsqu'on applique une rotation dessus
        double imageWidth = imageView.getImage().getWidth();
        double imageHeight = imageView.getImage().getHeight();
        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();
        double newWidth, newHeight;
        double valeurRotation = Math.abs(imageView.getRotate()) % 180; // si l'angle de rotation est un multiple de 180
        if (valeurRotation == 90 || valeurRotation == 270) {
            // Si l'image est pivotée de 90 ou 270 degrés, on échange la largeur et la hauteur
            newWidth = imageHeight;
            newHeight = imageWidth;
        } else { //sinon on met la taille basique
            newWidth = imageWidth;
            newHeight = imageHeight;
        }

        // on récupère le pourcentage de X et Y pour appliquer la nouvelle taille a l'image
        double pourcentageX = width / newWidth;
        double pourcentageY = height / newHeight;
        double scale = Math.min(pourcentageX, pourcentageY);

        // et on applique le scale à l'image
        imageView.setScaleX(scale);
        imageView.setScaleY(scale);
    }

    public static void FlipHorizontal(ImageView imageView) {
        //Méthode pour flip horizontalement une image
        Image originalImage = imageView.getImage();
        if (originalImage != null) {
            int width = (int) originalImage.getWidth();
            int height = (int) originalImage.getHeight();
            WritableImage flippedImage = new WritableImage(width, height);
            PixelReader reader = originalImage.getPixelReader();
            PixelWriter writer = flippedImage.getPixelWriter();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = reader.getColor(x, y);
                    //On récupère la valeur du pixel actuel et on set cette couleur a l'opposé de l'image
                    //pour appliquer un flip horizontale
                    writer.setColor(width - x - 1, y, color);
                }
            }
            //Et on applique l'image flip a imageView
            imageView.setImage(flippedImage);
        }
    }

    public static void FlipVertical(ImageView imageView) {
        //Méthode pour flip verticalement une image
        Image originalImage = imageView.getImage();
        if (originalImage != null) {
            int width = (int) originalImage.getWidth();
            int height = (int) originalImage.getHeight();
            WritableImage flippedImage = new WritableImage(width, height);
            PixelReader reader = originalImage.getPixelReader();
            PixelWriter writer = flippedImage.getPixelWriter();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = reader.getColor(x, y);
                    //On récupère la valeur du pixel actuel et on set cette couleur à l'autre pixel de l'image
                    //pour appliquer un flip vertical
                    writer.setColor(x, height - y - 1, color);
                }
            }
            //Et on applique l'image flip a imageView
            imageView.setImage(flippedImage);
        }
    }

    public static void SymetrieVerticale(ImageView imageView) {
        //Méthode pour appliquer une symétrie verticale
        Image originalImage = imageView.getImage();
        if (originalImage != null) {
            int width = (int) originalImage.getWidth();
            int height = (int) originalImage.getHeight();
            WritableImage symetriedImage = new WritableImage(width, height);
            PixelReader reader = originalImage.getPixelReader();
            PixelWriter writer = symetriedImage.getPixelWriter();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = reader.getColor(x, y);
                    writer.setColor(x, y, color);
                    writer.setColor(width - x - 1, y, color);
                    //On récupère la valeur du pixel actuel et on set cette couleur à l'autre pixel de l'image
                    //pour appliquer un symétrie vertical
                }
            }
            imageView.setImage(symetriedImage);
        }
    }

    public static void SymetrieHorizontale(ImageView imageView) {
        //Méthode pour appliquer une symétrie horizontale
        Image originalImage = imageView.getImage();
        if (originalImage != null) {
            int width = (int) originalImage.getWidth();
            int height = (int) originalImage.getHeight();
            WritableImage symetriedImage = new WritableImage(width, height);
            PixelReader reader = originalImage.getPixelReader();
            PixelWriter writer = symetriedImage.getPixelWriter();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = reader.getColor(x, y);
                    writer.setColor(x, y, color);
                    writer.setColor(x, height - y - 1, color);
                    //On récupère la valeur du pixel actuel et on set cette couleur a l'opposé de l'image
                    //pour appliquer un symétrie horizontale
                }
            }
            imageView.setImage(symetriedImage);
        }
    }
}
