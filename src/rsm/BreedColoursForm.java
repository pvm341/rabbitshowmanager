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

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author paul
 */
public class BreedColoursForm extends javax.swing.JFrame implements FormInterface{
    private BreedColourList breedColourList;
    private BreedColour curRecord;
    private BreedList breedList;
    private ColourList colourList; 
    private boolean newItem;
    private int selectedRecord;
    private final String[] arrayBreeds;
    private final String[] arrayColours;
    private final DefaultListModel<String> theListModelData;
    private final DefaultComboBoxModel modelBreeds;
    private final DefaultComboBoxModel modelColours;
    /**
     * Creates new form BreedColoursForm
     */
    public BreedColoursForm() {
        this.breedColourList = new BreedColourList();
        this.arrayBreeds = DBA.getStringArrayFromSQL("breeds", "breed", null);
        this.arrayColours = DBA.getStringArrayFromSQL("colours","colour", null);
        this.breedList = new BreedList();
        this.colourList = new ColourList();
        initComponents();
        theListModelData = new DefaultListModel<String>();
        modelBreeds  = new DefaultComboBoxModel(arrayBreeds);
        modelColours = new DefaultComboBoxModel(arrayColours);
        breedColourList.readList(HeaderRequired.HEADERS);
        breedList.readList(HeaderRequired.NOHEADERS);
        colourList.readList(HeaderRequired.NOHEADERS);
        createTheList();
        lstBreedColourDisplay.setModel(theListModelData);
        cmxBreedName.setModel(modelBreeds);       
        cmxColourName.setModel(modelColours);
        edtBreedColourHeaders.setText(breedColourList.header);
        displayTheList();
    }
    
    @Override
    public void setFormData(BaseDataItem dataRecord){
        BreedColour formRec = (BreedColour) dataRecord;
        int found;
        edtBreedColourBreedID.setText(Integer.toString(formRec.getBreedId(VersionRequired.CURRENT)));
        edtBreedColourColourID.setText(Integer.toString(formRec.getColourId(VersionRequired.CURRENT)));
        found = breedList.findInListById(formRec.getBreedId(VersionRequired.CURRENT));
        if (found > -1){
            Breed breed = (Breed) breedList.get(found);
            cmxBreedName.setSelectedIndex(found);
            edtBreedColourBreedID.setText(Integer.toString(breed.getId()));
        }
        found = colourList.findInListById(formRec.getColourId(VersionRequired.CURRENT));
        if (found > -1 ){
            Colour colour = (Colour) colourList.get(found);
            cmxColourName.setSelectedIndex(found);
            edtBreedColourColourID.setText(Integer.toString(colour.getId()));
            edtColourAbbreviation.setText(colour.getAbbrev());
        }
        cbxBreedColourAvailable.setSelected(formRec.isAvailable());
        cbxBreedColourSelected.setSelected(formRec.isSelected());
        edtBreedColourClassNo.setText(Integer.toString(formRec.getClassNo()));
        btnDelete.setEnabled(true);
        btnInsert.setEnabled(true);
        btnUpdate.setEnabled(true);
        setButtons(formRec);
    }
    
    @Override
    public BreedColour getFormData(){
        BreedColour dataRecord = new BreedColour();
        Breed breed = (Breed) breedList.get(cmxBreedName.getSelectedIndex());
        Colour colour = (Colour) colourList.get(cmxColourName.getSelectedIndex());
        dataRecord.setAvailable(cbxBreedColourAvailable.isSelected());
        dataRecord.setSelected(cbxBreedColourSelected.isSelected());
        dataRecord.setClassNo(Integer.valueOf(edtBreedColourClassNo.getText()));
        dataRecord.setBreedAndColourIds(breed.getId(),colour.getId());        
        setButtons(dataRecord);
        return dataRecord;
    }
    
   
    public void setButtons(){
       btnDelete.setText("Delete");
       btnUpdate.setText("Update");
       btnInsert.setText("New");
       btnUpdate.setEnabled(!edtBreedColourBreedID.getText().isEmpty());
       btnDelete.setEnabled(!edtBreedColourBreedID.getText().isEmpty());
       btnInsert.setEnabled(true);        
    }
    
