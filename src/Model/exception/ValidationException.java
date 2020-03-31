/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.exception;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author crash
 */
public class ValidationException extends RuntimeException{
    
    private static final long serialVersuinUID = 1L;
    
    private Map<String, String> errors = new HashMap<>();
    
    //pegando o Erro
    public Map<String, String> getErros(){
        return errors;
    }
    //metodo responsável por inserir mensagem de erro nos campos, e notificar o erro
    public void addErrors(String fieldName, String errorMessenge){
        errors.put(fieldName, errorMessenge);
    }
    
    public ValidationException(String msg){
        super(msg);
    }
}
