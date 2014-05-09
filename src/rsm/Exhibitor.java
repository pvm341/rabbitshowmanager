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
public class Exhibitor extends BaseDataItem implements DBInterface{
    private int id; 
    private String name;   
    private String address;
    private String phone;
    private String email;
    private boolean bookedIn;
    private boolean bookedOut;
    private boolean paidFees;
    private boolean paidPrizes; 
    private double entryFees;
    private double prizeMoney;
    private boolean clubMember;
    private int ageGroup;
    private int gender;
    
    public Exhibitor(){
        
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBookedIn() {
        return bookedIn;
    }

    public void setBookedIn(boolean bookedIn) {
        this.bookedIn = bookedIn;
    }

    public boolean isBooked_out() {
        return bookedOut;
    }

    public void setBooked_out(boolean booked_out) {
        this.bookedOut = booked_out;
    }

    public boolean isPaid_fees() {
        return paidFees;
    }

    public void setPaid_fees(boolean paid_fees) {
        this.paidFees = paid_fees;
    }

    public boolean isPaid_prizes() {
        return paidPrizes;
    }

    public void setPaid_prizes(boolean paid_prizes) {
        this.paidPrizes = paid_prizes;
    }

    public double getEntry_fees() {
        return entryFees;
    }

    public void setEntry_fees(double entry_fees) {
        this.entryFees = entry_fees;
    }

    public double getPrize_money() {
        return prizeMoney;
    }

    public void setPrize_money(double prize_money) {
        this.prizeMoney = prize_money;
    }

    public boolean isClub_member() {
        return clubMember;
    }

    public void setClub_member(boolean club_member) {
        this.clubMember = club_member;
    }

    public int getAge_group() {
        return ageGroup;
    }

    public void setAge_group(int age_group) {
        this.ageGroup = age_group;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
    
    @Override
    public String toListString(String formatString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exhibitor getData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.address = rs.getString("address");
        this.phone = rs.getString("phone");
        this.email = rs.getString("email");
        this.bookedIn = rs.getBoolean("booked_in");
        this.bookedOut = rs.getBoolean("booked_out");
        this.paidFees = rs.getBoolean("paid_fees");
        this.paidPrizes =rs.getBoolean("paid_prizes"); 
        this.entryFees = rs.getDouble("entry_fee");
        this.prizeMoney = rs.getDouble("prize_money");
        this.clubMember = rs.getBoolean("club_member");
        this.ageGroup  = rs.getInt("age_group");
        this.gender = rs.getInt("gender");
        super.getData();
        return this;
    }

    @Override
    public int performUpdate() {
        int results = 
        DBA.updateSQL(String.format(
                "UPDATE exhibitors SET "
                        + "name = \'%s\',"
                        + "address = \'%s\',"
                        + "phone = \'%s\',"
                        + "email = \'%s\',"
                        + "booked_in = %s,"
                        + "booked_out = %s,"
                        + "paid_fees = %s,"
                        + "paid_prizes = %s,"
                        + "entry_fee = %5.2f,"
                        + "prize_money = %5.2f,"
                        + "club_member = %s,"
                        + "age_group = %d,"
                        + "gender = %d "
                        + "WHERE id = %d",
                this.name,
                this.address,
                this.phone,
                this.email,
                Boolean.toString(this.bookedIn),
                Boolean.toString(this.bookedOut),
                Boolean.toString(this.paidFees),
                Boolean.toString(this.paidPrizes),
                this.entryFees,
                this.prizeMoney,
                Boolean.toString(this.clubMember),
                this.ageGroup,
                this.gender,
                this.id));
          this.setNewItem(true);
          return results;
    }

    @Override
    public int performDelete() {
        int results = 0;
        // delete exhibits for this exhibitor first
        DBA.updateSQL(String.format(
                "DELETE FROM exhibits WHERE exhibitor_id = %d",
                this.id));
        results = DBA.updateSQL(String.format(
                "DELETE FROM exhibitors WHERE id = %d",
                this.id));
        this.setReadyToDelete(false);
        return results;
    }

    @Override
    public int performInsert() {
        int results = 
        DBA.updateSQL(String.format(
                "INSERT INTO exhibitors (name,address,phone,email,booked_in,"
                        + "booked_out,paid_fees,paid_prizes,entry_fee,"
                        + "prize_money,club_member,age_group,gender) VALUES ("
                        + "\'%s\',\'%s\',\'%s\',\'%s\',%s,%s,%s,%s,%5.2f,%5.2f,"
                        + "%s,%d,%d)",
                        this.name, 
                        this.address, 
                        this.phone,
                        this.email,
                        Boolean.toString(this.bookedIn),
                        Boolean.toString(this.bookedOut),
                        Boolean.toString(this.paidFees),
                        Boolean.toString(this.paidPrizes),
                        this.entryFees,
                        this.prizeMoney,
                        Boolean.toString(this.clubMember),
                        this.ageGroup,
                        this.gender));
        this.setNewItem(false);
        return results;
    }

    @Override
    public Exhibitor performRead() {
        ResultSet rs = DBA.executeSQL(String.format(
                "SELECT * FROM exhibitors WHERE id = %d",this.id));
        try {
            rs.next();
            return this.getData(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Colour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
