/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author paul
 */
public class ShowClassEditor extends javax.swing.JFrame {
    private DBAccess dba = null;
    ArrayList<Integer> keyList = null;
    /**
     * Creates new form ShowClassEditor
     * @param dbcon
     */
    public ShowClassEditor(DBAccess dbcon) {
        dba = dbcon;
        keyList = new ArrayList<Integer>();
        initComponents();
        populateWithList(cmxBreed,"SELECT * FROM breeds","breed");
        populate(cmxSection,"SELECT * FROM showsections","section_text");
        //populateRestricted(cmxExhibitAge,"SELECT * FROM exhibit_ages","age_text");
        populate(cmxExhibitAge,"SELECT * FROM exhibit_ages","age_text");
        populate(cmxExhibitorAge,"SELECT * FROM human_ages","age_text");
        populate(cmxExhibitGender,"SELECT * FROM exhibit_genders","gender_class");
        populate(cmxExhibitorGender,"SELECT * FROM human_genders","gender_class");
        updateColours();
     }
    
    private void makeClassName(){
        String name = (String) cmxBreed.getSelectedItem();
        if (cmxColours.isEnabled()){
            name += " " + (String) cmxColours.getSelectedItem();
        }
        if (cmxExhibitAge.getSelectedIndex()!=0){
            name += " " + (String) cmxExhibitAge.getSelectedItem();
        }
        if (cmxExhibitGender.getSelectedIndex()!=0 && cmxExhibitGender.getSelectedIndex()!=3){
            name += " " + (String) cmxExhibitGender.getSelectedItem();
        }
        if (cmxExhibitorAge.getSelectedIndex()!=0 && cmxExhibitorAge.getSelectedIndex()!=3){
            name += " " + (String) cmxExhibitorAge.getSelectedItem();
        }
        if (cmxExhibitorGender.getSelectedIndex()!=0 && cmxExhibitorGender.getSelectedIndex()!=3){
            name += " " + (String) cmxExhibitorGender.getSelectedItem();
        }
        edtClassName.setText(name);
    }
    
