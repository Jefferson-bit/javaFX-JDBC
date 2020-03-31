/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import gui.Constraints;
import java.net.URL;
import java.util.ResourceBundle;
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
    public void onBtSaveAction() {
        System.out.println("onBSaveAction");
    }

    @FXML
    public void onBtCancelAction() {
        System.out.println("onBtCancelAction");
    }

    public void setDepartment(Department entityes){
        this.entityes = entityes;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNode();
    }

    private void initializeNode() {
        Constraints.setTextFieldInteger(txtId) ;
        Constraints.setTextFieldMaxLength(txtName, 30);
    }    
    public void updateDepartment(){
        
        txtId.setText(String.valueOf(entityes.getId()));
        txtName.setText(entityes.getName());
    }

}
