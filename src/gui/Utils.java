/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 *
 * @author crash
 */
public class Utils {
    
    public static Stage  currentStagem(ActionEvent event){
        return  (Stage) ((Node)event.getSource()).getScene().getWindow();
    }
    
    //ESSE METODO ELE RETORNA UM INTEGER 
    //MAS PODE SER QUE O CONTEUDO NA CAIXINHA  ELE NÃO SEJA UM NUMERO INTEIRO. AI SERÁ LANÇADO UMA EXCEÇÃO
    public static Integer tryParseToInt(String str){
        try{
            return Integer.parseInt(str);
        }catch(NumberFormatException e){
        //CASSO ACONTEÇA UMA EXCEÇÃO, EU NÃO VOU QUERER LANÇAR UMA EXECEÇÃO, ENTÃO RETORNO NULO
            return null;
        }
    }
    
}
