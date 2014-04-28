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
public final class ColoursForm extends javax.swing.JFrame implements FormInterface {
    private ColourList colourList;
    private Colour curRecord;
    private boolean newItem;
    private int selectedRecord;
    private final DefaultListModel<String> theListModelData;
    private final DefaultComboBoxModel modelAges;
    private final DefaultComboBoxModel modelSections;
    /**
     * Creates new form ColourEditor
     */
    public ColoursForm() {
        colourList = new ColourList();
        String[] arrayAges = DBA.getStringArrayFromSQL("exhibit_ages", "age_text", "age = 3");
        String[] arraySections = DBA.getStringArrayFromSQL("showsections", "section_text", null);
        modelAges = new DefaultComboBoxModel(arrayAges);
        modelSections = new DefaultComboBoxModel(arraySections);
        initComponents();
        //cmxYoungster.setModel(modelAges);
        //cmxSection.setModel(modelSections);
        theListModelData = new DefaultListModel();
        lstColourDisplay.setModel(theListModelData);       
        colourList.readList(HeaderRequired.HEADERS);
        createTheList();
        edtColourHeaders.setText(colourList.getHeader());
        setButtons();
        displayTheList();
        newItem = false;  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAbandon = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstColourDisplay = new javax.swing.JList();
        edtColourHeaders = new javax.swing.JTextField();
        edtColourName = new javax.swing.JTextField();
        lblBreedName = new javax.swing.JLabel();
        edtColourID = new javax.swing.JTextField();
        lblColourID = new javax.swing.JLabel();
        lblColourAbbrev = new javax.swing.JLabel();
        edtColourAbbreviation = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        lstColourDisplay.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        lstColourDisplay.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstColourDisplayValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstColourDisplay);

        edtColourHeaders.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        edtColourHeaders.setText("Header");

        lblBreedName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblBreedName.setText("Name");
        lblBreedName.setMaximumSize(null);
        lblBreedName.setMinimumSize(null);

        edtColourID.setEditable(false);
        edtColourID.setBackground(new java.awt.Color(149, 204, 204));

        lblColourID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblColourID.setText("Colour ID");

