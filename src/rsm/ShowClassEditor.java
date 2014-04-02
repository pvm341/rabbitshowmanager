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
//import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author paul
 */
public class ShowClassEditor extends javax.swing.JFrame {
    private ShowClass sc;
    private Vector<Integer> breedIdKeyList = null, coloursIdKeyList = null;
    //private Vector<String> sltColoursForClass = new Vector<String>();
    //private Vector<Integer> iltColoursForClass = new Vector<Integer>();
    private boolean initDone, useAbbrevs;
    @SuppressWarnings("FieldMayBeFinal")
    //private ListModel<String> lstColoursForClassModel;
    private DefaultListModel<String> lstColoursForClassData;// = (DefaultListModel<String>)(.getModel());
    private Vector<Colour> availableColourList;
    
    
    /**
     * Creates new form ShowClassEditor
     * @param dbcon
     */
    public ShowClassEditor() {
        sc = new ShowClass();
        initDone = false;
        DBAccess.getInstance();
        breedIdKeyList = new Vector<Integer>();
        availableColourList = new Vector<Colour>();
        initComponents();
        lstColoursForClass.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstColoursForClass.setVisible(true);
        lstColoursForClassData= new DefaultListModel<>();
        lstColoursForClass.setModel(lstColoursForClassData);
        lstColoursForClass.setVisibleRowCount(8);
        breedIdKeyList = populateWithList(cmxBreed,"SELECT * FROM breeds ORDER BY breeds.id","breed",breedIdKeyList);
        initDone=true;
       

        populate(cmxSection,"SELECT * FROM showsections","section_text");
        //populateRestricted(cmxExhibitAge,"SELECT * FROM exhibit_ages","age_text");
        //populate(cmxExhibitAge,"SELECT * FROM exhibit_ages","age_text");
        populate(cmxExhibitorAge,"SELECT * FROM human_ages","age_text");
        populate(cmxExhibitGender,"SELECT * FROM exhibit_genders","gender_class");
        populate(cmxExhibitorGender,"SELECT * FROM human_genders","gender_class");
        createColoursAndAges();
        setColoursEnabled();

     }

    /**
     *
     * @param sc the value of sc
     * @return the rsm.ShowClass
     */
    private ShowClass makeClassName(ShowClass sc){
        String name = String.format("%cClass %s ",cbxBreedClass.isSelected()?'*':' ',edtClassNo.getText());
        rbnStandard.setEnabled(!sc.isBreedClass());
        rbnBreeders.setEnabled(!sc.isBreedClass());
        rbnUpSideDown.setEnabled(!sc.isBreedClass());
        rbnMembers.setEnabled(!sc.isBreedClass());
        useAbbrevs = useAbbrevs || name.length()>30;
        
        name += (String) cmxBreed.getSelectedItem();
        
        if (cmxColours.isEnabled()){
            for (int idx = 0; idx < lstColoursForClassData.size(); idx++){
                name += " " + lstColoursForClassData.get(idx);
                if ((idx+1) < lstColoursForClassData.size()){
                    name += " &";
                }
            }            
        }
        
        name += " " + (String) cmxExhibitAge.getSelectedItem();
 
        if (cmxExhibitGender.getSelectedIndex()!=0){
            name += " " + (String) cmxExhibitGender.getSelectedItem();
        }
        if (cmxExhibitorAge.getSelectedIndex()!=0){
            name += " " + (String) cmxExhibitorAge.getSelectedItem();
        }
        if (cmxExhibitorGender.getSelectedIndex()!=0){
            name += " " + (String) cmxExhibitorGender.getSelectedItem();
        }
        if (rbnBreeders.isEnabled() && rbnBreeders.isSelected()){
            name += " Breeders";
        }
        if (rbnUpSideDown.isEnabled() && rbnUpSideDown.isSelected()){
            name += " Up Side Down";
        }
        if (rbnMembers.isEnabled() && rbnMembers.isSelected()){
            name += " Members";
        }
        sc.setName(name);
        edtClassName.setText(sc.getName());
        sc.setClassNo(Integer.valueOf(edtClassNo.getText()));
        sc.setBreedClass(cbxBreedClass.isSelected());
        sc.setBreedersOnly(!cbxBreedClass.isSelected() && rbnBreeders.isSelected());
        sc.setUpSideDown(!cbxBreedClass.isSelected() && rbnUpSideDown.isSelected());
        sc.setMembersOnly(!cbxBreedClass.isSelected() && rbnMembers.isSelected());
        sc.setStandard(!cbxBreedClass.isSelected() && rbnStandard.isSelected());
        sc.setExhibitAge(cmxExhibitAge.getSelectedIndex());
        sc.setExhibitGender(cmxExhibitGender.getSelectedIndex());
        sc.setExhibitorAge(cmxExhibitorAge.getSelectedIndex());
        sc.setExhibitorGender(cmxExhibitorGender.getSelectedIndex());        
        return sc;
    }
    
