package com.example.demo;

import com.example.demo.model.Participante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtTelefono;

    @FXML private ComboBox<String> cbGenero;
    @FXML private ComboBox<String> cbCategoria;
    @FXML private ComboBox<String> cbModalidad;
    @FXML private ComboBox<String> cbDisciplina;

    @FXML private CheckBox chkFederado;
    @FXML private CheckBox chkExperiencia;
    @FXML private CheckBox chkFinesSemana;
    @FXML private CheckBox chkSeguro;

    @FXML private TableView<Participante> tabla;
    @FXML private TableColumn<Participante, String> colNombre;
    @FXML private TableColumn<Participante, String> colEdad;
    @FXML private TableColumn<Participante, String> colCategoria;
    @FXML private TableColumn<Participante, String> colModalidad;
    @FXML private TableColumn<Participante, String> colDisciplina;
    @FXML private TableColumn<Participante, String> colCaracteristicas;
    @FXML private TableColumn<Participante, String> colEstado;

    private final ObservableList<Participante> lista = FXCollections.observableArrayList();
    private Participante participanteSeleccionado = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar datos en los ComboBox
        cbGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino", "Otro"));
        cbCategoria.setItems(FXCollections.observableArrayList("Juvenil", "Intermedia", "Senior"));
        cbModalidad.setItems(FXCollections.observableArrayList("Individual", "Parejas", "Equipos"));
        cbDisciplina.setItems(FXCollections.observableArrayList("Fútbol", "Baloncesto", "Voleibol", "Atletismo", "Natación", "Tenis"));

        // Configurar Columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplina"));
        colCaracteristicas.setCellValueFactory(new PropertyValueFactory<>("caracteristicas"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tabla.setItems(lista);

        // Evento al seleccionar una fila
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo != null) {
                participanteSeleccionado = nuevo;
                txtNombre.setText(nuevo.getNombre());
                txtEdad.setText(nuevo.getEdad());
                txtTelefono.setText(nuevo.getTelefono());
                cbGenero.setValue(nuevo.getGenero());
                cbCategoria.setValue(nuevo.getCategoria());
                cbModalidad.setValue(nuevo.getModalidad());
                cbDisciplina.setValue(nuevo.getDisciplina());

                // Marcar los CheckBox si la característica está en el String
                chkFederado.setSelected(nuevo.getCaracteristicas().contains("Federado"));
                chkExperiencia.setSelected(nuevo.getCaracteristicas().contains("Exp. Previa"));
                chkFinesSemana.setSelected(nuevo.getCaracteristicas().contains("Fines Sem."));
                chkSeguro.setSelected(nuevo.getCaracteristicas().contains("Seguro"));
            }
        });
    }

    @FXML
    private void agregarParticipante() {
        if (!camposValidos()) return;

        lista.add(new Participante(
                txtNombre.getText().trim(),
                txtEdad.getText().trim(),
                txtTelefono.getText().trim(),
                cbGenero.getValue(),
                cbCategoria.getValue(),
                cbModalidad.getValue(),
                cbDisciplina.getValue(),
                obtenerCaracteristicas(),
                "Inscrito" // Estado por defecto
        ));
        limpiarCampos();
    }

    @FXML
    private void editarParticipante() {
        if (participanteSeleccionado == null) {
            mostrarAlerta("Sin selección", "Selecciona en la tabla el participante que quieres editar.");
            return;
        }
        if (!camposValidos()) return;

        participanteSeleccionado.setNombre(txtNombre.getText().trim());
        participanteSeleccionado.setEdad(txtEdad.getText().trim());
        participanteSeleccionado.setTelefono(txtTelefono.getText().trim());
        participanteSeleccionado.setGenero(cbGenero.getValue());
        participanteSeleccionado.setCategoria(cbCategoria.getValue());
        participanteSeleccionado.setModalidad(cbModalidad.getValue());
        participanteSeleccionado.setDisciplina(cbDisciplina.getValue());
        participanteSeleccionado.setCaracteristicas(obtenerCaracteristicas());

        tabla.refresh();
        limpiarCampos();
        participanteSeleccionado = null;
    }

    @FXML
    private void eliminarParticipante() {
        Participante seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Sin selección", "Selecciona en la tabla el participante que quieres eliminar.");
            return;
        }
        lista.remove(seleccionado);
        limpiarCampos();
        participanteSeleccionado = null;
    }

    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        txtEdad.clear();
        txtTelefono.clear();

        cbGenero.getSelectionModel().clearSelection();
        cbCategoria.getSelectionModel().clearSelection();
        cbModalidad.getSelectionModel().clearSelection();
        cbDisciplina.getSelectionModel().clearSelection();

        chkFederado.setSelected(false);
        chkExperiencia.setSelected(false);
        chkFinesSemana.setSelected(false);
        chkSeguro.setSelected(false);

        tabla.getSelectionModel().clearSelection();
        participanteSeleccionado = null;
    }

    // --- MÉTODOS DE APOYO Y VALIDACIONES ---

    private String obtenerCaracteristicas() {
        String resultado = "";
        if (chkFederado.isSelected()) resultado += "Federado, ";
        if (chkExperiencia.isSelected()) resultado += "Exp. Previa, ";
        if (chkFinesSemana.isSelected()) resultado += "Fines Sem., ";
        if (chkSeguro.isSelected()) resultado += "Seguro, ";

        // Quitar la última coma y el espacio, o retornar "Ninguna"
        if (!resultado.isEmpty()) {
            return resultado.substring(0, resultado.length() - 2);
        }
        return "Ninguna";
    }

    private boolean camposValidos() {
        String nombre = txtNombre.getText().trim();

        // 1. Nombre obligatorio y 2. Mínimo 5 caracteres
        if (nombre.isEmpty() || nombre.length() < 5) {
            mostrarAlerta("Error en Nombre", "El nombre es obligatorio y debe tener al menos 5 caracteres.");
            return false;
        }

        // 3. La edad debe estar entre 15 y 60 años
        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad < 15 || edad > 60) {
                mostrarAlerta("Error en Edad", "La edad debe estar entre 15 y 60 años.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error en Edad", "Por favor ingresa un número válido para la edad.");
            return false;
        }

        // 4. El teléfono solamente debe aceptar números (Expresión regular)
        String telefono = txtTelefono.getText().trim();
        if (!telefono.matches("\\d+")) {
            mostrarAlerta("Error en Teléfono", "El teléfono solamente debe contener números.");
            return false;
        }

        // 5, 6 y 7. Categoría, Modalidad y Disciplina obligatorias
        if (cbCategoria.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionarse una categoría.");
            return false;
        }
        if (cbModalidad.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionarse una modalidad.");
            return false;
        }
        if (cbDisciplina.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionarse una disciplina.");
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}