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
public abstract class AbstractAge implements DBInterface {
    protected int id;
    protected int age;
    protected String ageText;
    protected String abbrev;

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
    public void deleteRecord() {
        String where = String.format("WHERE id = %d", this.id);
        if (DBAccess.isExistingRec("exhibit_ages", where)){
            DBAccess.updateSQL("DELETE FROM exhibit_ages "+where);
        }
    }

    @Override
    public void readRecord(int recNo) {
        ResultSet rs;
        String sql = String.format("SELECT * FROM exhibit_ages WHERE id= %d",recNo);
        rs = DBAccess.executeSQL(sql);
        try {
            while (rs.next()){
                this.id = rs.getInt("id");
                this.age = rs.getInt("age");
                this.ageText = rs.getString("age_text");
                this.abbrev = rs.getString("abbrev");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExhibitAge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeRecord() {
        String sql;
        String where = String.format("where id = %d", this.id);
        if (DBAccess.isExistingRec("exhibit_ages",where)){
            sql=String.format("UPDATE breeds SET age = %d, age_text = %s, abbrev = %s %s",this.age, this.ageText,this.abbrev,where); 
        } else {
            this.id = 1 + DBAccess.getRecordCount("exhibit_ages");
            sql=String.format("INSERT INTO breeds (id,age,age_text,abbrev) VALUES (%d,%d,%s,%s)",this.id,this.age, this.ageText,this.abbrev);
        }
        DBAccess.updateSQL(sql); 
    }

}
