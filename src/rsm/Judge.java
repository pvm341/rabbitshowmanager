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
public class Judge extends BaseDataItem implements DBInterface {
    private int id;
    private String name;
    private int sections;
    
    Judge() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSections() {
        return sections;
    }

    public void setSections(int sections) {
        this.sections = sections;
    }


    @Override
    public String toListString(String formatString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Judge getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.sections = rs.getInt("sections");
        super.getData();
        return this;
    }

    @Override
    public void performUpdate() {
        DBA.updateSQL(String.format(
            "UPDATE judges set name = \'%s\', sections = %d WHERE id = %d",
             this.name, this.sections, this.id));
        this.setDirty(false);
    }

    @Override
    public void performDelete() {
        DBA.updateSQL(String.format(
            "DELETE FROM judges WHERE id = %d",
             this.id));
        this.setReadyToDelete(false);
    }

    @Override
    public void performInsert() {
        DBA.updateSQL(String.format("INSERT INTO judges (id,name, sections) "
                + "VALUES (%d,\'%s\',%d",this.id,this.name,this.sections));
        this.setNewItem(false);
    }

    @Override
    public Judge performRead() {
            ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM judges WHERE id = %d",this.id));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
}

   
}
