module com.example.gamefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gamefx to javafx.fxml;
    exports com.example.gamefx;
}