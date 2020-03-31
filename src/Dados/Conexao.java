package Dados;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author crash
 */
public class Conexao {

    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {

            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                con = DriverManager.getConnection(url, props);
                System.out.println("Conexao bem sucedida");
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao conectar no banco" + ex.getMessage());
            }
        }
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexao fechada com sucesso");
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar banco" + ex.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("src/Dados/db")) {
            Properties props = new Properties();
            props.load(fs);
            return props;

        } catch (IOException ex) {
            throw new RuntimeException("Erro arquivo" + ex.getMessage());

        }

    }
    public static void closeStatement(Statement stm){
        if(stm != null){
            try {
                stm.close();
            } catch (SQLException ex) {
                throw new RuntimeException("erro statement"+ex.getMessage());
            }
        }
    }
    
    
    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException ex) {
                throw new RuntimeException("erro ResultSet"+ex.getMessage());
            }
        }
    }

    
    
}
