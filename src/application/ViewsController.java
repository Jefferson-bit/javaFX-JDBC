/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;


public class ViewsController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;
    
    @FXML
    private MenuItem menuItemDepartment;
    
    @FXML
    private MenuItem menuItemAbout;
    
    @FXML
    public void onMenuItemSellerAction(){
            System.out.println("Hello Selller");
    }
    
    @FXML
    public void onMenuItemDepartmentAction(){
            System.out.println("Hello Department");
    }
    @FXML
    public void onMenuItemAboutAction(){
            System.out.println("Hello About");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        
    }    
    
}
