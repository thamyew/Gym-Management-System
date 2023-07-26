package gp6.gymmanagementapp;

import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageEquipmentMenu extends javax.swing.JFrame {

    Equipment selectedEquipment;

    public ManageEquipmentMenu() {
        initComponents();
        
        this.setVisible(true);
        updateTableRow();        
    }
    
    // This function is used to enable all utility buttons such as add button and return to menu button
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void enableAllUtilityButtons()
    {
        // Enable return to menu button
        backB.setEnabled(true);
        // Enable add exercise plan button
        button1.setEnabled(true);
        // Enable table/list of exercise plan function (Allow select data row)
        equipmentTable.setEnabled(true);
    }
    
    // This function is used to disable all utility buttons such as add button and return to menu button to avoid users accidentally close the window while still editing data
    // Is used when:
    // 1) modifying data
    // 2) add new exercise plan
    private void disableAllUtilityButtons()
    {
        // Disable return to menu button
        backB.setEnabled(false);
        // Disable add exercise plan button
        button1.setEnabled(false);
        // Disable table/list of exercise plan function (Prevent select data row)
        equipmentTable.setEnabled(false);
    }

    private void updateTableRow()
    {
        jPanel1.setVisible(false);

        Collections.sort(GymManagementApp.equipmentList, (Equipment equipment1, Equipment equipment2) -> equipment1.getEquipmentID() - equipment2.getEquipmentID());
        
        DefaultTableModel model = (DefaultTableModel) equipmentTable.getModel();
        
        model.setRowCount(0);
        
         // Variables used to store the data to be contained in the list/table which has 2 columns: ID, Name
        Object[] rowData = new Object[2];
        
        // Enhanced for-loop to loop the sorted trainer list
        for (Equipment equipment: GymManagementApp.equipmentList) 
        {
            // Storing related data which are trainer ID, name and phone number
            rowData[0] = equipment.getEquipmentID();
            rowData[1] = equipment.getEquipmentName();
           
            // Adding the data into the list/table
            model.addRow(rowData);
            
        }
        enableAllUtilityButtons();
        equipmentTable.setDefaultEditor(Object.class, null);
    }

    private void displayInformation()
    {
        int selectedRow = equipmentTable.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)equipmentTable.getModel();
        
        int selectedEquipmentID = Integer.valueOf(model.getValueAt(selectedRow, 0).toString());
        selectedEquipment = GymManagementApp.getEquipment(selectedEquipmentID);
        
        jPanel1.setVisible(true); 
        button2.setText("Modify");
        button3.setText("Delete");
        
        equipmentID.setText(String.valueOf(selectedEquipment.getEquipmentID()));
        equipmentName.setText(selectedEquipment.getEquipmentName());
        
        equipmentID.setEditable(false);
        equipmentName.setEditable(false);
    }
    
    private boolean checkIDSame(int equipmentID)
    {
        for (Equipment equipment : GymManagementApp.equipmentList)
            if (equipment.getEquipmentID() == equipmentID)
                return true;
        
        return false;
    }
    
    private void performAction2(String word)
    {
        switch(word)
        {
            case "Confirm" -> confirmButton();
            case "Modify" -> modifyButton();
            case "Update" -> updateButton();
            default -> {}
        }
    }
    
    private void performAction3(String word)
    {
        switch(word)
        {
            case "Cancel" -> cancelButton();
            case "Delete" -> deleteButton();
            default -> {}
        }
    }
    private void confirmButton()
    {
        if(equipmentID.getText().isEmpty() || equipmentName.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please enter all fields!");
        }
        else if (!GymManagementApp.checkIsNumeric(equipmentID.getText()))
        {
            JOptionPane.showMessageDialog(null, "The equipment ID should be integer!");
        }
        else if (checkIDSame(Integer.valueOf(equipmentID.getText())))
        {
            JOptionPane.showMessageDialog(null, "The equipment ID has been used! Please use another equipment ID!");
        }
        else
        {
            int equipID = Integer.valueOf(equipmentID.getText());
            String equipName = equipmentName.getText(); 
           
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to add this equipment?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
            if (answer == JOptionPane.YES_OPTION)
            {
                GymManagementApp.equipmentList.add(new Equipment(equipID, equipName));
                
                updateTableRow();
            }
        }
    }
    
    private void modifyButton()
    {
        disableAllUtilityButtons();
        equipmentID.setEditable(true);
        equipmentName.setEditable(true);
        button2.setText("Update");
        button3.setText("Cancel");
    }
    
    private void updateButton()
    {        
        if (checkIDSame(Integer.valueOf(equipmentID.getText())) && selectedEquipment.getEquipmentID() != Integer.valueOf(equipmentID.getText()))
        {
            JOptionPane.showMessageDialog(this, "The ID of equipment entered has been used! Please change to another one");
        }
        else if (!GymManagementApp.checkIsNumeric(equipmentID.getText()))
        {
            JOptionPane.showMessageDialog(null, "The equipment ID should be integer!");
        }
        else if (selectedEquipment.getEquipmentID() == Integer.valueOf(equipmentID.getText()) && 
                selectedEquipment.getEquipmentName().equals(equipmentName.getText()))
        {
            JOptionPane.showMessageDialog(this, "There is nothing changed!");
        }        
        else
        {
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this equipment?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (answer == JOptionPane.YES_OPTION)
            {
                int tempID = Integer.valueOf(equipmentID.getText());
                String tempName = equipmentName.getText();

                selectedEquipment.setEquipmentID(tempID);
                selectedEquipment.setEquipmentName(tempName);

                updateTableRow();
                
                selectedEquipment = null;
            }        
        }
    }
    
    
    private void cancelButton()
    {
        if(button2.getText().equals("Update"))
        {
            displayInformation();
        }
        else
        {
            jPanel1.setVisible(false);
        }
        enableAllUtilityButtons();
    }
    
    private void deleteButton()
    {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this equipment?\n(WARNING: This might also delete exercise plans/subscriptions related to the equipment)", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (answer == JOptionPane.YES_OPTION)
        {
            GymManagementApp.deleteAllRelatedExercisePlan(selectedEquipment);
            GymManagementApp.equipmentList.remove(selectedEquipment);
            selectedEquipment = null;
            updateTableRow();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        equipmentTable = new javax.swing.JTable();
        customerListLabel3 = new javax.swing.JLabel();
        backB = new javax.swing.JButton();
        button1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        customerListLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        equipmentID = new javax.swing.JTextField();
        equipmentName = new javax.swing.JTextField();
        button2 = new javax.swing.JButton();
        button3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Manage Equipment");

        equipmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        equipmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                equipmentTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(equipmentTable);
        if (equipmentTable.getColumnModel().getColumnCount() > 0) {
            equipmentTable.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        customerListLabel3.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        customerListLabel3.setText("<html><u>Equipment List</u>");

        backB.setText("<< Back to Menu");
        backB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBMouseClicked(evt);
            }
        });

        button1.setText("Add");
        button1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerListLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(backB)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button1))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerListLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backB)
                    .addComponent(button1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        customerListLabel2.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        customerListLabel2.setText("<html><u>Equipment Detail:</u>");

        jLabel1.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Equipment ID:");

        jLabel2.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Equipment Name:");

        equipmentID.setToolTipText("");
        equipmentID.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        equipmentName.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        button2.setText("Confirm");
        button2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button2MouseClicked(evt);
            }
        });

        button3.setText("Cancel");
        button3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerListLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(button2)
                            .addGap(18, 18, 18)
                            .addComponent(button3))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(equipmentName, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                .addComponent(equipmentID)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerListLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(equipmentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(equipmentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button2)
                    .addComponent(button3))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        jPanel1.setVisible(true);
        button2.setText("Confirm");
        button3.setText("Cancel");
        disableAllUtilityButtons();
       
        equipmentID.setEditable(true);
        equipmentName.setEditable(true);
        equipmentID.setText("");
        equipmentName.setText("");
    }//GEN-LAST:event_button1ActionPerformed

    private void backBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backBMouseClicked
        // Create again the menu window
        if (StaffMainMenu.loggedInStaff != null)
            new StaffMainMenu();
        else 
            new AdminMainMenu();
        // Dispose the window when the return to menu button is clicked (**This action will close the manage customer window)
        this.dispose();
    }//GEN-LAST:event_backBMouseClicked

    private void equipmentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_equipmentTableMouseClicked
        if (equipmentTable.isEnabled())
            displayInformation();
    }//GEN-LAST:event_equipmentTableMouseClicked

    private void button2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseClicked
        performAction2(button2.getText());
    }//GEN-LAST:event_button2MouseClicked

    private void button3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button3MouseClicked
        performAction3(button3.getText());
    }//GEN-LAST:event_button3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backB;
    private javax.swing.JButton button1;
    private javax.swing.JButton button2;
    private javax.swing.JButton button3;
    private javax.swing.JLabel customerListLabel2;
    private javax.swing.JLabel customerListLabel3;
    private javax.swing.JTextField equipmentID;
    private javax.swing.JTextField equipmentName;
    private javax.swing.JTable equipmentTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