        lblColourAbbrev.setText("Abbreviation");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(edtColourHeaders)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(btnDelete, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(btnUpdate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(btnInsert, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(btnClose))
                    .add(layout.createSequentialGroup()
                        .add(15, 15, 15)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(lblColourAbbrev)
                                .add(4, 4, 4)
                                .add(edtColourAbbreviation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(lblColourID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(lblBreedName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(4, 4, 4)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(edtColourName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 207, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(btnAbandon))
                                    .add(layout.createSequentialGroup()
                                        .add(edtColourID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(175, 175, 175)
                                        .add(lblStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 506, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(edtColourHeaders, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 194, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(43, 43, 43)
                        .add(btnAbandon)
                        .add(38, 38, 38)
                        .add(btnClose)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 15, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(edtColourID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lblColourID))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(lblColourAbbrev)
                            .add(edtColourAbbreviation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(4, 4, 4)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(lblBreedName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(edtColourName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(86, 86, 86)))
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnDelete)
                    .add(btnUpdate)
                    .add(btnInsert)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbandonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbandonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnAbandonActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        colourList.writeList();
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Colour dataRecord = getFormData();
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
            colourList.list.set(selectedRecord, dataRecord);
            theListModelData.set(selectedRecord, dataRecord.toListString(colourList.getFormatStr()));
        }
        setButtons(dataRecord);
        displayTheList();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        newItem = ! newItem;
        if (newItem){
            lblStatus.setText("New Record - Add or Cancel");
            edtColourName.setText("");
            edtColourID.setText(Integer.toString(DBA.getNewKey("breeds", "id")));
            btnInsert.setText("Add");
        } else if (edtColourName.getText().isEmpty()){
            newItem = true;
            lblStatus.setText("New Record - Not added as Colour Name not specified");
        } else {
            lblStatus.setText("New Record - Added");
            Colour dataRecord = getFormData();
            dataRecord.setNewItem(true);
            colourList.add(dataRecord);
            theListModelData.addElement(dataRecord.toListString(colourList.getFormatStr()));
            setButtons(dataRecord);
            selectedRecord = colourList.list.size();
            curRecord = (Colour) colourList.get(selectedRecord);
            lstColourDisplay.setSelectedIndex(selectedRecord);
            displayTheList();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        //curRecord = getFormData();
        curRecord.setReadyToDelete(!curRecord.isReadyToDelete());
        //btnDelete.setText(curRecord.isReadyToDelete()?"Undelete":"Delete");
        colourList.list.set(selectedRecord, curRecord);
        theListModelData.set(selectedRecord, curRecord.toListString(colourList.getFormatStr()));
        setButtons(curRecord);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void lstColourDisplayValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstColourDisplayValueChanged
       selectedRecord = lstColourDisplay.getSelectedIndex();
        setFormData(colourList.get(selectedRecord));
        curRecord = colourList.get(selectedRecord);
    }//GEN-LAST:event_lstColourDisplayValueChanged



      
    /**
     * Creates the ListModelData 
     */
    @Override
    public void createTheList() {
        theListModelData.clear();
        for (int idx = 0; colourList.get(idx) != null; idx++){
            theListModelData.addElement(colourList.get(idx).toListString(colourList.getFormatStr()));
        }
        displayTheList();
    }

    @Override
    public void displayTheList(){
        lstColourDisplay.updateUI();
    }
   // </editor-fold>                        

//    private int sectionToIndex(int key){
//        int idx;
//        
//        for (idx =0; intSections[idx]!=key; idx++) /*void */;
//        return idx;
//    }
    
//    private int indexToSection(int key){
//        return intSections[key];
//    }
    
    @Override
    public void setFormData(BaseDataItem dataRecord) {
        Colour formRec = (Colour) dataRecord;
        edtColourID.setText(Integer.toString(formRec.getId()));
        edtColourAbbreviation.setText(formRec.getAbbrev());
        edtColourName.setText(formRec.getColour());
        btnDelete.setEnabled(true);
        btnInsert.setEnabled(true);
        btnUpdate.setEnabled(true);
        setButtons(formRec);
    }
    
    @Override
    public Colour getFormData(){
        Colour dataRecord = new Colour();
        if (edtColourID.getText().isEmpty()){
            dataRecord.setId(DBA.getNewKey("colours", "id"));
            dataRecord.setNewItem(true);
        } else {
            dataRecord.setNewItem(false);
            dataRecord.setId(Integer.parseInt(edtColourID.getText()));        
        }
        dataRecord.setAbbrev(edtColourAbbreviation.getText());
        dataRecord.setColour(edtColourName.getText());
        setButtons(dataRecord);
        return dataRecord;
    }
    
    @Override
    public void setButtons(){
       btnDelete.setText("Delete");
       btnUpdate.setText("Update");
       btnInsert.setText("New");
       btnUpdate.setEnabled(!edtColourID.getText().isEmpty());
       btnDelete.setEnabled(!edtColourID.getText().isEmpty());
       btnInsert.setEnabled(true);        
    }
    
    @Override
    public void setButtons(BaseDataItem dataRecord) {
        btnDelete.setText(dataRecord.isReadyToDelete()?"Undelete":"Delete");
        btnUpdate.setText(dataRecord.isDirty()?" Undo ":"Update");
        btnInsert.setText(dataRecord.isNewItem()?"Add":"New");
        btnUpdate.setEnabled(!dataRecord.isReadyToDelete());
        //btnDelete.setEnabled(!dataRecord.isNewItem());
   }                                         

//    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {                                          
//        Colour dataRecord = getFormData();
//        dataRecord.setDirty(false);
//        dataRecord.setNewItem(false);
//        dataRecord.setReadyToDelete(false);
//        if (!dataRecord.equals(curRecord)){
//            if (curRecord.isDirty()){
//                dataRecord.readRecord(dataRecord.getId());
//                curRecord.setDirty(false);
//            } else {
//                curRecord.setDirty(true);
//                dataRecord.setDirty(true);
//            }
//            colourList.set(selectedRecord, dataRecord);
//            theListModelData.set(selectedRecord, dataRecord.toListString(colourList.getFormatStr()));        
//        }    
//        setButtons(dataRecord);
//        displayTheList();
//    }                                         


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbandon;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTextField edtColourAbbreviation;
    private javax.swing.JTextField edtColourHeaders;
    private javax.swing.JTextField edtColourID;
    private javax.swing.JTextField edtColourName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBreedName;
    private javax.swing.JLabel lblColourAbbrev;
    private javax.swing.JLabel lblColourID;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList lstColourDisplay;
    // End of variables declaration//GEN-END:variables

   
}
