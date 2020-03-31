package model.Dao;

import Dados.Conexao;
import Dados.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author crash
 */
public class SellerDaoJDBC implements SellerDao {

    private Connection con;

    public SellerDaoJDBC(Connection con) {
        this.con = con;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name,Email,BirthDate,BaseSalary,DepartmentId)"
                    + "VALUES"
                    + "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                Conexao.closeResultSet(rs);
            } else {
                throw new DBException("unexcepted erro! no rows affected");
            }
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        } finally {
            Conexao.closeStatement(st);
        }

    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "UPDATE seller SET"
                    + "Name=?,Email=?,BirthDate=?,BaseSalary=?,DepartmentId=?"
                    + "WHERE Id=?");
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();

        } catch (SQLException ex) {
            throw new DBException("Erro ao atualizar dados");
        } finally {
            Conexao.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("DELETE FROM seller WHERE id =? ");
            st.setInt(1, id);
            st.executeUpdate();
            
        } catch (SQLException ex) {
            throw new DBException("Erro ao excluir");
        }finally{
            Conexao.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement(
                    "SELECT seller.*,department.name as DepName "
                    + "FROM seller inner join department "
                    + "on seller.DepartmentId = department.id "
                    + "where seller.id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = instatiateDepartment(rs);
                Seller obj = instatiateSeller(rs, dep);
                return obj;
            }
            return null;
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        } finally {
            Conexao.closeStatement(st);
            Conexao.closeResultSet(rs);
        }

    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement(
                    "SELECT seller.*,department.name as DepName "
                    + "FROM seller inner join department "
                    + "on seller.DepartmentId = department.id "
                    + "order by name");

            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instatiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        } finally {
            Conexao.closeStatement(st);
            Conexao.closeResultSet(rs);
        }
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setDepartment(dep);
        return obj;
    }

    @Override
    public List<Seller> findByDeparment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement(
                    "SELECT seller.*,department.name as DepName "
                    + "FROM seller inner join department "
                    + "on seller.DepartmentId = department.id "
                    + "where DepartmentId = ? "
                    + "order by Name");
            st.setInt(1, department.getId());
            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instatiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        } finally {
            Conexao.closeStatement(st);
            Conexao.closeResultSet(rs);
        }

    }

}
