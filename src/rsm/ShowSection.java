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
public class ShowSection extends BaseDataItem implements DBInterface {
    private int id;
    private int section;
    private String sectionText;
    
    public ShowSection() {
        super();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }
        
    @Override
    public String toListString(String formatString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ShowSection getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.section = rs.getInt("section");
        this.sectionText = rs.getString("section_text");
        this.getData();
        return this;
    }

    @Override
    public int performUpdate() {
        int results = DBA.updateSQL(String.format(
                "UPDATE showsections SET section = %d, section_text = \'%s\' "
                        + "WHERE id = %d",
                this.section,this.sectionText,this.id));
        this.setDirty(false);
        return results;
    }

    @Override
    public int performDelete() {
        int results = DBA.updateSQL(String.format(
                "DELETE FROM showsections WHERE id= %d", this.id));
        this.setReadyToDelete(false);
        return results;
    }

    @Override
    public int performInsert() {
        int results = DBA.updateSQL(String.format(
                "INSERT INTO showsections (id,section, section_text) "
                        + "VALUES (%d,%d,\'%s\')", 
                 this.id,this.section,this.sectionText));
        this.setNewItem(false);
        return results;
    }

    @Override
    public ShowSection performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
            "SELECT * FROM showsection WHERE id = %d",this.id));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
