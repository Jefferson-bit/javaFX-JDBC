/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Dao;

import Dados.Conexao;
import Model.dao.DepartmentDaoJDBC;
import com.mysql.jdbc.Connection;


public class DaoFactory {
    
    
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(Conexao.getConnection()); 
    }
    public static DepartmentDAO createDepartment(){
        return new DepartmentDaoJDBC((Connection) Conexao.getConnection());
    }
}
