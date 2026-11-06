module com.example.tp_gontrandlefletan {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tp_gontrandlefletan to javafx.fxml;
    exports com.example.tp_gontrandlefletan;
}