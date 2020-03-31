package Dados;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author crash
 */
public class DBException extends RuntimeException {

    private static final long serialVersioUID = 1L;
    
    public DBException(String msg) {
        super(msg);
    }
    
    
}
