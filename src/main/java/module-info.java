module com.example.bs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.almasb.fxgl.all;

    opens com.example.bs to javafx.fxml;
    exports com.example.bs;
}
//module com.example.bookstore1 {
//        requires javafx.controls;
//        requires javafx.fxml;
//        requires org.controlsfx.controls;
//        requires com.dlsc.formsfx;
//        requires org.kordamp.bootstrapfx.core;
//        requires java.sql;
//
//        exports com.example.bookstore1;
//
//        opens com.example.bookstore1 to
//        javafx.fxml;
//        }
