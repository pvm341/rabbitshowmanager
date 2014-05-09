/*
 * Copyright (C) 2014 paul
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    private static DBAccess instance; 
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static ResultSetMetaData rsMetaData;
    
    private static String[] tableNames= {
        "breeds", "colours", "breedclasses", "classcolours", "entries",
        "exhibit_ages", "exhibit_genders", "human_ages", "human_genders",
        "exhibits", "exhibitors", "showsections","showclasses", "judges",
        "availablecolours"
    };
    String statusString = "Classes %d Exhibitors %d Exhibits %d Entries %d";
    
    private DBAccess() {
        String[] ipaddress = {"144.124.30.189","10.80.12.92","localhost","<end>"};
        
        int attempt = 0;
        boolean connect = false;
        
            try {
                Class.forName("org.postgresql.Driver");
 
                while(!connect && !ipaddress[attempt].equals("<end>")){
                    try {
                        conn = DriverManager.getConnection(
                                    "jdbc:postgresql://"+ipaddress[attempt]+":5432/rsm", "paul",
                                    "m1c8431"); 
                        connect = true;
                        } catch (Exception e) {
                            conn = null;
                            System.out.println("Unable to connect to the database "+ipaddress[attempt]);
                        } finally{
                            attempt++;
                        }
                    }
                } 
                catch (Exception e) { 
                    System.out.println("Class not found");
                }
                finally {
                    if (!connect)
                        System.exit(1);
                }
            
    }
    public static DBAccess getInstance() {
        if (instance == null){
            instance = new DBAccess();
        }
        return instance;
    }
    
    public static ResultSet executeSQL(String sql) {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }
        return rs;
    }
    
    public static ResultSet executeSQL(String sql, int key) {
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setShort(1, (short)key);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }
        return rs;
    }

    public static ResultSetMetaData getColumns(ResultSet rs) {
        ResultSetMetaData rsmd;
        try {
            rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            rsmd = null;
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return rsmd;     
    }
    
    public static void restockTable(String toTable,    String toFields, String fromFields, 
                                    String fromTables, String where, int key){
        PreparedStatement ps;
        int results;
        String sql;
        try {
            // Clear the receiving table
            stmt = conn.createStatement();
            results = stmt.executeUpdate("DELETE FROM "+toTable);
            // 
            sql = String.format("INSERT INTO %s (%s) SELECT %s FROM %s WHERE %s", toTable,toFields,fromFields,fromTables,where);
                 
            if (sql.contains("?")){
                ps = conn.prepareStatement(sql);
                ps.setInt(1,key);
                results = ps.executeUpdate();
            } else {
                results = stmt.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int getRecordCount(String table){
        return getRecordCount(table,null);
    }
  
       public static int getRecordCount(String table, String where){
        int count=0;
        int rec; 
        if (where == null){
            where = " ";
        } else {
            where = "WHERE "+where;
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM "+table +" "+ where);
            while(rs.next()){
                count++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    public static boolean isExistingRec(String table, String where){
        return getRecordCount(table,where)>0;
    }
    
    public static String lookup(String field, String table, String key, String value){
        String result = "nf";
        String sql;
        sql = "SELECT "+table+"."+field+" FROM "+table+" WHERE "+key+" = "+value;
        rs = DBAccess.executeSQL(sql);
        try{
            rs.next();
            result = rs.getString(field);
        } catch (SQLException ex){
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static void updateSQL(String sql) {
        int alteredRecords = 0;
        try {
            stmt = conn.createStatement();
            alteredRecords = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("%3d Records affected by %s \n", alteredRecords, sql);
    }
    
    /**
     * @param tableNumber the table number to get 
     * @return null if out of range else tableName  if in range
     */
    
    public static String getTableName(int tableNumber){
        String tableName = null;
        if (tableNumber>=0 && tableNumber<15){
            tableName = tableNames[tableNumber];
        }
        return tableName;
    }
    
    public static int getNewKey(String table, String keyName){
        int key =0;
        boolean found = false;
        String sql = String.format("SELECT %s FROM %s",keyName,table);
        rs = DBAccess.executeSQL(sql);
        try {
            while (!found && rs.next()){
                found = ++key != rs.getInt(keyName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return key;
    }

    
}
