/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.dao;

import Dados.Conexao;
import Dados.DBException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO {

    private Connection con;

    public DepartmentDaoJDBC(Connection con) {
        this.con = con;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement stm = null;
        try {

            stm = (PreparedStatement) con.prepareStatement("INSERT INTO department"
                    + "(name)"
                    + "VALUE (?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, obj.getName());
            int rows = stm.executeUpdate();

            if (rows > 0) {
                ResultSetImpl   rs = (ResultSetImpl) stm.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getId();
                    obj.setId(id);
                }
                Conexao.closeResultSet(rs);
            }
        } catch (SQLException ex) {
            throw new DBException("Erro ao iserir dados" + ex.getMessage());
        } finally {
            Conexao.closeStatement(stm);

        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement stm = null;
        try {
            stm = (PreparedStatement) con.prepareStatement("UPDATE department SET Name=?"
                    + "WHERE Id=?");
            stm.setString(1, obj.getName());
            stm.setInt(2, obj.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException("erro ao atualizar" + ex.getMessage());
        } finally {
            Conexao.closeStatement(stm);
        }

    }

    @Override
    public void deletById(Integer id) {
        PreparedStatement stm = null;
        try {
            stm = (PreparedStatement) con.prepareStatement("DELETE FROM department WHERE id=?");
            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException ex) {
            throw new DBException("Erro ao excluir");
        } finally {
            Conexao.closeStatement(stm);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement stm = null;
        ResultSetImpl rs = null;
        try {
            stm = (PreparedStatement) con.prepareStatement("SELECT * FROM department WHERE id = ?");
            stm.setInt(1, id);
            rs = (ResultSetImpl) stm.executeQuery();
            if (rs.next()) {
                Department obj = new Department();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                return obj;
            }
            return null;
        } catch (SQLException ex) {
            throw new DBException("Erroa busca Id" + ex.getMessage());
        }finally{
            Conexao.closeResultSet(rs);
            Conexao.closeStatement(stm);
        }

    }

    @Override
    public List<Department> findAll() {

        PreparedStatement stm = null;
        ResultSetImpl rs = null;
        try {
            stm = (PreparedStatement) con.prepareStatement("SELECT * FROM department ORDER BY Name");          
            rs = (ResultSetImpl) stm.executeQuery();
            List<Department> list = new ArrayList<>();
            
            while(rs.next()) {
                Department obj = new Department();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                list.add(obj);
            }
            return list;
        } catch (SQLException ex) {
            throw new DBException("Erroa busca Total" + ex.getMessage());
        }
    }

}
