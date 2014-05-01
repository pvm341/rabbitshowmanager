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

//import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.*;


/**
 *
 * @author paul
 */
public class ShowClassForm extends javax.swing.JFrame implements FormInterface{
    private ShowClass curRecord;
    private ShowClasses showClasses;
    private Vector<Integer> colours4Breed;
    private boolean newItem;
    private boolean useAbbrevs;
    private BreedColourList availableColourList;
    private int selectedRecord;
    private final DefaultListModel<String> lstColoursForClassData;
    private final DefaultListModel<String> lstShowClassesDataModel;
    private final DefaultComboBoxModel modelBreeds;
    final DefaultComboBoxModel modelColours;
    private final DefaultComboBoxModel modelExhibitAges;
    private final DefaultComboBoxModel modelExhibitorAges;
    private final DefaultComboBoxModel modelExhibitGenders;
    private final DefaultComboBoxModel modelExhibitorGenders;
    private final DefaultComboBoxModel modelSections;
    private final BreedList breedList;
    final ColourList colourList;
    final BreedColourList breedColourList;
    private final ShowSectionList showSectionList;
    
            
    public ShowClassForm() {
        DBA.getInstance();
        initComponents();
        modelBreeds = new DefaultComboBoxModel(DBA.getStringArrayFromSQL("breeds", "breed", null));
        breedList = new BreedList();
        breedList.readList(HeaderRequired.NOHEADERS);
        modelColours = new DefaultComboBoxModel(DBA.getStringArrayFromSQL("colours", "colour", null));
        modelExhibitAges = new DefaultComboBoxModel(DBA.getStringArrayFromSQL("exhibit_ages", "age_text", null));
        modelExhibitorAges = new DefaultComboBoxModel(DBA.getStringArrayFromSQL("human_ages", "age_text", null));
        modelExhibitGenders = new DefaultComboBoxModel(DBA.getStringArrayFromSQL("exhibit_genders", "gender_class", null));
        modelExhibitorGenders = new DefaultComboBoxModel(DBA.getStringArrayFromSQL("human_genders", "gender_class", null));
        modelSections = new DefaultComboBoxModel(DBA.getStringArrayFromSQL("showsections", "section_text", null));
        colourList = new ColourList();
        colourList.readList(HeaderRequired.NOHEADERS);
        breedColourList = new BreedColourList();
        breedColourList.readList(HeaderRequired.NOHEADERS);
        showSectionList = new ShowSectionList();
        showSectionList.readList(HeaderRequired.NOHEADERS);
        lstShowClassesDataModel = new  DefaultListModel<String>();
        showClasses = new ShowClasses();
        showClasses.readList(HeaderRequired.HEADERS);
        lstColoursForClass.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstColoursForClass.setVisible(true);
        lstColoursForClassData = new DefaultListModel<String>();
        lstColoursForClass.setModel(lstColoursForClassData);
        lstDisplay.setModel(lstShowClassesDataModel);
        lstColoursForClass.setVisibleRowCount(7);
        cmxBreed.setModel(modelBreeds);
        cmxColours.setModel(modelColours);
        cmxExhibitAge.setModel(modelExhibitAges);
        cmxExhibitGender.setModel(modelExhibitGenders);
        cmxExhibitorAge.setModel(modelExhibitorAges);
        cmxExhibitorGender.setModel(modelExhibitorGenders);
        cmxSection.setModel(modelSections);
        edtHeaders.setText(showClasses.header);
        colours4Breed = new Vector<Integer>();
        createTheList();
    }

    @Override
    public final void createTheList(){
        String forDisplay;
        lstShowClassesDataModel.clear();
        for (int idx = 0; showClasses.get(idx) != null; idx++){
            forDisplay = showClasses.get(idx).toListString(showClasses.getFormatStr());
            lstShowClassesDataModel.addElement(forDisplay);
        }
        displayTheList();
 
    }
    
    @Override
    public void displayTheList(){
        lstDisplay.updateUI();
    }
    
