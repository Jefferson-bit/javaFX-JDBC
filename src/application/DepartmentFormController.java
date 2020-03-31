/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Dados.DBException;
import Model.exception.ValidationException;
import Model.services.DepartmentService;
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
import model.entities.Department;
import gui.DataChangeListener;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.Alert;

public class DepartmentFormController implements Initializable {

    private Department entityes;

    private DepartmentService service;

    private List<DataChangeListener> dataChangeListener  = new ArrayList<>();
    
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
    try{
        entityes = getFormData();
        service.saveOrUpdate(entityes);
        Utils.currentStagem(event).close();
        notifyDataChangeListener();
    }catch(ValidationException e){
            setErrorMessages(e.getErros());
    }catch(DBException e){
        Alerts.alertShow("IO Excpetion", "error button", e.getMessage(), Alert.AlertType.ERROR);
    }
    
  }
    //esse metodo ele é responsavel por notificar a minha lista quando houver a inserção de dados
    private void notifyDataChangeListener() {
        for(DataChangeListener listener: dataChangeListener){
            listener.onDataChenged();
        }
    }    
    //metodo responsável por escreve na minha lista
    public void subsCribleDateChangeListener(DataChangeListener listener){
        dataChangeListener.add(listener);
    }
    
    @FXML
    private Department getFormData() {
        Department obj = new Department();
        
        ValidationException exception = new ValidationException("Validation Error");
              
        obj.setId(Utils.tryParseToInt(txtId.getText()));
        //Verificando se o campo está vazio
        if(txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addErrors("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());
      
        if(exception.getErros().size() > 0){
            throw exception;
        }   
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
    private void setErrorMessages(Map<String,String> errors){
        Set<String> fields = errors.keySet();
        
        if(fields.contains("name")){
            labelErroName.setText(errors.get("name"));
        }
    }

}
