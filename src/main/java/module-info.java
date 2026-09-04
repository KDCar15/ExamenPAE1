module com.uamv.examenpae1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.uamv.examenpae1 to javafx.fxml;
    exports com.uamv.examenpae1;
}