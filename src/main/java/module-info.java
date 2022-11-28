module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;

    opens sample to javafx.fxml;
    exports sample;
}

