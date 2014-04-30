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
public class ExhibitorGender extends BaseDataItem implements DBInterface {
    private int id;
    private int gender;
    private String genderClass;
    private String genderText;
    
    ExhibitorGender() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderClass() {
        return genderClass;
    }

    public void setGenderClass(String genderClass) {
        this.genderClass = genderClass;
    }

    public String getGenderText() {
        return genderText;
    }

    public void setGenderText(String genderText) {
        this.genderText = genderText;
    }

    @Override
    public String toListString(String formatString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExhibitorGender getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.gender = rs.getInt("gender");
        this.genderClass = rs.getString("gender_class");
        this.genderText = rs.getString("gender_text");
        super.getData();
        return this;
    }

    @Override
    public void performUpdate() {
        DBA.updateSQL(String.format(
                "UPDATE exhibitor_genders SET gender = %d, gender_class = \'%s\'"
                        + "gender_text = \'%s\' WHERE id = %d", 
                this.gender,
                this.genderClass,
                this.genderText,
                this.id));
        this.setDirty(false);
    }

    @Override
    public void performDelete() {
        DBA.updateSQL(String.format(
                "DELETE FROM exhibitor_genders WHERE id = %d",
                this.id));
        this.setReadyToDelete(false);
    }

    @Override
    public void performInsert() {
        DBA.updateSQL(String.format(
                "INSERT INTO exhibitor_genders (id, gender, gender_class, "
                        + "gender_text) VALUES (%d,%d,\'%s\',\'%s\')", 
                this.id,
                this.gender,
                this.genderClass,
                this.genderText));
        this.setNewItem(false);
    }

    @Override
    public ExhibitorGender performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM human_genders WHERE id = %d",this.id));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