    private void setModelColours(Vector<Integer> localList){
        modelColours.removeAllElements();
        for (int idx : localList ) {
            BreedColour bc = (BreedColour) breedColourList.get(idx);
            if (bc.isAvailable() && !bc.isSelected()){
                modelColours.addElement(colourList.get(bc.getColourId(VersionRequired.CURRENT)).getColour());
            }
        }
    }
    
    
    @Override
    public void setFormData(BaseDataItem dataRecord) {
        ShowClass formRec = (ShowClass) dataRecord;
        edtClassNo.setText(Integer.toString(formRec.getClassNo()));
        cmxBreed.setSelectedIndex(formRec.getBreedId());
        cmxSection.setSelectedIndex(formRec.getSection());
        //setModelColours(colour4Breed);
        rbnStandard.setEnabled(!formRec.isBreedClass());
        rbnBreeders.setEnabled(!formRec.isBreedClass());
        rbnUpSideDown.setEnabled(!formRec.isBreedClass());
        rbnMembers.setEnabled(!formRec.isBreedClass());
        
    }
    /**
     *
     * @return the rsm.ShowClass
     */
    public ShowClass getFormData(){
        Breed breed = new Breed();
        ShowClass dataRecord = new ShowClass();
        dataRecord.setBreedClass(cbxBreedClass.isSelected());
        breed = (Breed) breedList.get(cmxBreed.getSelectedIndex());
        dataRecord.setBreedId(breed.getId());
        dataRecord.setBreedersOnly(rbnBreeders.isSelected());
        dataRecord.setClassNo(Integer.valueOf(edtClassNo.getText()));
        dataRecord.setBreedClass(cbxBreedClass.isSelected());
        dataRecord.setBreedersOnly(!cbxBreedClass.isSelected() && rbnBreeders.isSelected());
        dataRecord.setUpSideDown(!cbxBreedClass.isSelected() && rbnUpSideDown.isSelected());
        dataRecord.setMembersOnly(!cbxBreedClass.isSelected() && rbnMembers.isSelected());
        dataRecord.setStandard(!cbxBreedClass.isSelected() && rbnStandard.isSelected());
        dataRecord.setExhibitAge(cmxExhibitAge.getSelectedIndex());
        dataRecord.setExhibitGender(cmxExhibitGender.getSelectedIndex());
        dataRecord.setExhibitorAge(cmxExhibitorAge.getSelectedIndex());
        dataRecord.setExhibitorGender(cmxExhibitorGender.getSelectedIndex());
        dataRecord.setSection(cmxSection.getSelectedIndex());
        //dataRecord.
       // sc.setName(name);
        //edtClassName.setText(sc.getName());
        return dataRecord;
    }
    

    private String makeName(){
        String name;
        boolean useAbbrev = false;
        
        // This colRec is used for DB lookup
        Colour colRec = new Colour();
        BreedColour breedColour;
        
        name = String.format("%c%s %s ",cbxBreedClass.isSelected()?'*':' ',edtClassNo.getText(),(String)cmxBreed.getSelectedItem());
        if (cmxColours.isEnabled()){
            for (int idx = 0; idx < lstColoursForClassData.size(); idx++){
                useAbbrevs = useAbbrevs | name.length()>45;
                System.out.printf("AIU %c len %d\n",useAbbrevs?'Y':'N',name.length());
               // colRec.performRead(availableColourList.getTheColourFromIdx(idx).getId());
                
                if (useAbbrevs){
                    name += " " + colRec.getAbbrev();
                } else {
                    name += " " + colRec.getColour();
                    //lstColoursForClassData.get(idx);
                }
                if ((idx+2) <lstColoursForClassData.size()){
                    name += ",";
                } else if ((idx+1) <lstColoursForClassData.size()){
                    name += " &";
                }
            }            
        }
        
        name += " " + cmxExhibitAge.getSelectedItem();
 
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
        
        
        return null;   
    }
    
