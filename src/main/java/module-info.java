module images.satan {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires javafx.swing;
    requires com.google.gson;

    exports images.ProjetJavaPOOGestionnaireImages;

    opens images.ProjetJavaPOOGestionnaireImages to
            javafx.fxml;
}
