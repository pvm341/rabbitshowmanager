/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul
 */
public class DBAccess {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    
    public DBAccess() {
        try {
            Class.forName("org.postgresql.Driver");
 
            connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/rsm", "postgres",
					"postgres"); 
     
        } catch (Exception e) {
            connection = null;
        }
    
    }
    
    public ResultSet select(String sql) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            resultSet = null;
        }
        return resultSet;
    }
    
    public ResultSet select(String sql, int key) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setShort(1, (short)key);
            resultSet = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            resultSet = null;
        }
        return resultSet;
    }

    public ResultSetMetaData getColumns(ResultSet rs) {
        ResultSetMetaData rsmd;
        try {
            rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            rsmd = null;
            Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsmd;
    }
}