    private void  makeListOfColoursForBreed(int breedId) {
        BreedColour bc;
        Colour colour;
        this.colours4Breed.clear();
        this.modelColours.removeAllElements();
        for (int idx = 0; idx < this.breedColourList.list.size(); idx++) {
            bc = (BreedColour) this.breedColourList.get(idx);
            if (bc.getBreedId(VersionRequired.CURRENT) == breedId && bc.isAvailable() && !bc.isSelected()) {
                colour = (Colour) colourList.findInListWithId(bc.getColourId(VersionRequired.CURRENT));
                this.colours4Breed.add(idx);
                this.modelColours.addElement(colour.getColour());
            }
        }
    }

    private void adjustListOfColoursForBreed(){
        BreedColour bc;
        Colour colour;
        this.lstColoursForClassData.removeAllElements();
        this.modelColours.removeAllElements();
        for (int idx = 0; idx < colours4Breed.size(); idx++) {
            bc = (BreedColour) this.breedColourList.get(colours4Breed.get(idx));
            if (bc.isAvailable() && !bc.isSelected()) {
                colour = (Colour) colourList.findInListWithId(bc.getColourId(VersionRequired.CURRENT));
                this.modelColours.addElement(colour.getColour());
            }
            if (bc.isAvailable() && bc.isSelected()) {
                colour = (Colour) colourList.findInListWithId(bc.getColourId(VersionRequired.CURRENT));
                this.lstColoursForClassData.addElement(colour.getColour());
            }
        }
    }
    
