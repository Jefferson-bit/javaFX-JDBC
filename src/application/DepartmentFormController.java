/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Model.services.DepartmentService;
import gui.Constraints;
import gui.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

    @FXML
    private Department entityes;

    @FXML
    private DepartmentService service;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErroName;

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

        entityes = getFormData();
        service.saveOrUpdate(entityes);
        Utils.currentStagem(event).close();

    }

    @FXML
    private Department getFormData() {
        Department obj = new Department();
        obj.setId(Utils.tryParseToInt(txtId.getText()));
        obj.setName(txtName.getText());
        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStagem(event).close();
    }

    public void setDepartment(Department entityes) {
        this.entityes = entityes;
    }

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNode();
    }

    private void initializeNode() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateDepartment() {
        txtId.setText(String.valueOf(entityes.getId()));
        txtName.setText(entityes.getName());
    }

}
