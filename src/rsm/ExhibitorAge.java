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
public class ExhibitorAge extends BaseDataItem implements DBInterface {
    private int id;
    private int age;
    private String ageText;
    private String abbrev;
    
    ExhibitorAge() {
        super();
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

    @Override
    public ExhibitorAge getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.age = rs.getInt("age");
        this.abbrev = rs.getString("abbrev");
        this.ageText = rs.getString("age_text");
        return this;
    }

    @Override
    public void performUpdate() {
        DBA.updateSQL(String.format(
                "UPDATE human_ages SET age = %d, age_text = \'%s\', abbrev " 
                        +"\'%s\' WHERE id = %d",
                       this.age,
                       this.ageText,
                       this.abbrev,
                       this.id));
        this.setDirty(false);
    }

    @Override
    public void performDelete() {
        DBA.updateSQL(String.format(
                "DELETE FROM human_ages WHERE id = %d",
                this.id));
        this.setDirty(false);
    }

    @Override
    public void performInsert() {
        DBA.updateSQL(String.format(
                "INSERT INTO human_ages (id,age,age_text,abbrev) VALUES "
                        + "(%d,%d,\'%s\',\'%s\')",
                this.id,
                this.age,
                this.ageText,
                this.abbrev));
        this.setNewItem(false);
    }

    @Override
    public ExhibitorAge performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM human_ages WHERE id = %d",this.id));
        try {
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