    private void setColoursEnabled(){
        lstColoursForClass.setEnabled(colours4Breed.size()>1);
        btnAddColour.setEnabled(colours4Breed.size()>1);
        btnDelColour.setEnabled(colours4Breed.size()>1 && lstColoursForClass.getComponentCount()>0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The               Vector columnNames = new Vector();
        Vector data = new Vector();
        JPanel panel = new JPanel();
       content of this method is always
     * regenerated by the Form Editor.
     */
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpDuplicates = new javax.swing.ButtonGroup();
        btnClose = new javax.swing.JButton();
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
        edtHeaders = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstDisplay = new javax.swing.JList();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        setTitle("Class Editor");

        btnClose.setText("Close");
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        lblClassNo.setText("Class No");

        edtClassNo.setText("100");

        lblClassName.setText("Class Name");

        edtClassName.setBackground(new java.awt.Color(125, 200, 215));
        edtClassName.setFont(new java.awt.Font("Lucida Console", 0, 10)); // NOI18N
        edtClassName.setText("developed as data is entered");

        lblBreed.setText("Breed");

        cmxBreed.setToolTipText("Check for a breed class");
        cmxBreed.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmxBreedItemStateChanged(evt);
            }
        });
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

        cmxSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmxSectionActionPerformed(evt);
            }
        });

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

        btnGrpDuplicates.add(rbnBreeders);
        rbnBreeders.setText("Breeders");
        rbnBreeders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnBreedersActionPerformed(evt);
            }
        });

        btnGrpDuplicates.add(rbnStandard);
        rbnStandard.setText("Standard Open");
        rbnStandard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnStandardActionPerformed(evt);
            }
        });

        btnGrpDuplicates.add(rbnMembers);
        rbnMembers.setText("Members");
        rbnMembers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnMembersActionPerformed(evt);
            }
        });

        btnGrpDuplicates.add(rbnUpSideDown);
        rbnUpSideDown.setText("Up Side Down");
        rbnUpSideDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnUpSideDownActionPerformed(evt);
            }
        });

        lblColoursForClass.setText("Colours For this class");

        edtHeaders.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 10)); // NOI18N
        edtHeaders.setText("Header");

        lstDisplay.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 10)); // NOI18N
        lstDisplay.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstDisplayValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstDisplay);

        btnInsert.setText("Add");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rbnStandard)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbnMembers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbnUpSideDown)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbnBreeders)
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelColour)
                            .addComponent(lblColoursForClass)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(edtHeaders)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSection)
                            .addComponent(lblBreed)
                            .addComponent(lblColours))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmxSection, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxBreedClass))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblExhibitorAge)
                            .addComponent(lblEhibitAge))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmxExhibitAge, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmxExhibitorAge, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblExhibitorGender, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblExhibitGender))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmxExhibitorGender, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmxExhibitGender, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cmxColours, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAddColour, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cmxBreed, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lblClassNo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtClassNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblClassName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtClassName)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(edtHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete)
                            .addComponent(btnUpdate)
                            .addComponent(btnInsert)
                            .addComponent(btnClose)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblClassName)
                            .addComponent(edtClassNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblClassNo)
                            .addComponent(edtClassName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(59, 59, 59)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnAddColour)
                                            .addComponent(cmxColours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmxSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSection)
                                        .addComponent(cbxBreedClass))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblBreed)
                                            .addComponent(cmxBreed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(10, 10, 10)
                                        .addComponent(lblColours)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblExhibitGender)
                                            .addComponent(cmxExhibitorGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmxExhibitAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(5, 5, 5))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEhibitAge)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblExhibitorGender)
                                    .addComponent(cmxExhibitGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmxExhibitorAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblExhibitorAge))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rbnStandard)
                                    .addComponent(rbnMembers)
                                    .addComponent(rbnUpSideDown)
                                    .addComponent(rbnBreeders)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblColoursForClass)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(btnDelColour)))
                        .addContainerGap(35, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_btnCloseMouseClicked

    private void cbxBreedClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxBreedClassActionPerformed
       
    }//GEN-LAST:event_cbxBreedClassActionPerformed
    
    private void cmxBreedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxBreedActionPerformed
        makeListOfColoursForBreed(breedList.findInListByIndex(cmxBreed.getSelectedIndex()));
        setColoursEnabled();  
    }//GEN-LAST:event_cmxBreedActionPerformed

    private void cmxColoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxColoursActionPerformed
       curRecord = getFormData(); 
    }//GEN-LAST:event_cmxColoursActionPerformed

    private void cmxExhibitAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxExhibitAgeActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_cmxExhibitAgeActionPerformed

    private void cmxExhibitorAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxExhibitorAgeActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_cmxExhibitorAgeActionPerformed

    private void cmxExhibitorGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxExhibitorGenderActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_cmxExhibitorGenderActionPerformed

    private void cmxExhibitGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxExhibitGenderActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_cmxExhibitGenderActionPerformed

    private void btnAddColourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddColourActionPerformed
        int colourIdxValue = cmxColours.getSelectedIndex();
        // get initial value of index to colours4Breed
        BreedColour bc;
        int breedColourIdxValue = colours4Breed.get(colourIdxValue);
        // now adjust the index because some may have been removed
        for (int idx = 0; idx < colours4Breed.size() && idx <= breedColourIdxValue; idx++){
            bc = (BreedColour) breedColourList.get(colours4Breed.get(idx));
            if (bc.isAvailable() && bc.isSelected()){
                if (colours4Breed.get(idx) <= breedColourIdxValue)
                    breedColourIdxValue++;
            }
        }
        bc = (BreedColour) breedColourList.get(breedColourIdxValue);
        bc.setSelected(true);
        this.adjustListOfColoursForBreed();
        this.lstColoursForClass.updateUI();
    }//GEN-LAST:event_btnAddColourActionPerformed

    private void rbnMembersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnMembersActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_rbnMembersActionPerformed

    private void rbnStandardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnStandardActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_rbnStandardActionPerformed

    private void rbnUpSideDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnUpSideDownActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_rbnUpSideDownActionPerformed

    private void rbnBreedersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnBreedersActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_rbnBreedersActionPerformed

    private void btnDelColourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelColourActionPerformed
        String colourName = (String) lstColoursForClass.getSelectedValue();
        BreedColour bc;
        Colour colour;
        boolean done = false;
        
        for (int idx = 0; !done && idx < colours4Breed.size(); idx++){
            bc = (BreedColour) breedColourList.get(colours4Breed.get(idx));
            colour = (Colour) colourList.findInListWithId(bc.getColourId(VersionRequired.CURRENT));
            if (colourName.equals(colour.getColour())){
                bc.setSelected(false);
            }
        }
        this.adjustListOfColoursForBreed();
    }//GEN-LAST:event_btnDelColourActionPerformed

    private void cmxSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmxSectionActionPerformed
        curRecord = getFormData();
    }//GEN-LAST:event_cmxSectionActionPerformed

    private void lstDisplayValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstDisplayValueChanged
        selectedRecord =lstDisplay.getSelectedIndex();
        curRecord = (ShowClass) showClasses.get(selectedRecord);
        setFormData(curRecord);
    }//GEN-LAST:event_lstDisplayValueChanged

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        newItem = ! newItem;
        if (newItem){
            lblStatus.setText("New Record - Add or Cancel");
            cmxBreed.setSelectedIndex(0);
            cmxSection.setSelectedIndex(4);
            cmxExhibitAge.setSelectedIndex(0);
            cmxExhibitGender.setSelectedIndex(0);
            cmxExhibitorAge.setSelectedIndex(0);
            cmxExhibitorGender.setSelectedIndex(0);
            cbxBreedClass.setSelected(false);
            rbnStandard.setSelected(false);
            rbnMembers.setSelected(false);
            rbnBreeders.setSelected(false);
            rbnUpSideDown.setSelected(false);
            lstColoursForClass.removeAll();
            btnInsert.setText("Add");
        } else if (showClasses.isAlreadyInTheList(Integer.valueOf(edtClassNo.getText()))){
            newItem = true;
            lblStatus.setText("New Record - Class No is already defined change the Class number");
        } else {
            lblStatus.setText("New Record - Added");
            ShowClass dataRecord = getFormData();
            dataRecord.setNewItem(true);
            showClasses.add(dataRecord);
            lstShowClassesDataModel.addElement(dataRecord.toListString(showClasses.getFormatStr()));
            setButtons(dataRecord);
            selectedRecord = breedList.list.size();
            curRecord = (ShowClass) showClasses.get(selectedRecord);
            lstDisplay.setSelectedIndex(selectedRecord);
            displayTheList();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        ShowClass dataRecord = getFormData();
        dataRecord.setDirty(false);
        dataRecord.setNewItem(false);
        dataRecord.setReadyToDelete(false);
        if (!dataRecord.equals(curRecord)){
            if (curRecord.isDirty()){
                dataRecord.performUpdate();
                curRecord.setDirty(false);
            } else {
                curRecord.setDirty(true);
                dataRecord.setDirty(true);
            }
            breedList.list.set(selectedRecord, dataRecord);
            lstShowClassesDataModel.set(selectedRecord, dataRecord.toListString(breedList.getFormatStr()));
        }
        setButtons(dataRecord);
        displayTheList();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        //curRecord = getFormData();
        curRecord.setReadyToDelete(!curRecord.isReadyToDelete());
        //btnDelete.setText(curRecord.isReadyToDelete()?"Undelete":"Delete");
        breedList.list.set(selectedRecord, curRecord);
        lstShowClassesDataModel.set(selectedRecord, curRecord.toListString(breedList.getFormatStr()));
        setButtons(curRecord);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cmxBreedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmxBreedItemStateChanged
        makeListOfColoursForBreed(breedList.findInListByIndex(cmxBreed.getSelectedIndex()));
        setColoursEnabled();  
    }//GEN-LAST:event_cmxBreedItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddColour;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelColour;
    private javax.swing.JButton btnDelete;
    private javax.swing.ButtonGroup btnGrpDuplicates;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnUpdate;
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
    private javax.swing.JTextField edtHeaders;
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
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList lstColoursForClass;
    private javax.swing.JList lstDisplay;
    private javax.swing.JRadioButton rbnBreeders;
    private javax.swing.JRadioButton rbnMembers;
    private javax.swing.JRadioButton rbnStandard;
    private javax.swing.JRadioButton rbnUpSideDown;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setButtons() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setButtons(BaseDataItem dataRecord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
  
//
// 
//

}

