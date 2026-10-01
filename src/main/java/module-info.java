module produ.jes12.registroconsulta {
    requires javafx.controls;
    requires javafx.fxml;


    opens produ.jes12.registroconsulta to javafx.fxml;
    exports produ.jes12.registroconsulta;
}