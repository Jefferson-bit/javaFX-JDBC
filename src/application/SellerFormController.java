/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Dados.DBException;
import Model.exception.ValidationException;
import Model.services.DepartmentService;
import Model.services.SellerService;
import gui.Alerts;
import gui.Constraints;
import gui.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import gui.DataChangeListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.Department;

public class SellerFormController implements Initializable {

    private Seller entityes;

    private SellerService service;

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListener = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private ObservableList<Department> obsList;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpbBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private Label labelErroName;

    @FXML
    private Label labelErroEmail;

    @FXML
    private Label labelErroBirthDate;

    @FXML
    private Label labelErroBaseSalary;

    @FXML
    private Button btCancelar;
    @FXML
    private Button btSalvar;

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entityes == null) {
            throw new IllegalStateException("Entityes was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        try {
            entityes = getFormData();
            service.saveOrUpdate(entityes);
            notifyDataChangeListener();//quando o salvamente dos objetos for bem sucedido, temos q notificar os listener
            Utils.currentStagem(event).close();
            

        } catch (ValidationException e) {
            setErrorMessages(e.getErros());
        } catch (DBException e) {
            Alerts.alertShow("IO Excpetion", "error button", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    //esse metodo ele é responsavel por notificar a minha lista quando houver a inserção de dados
    private void notifyDataChangeListener() {
        for (DataChangeListener listener : dataChangeListener) {
            listener.onDataChenged();
        }
    }

    //metodo responsável por escreve na minha lista
    public void subsCribleDateChangeListener(DataChangeListener listener) {
        dataChangeListener.add(listener);
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation Error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));
        //Verificando se o campo está vazio, o trim eliminia qualquer espaço em branco, no inicio ou final
        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addErrors("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());
        //testando se na coleção tem pelo menos um erro

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
            exception.addErrors("email", "Field can't be empty");
        }
        obj.setEmail(txtEmail.getText());
        //testando se na coleão tem pelo menos um erro

        if (dpbBirthDate.getValue() == null) {
            exception.addErrors("birthDate", "Field can't be empty");

        } else {
            Instant instant = Instant.from((dpbBirthDate.getValue().atStartOfDay(ZoneId.systemDefault())));
            obj.setBirthDate(Date.from(instant));
        }

        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
            exception.addErrors("baseSalary", "Field can't be empty");
        }

        obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

        obj.setDepartment(comboBoxDepartment.getValue());

        if (exception.getErros().size() > 0) {
            throw exception;
        }

        return obj;
    }

    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStagem(event).close();
    }

    public void setSeller(Seller entityes) {
        this.entityes = entityes;
    }

    public void setServices(SellerService service, DepartmentService departmentService) {
        this.service = service;
        this.departmentService = departmentService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNode();

    }

    private void initializeNode() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpbBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    public void updateFormData() {
        if (entityes == null) {
            throw new IllegalStateException("Entityes was null");
        }
        txtId.setText(String.valueOf(entityes.getId()));
        txtName.setText(entityes.getName());
        txtEmail.setText(entityes.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entityes.getBaseSalary()));
        if (entityes.getBirthDate() != null) {
            dpbBirthDate.setValue(LocalDate.ofInstant(entityes.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }

        if (entityes.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(entityes.getDepartment());
        }
    }

    public void loadAssociatedObject() {
        if (departmentService == null) {
            throw new IllegalStateException("DepartmentService was null");
        }
        List<Department> list = departmentService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErroName.setText(fields.contains("name") ? errors.get("name") : "");
        labelErroEmail.setText(fields.contains("email") ? errors.get("email") : "");
        labelErroBirthDate.setText(fields.contains("birthDate") ? errors.get("birthDate") : "");
        labelErroBaseSalary.setText(fields.contains("baseSalary") ? errors.get("baseSalary") : "");
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }
}
