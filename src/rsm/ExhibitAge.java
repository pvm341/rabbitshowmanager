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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paul
 */
public class ExhibitAge extends BaseDataItem implements DBInterface{
    private int id;
    private int age;
    private String ageText;
    private String abbrev;

    public ExhibitAge(){
        
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAgeText() {
        return ageText;
    }

    public void setAgeText(String ageText) {
        this.ageText = ageText;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    @Override
    public String toListString(String formatString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     /**
     * Gets the data for Current Object (ExhibitAge) from the database result set
     * @param rs The ResultSet
     * @return pointer to current object
     * @throws SQLException 
     */
    @Override
    final public ExhibitAge getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.age = rs.getInt("age");
        this.ageText = rs.getString("age_text");
        this.abbrev = rs.getString("abbrev");
        super.getData();
        return this; 
    }

    @Override
    public int performUpdate() {
        int results =
        DBA.updateSQL(String.format(
                "UPDATE exhibit_ages SET age = %d, age_text = \'%s\', abbrev "
                        + "= \'%s\' WHERE id = %d",
                this.age,
                this.ageText,
                this.abbrev,
                this.id));
        this.setDirty(false);
        return results;
    }

    @Override
    public int performDelete() {
        int results = DBA.updateSQL(String.format(
                "DELETE FROM exhibit_ages WHERE id = %d ", 
                this.id));
        this.setReadyToDelete(false);
        return results;
    }

    @Override
    public int performInsert() {
        int results = DBA.updateSQL(String.format(
                "INSERT INTO exhibit_ages (id,age,age_text,abbrev) VALUES "
                        + "(%d,%d,\'%s\',\'%s\')",
                this.id,
                this.age,
                this.ageText,
                this.abbrev));
        this.setNewItem(false);
        return results;
    }

    @Override
    public ExhibitAge performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM exhibit_ages WHERE id = %d",this.id));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