//    private Vector<Integer> populateWithList(JComboBox comboBox, String sql, String field, Vector<Integer> keyList){
//        ResultSet rs;
//        keyList.clear();
//        comboBox.removeAllItems();
//        rs = DBA.executeSQL(sql);
//        if (rs != null){
//            try {
//
//                while (rs.next()){
//                   comboBox.addItem(rs.getString(field));
//                   //breedIdKeyList.add(rsDBAccess.getInstance();.getInt("id"));
//                   keyList.add(rs.getInt("id"));
//                }
//                comboBox.updateUI();
//            } catch (SQLException ex) {
//                Logger.getLogger(ShowClassForm.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return keyList;
//    } 
    
//    private void populate(JComboBox comboBox, String sql, String field){
//        ResultSet rs;
//        
//        comboBox.removeAllItems();
//        rs = DBA.executeSQL(sql);
//        if (rs != null){
//            try {
//                while (rs.next()){
//                   comboBox.addItem(rs.getString(field)); 
//                }
//                comboBox.updateUI();
//            } catch (SQLException ex) {
//                Logger.getLogger(ShowClassForm.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    /**
     * @param comboBox  The JComboBox to po<String>(v)pulate
     * @param sql       The SQL statement from the table
     * @param cmxKey    The JComboBox that holds the key to restricted executeSQLion
     * @param keys      The key values
     */
