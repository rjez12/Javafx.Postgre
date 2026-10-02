module produ.jes12.registroconsulta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports produ.jes12.registroconsulta;
    opens produ.jes12.registroconsulta to javafx.fxml;

    opens produ.jes12.registroconsulta.controller to javafx.fxml;
    exports produ.jes12.registroconsulta.controller;
    opens produ.jes12.registroconsulta.model to javafx.base;
    exports produ.jes12.registroconsulta.model;
}