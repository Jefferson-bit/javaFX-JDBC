/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.services;


import Model.dao.DepartmentDaoJDBC;
import java.util.ArrayList;
import java.util.List;
import model.Dao.DaoFactory;
import model.Dao.DepartmentDAO;
import model.entities.Department;


public class DepartmentService {
    
   private DepartmentDAO dao = DaoFactory.createDepartment();
    
    public List<Department> findAll(){
        return dao.findAll();
    }
}
