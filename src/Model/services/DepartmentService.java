/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.services;


import java.util.List;
import model.Dao.DaoFactory;
import model.Dao.DepartmentDAO;
import model.entities.Department;


public class DepartmentService {
    
   private DepartmentDAO dao = DaoFactory.createDepartment();
    
    public List<Department> findAll(){
        return dao.findAll();
    }
    //estamos inserindo ou atualizando, caso ID for nulo, ele entra no else, 
    //chamando o metodo de atualização
    public void  saveOrUpdate(Department obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else{
            dao.update(obj);
        }
    }
}
