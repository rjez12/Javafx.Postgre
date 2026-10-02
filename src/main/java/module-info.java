module produ.jes12.registroconsulta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens produ.jes12.registroconsulta to javafx.fxml;
    exports produ.jes12.registroconsulta;
}