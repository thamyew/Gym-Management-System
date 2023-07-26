package gp6.gymmanagementapp;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageStaffDetailMenu extends javax.swing.JFrame {

    private static String firstName;
    private static String lastName; 
    private static String address;
    private static String phone;
    private static String email;
    private static String ID;
    private static String username;
    private static String password;
    
    // Constructor to initialize menu
    public ManageStaffDetailMenu() {
        initComponents();
        // Set the menu to visible
        this.setVisible(true);
        // Update the data in the table
        updateTableRow();
    }
    
    // Check if input has digit
    private boolean checkHasDegit()
    {
        String IDrefined = IDField.getText().replaceAll("[^0-9]", "");
        
        try
        {
            // try parsing the content to int
            Integer.parseInt(IDrefined);
        }
        catch (NumberFormatException e)
        {
            // Return false if there is parse error
            return false;
        }
        
        return true;
    }
    
    private void updateTableRow()
    {
        // Set staff detail container to not visible
        staffDetailContainer.setVisible(false);
        
        // Sorting the staff list
        Collections.sort(GymManagementApp.staffList, (Staff staff1, Staff staff2) -> staff1.getStaffID() - staff2.getStaffID());
        
        // Get the table model component
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        // Initialize the data table with zero information
        model.setRowCount(0);
        
         // Variables used to store the data to be contained in the list/table which has 3 columns: ID, name and phone number
        Object[] rowData = new Object[3];
        
        // Enhanced for-loop to loop the sorted staff list
        for (Staff staff: GymManagementApp.staffList) 
        {
            // Storing related data which are trainer ID, name and phone number
            rowData[0] = staff.getID();
            rowData[1] = staff.getFirstName() + " " + staff.getLastName();
            rowData[2] = staff.getPhone();
           
            // Adding the data into the list/table
            model.addRow(rowData);
            
        }
        staffTable.setDefaultEditor(Object.class, null);
    }

    // Display the selected trainer's information
    private void displayInformation()
    {
        // Get the selected row based on which row the user select
        int selectedRow = staffTable.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)staffTable.getModel();
        
        // Set the staff detail container to be visible
        staffDetailContainer.setVisible(true);
          
        Object[] tempData = new Object[8];
        
        //Create new arrayList
        ArrayList<Staff> staffTemp = GymManagementApp.staffList;
        
        Staff staff = staffTemp.get(selectedRow);

        tempData[0] = staff.getFirstName();
        tempData[1] = staff.getLastName();
        tempData[2] = staff.getAddress();
        tempData[3] = staff.getPhone();
        tempData[4] = staff.getEmail();
        tempData[5] = staff.getID();
        tempData[6] = staff.getStaffUsername();
        tempData[7] = staff.getStaffPassword();  
        
        firstNameField.setText((String) tempData[0]);
        lastNameField.setText((String) tempData[1]);
        addressField.setText((String) tempData[2]);
        phoneField.setText((String) tempData[3]);
        emailField.setText((String) tempData[4]);
        IDField.setText((String) tempData[5]);
        usernameField.setText((String) tempData[6]);
        passwordField.setText((String) tempData[7]);
        
        jButton3.setText("Modify");
        jButton4.setText("Delete");
        disableTextField();
    }
    
    // Determine the function of the button based on the current word displayed on the button
    private void performAction1(String word)
    {
        // Switch statement to determine the function
        switch(word)
        {
            // Case confirm, using confirmButton() function
            case "Confirm" -> {
                confirmButton();
            }
            // Case modify, using modifyButton() function
            case "Modify" -> {
                modifyButton();
            }
            // Case update, using updateButton() function
            case "Update" -> {
                updateButton();
            }
            default -> {}
        }
    }
    
    // Determine the function of the button based on the current word displayed on the button
    private void performAction2(String word)
    {
        // Switch statement to determine the function
        switch(word)
        {
            // Case cancel, using cancelButton() function
            case "Cancel" -> {
                cancelButton();
            }
            // Case delete, using deleteButton() function
            case "Delete" -> {
                deleteButton();
            }
            default -> {}
        }
    }
    
    // Check whether there is empty fields
    private boolean checkEmpty()
    {
        // Check if firstNameField is filled
        if(firstNameField.getText().isEmpty())
        {}
        // Check if lastNameField is filled
        else if(lastNameField.getText().isEmpty())
        {}
        // Check if addressField is filled
        else if(addressField.getText().isEmpty())
        {}
        // Check if phoneField is filled
        else if(phoneField.getText().isEmpty())
        {}
        // Check if emailField is filled
        else if(emailField.getText().isEmpty())
        {}
        // Check if IDField is filled
        else if(IDField.getText().isEmpty())
        {}
        // Check if usernameField is filled
        else if(usernameField.getText().isEmpty())
        {}
        // Check if passwordField is filled
        else if(passwordField.getText().isEmpty()) 
        {} 
        // If no field is emptied, return false
        else {
            return false;
        }
        // If there is empty field, return true
        return true;
    }
    
    // Check if the current inputted ID is used already
    private boolean isSameID()
    {         
        String stID = IDField.getText();
        int pureID = Integer.parseInt(stID.replaceAll("[^0-9]", ""));
        
        int retrieveID = pureID;
        for(Staff staff : GymManagementApp.staffList)
        {
            if(retrieveID == staff.getStaffID())
                return true;
        }
        
        return false;
    }
    
    // Function used to check whether the staff username used to modify/add staff is used already
    // The function returns false if the staff username is not used yet
    // Is used when:
    // 1) modifying data
    // 2) adding new staff
    private boolean isStaffUsernameUsed(String staffUsername)
    {
        // Enhanced for-loop to loop over staff list
        for (Staff staff : GymManagementApp.staffList)
            // Check if the staff username held by the Staff object is same with the staff username passed to the function
            if (staff.getStaffUsername().equals(staffUsername))
                // returns true if the they are the same
                return true;
        // Returns false if cannot find any Staff object is having the same staff username with the staff username passed to the function
        // Indicates the username can be used
        return false;
    }
    
    // Function acts as a confirm button
    private void confirmButton()
    {
        // Check if there is any empty field
        if(checkEmpty())
        {
            // Prompt message to notify users to enter all required fields
            JOptionPane.showMessageDialog(null, "Please enter all fields!");
        }
        else if (!checkHasDegit())
        {
            // Prompt message to notify users to check ID field to have integer
            JOptionPane.showMessageDialog(null, "The staff ID field should have integer!");
        }
        // If there is no empty field
        // Check if the ID inputted is already used by other staff
        else if(isSameID())
        {
            // Prompt message to notify users to enter another ID
            JOptionPane.showMessageDialog(null, "Cannot contain 2 same ID! Please change the ID!");
        }
        else if(isStaffUsernameUsed(usernameField.getText()))
        {
            // Prompt message to notify users to enter another username
            JOptionPane.showMessageDialog(null, "Cannot contain 2 same username! Please change the ID!");
        }
        else
        {
            firstName = firstNameField.getText();
            lastName = lastNameField.getText(); 
            address = addressField.getText();
            phone = phoneField.getText();
            email = emailField.getText();
            ID = IDField.getText();
            username = usernameField.getText();
            password = passwordField.getText();
           
            String IDrefined = ID.replaceAll("[^0-9]", "");
            int adminID = Integer.parseInt(IDrefined);
            
            // Prompt message for user to confirm to add new staff
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to add this staff?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
            // If Yes is selected
            if (answer == JOptionPane.YES_OPTION)
            {
                // Add new staff to the staff list
                GymManagementApp.staffList.add(new Staff(firstName, lastName, address, phone, email, adminID, username, password));
                // Update the table with the new staff added
                updateTableRow();
                // Enable all utility buttons
                enableAllUtilityButtons();
            }
        }        
    }
    
    // Function acts as a modify button
    private void modifyButton()
    {
        // Enable all text fields
        enableTextField();
        // Change the word displayed on jButton3 as jButton3 has multiple functions based on the text on it
        jButton3.setText("Update");
        // Change the word displayed on jButton4 as jButton4 has multiple functions based on the text on it
        jButton4.setText("Cancel");
    }
    
    // Check if the input fields has any changes when modifying data
    private boolean checkChange()
    {       
        // Check if the current value in firstNameField is same with the selected staff first name
        if (!firstNameField.getText().equals(firstName))
        {}
        // Check if the current value in lastNameField is same with the selected staff last name
        else if(!lastNameField.getText().equals(lastName))
        {}
        // Check if the current value in addressField is same with the selected staff address
        else if(!addressField.getText().equals(address))
        {}
        // Check if the current value in phoneField is same with the selected staff phone number
        else if(!phoneField.getText().equals(phone))
        {}
        // Check if the current value in emailField is same with the selected staff email
        else if(!emailField.getText().equals(email))
        {}
        // Check if the current value in IDField is same with the selected staff ID
        else if(!IDField.getText().equals(ID))
        {}
        // Check if the current value in usernameField is same with the selected staff username
        else if(!usernameField.getText().equals(username))
        {}
        // Check if the current value in passwordField is same with the selected staff password
        else if(!passwordField.getText().equals(password))
        {}
        else
            // Returns false if there is no changes between current value and existing value
            return false;
        // Returns true if exists at least one different
        return true;
    }
    
    // Function acts as update button
    private void updateButton()
    {
        int selectedRow = staffTable.getSelectedRow();
        Staff tempStaff = GymManagementApp.staffList.get(selectedRow);
        int ignore = tempStaff.getStaffID();
        
        DefaultTableModel model = (DefaultTableModel)staffTable.getModel();   
        
        // Check if there is any data changed
        if (!checkChange())
        {
            JOptionPane.showMessageDialog(this, "There is nothing changed!");
        }
        else if (!checkHasDegit())
        {
            // Prompt message to notify users to check ID field to have integer
            JOptionPane.showMessageDialog(null, "The staff ID field should have integer!");
        }
        // Check if the ID inputted is already used by other staff
        else if(isSameID() && Integer.parseInt(IDField.getText().replaceAll("[^0-9]", "")) != ignore)
        {
            // Prompt message to notify user that cannot have 2 same ID
            JOptionPane.showMessageDialog(null, "Cannot contain 2 same ID!");
        }
        else if(isStaffUsernameUsed(usernameField.getText()) && !usernameField.getText().equals(tempStaff.getStaffUsername()))
        {
            // Prompt message to notify users to enter another username
            JOptionPane.showMessageDialog(null, "Cannot contain 2 same username! Please change the username!");
        }
        else
        {
            // Prompt message to let user confirm to update the selected staff
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this staff?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (answer == JOptionPane.YES_OPTION)
            {                
                String tempID = IDField.getText();
                String newTempID = tempID.replaceAll("[^0-9]", "");

                // Replace staff in staff list
                GymManagementApp.staffList.set(selectedRow, new Staff (firstNameField.getText(), lastNameField.getText(),
                        addressField.getText(), phoneField.getText(), emailField.getText(), Integer.parseInt(newTempID),
                        usernameField.getText(), passwordField.getText()));
                
                // Update the table row as has new data for the selected staff
                updateTableRow();   
                
                // Enable all utility buttons
                enableAllUtilityButtons();
            }        
        }
    }
    
    // Function acts as cancel button
    private void cancelButton()
    {
        // Make staffDetailContainer become not visible
        staffDetailContainer.setVisible(false);
        // Enable all utility buttons;
        enableAllUtilityButtons();
    }
    
    // Function acts as delete button
    private void deleteButton()
    {
        // Prompt message to let user confirm their action to delete the staff
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this staff?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (answer == JOptionPane.YES_OPTION)
        {
            // Get the selected row number
            int selectedRow = staffTable.getSelectedRow();
            // Remove the staff from the staff list based on the selected row number
            GymManagementApp.staffList.remove(selectedRow);
            // Update the list of staff as a staff is removed from the system
            updateTableRow();    
        }        
    }
    
    // Disable all text fields in staffDetailContainer
    private void disableTextField()
    {
        firstNameField.setEnabled(false);
        lastNameField.setEnabled(false);
        addressField.setEnabled(false);
        phoneField.setEnabled(false);
        emailField.setEnabled(false);
        IDField.setEnabled(false);
        usernameField.setEnabled(false);
        passwordField.setEnabled(false);
        enableAllUtilityButtons();
    }
    
    // Enable all text fields in staffDetailContainer
    private void enableTextField()
    {
        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        addressField.setEnabled(true);
        phoneField.setEnabled(true);
        emailField.setEnabled(true);
        IDField.setEnabled(true);
        usernameField.setEnabled(true);
        passwordField.setEnabled(true);
        disableAllUtilityButtons();
    }
    
    // Initialize all text fields to empty string
    private void clearTextField()
    {
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
        IDField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }
    
    // This function is used to enable all utility buttons such as add button and return to menu button
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void enableAllUtilityButtons()
    {
        // Enable return to menu button
        jButton1.setEnabled(true);
        // Enable add staff button
        addB.setEnabled(true);
        // Enable table/list of staff function (Allow select data row)
        staffTable.setEnabled(true);
    }
    
    // This function is used to disable all utility buttons such as add button and return to menu button to avoid users accidentally close the window while still editing data
    // Is used when:
    // 1) modifying data
    // 2) add new exercise plan
    private void disableAllUtilityButtons()
    {
        // Disable return to menu button
        jButton1.setEnabled(false);
        // Disable add staff button
        addB.setEnabled(false);
        // Disable table/list of staff function (Allow select data row)
        staffTable.setEnabled(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        staffList = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        staffTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        addB = new javax.swing.JButton();
        staffDetailContainer = new javax.swing.JPanel();
        staffDetail = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        firstNameField = new javax.swing.JTextField();
        addressField = new javax.swing.JTextField();
        lastNameField = new javax.swing.JTextField();
        phoneField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        IDField = new javax.swing.JTextField();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Manage Staff");

        staffList.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        staffList.setText("<html><u>Staff List");

        staffTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Name", "Phone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        staffTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                staffTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(staffTable);
        if (staffTable.getColumnModel().getColumnCount() > 0) {
            staffTable.getColumnModel().getColumn(0).setMaxWidth(65);
        }

        jButton1.setText("<< Back to Menu");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        addB.setText("Add");
        addB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBActionPerformed(evt);
            }
        });

        staffDetail.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        jLabel1.setText("<html><u>Staff Detail:");

        jLabel2.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel2.setText("First Name:");

        jLabel3.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Address:");

        jLabel4.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel4.setText("Phone Number:");

        jLabel5.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel5.setText("Email Address:");

        jLabel6.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel6.setText("Staff ID Number:");

        jLabel7.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel7.setText("Staff Username:");

        jLabel8.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel8.setText("Staff Password:");

        jLabel9.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel9.setText("Last Name:");

        firstNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        addressField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        lastNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        phoneField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        emailField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        IDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        usernameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        passwordField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout staffDetailLayout = new javax.swing.GroupLayout(staffDetail);
        staffDetail.setLayout(staffDetailLayout);
        staffDetailLayout.setHorizontalGroup(
            staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffDetailLayout.createSequentialGroup()
                .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(staffDetailLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(staffDetailLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lastNameField))
                            .addGroup(staffDetailLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emailField))
                            .addGroup(staffDetailLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IDField))
                            .addGroup(staffDetailLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordField))
                            .addGroup(staffDetailLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(phoneField))
                            .addGroup(staffDetailLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameField))
                            .addGroup(staffDetailLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(staffDetailLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        staffDetailLayout.setVerticalGroup(
            staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(staffDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jButton3.setText("jButton3");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton4.setText("jButton4");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout staffDetailContainerLayout = new javax.swing.GroupLayout(staffDetailContainer);
        staffDetailContainer.setLayout(staffDetailContainerLayout);
        staffDetailContainerLayout.setHorizontalGroup(
            staffDetailContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffDetailContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(staffDetailContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(staffDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, staffDetailContainerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(14, 14, 14)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        staffDetailContainerLayout.setVerticalGroup(
            staffDetailContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffDetailContainerLayout.createSequentialGroup()
                .addComponent(staffDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(staffDetailContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(staffList, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addB))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(staffDetailContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(staffList, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(addB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(staffDetailContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new AdminMainMenu();
        
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBActionPerformed
        staffDetailContainer.setVisible(true);
        jButton3.setText("Confirm");
        jButton4.setText("Cancel");
        enableTextField();
        clearTextField();
    }//GEN-LAST:event_addBActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        performAction2(jButton4.getText());
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        performAction1(jButton3.getText());
    }//GEN-LAST:event_jButton3MouseClicked

    private void staffTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffTableMouseClicked
        if (staffTable.isEnabled())
            displayInformation();
    }//GEN-LAST:event_staffTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IDField;
    private javax.swing.JButton addB;
    private javax.swing.JTextField addressField;
    private javax.swing.JTextField emailField;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField phoneField;
    private javax.swing.JPanel staffDetail;
    private javax.swing.JPanel staffDetailContainer;
    private javax.swing.JLabel staffList;
    private javax.swing.JTable staffTable;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
