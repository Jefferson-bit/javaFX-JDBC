package model.Dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import model.entities.Department;

/**
 *
 * @author crash
 */
public interface DepartmentDAO {
    
    void insert(Department obj);
    void update (Department obj);
    void deletById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();
}
