/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.control.Alert;



/**
 *
 * @author crash
 */
public class Alerts {
    
    
    public static void alertShow(String title, String header, String content, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle("Titulo");
        alert.setHeaderText("Aviso");
        alert.setContentText("Resultado");
        alert.show();
        
    }
}
