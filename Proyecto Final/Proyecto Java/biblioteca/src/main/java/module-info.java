module com.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.base;

    opens com.biblioteca to javafx.fxml;
    exports com.biblioteca;
    exports com.biblioteca.modelos;
    exports com.biblioteca.controladores;
    exports com.biblioteca.interfaces;
    exports com.biblioteca.utilidades;
    opens com.biblioteca.controladores to javafx.fxml;
    opens com.biblioteca.modelos to javafx.base;
    opens com.biblioteca.interfaces to javafx.base;
}
