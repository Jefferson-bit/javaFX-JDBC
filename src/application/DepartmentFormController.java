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

/**
 * FXML Controller class
 *
 * @author crash
 */
public class DepartmentFormController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNode();
    }

    private void initializeNode() {
        Constraints.setTextFieldInteger(txtId) ;
        Constraints.setTextFieldMaxLength(txtName, 30);
    }    


}
