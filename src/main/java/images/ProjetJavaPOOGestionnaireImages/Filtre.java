//--------------------------------------//
//---HENAULT ARCHER Noah et IACONE Alexi//
//-------------Filtre.java--------------//
//--------------------------------------//

//---------package et importation----------//
package images.ProjetJavaPOOGestionnaireImages;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Filtre {

    public static Image Negatif(Image image) {
        //Méthode pour appliquer un filtre négatif
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage negativeImage = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = negativeImage.getPixelWriter();
        // Parcours de chaque pixel de l'image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                // Inversion des couleurs
                int red = (int) (color.getRed() * 255);
                int green = (int) (color.getGreen() * 255);
                int blue = (int) (color.getBlue() * 255);
                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;
                Color negativeColor = Color.rgb(red, green, blue);
                // Écrire la couleur inversée dans l'image
                writer.setColor(x, y, negativeColor);
            }
        }
        return negativeImage;
    }


    public static Image Gris(Image image) {
        //Méthode pour appliquer un filtre gris
        int largeur = (int) image.getWidth();
        int hauteur = (int) image.getHeight();
        WritableImage imageModifie = new WritableImage(largeur, hauteur);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = imageModifie.getPixelWriter();
        // Parcours de chaque pixel de l'image
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                Color couleur = reader.getColor(x, y);
                // Calcul de la luminosité (niveau de gris)
                double gris = 0.2 * couleur.getRed() + 0.2 * couleur.getGreen() + 0.2 * couleur.getBlue();
                // Écrire la couleur en niveaux de gris dans l'image
                writer.setColor(x, y, Color.color(gris, gris, gris));
            }
        }
        return imageModifie;
    }


    public static Image Sepia(Image image) {
        //Méthode pour appliquer un filtre sépia
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage sepiaImage = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = sepiaImage.getPixelWriter();
        //On récupère l'image et on parcourt chaque pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                double red = Math.min(1, (color.getRed() * 0.393) + (color.getGreen() * 0.769) + (color.getBlue() * 0.189));
                double green = Math.min(1, (color.getRed() * 0.349) + (color.getGreen() * 0.686) + (color.getBlue() * 0.168));
                double blue = Math.min(1, (color.getRed() * 0.272) + (color.getGreen() * 0.534) + (color.getBlue() * 0.131));
                //Puis on applique a chaque pixels, un changement de couleur
                //valeurs des multiplications de couleurs trouvé ici : https://www.geeksforgeeks.org/image-processing-in-java-colored-image-to-sepia-image-conversion/
                writer.setColor(x, y, Color.color(red, green, blue));
                //et on applique la nouvelle couleur au pixel actuel
            }
        }
        return sepiaImage;
    }

    public static Image Sobel(Image image) {
        //Méthode pour appliquer un filtre de Sobel
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage sobelImage = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = sobelImage.getPixelWriter();
        //On récupère l'image
        // On définit des matrices de sobel, récupérer ici : https://www.developpez.net/forums/d1589916/java/interfaces-graphiques-java/graphisme/2d/detection-contours-filtre-sobel/
        int[][] sobelX =     {{-1, 0, 1},
                              {-2, 0, 2},
                              {-1, 0, 1}};

        int[][] sobelY =     {{-1, -2, -1},
                              {0 ,  0,  0},
                              {1 ,  2,  1}};
        double eclairage = 10;
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                // Puis on parcours chaque pixel de l'image sans compter les bords pour ne pas avoir
                // de probleme où l'on souhaite accéder à un pixel en dehors de l'image
                double gradientX = 0;
                double gradientY = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        //On parcours pour chaque pixel, les pixels voisin où l'on additionne leurs valeurs après application des matrices
                        Color color = reader.getColor(x + i, y + j);
                        double luminosity = 0.5 * color.getRed() + 0.5 * color.getGreen() + 0.5 * color.getBlue();
                        gradientX += eclairage * luminosity * sobelX[j + 1][i + 1];
                        gradientY += eclairage * luminosity * sobelY[j + 1][i + 1];
                    }
                }
                double gradientMagnitude = Math.sqrt(gradientX * gradientX + gradientY * gradientY);
                gradientMagnitude = Math.min(1.0, gradientMagnitude / (2 * Math.sqrt(255)));
                // Écrire la valeur du gradient dans l'image (convertie en niveaux de gris)
                writer.setColor(x, y, Color.gray(gradientMagnitude));
            }
        }
        return sobelImage;
    }


    public static Image GBR(Image image) {
        //Méthode pour appliquer un changement de couleur (RGB en GBR)
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage exchangedImage = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = exchangedImage.getPixelWriter();
        // Parcours de chaque pixel de l'image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                // Échange des composantes de couleur (R, G, B) pour obtenir (G, B, R)
                Color exchangedColor = Color.color(color.getGreen(), color.getBlue(), color.getRed());
                // Écrire la couleur échangée dans l'image
                writer.setColor(x, y, exchangedColor);
            }
        }
        return exchangedImage;
    }

    private static final int valeurMaxCouleur = 255;

    public static Image Cryptage(Image chosenImage) {
        int width = (int) chosenImage.getWidth();
        int height = (int) chosenImage.getHeight();
        WritableImage cryptedImage = new WritableImage(width, height);
        PixelReader reader = chosenImage.getPixelReader();
        PixelWriter writer = cryptedImage.getPixelWriter();
        try {
            String seed = "aze"; //mdp
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] tableau = digest.digest(seed.getBytes());
            SecureRandom secureRandom = new SecureRandom(tableau);
            // Parcours de chaque pixel de l'image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = reader.getColor(x, y);
                    // Génération d'un nombre aléatoire pour le cryptage
                    int randomInt = secureRandom.nextInt();
                    // Génération d'une valeur semi-aléatoire pour chaque composante RGB
                    int rand = secureRandom.nextInt(valeurMaxCouleur + 1);
                    // Ajout de la valeur semi-aléatoire aux composantes RGB
                    int red = (int) (255 - (color.getRed() * 255) + rand) % (valeurMaxCouleur + 1);
                    int green = (int) (255 - (color.getGreen() * 255) + rand) % (valeurMaxCouleur + 1);
                    int blue = (int) (255 - (color.getBlue() * 255) + rand) % (valeurMaxCouleur + 1);
                    Color cryptedColor = Color.rgb(red, green, blue);
                    // Écrire la couleur cryptée dans l'image
                    writer.setColor(x, y, cryptedColor);
                    // Ajout d'informations de débogage dans la console
                    System.out.println("Pixel crypté [" + x + ", " + y + "]: " + cryptedColor.toString());
                }
            }
            System.out.println("SecureRandom : " + secureRandom);
            System.out.println("Digest : " + digest);
            System.out.println("seedBytes : " + Arrays.toString(tableau));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // Gérer les erreurs d'une manière appropriée dans votre application
        }
        return cryptedImage;
    }

    public static Image Decryptage(Image img, byte[] tab) {
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        WritableImage imgDecryptee = new WritableImage(width, height);
        PixelReader reader = img.getPixelReader();
        PixelWriter writer = imgDecryptee.getPixelWriter();
        try {
            SecureRandom secureRandom = new SecureRandom(tab);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color cryptedColor = reader.getColor(x, y);
                    int semiRandomValue = secureRandom.nextInt(valeurMaxCouleur + 1);
                    // soustraction car décryptage
                    int red = (int) ((255 + cryptedColor.getRed() * 255 - semiRandomValue) % (valeurMaxCouleur + 1));
                    int green = (int) ((255 + cryptedColor.getGreen() * 255 - semiRandomValue) % (valeurMaxCouleur + 1));
                    int blue = (int) ((255 + cryptedColor.getBlue() * 255 - semiRandomValue) % (valeurMaxCouleur + 1));
                    red = red < 0 ? red + valeurMaxCouleur + 1 : red;
                    green = green < 0 ? green + valeurMaxCouleur + 1 : green;
                    blue = blue < 0 ? blue + valeurMaxCouleur + 1 : blue;
                    Color decryptedColor = Color.rgb(red, green, blue);
                    writer.setColor(x, y, decryptedColor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgDecryptee;
    }
}
