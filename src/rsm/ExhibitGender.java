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
public class ExhibitGender extends BaseDataItem implements DBInterface {
    private int id;
    private int gender;
    private String genderClass;
    private String genderText;

    ExhibitGender() {
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
    public ExhibitGender getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.gender = rs.getInt("gender");
        this.genderClass = rs.getString("gender_class");
        this.genderText = rs.getString("gender_text");
        super.getData();
        return this;
    }

    @Override
    public String toListString(String formatString) {
    String tableLine = String.format(formatString, 
            this.getStatusChar(),
            this.getId(),
            Integer.toString(this.getGender()),
            this.getGenderClass(),
            this.getGenderText());
        return tableLine;
    }

    @Override
    public int performUpdate() {
        int results = 
        DBA.updateSQL(String.format(
                "UPDATE exhibit_genders SET gender = %d, gender_class = \'%s\'"
                        + "gender_text = \'%s\' WHERE id = %d", 
                this.gender,
                this.genderClass,
                this.genderText,
                this.id));
        this.setDirty(false);
        return results;
    }

    @Override
    public int performDelete() {
        int results = 
        DBA.updateSQL(String.format(
                "DELETE FROM exhibit_genders WHERE id = %d",
                this.id));
        this.setReadyToDelete(false);
        return results;
    }

    @Override
    public int performInsert() {
        int results =
        DBA.updateSQL(String.format(
                "INSERT INTO exhibit_genders (id, gender, gender_class, "
                        + "gender_text) VALUES (%d,%d,\'%s\',\'%s\')", 
                this.id,
                this.gender,
                this.genderClass,
                this.genderText));
        this.setNewItem(false);
        return results;
    }

    @Override
    public ExhibitGender performRead() {
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
