module com.mycompany.objectdbfxml {
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.persistence;
    requires lombok;
    requires java.sql;
    
    opens models;

    opens com.mycompany.objectdbfxml to javafx.fxml, java.sql;
    exports com.mycompany.objectdbfxml;
}