//    private void populate(JComboBox comboBox, String sql, String field, JComboBox cmxKey, Vector<Integer> keys){
//        ResultSet rs;
//        
//        comboBox.removeAllItems();
//        rs = DBA.executeSQL(sql,keys.get(cmxKey.getSelectedIndex()));
//        if (rs != null){
//            try {
//                while (rs.next()){
//                   comboBox.addItem(rs.getString(field)); 
//                }
//                comboBox.updateUI();
//            } catch (SQLException ex) {
//                Logger.getLogger(ShowClassForm.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

//    private Vector<Colour> populateColours(JComboBox comboBox, String sql, String field, JComboBox cmxKey, Vector<Integer> keys){
//                return localColourList;
//    }
    
//    private Vector<Integer> populate(JComboBox comboBox, String sql, String field, JComboBox cmxKey, Vector<Integer> keys, Vector<Integer> keyList){
//        ResultSet rs;
//        Vector<Integer> localKeyList = null; 
//        if (keyList == null){
//            localKeyList = new Vector<>();
//        } else {
//            localKeyList = keyList;
//        }
//        comboBox.removeAllItems();
//        //keyList.clear();
//        rs = DBA.executeSQL(sql,keys.get(cmxKey.getSelectedIndex()));
//        if (rs != null){
//            try {
//                while (rs.next()){
//                   comboBox.addItem(rs.getString(field)); 
//                   localKeyList.add(rs.getInt("id"));
//                }
//                comboBox.updateUI();
//            } catch (SQLException ex) {
//                Logger.getLogger(ShowClassForm.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return localKeyList;
//    }

    /**
     * updates the colours JComboBox to the colours available with
     * the current breed.
     * Netherland Dwarf and Polish have a large executeSQLion of colours
     * Some have only one colour and the colour executeSQLion is disabled,
     * Changing the breed again the colour is re-enabled.
     */
