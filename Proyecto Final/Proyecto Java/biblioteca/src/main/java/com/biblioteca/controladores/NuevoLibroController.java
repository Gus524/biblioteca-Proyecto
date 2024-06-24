package com.biblioteca.controladores;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NuevoLibroController {
    @FXML
    private Label plusIcon;
    @FXML
    private Label minusIcon;
    @FXML
    private Label categoriaIconPlus;
    @FXML
    private Label categoriaIconMinus;

    @FXML
    private void initialize() {
        cargarIconos();
    }
    
    @FXML
    private void cargarIconos() {
        plusIcon.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
        minusIcon.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
        categoriaIconPlus.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
        categoriaIconMinus.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
    }

    
}