    public void populateWithList(JComboBox comboBox, String sql, String field){
        ResultSet rs;
        keyList.clear();
        rs = dba.select(sql);
        if (rs != null){
            try {
                while (rs.next()){
                   comboBox.addItem(rs.getString(field));
                   keyList.add(rs.getInt("id"));
                }
                comboBox.updateUI();
            } catch (SQLException ex) {
                Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    } 
    
    public void populate(JComboBox comboBox, String sql, String field){
        ResultSet rs;

        rs = dba.select(sql);
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
     * @param comboBox  The JComboBox to populate
     * @param sql       The SQL statement from the table
     * @param cmxKey    The JComboBox that holds the key to restricted selection
     * @param keys      The key values
     */
    public void populate(JComboBox comboBox, String sql, JComboBox cmxKey, ArrayList<Integer> keys){
        ResultSet rs;
        rs = dba.select(sql,keys.get(cmxKey.getSelectedIndex()));
        if (rs != null){
            try {
                while (rs.next()){
                   comboBox.addItem(rs.getString("colour")); 
                }
                comboBox.updateUI();
            } catch (SQLException ex) {
                Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void populateRestricted(JComboBox cmxExhibitAge, String sql, String age_text) {
        int adultAge;
        String modSql;
        ResultSet rs1 = dba.select("SELECT * FROM breeds where breeds.id = "+keyList.get(cmxBreed.getSelectedIndex()));
        try {
            adultAge = rs1.getInt("adult_age");
        } catch (SQLException ex) {
            Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * updates the colours JComboBox to the colours available with
     * the current breed.
     * Netherland Dwarf and Polish have a large selection of colours
     * Some have only one colour and the colour selection is disabled,
     * Changing the breed again the colour is re-enabled.
     */
    public void updateColours(){
        lblColours.setEnabled(true);
        cmxColours.setEnabled(true);
        cmxColours.removeAllItems();
        populate(cmxColours,
                        "SELECT colours.colour "+
                        "FROM breedcolours, breeds, colours "+
                        "WHERE breedcolours.breed_id = breeds.id "+
                        "AND breedcolours.colour_id = colours.id "+
                        "AND breeds.id = ?",cmxBreed, keyList);
        lblColours.setEnabled(cmxColours.getItemCount()>0);
        cmxColours.setEnabled(cmxColours.getItemCount()>0);
    }
    
    public void populateTable(){
        ResultSet rs = dba.select("SELECT * FROM showclasses");
        try {
            //ResultSetMetaData rsmd = rs.getMetaData();
            tblShowClasses.getColumnModel().getColumn(0).setHeaderValue("Class No");
            tblShowClasses.getColumnModel().getColumn(1).setHeaderValue("Class Name");
            
             while (rs.next()){
                 
             }
        } catch (SQLException ex) {
            Logger.getLogger(ShowClassEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

        //tblShowClasses.addColumn(null);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        cbxUpSideDownClassOnly = new javax.swing.JCheckBox();
        cbxBreederOnlyClass = new javax.swing.JCheckBox();
        cbxMembersOnly = new javax.swing.JCheckBox();

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
        edtClassName.setText("developed as data is entered");

        lblBreed.setText("Breed");

        cmxBreed.setToolTipText("Check for a breed class");
        cmxBreed.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmxBreedItemStateChanged(evt);
            }
        });
        cmxBreed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmxBreedFocusLost(evt);
            }
        });

        lblColours.setText("Colours");

        cmxColours.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmxColoursFocusLost(evt);
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

        cmxExhibitAge.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmxExhibitAgeFocusLost(evt);
            }
        });

        lblEhibitAge.setText("Exibit Age");

        lblExhibitGender.setText("Exhibit Gender");

        cmxExhibitGender.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmxExhibitGenderFocusLost(evt);
            }
        });

        lblExhibitorAge.setText("Exhibitor Age");

        cmxExhibitorAge.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmxExhibitorAgeFocusLost(evt);
            }
        });

        cmxExhibitorGender.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmxExhibitorGenderFocusLost(evt);
            }
        });

        lblExhibitorGender.setText("Exhibitor Gender");

        cbxUpSideDownClassOnly.setText("Up Side Down");
        cbxUpSideDownClassOnly.setToolTipText("Check for an up side down class");
        cbxUpSideDownClassOnly.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbxUpSideDownClassOnly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxUpSideDownClassOnlyActionPerformed(evt);
            }
        });

        cbxBreederOnlyClass.setText("Breeders");
        cbxBreederOnlyClass.setToolTipText("Exhibit bread by Exhibitor");
        cbxBreederOnlyClass.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbxBreederOnlyClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxBreederOnlyClassActionPerformed(evt);
            }
        });

        cbxMembersOnly.setText("Members");
        cbxMembersOnly.setToolTipText("Check for an up side down class");
        cbxMembersOnly.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbxMembersOnly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMembersOnlyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 653, Short.MAX_VALUE)
                .addComponent(btnClose))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExhibitorAge)
                    .addComponent(lblEhibitAge, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblColours, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBreed, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSection, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblClassNo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(edtClassNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblClassName))
                            .addComponent(cmxSection, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(edtClassName)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxBreedClass)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmxBreed, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmxColours, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmxExhibitorAge, 0, 197, Short.MAX_VALUE)
                                    .addComponent(cmxExhibitAge, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(lblExhibitGender)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmxExhibitGender, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblExhibitorGender)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmxExhibitorGender, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxMembersOnly)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxUpSideDownClassOnly)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxBreederOnlyClass)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClassName)
                    .addComponent(edtClassName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClassNo)
                    .addComponent(edtClassNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmxSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxBreedClass)
                    .addComponent(lblSection))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmxBreed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBreed))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmxColours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblColours))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEhibitAge)
                    .addComponent(lblExhibitGender)
                    .addComponent(cmxExhibitGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmxExhibitAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExhibitorAge)
                    .addComponent(cmxExhibitorAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmxExhibitorGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExhibitorGender))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxUpSideDownClassOnly)
                    .addComponent(cbxBreederOnlyClass)
                    .addComponent(cbxMembersOnly))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(btnClose))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseMouseClicked

    private void cmxBreedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmxBreedItemStateChanged
       // updateColours();
    }//GEN-LAST:event_cmxBreedItemStateChanged

    private void cmxBreedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmxBreedFocusLost
       updateColours();
       makeClassName();
    }//GEN-LAST:event_cmxBreedFocusLost

    private void cmxColoursFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmxColoursFocusLost
       makeClassName();
    }//GEN-LAST:event_cmxColoursFocusLost

    private void cmxExhibitAgeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmxExhibitAgeFocusLost
        makeClassName();
    }//GEN-LAST:event_cmxExhibitAgeFocusLost

    private void cmxExhibitGenderFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmxExhibitGenderFocusLost
        makeClassName();
    }//GEN-LAST:event_cmxExhibitGenderFocusLost

    private void cmxExhibitorAgeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmxExhibitorAgeFocusLost
        makeClassName();
    }//GEN-LAST:event_cmxExhibitorAgeFocusLost

    private void cmxExhibitorGenderFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmxExhibitorGenderFocusLost
        makeClassName();
    }//GEN-LAST:event_cmxExhibitorGenderFocusLost

    private void cbxMembersOnlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMembersOnlyActionPerformed
        makeClassName();
    }//GEN-LAST:event_cbxMembersOnlyActionPerformed

    private void cbxUpSideDownClassOnlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxUpSideDownClassOnlyActionPerformed
        makeClassName();
    }//GEN-LAST:event_cbxUpSideDownClassOnlyActionPerformed

    private void cbxBreederOnlyClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxBreederOnlyClassActionPerformed
        makeClassName();
    }//GEN-LAST:event_cbxBreederOnlyClassActionPerformed

    private void cbxBreedClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxBreedClassActionPerformed
        makeClassName();
    }//GEN-LAST:event_cbxBreedClassActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JCheckBox cbxBreedClass;
    private javax.swing.JCheckBox cbxBreederOnlyClass;
    private javax.swing.JCheckBox cbxMembersOnly;
    private javax.swing.JCheckBox cbxUpSideDownClassOnly;
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
    private javax.swing.JLabel lblBreed;
    private javax.swing.JLabel lblClassName;
    private javax.swing.JLabel lblClassNo;
    private javax.swing.JLabel lblColours;
    private javax.swing.JLabel lblEhibitAge;
    private javax.swing.JLabel lblExhibitGender;
    private javax.swing.JLabel lblExhibitorAge;
    private javax.swing.JLabel lblExhibitorGender;
    private javax.swing.JLabel lblSection;
    private javax.swing.JTable tblShowClasses;
    // End of variables declaration//GEN-END:variables

}