//    private void createColoursAndAges(){
//        Colour colour;
//        if (initDone){
//            lblColours.setEnabled(true);        String name = String.format("%c%s ",cbxBreedClass.isSelected()?'*':' ',edtClassNo.getText());
//        rbnStandard.setEnabled(!sc.isBreedClass());
//        rbnBreeders.setEnabled(!sc.isBreedClass());
//        rbnUpSideDown.setEnabled(!sc.isBreedClass());
//        rbnMembers.setEnabled(!sc.isBreedClass());
//        cmxSection.setSelectedIndex(sc.getSection()-1);
//
//            cmxColours.setEnabled(true);
//            cmxColours.removeAllItems();
//            availableColourList.getColoursForBreed(breedIdKeyList.get(cmxBreed.getSelectedIndex()));
//
//            cmxColours = updateColours(cmxColours,availableColourList);
//            cmxExhibitAge.removeAllItems();
//            populate(cmxExhibitAge,
//                    "SELECT DISTINCT exhibit_ages.age_text, exhibit_ages.age "
//                    + "FROM breeds, exhibit_ages "
//                    + "WHERE exhibit_ages.id = breeds.adult_age "
//                    + "AND breeds.adult_age = exhibit_ages.id " 
//                    + "AND breeds.id = ? " 
//                    + "AND exhibit_ages.age = 3 "
//                    + "OR exhibit_ages.age != 3 ",
//                    "age_text",
//                    cmxBreed, 
//                    breedIdKeyList);
//            lblColours.setEnabled(cmxColours.getItemCount()>0);
//            cmxColours.setEnabled(cmxColours.getItemCount()>0);
//        }
//    }
    
//    private JComboBox updateColours(JComboBox comboBox,final AvailableColoursList acl){
//        comboBox.removeAllItems();
//        for (AC ac : acl.getAll()){
//            if (ac.isAvailable()){
//                comboBox.addItem(ac.getColour());
//            }
//        }
//        return comboBox;
//    }
//    
  //    private void updateClassTable() {
//        
//        
//    }
//

//    private void updateEditor(ShowClass sc) {
//        String name = String.format("%c%s ",cbxBreedClass.isSelected()?'*':' ',edtClassNo.getText());
//        rbnStandard.setEnabled(!sc.isBreedClass());
//        rbnBreeders.setEnabled(!sc.isBreedClass());
//        rbnUpSideDown.setEnabled(!sc.isBreedClass());
//        rbnMembers.setEnabled(!sc.isBreedClass());
//        cmxSection.setSelectedIndex(sc.getSection()-1);
//        cmxBreed.setSelectedIndex(sc.getBreedId()-1);
//        Colour colRec = new Colour();
//        
//        name += (String) cmxBreed.getSelectedItem();
//        
//        if (cmxColours.isEnabled()){
//            for (int idx = 0; idx < lstColoursForClassData.size(); idx++){
//                useAbbrevs = useAbbrevs | name.length()>45;
//                System.out.printf("AIU %c len %d\n",useAbbrevs?'Y':'N',name.length());
//                colRec.performRead(sc.getColours().get(idx));
//                if (useAbbrevs){
//                    name += " " + colRec.getAbbrev();
//                } else {
//                    name += " " + colRec.getColour();
//                    //lstColoursForClassData.get(idx);
//                }
//                if ((idx+2) <lstColoursForClassData.size()){
//                    name += ",";
//                } else if ((idx+1) <lstColoursForClassData.size()){
//                    name += " &";
//                }
//            }            
//        }
//        name += " " + (String) cmxExhibitAge.getSelectedItem();
// 
//        if (cmxExhibitGender.getSelectedIndex()!=0){
//            name += " " + (String) cmxExhibitGender.getSelectedItem();
//        }
//        if (cmxExhibitorAge.getSelectedIndex()!=0){
//            name += " " + (String) cmxExhibitorAge.getSelectedItem();
//        }
//        if (cmxExhibitorGender.getSelectedIndex()!=0){
//            name += " " + (String) cmxExhibitorGender.getSelectedItem();
//        }
//        if (rbnBreeders.isEnabled() && rbnBreeders.isSelected()){
//            name += " Breeders";
//        }
//        if (rbnUpSideDown.isEnabled() && rbnUpSideDown.isSelected()){
//            name += " Up Side Down";
//        }
//        if (rbnMembers.isEnabled() && rbnMembers.isSelected()){
//            name += " Members";
//        }
//        edtClassName.setText(sc.getName());
//
//    }