    private Vector<Integer> populateWithList(JComboBox comboBox, String sql, String field, Vector<Integer> keyList){
        ResultSet rs;
        keyList.clear();
        comboBox.removeAllItems();
        rs = DBAccess.executeSQL(sql);
        if (rs != null){
            try {

                while (rs.next()){
                   comboBox.addItem(rs.getString(field));
                   //breedIdKeyList.add(rsDBAccess.getInstance();.getInt("id"));
                   keyList.add(rs.getInt("id"));
                }
                comboBox.updateUI();
            } catch (SQLException ex) {
                Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return keyList;
    } 
    
    private void populate(JComboBox comboBox, String sql, String field){
        ResultSet rs;
        
        comboBox.removeAllItems();
        rs = DBAccess.executeSQL(sql);
        if (rs != null){
            try {
                while (rs.next()){
                   comboBox.addItem(rs.getString(field)); 
                }
                comboBox.updateUI();
            } catch (SQLException ex) {
                Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param comboBox  The JComboBox to po<String>(v)pulate
     * @param sql       The SQL statement from the table
     * @param cmxKey    The JComboBox that holds the key to restricted executeSQLion
     * @param keys      The key values
     */
    private void populate(JComboBox comboBox, String sql, String field, JComboBox cmxKey, Vector<Integer> keys){
        ResultSet rs;
        
        comboBox.removeAllItems();
        rs = DBAccess.executeSQL(sql,keys.get(cmxKey.getSelectedIndex()));
        if (rs != null){
            try {
                while (rs.next()){
                   comboBox.addItem(rs.getString(field)); 
                }
                comboBox.updateUI();
            } catch (SQLException ex) {
                Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Vector<Colour> populateColours(JComboBox comboBox, String sql, String field, JComboBox cmxKey, Vector<Integer> keys){
        ResultSet rs;
        String clr;
        int cl;
        useAbbrevs = false;
        Vector<Colour> localColourList = new Vector<Colour>();
        Colour localColour = null;
        comboBox.removeAllItems();
        rs = DBAccess.executeSQL(sql, keys.get(cmxKey.getSelectedIndex()));
        if (rs != null){
            try{
                //rsmd =rs.getMetaData();
                while (rs.next()){
                    clr = rs.getString(field);
                    cl = rs.getInt("id");
                    comboBox.addItem(clr);
                    localColour = new Colour();
                    
                    localColour.readRecord(cl);
                    localColourList.add(localColour);
                }   
                comboBox.updateUI();
            } catch(SQLException ex){
                Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);                
            }
        }
        return localColourList;
    }
    
    private Vector<Integer> populate(JComboBox comboBox, String sql, String field, JComboBox cmxKey, Vector<Integer> keys, Vector<Integer> keyList){
        ResultSet rs;
        Vector<Integer> localKeyList = null; 
        if (keyList == null){
            localKeyList = new Vector<>();
        } else {
            localKeyList = keyList;
        }
        comboBox.removeAllItems();
        //keyList.clear();
        rs = DBAccess.executeSQL(sql,keys.get(cmxKey.getSelectedIndex()));
        if (rs != null){
            try {
                while (rs.next()){
                   comboBox.addItem(rs.getString(field)); 
                   localKeyList.add(rs.getInt("id"));
                }
                comboBox.updateUI();
            } catch (SQLException ex) {
                Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return localKeyList;
    }

    /**
     * updates the colours JComboBox to the colours available with
     * the current breed.
     * Netherland Dwarf and Polish have a large executeSQLion of colours
     * Some have only one colour and the colour executeSQLion is disabled,
     * Changing the breed again the colour is re-enabled.
     */
    private void createColoursAndAges(){
        Colour colour;
        if (initDone){
            lblColours.setEnabled(true);
            cmxColours.setEnabled(true);
            cmxColours.removeAllItems();
            availableColourList.clear();
            if (coloursIdKeyList != null && !coloursIdKeyList.isEmpty()){
                coloursIdKeyList.clear();
            }
            coloursIdKeyList = populate(cmxColours,
                    "SELECT colours.colour, colours.id "
                    + "FROM breedcolours, breeds, colours "
                    + "WHERE breedcolours.breed_id = breeds.id "
                    + "AND breedcolours.colour_id = colours.id "
                    + "AND breeds.id = ?",
                    "colour",
                    cmxBreed, 
                    breedIdKeyList,coloursIdKeyList);
            availableColourList.clear();
            for (int idx = 0; idx < coloursIdKeyList.size(); idx++){
                colour = new Colour();
                colour.readRecord(coloursIdKeyList.get(idx));
                availableColourList.add(colour);
            }
            cmxExhibitAge.removeAllItems();
            populate(cmxExhibitAge,
                    "SELECT DISTINCT exhibit_ages.age_text, exhibit_ages.age "
                    + "FROM breeds, exhibit_ages "
                    + "WHERE exhibit_ages.id = breeds.adult_age "
                    + "AND breeds.adult_age = exhibit_ages.id " 
                    + "AND breeds.id = ? " 
                    + "AND exhibit_ages.age = 3 "
                    + "OR exhibit_ages.age != 3 ",
                    "age_text",
                    cmxBreed, 
                    breedIdKeyList);
            lblColours.setEnabled(cmxColours.getItemCount()>0);
            cmxColours.setEnabled(cmxColours.getItemCount()>0);
        }
    }
    
    private void populateAvailableColoursTable(int breed){
        
        
//     "colour", 
//                             "colours.id ", 
//                             "breedcolours, breeds, colours", 
//                             "breedcolours.breed_id = breeds.id AND breedcolours.colour_id = colours.id AND breeds.id = ?", 
//                             breedIdKeyList.get(cmxBreed.getSelectedIndex()));
            
        
    }
    private JComboBox updateColours(JComboBox comboBox, final Vector<Integer> coloursIdKeyList ){
        comboBox.removeAllItems();
        for (int idx = 0; idx<coloursIdKeyList.size(); idx++){
            if (coloursIdKeyList.get(idx)>0){
                comboBox.addItem(availableColourList.get(idx).getColour());
            }
        }
        return comboBox;
    }
    
    public void populateTable(){
        ResultSet rs = DBAccess.executeSQL("SELECT * FROM showclasses");
        try {
         
             while (rs.next()){
                 
             }
        } catch (SQLException ex) {
            Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

        //tblShowClasses.addColumn(null);
    }
    
    private String getSelectedColours() {
        return "To be completed";
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnClose = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShowClasses = new javax.swing.JTable();
        lblClassNo = new javax.swing.JLabel();
        edtClassNo = new javax.swing.JTextField();
        lblClassName = new javax.swing.JLabel();
        edtClassName = new javax.swing.JTextField();
        lblBreed = new javax.swing.JLabel();
        cmxBreed = new javax.swing.JComboBox();
        lblColours = new javax.swing.JLabel();
        cmxColours = new javax.swing.JComboBox();
        lblSection = new javax.swing.JLabel();
        cmxSection = new javax.swing.JComboBox();
        cbxBreedClass = new javax.swing.JCheckBox();
        cmxExhibitAge = new javax.swing.JComboBox();
        lblEhibitAge = new javax.swing.JLabel();
        lblExhibitGender = new javax.swing.JLabel();
        cmxExhibitGender = new javax.swing.JComboBox();
        lblExhibitorAge = new javax.swing.JLabel();
        cmxExhibitorAge = new javax.swing.JComboBox();
        cmxExhibitorGender = new javax.swing.JComboBox();
        lblExhibitorGender = new javax.swing.JLabel();
        btnAddColour = new javax.swing.JButton();
        btnDelColour = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstColoursForClass = new javax.swing.JList<String>();
        rbnBreeders = new javax.swing.JRadioButton();
        rbnStandard = new javax.swing.JRadioButton();
        rbnMembers = new javax.swing.JRadioButton();
        rbnUpSideDown = new javax.swing.JRadioButton();
        lblColoursForClass = new javax.swing.JLabel();
        btnAddThisClass = new javax.swing.JButton();

        setTitle("Class Editor");

        btnClose.setText("Close");
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        tblShowClasses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblShowClasses.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tblShowClasses);
        tblShowClasses.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        lblClassNo.setText("Class No");

        edtClassNo.setText("100");

        lblClassName.setText("Class Name");

        edtClassName.setBackground(new java.awt.Color(125, 200, 215));
        edtClassName.setFont(new java.awt.Font("Lucida Console", 0, 10)); // NOI18N
        edtClassName.setText("developed as data is entered");

        lblBreed.setText("Breed");

        cmxBreed.setToolTipText("Check for a breed class");
        cmxBreed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxBreedActionPerformed(evt);
            }
        });

        lblColours.setText("Colours");

        cmxColours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxColoursActionPerformed(evt);
            }
        });

        lblSection.setText("Section");

        cbxBreedClass.setText("Breed Class");
        cbxBreedClass.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbxBreedClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxBreedClassActionPerformed(evt);
            }
        });

        cmxExhibitAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxExhibitAgeActionPerformed(evt);
            }
        });

        lblEhibitAge.setText("Exibit Age");

        lblExhibitGender.setText("Exhibit Gender");

        cmxExhibitGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxExhibitGenderActionPerformed(evt);
            }
        });

        lblExhibitorAge.setText("Exhibitor Age");

        cmxExhibitorAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxExhibitorAgeActionPerformed(evt);
            }
        });

        cmxExhibitorGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxExhibitorGenderActionPerformed(evt);
            }
        });

        lblExhibitorGender.setText("Exhibitor Gender");

        btnAddColour.setBackground(new java.awt.Color(107, 201, 134));
        btnAddColour.setFont(new java.awt.Font("DejaVu Sans", 0, 10)); // NOI18N
        btnAddColour.setText("Add Colour");
        btnAddColour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddColourActionPerformed(evt);
            }
        });

        btnDelColour.setFont(new java.awt.Font("DejaVu Sans", 0, 10)); // NOI18N
        btnDelColour.setText("Delete Colour");
        btnDelColour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelColourActionPerformed(evt);
            }
        });

        lstColoursForClass.setModel(cmxColours.getModel());
        lstColoursForClass.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(lstColoursForClass);

        buttonGroup1.add(rbnBreeders);
        rbnBreeders.setText("Breeders");
        rbnBreeders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnBreedersActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbnStandard);
        rbnStandard.setText("Standard Open");
        rbnStandard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnStandardActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbnMembers);
        rbnMembers.setText("Members");
        rbnMembers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnMembersActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbnUpSideDown);
        rbnUpSideDown.setText("Up Side Down");
        rbnUpSideDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnUpSideDownActionPerformed(evt);
            }
        });

        lblColoursForClass.setText("Colours For this class");

        btnAddThisClass.setText("Add This Class");
        btnAddThisClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddThisClassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSection)
                            .addComponent(lblBreed)
                            .addComponent(lblColours)
                            .addComponent(lblEhibitAge)
                            .addComponent(lblExhibitorAge))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(cmxSection, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(cbxBreedClass))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmxExhibitAge, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(lblExhibitGender)
                                .addGap(9, 9, 9)
                                .addComponent(cmxExhibitGender, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmxExhibitorAge, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(lblExhibitorGender)
                                .addGap(6, 6, 6)
                                .addComponent(cmxExhibitorGender, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmxColours, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddColour, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmxBreed, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbnStandard)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbnMembers)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbnUpSideDown)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbnBreeders)))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblColoursForClass)
                                    .addComponent(btnDelColour))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblClassNo)
                        .addGap(4, 4, 4)
                        .addComponent(edtClassNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblClassName)
                        .addGap(6, 6, 6)
                        .addComponent(edtClassName, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(257, 257, 257)
                        .addComponent(btnAddThisClass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edtClassNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtClassName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClassNo)
                            .addComponent(lblClassName))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmxSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblSection))
                            .addComponent(cbxBreedClass)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblColoursForClass)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmxBreed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBreed))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmxColours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblColours)
                            .addComponent(btnAddColour))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmxExhibitAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEhibitAge))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblExhibitGender))
                            .addComponent(cmxExhibitGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmxExhibitorAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblExhibitorGender))
                            .addComponent(cmxExhibitorGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblExhibitorAge))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbnStandard)
                            .addComponent(rbnMembers)
                            .addComponent(rbnUpSideDown)
                            .addComponent(rbnBreeders)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelColour)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnClose)
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAddThisClass)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseMouseClicked

    private void cbxBreedClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxBreedClassActionPerformed
        sc.setBreedClass(cbxBreedClass.isSelected());
        sc = makeClassName(sc);
    }//GEN-LAST:event_cbxBreedClassActionPerformed
    private void setColoursEnabled(){
        lstColoursForClass.setEnabled(coloursIdKeyList.size()>1);
        btnAddColour.setEnabled(coloursIdKeyList.size()>1);
        btnDelColour.setEnabled(coloursIdKeyList.size()>1 && lstColoursForClass.getComponentCount()>0);
    }
    private void cmxBreedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxBreedActionPerformed
        if (initDone){
            Breed breed = new Breed();
            breed.readRecord(breedIdKeyList.get(cmxBreed.getSelectedIndex()));
            
            
            switch (breed.getSection()){
                case 0: cmxSection.setSelectedIndex(5);
                        break;
                case 1: cmxSection.setSelectedIndex(0); // Fancy
                        break;
                case 2: cmxSection.setSelectedIndex(1); // lop
                        break;
                case 4: cmxSection.setSelectedIndex(2); // fur
                        break;
                case 8: cmxSection.setSelectedIndex(3); // rex
                        break;
                default: 
                        cmxSection.setSelectedIndex(4); // open
                        break;           
            }
            createColoursAndAges();
            makeClassName(sc);
            if (cbxBreedClass.isSelected()){
                
            }
            setColoursEnabled();
        }
    }//GEN-LAST:event_cmxBreedActionPerformed

    private void cmxColoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxColoursActionPerformed
        sc = makeClassName(sc); 
    }//GEN-LAST:event_cmxColoursActionPerformed

    private void cmxExhibitAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxExhibitAgeActionPerformed
        sc =makeClassName(sc); 
    }//GEN-LAST:event_cmxExhibitAgeActionPerformed

    private void cmxExhibitorAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxExhibitorAgeActionPerformed
        sc =makeClassName(sc);
    }//GEN-LAST:event_cmxExhibitorAgeActionPerformed

    private void cmxExhibitorGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxExhibitorGenderActionPerformed
        sc = makeClassName(sc);
    }//GEN-LAST:event_cmxExhibitorGenderActionPerformed

    private void cmxExhibitGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxExhibitGenderActionPerformed
        sc = makeClassName(sc);
    }//GEN-LAST:event_cmxExhibitGenderActionPerformed

    private void btnAddColourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddColourActionPerformed
        // there is no point of addiong a colour if there is only one colour for 
        // that breed
        String theColourText;
        int theColourInt,theColourIdx;
        int start = 0;
        int finish = coloursIdKeyList.size(); 
        // * first get the selected colour
        theColourIdx = cmxColours.getSelectedIndex();
        // * adjust if for missing items 
        for (int idx=start;idx<finish;idx++){
            if (coloursIdKeyList.get(idx)<0){
               theColourIdx++; 
            }
        }
        theColourInt =coloursIdKeyList.get(theColourIdx);
        if (theColourInt>0) {
            System.out.printf("Add Colour - %s\n",cmxColours.getSelectedItem());
            coloursIdKeyList.set(theColourIdx, -theColourInt);
            theColourText = availableColourList.get(theColourIdx).getColour();
            lstColoursForClassData.addElement(theColourText);
            cmxColours.removeItemAt(cmxColours.getSelectedIndex());
        }
        if (lstColoursForClassData.getSize()>0 && coloursIdKeyList.get(0)>0){
            coloursIdKeyList.set(0, -coloursIdKeyList.get(0));
            cmxColours.removeItemAt(0);
        }
        cmxColours.removeAll();
        for (int idx=start;idx<finish;idx++){
            if (coloursIdKeyList.get(idx)>0){
               cmxColours.addItem(availableColourList.get(idx).getColour());
            }
        }
        if (lstColoursForClass.isEnabled()){
            lstColoursForClass.updateUI();
        }  
    }//GEN-LAST:event_btnAddColourActionPerformed

    private void rbnMembersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnMembersActionPerformed
        sc = makeClassName(sc);
    }//GEN-LAST:event_rbnMembersActionPerformed

    private void rbnStandardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnStandardActionPerformed
        sc = makeClassName(sc);
    }//GEN-LAST:event_rbnStandardActionPerformed

    private void rbnUpSideDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnUpSideDownActionPerformed
         sc = makeClassName(sc);
    }//GEN-LAST:event_rbnUpSideDownActionPerformed

    private void rbnBreedersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnBreedersActionPerformed
         sc = makeClassName(sc);
    }//GEN-LAST:event_rbnBreedersActionPerformed

    private void btnDelColourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelColourActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDelColourActionPerformed

    private void btnAddThisClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddThisClassActionPerformed
        sc.insertRecord();
        sc.updateRecord();
    }//GEN-LAST:event_btnAddThisClassActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddColour;
    private javax.swing.JButton btnAddThisClass;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelColour;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbxBreedClass;
    private javax.swing.JComboBox cmxBreed;
    private javax.swing.JComboBox cmxColours;
    private javax.swing.JComboBox cmxExhibitAge;
    private javax.swing.JComboBox cmxExhibitGender;
    private javax.swing.JComboBox cmxExhibitorAge;
    private javax.swing.JComboBox cmxExhibitorGender;
    private javax.swing.JComboBox cmxSection;
    private javax.swing.JTextField edtClassName;
    private javax.swing.JTextField edtClassNo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBreed;
    private javax.swing.JLabel lblClassName;
    private javax.swing.JLabel lblClassNo;
    private javax.swing.JLabel lblColours;
    private javax.swing.JLabel lblColoursForClass;
    private javax.swing.JLabel lblEhibitAge;
    private javax.swing.JLabel lblExhibitGender;
    private javax.swing.JLabel lblExhibitorAge;
    private javax.swing.JLabel lblExhibitorGender;
    private javax.swing.JLabel lblSection;
    private javax.swing.JList lstColoursForClass;
    private javax.swing.JRadioButton rbnBreeders;
    private javax.swing.JRadioButton rbnMembers;
    private javax.swing.JRadioButton rbnStandard;
    private javax.swing.JRadioButton rbnUpSideDown;
    private javax.swing.JTable tblShowClasses;
    // End of variables declaration//GEN-END:variables

}