    @Override
    public void setButtons(BaseDataItem dataRecord){
        btnDelete.setText(dataRecord.isReadyToDelete()?"Undelete":"Delete");
        btnUpdate.setText(dataRecord.isDirty()?" Undo ":"Update");
        btnInsert.setText(dataRecord.isNewItem()?"Add":"New");
        btnUpdate.setEnabled(!dataRecord.isReadyToDelete());
        //btnDelete.setEnabled(!dataRecord.isNewItem());
   }
    @Override
    public void createTheList() {
        theListModelData.clear();
        int idx;
        BreedColour breedColour;      
        for (idx=0;idx<breedColourList.list.size();idx++){
            breedColour = (BreedColour) breedColourList.get(idx);
            theListModelData.addElement(breedColour.toListString(breedColourList.getFormatStr()));
        }
        //displayTheList();
    }

    @Override
    public void displayTheList(){
        lstBreedColourDisplay.updateUI();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBreedColourColourID = new javax.swing.JLabel();
        edtBreedColourColourID = new javax.swing.JTextField();
        lblBreedName = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstBreedColourDisplay = new javax.swing.JList();
        btnAbandon = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        edtBreedColourHeaders = new javax.swing.JTextField();
        lblColourAbbrev = new javax.swing.JLabel();
        edtColourAbbreviation = new javax.swing.JTextField();
        lblBreedColourBreedID = new javax.swing.JLabel();
        edtBreedColourBreedID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmxBreedName = new javax.swing.JComboBox();
        cmxColourName = new javax.swing.JComboBox();
        cbxBreedColourAvailable = new javax.swing.JCheckBox();
        cbxBreedColourSelected = new javax.swing.JCheckBox();
        lblBreedColourClassNo = new javax.swing.JLabel();
        edtBreedColourClassNo = new javax.swing.JTextField();

        lblBreedColourColourID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblBreedColourColourID.setText("Colour ID");

        edtBreedColourColourID.setEditable(false);
        edtBreedColourColourID.setBackground(new java.awt.Color(149, 204, 204));

        lblBreedName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblBreedName.setText("Colour Name");

        lstBreedColourDisplay.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        lstBreedColourDisplay.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstBreedColourDisplayValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstBreedColourDisplay);

