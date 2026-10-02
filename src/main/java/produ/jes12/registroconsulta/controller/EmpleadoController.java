package produ.jes12.registroconsulta.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import produ.jes12.registroconsulta.database.DatabaseConnection;
import produ.jes12.registroconsulta.model.Empleado;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmpleadoController implements Initializable {

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtCedula;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCargo;
    @FXML private ComboBox<String> cmbDepartamento;
    @FXML private TextField txtSalario;
    @FXML private DatePicker dtpFechaContratacion;
    @FXML private ComboBox<String> cmbEstado;

    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnActualizar;

    @FXML private TableView<Empleado> tblEmpleados;
    @FXML private TableColumn<Empleado, Integer> colId;
    @FXML private TableColumn<Empleado, String> colNombres;
    @FXML private TableColumn<Empleado, String> colApellidos;
    @FXML private TableColumn<Empleado, String> colCedula;
    @FXML private TableColumn<Empleado, String> colCorreo;
    @FXML private TableColumn<Empleado, String> colTelefono;
    @FXML private TableColumn<Empleado, String> colCargo;
    @FXML private TableColumn<Empleado, String> colDepartamento;
    @FXML private TableColumn<Empleado, Double> colSalario;
    @FXML private TableColumn<Empleado, LocalDate> colFechaContratacion;
    @FXML private TableColumn<Empleado, String> colEstado;

    private final ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas();
        cargarOpcionesComboBox();
        cargarEmpleados();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblEmpleados.setItems(listaEmpleados);
    }

    private void cargarOpcionesComboBox() {
        cmbDepartamento.setItems(FXCollections.observableArrayList(
                "TI / Sistemas",
                "Recursos Humanos",
                "Finanzas y Contabilidad",
                "Marketing",
                "Operaciones",
                "Ventas"
        ));

        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
    }

    @FXML
    public void cargarEmpleados() {
        listaEmpleados.clear();
        String sql = "SELECT id, nombres, apellidos, cedula, correo, telefono, cargo, departamento, salario, fecha_contratacion, estado FROM empleado ORDER BY id ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("cedula"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("cargo"),
                        rs.getString("departamento"),
                        rs.getDouble("salario"),
                        rs.getDate("fecha_contratacion").toLocalDate(),
                        rs.getString("estado")
                );
                listaEmpleados.add(emp);
            }

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos", "No se pudieron consultar los empleados:\n" + e.getMessage());
        }
    }

    @FXML
    public void guardarEmpleado(ActionEvent event) {
        if (!validarFormulario()) {
            return;
        }

        String sql = "INSERT INTO empleado (nombres, apellidos, cedula, correo, telefono, cargo, departamento, salario, fecha_contratacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, txtNombres.getText().trim());
            ps.setString(2, txtApellidos.getText().trim());
            ps.setString(3, txtCedula.getText().trim());
            ps.setString(4, txtCorreo.getText().trim());
            ps.setString(5, txtTelefono.getText().trim());
            ps.setString(6, txtCargo.getText().trim());
            ps.setString(7, cmbDepartamento.getValue());
            ps.setDouble(8, Double.parseDouble(txtSalario.getText().trim()));
            ps.setDate(9, Date.valueOf(dtpFechaContratacion.getValue()));
            ps.setString(10, cmbEstado.getValue());

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Empleado registrado correctamente.");
                limpiarFormulario(null);
                cargarEmpleados();
            }

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Inserción", "No se pudo registrar el empleado:\n" + e.getMessage());
        }
    }

    @FXML
    public void limpiarFormulario(ActionEvent event) {
        txtNombres.clear();
        txtApellidos.clear();
        txtCedula.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCargo.clear();
        txtSalario.clear();
        cmbDepartamento.setValue(null);
        cmbEstado.setValue(null);
        dtpFechaContratacion.setValue(null);
    }

    private boolean validarFormulario() {
        if (txtNombres.getText().trim().isEmpty() ||
                txtApellidos.getText().trim().isEmpty() ||
                txtCedula.getText().trim().isEmpty() ||
                txtCorreo.getText().trim().isEmpty() ||
                txtCargo.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor complete todos los campos de texto obligatorios.");
            return false;
        }

        if (cmbDepartamento.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Debe seleccionar un departamento.");
            return false;
        }

        if (cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Debe seleccionar un estado para el empleado.");
            return false;
        }

        if (dtpFechaContratacion.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Debe seleccionar la fecha de contratación.");
            return false;
        }

        try {
            double salario = Double.parseDouble(txtSalario.getText().trim());
            if (salario < 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Validación", "El salario no puede ser un valor negativo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "El salario debe ser un número válido (ej. 1500.50).");
            return false;
        }

        return true;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