        btnAbandon.setText("Abandon and Close");
        btnAbandon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbandonActionPerformed(evt);
            }
        });

        btnClose.setText("Save and Close ");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnInsert.setText("Add");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        edtBreedColourHeaders.setEditable(false);
        edtBreedColourHeaders.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        edtBreedColourHeaders.setText("Header");

        lblColourAbbrev.setText("Abbreviation");

        lblBreedColourBreedID.setText("Breed ID");

        edtBreedColourBreedID.setBackground(new java.awt.Color(149, 204, 204));

        jLabel5.setText("Breed Name");

        cmxBreedName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmxBreedName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxBreedNameActionPerformed(evt);
            }
        });

        cmxColourName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmxColourName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxColourNameActionPerformed(evt);
            }
        });

        cbxBreedColourAvailable.setText("Available");
        cbxBreedColourAvailable.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        cbxBreedColourSelected.setText("Selected");
        cbxBreedColourSelected.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lblBreedColourClassNo.setText("Class No");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(edtBreedColourHeaders)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1)
            .add(lblStatus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(lblBreedColourBreedID)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(cmxBreedName, 0, 245, Short.MAX_VALUE)
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(lblBreedColourColourID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(4, 4, 4))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(lblBreedName)
                                    .add(lblColourAbbrev)
                                    .add(lblBreedColourClassNo))
                                .add(5, 5, 5)))
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(layout.createSequentialGroup()
                                .add(edtBreedColourColourID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(406, 406, 406))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(edtColourAbbreviation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(btnAbandon))
                                    .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(cmxColourName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 211, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(edtBreedColourClassNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())))
                    .add(layout.createSequentialGroup()
                        .add(edtBreedColourBreedID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .add(layout.createSequentialGroup()
                .add(btnDelete, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(cbxBreedColourAvailable)
                        .add(53, 53, 53)
                        .add(cbxBreedColourSelected)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(btnUpdate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(btnInsert, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(btnClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(edtBreedColourHeaders, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 194, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lblStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(edtBreedColourColourID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblBreedColourColourID)
                    .add(lblBreedColourBreedID)
                    .add(edtBreedColourBreedID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblColourAbbrev)
                    .add(edtColourAbbreviation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnAbandon))
                .add(7, 7, 7)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblBreedName)
                    .add(jLabel5)
                    .add(cmxBreedName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cmxColourName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(37, 37, 37)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cbxBreedColourSelected)
                    .add(cbxBreedColourAvailable)
                    .add(lblBreedColourClassNo)
                    .add(edtBreedColourClassNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(30, 30, 30)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnDelete)
                    .add(btnUpdate)
                    .add(btnInsert)
                    .add(btnClose))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstBreedColourDisplayValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstBreedColourDisplayValueChanged
        selectedRecord = lstBreedColourDisplay.getSelectedIndex();
        setFormData((BreedColour) breedColourList.get(selectedRecord));
        curRecord = (BreedColour) breedColourList.get(selectedRecord);
    }//GEN-LAST:event_lstBreedColourDisplayValueChanged

    private void btnAbandonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbandonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnAbandonActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        breedColourList.writeList();
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        BreedColour dataRecord = getFormData();
        dataRecord.setDirty(false);
        dataRecord.setNewItem(false);
        dataRecord.setReadyToDelete(false);
        if (!dataRecord.equals(curRecord)){
            if (curRecord.isDirty()){
                dataRecord.performRead();
                curRecord.setDirty(false);
            } else {
                curRecord.setDirty(true);
                dataRecord.setDirty(true);
            }
            breedColourList.list.set(selectedRecord, dataRecord);
            theListModelData.set(selectedRecord, dataRecord.toListString(breedColourList.getFormatStr()));
        }
        setButtons(dataRecord);
        displayTheList();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        newItem = ! newItem;
        if (newItem){
            lblStatus.setText("New Record - Add or Cancel");
            edtBreedColourClassNo.setText("0");
            curRecord = getFormData();
            btnInsert.setText("Add");
        } else if (breedColourList.isAlreadyInList(curRecord)){
            newItem = true;
            lblStatus.setText("New Record - Not added as already present");
        } else {
            lblStatus.setText("New Record - Added");
            curRecord.setNewItem(true);
            breedColourList.add(curRecord);
            theListModelData.addElement(curRecord.toListString(breedColourList.getFormatStr()));
            setButtons(curRecord);
            selectedRecord = breedColourList.list.size();
            curRecord = (BreedColour) breedColourList.get(selectedRecord);
            lstBreedColourDisplay.setSelectedIndex(selectedRecord);
            displayTheList();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        //curRecord = getFormData();
        curRecord.setReadyToDelete(!curRecord.isReadyToDelete());
        //btnDelete.setText(curRecord.isReadyToDelete()?"Undelete":"Delete");
        breedColourList.list.set(selectedRecord, curRecord);
        theListModelData.set(selectedRecord, curRecord.toListString(breedColourList.getFormatStr()));
        setButtons(curRecord);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cmxBreedNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxBreedNameActionPerformed
       int idx=cmxBreedName.getSelectedIndex();
       Breed breed = (Breed) breedList.get(idx);
       int id = breed.getId();
       this.edtBreedColourBreedID.setText(Integer.toString(id));
       //breedColour
       
    }//GEN-LAST:event_cmxBreedNameActionPerformed

    private void cmxColourNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxColourNameActionPerformed
    int idx = cmxColourName.getSelectedIndex();
    Colour colour = (Colour) colourList.get(idx);
    this.edtColourAbbreviation.setText(colour.getAbbrev());
    this.edtBreedColourColourID.setText(Integer.toString(colour.getId()));
    }//GEN-LAST:event_cmxColourNameActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbandon;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox cbxBreedColourAvailable;
    private javax.swing.JCheckBox cbxBreedColourSelected;
    private javax.swing.JComboBox cmxBreedName;
    private javax.swing.JComboBox cmxColourName;
    private javax.swing.JTextField edtBreedColourBreedID;
    private javax.swing.JTextField edtBreedColourClassNo;
    private javax.swing.JTextField edtBreedColourColourID;
    private javax.swing.JTextField edtBreedColourHeaders;
    private javax.swing.JTextField edtColourAbbreviation;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBreedColourBreedID;
    private javax.swing.JLabel lblBreedColourClassNo;
    private javax.swing.JLabel lblBreedColourColourID;
    private javax.swing.JLabel lblBreedName;
    private javax.swing.JLabel lblColourAbbrev;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList lstBreedColourDisplay;
    // End of variables declaration//GEN-END:variables

   
}
