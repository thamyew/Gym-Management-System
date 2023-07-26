
package gp6.gymmanagementapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageCustomerMenu extends javax.swing.JFrame 
{
    // Variable used to store the current customer selected based on the selection on customer list
    private Customer selectedCustomer;
    
    /**
     * Creates new form AdminManageCustomerMenu
     */
    public ManageCustomerMenu() 
    {
        initComponents();
        // Set the menu to be visible on the screen
        this.setVisible(true);
        
        // Used for adding the 2 radio buttons for select the type of customer into a button group
        // Adding the 2 radio buttons to a button group allow only 1 radio button to be selected at the same time
        customerTypeButtonGroup.add(customerTypeNormal);    // Adding radio button for selecting "NORMAL" as type of customer into button group
        customerTypeButtonGroup.add(customerTypeStudent);   // Adding radio button for selecting "STUDENT" as type of customer into button group
        
        // Used to update the content in the list/table of customer
        updateTableRow();
    }
    
    // Function used to update the content in the list/tablee of customer
    // Is used when:
    // 1) the menu is first initialized
    // 2) there is update in the data of a customer
    // 3) when there is cancel function used when modifying or adding customer
    private void updateTableRow()
    {
        // Hiding the customerDetailPanel when there is no customer selected from the customer list
        customerDetailPanel.setVisible(false);
        // Update the jButton1 into mode 1, which grants delete function for the user to delete the selected customer
        updatejButton1(1);
        
        // Sorting the customer list based on the customer ID number of the customer
        Collections.sort(GymManagementApp.customerList, (Customer customer1, Customer customer2) -> customer1.getCustomerID() - customer2.getCustomerID());
        
        // Getting the model of table used to display the customer list
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();      
        // Clear the content in the list/table
        model.setRowCount(0);
        
        // Variables used to store the data to be contained in the list/table which has 4 columns: ID, Name, Phone and Type (type of customer)
        Object[] rowData = new Object[4];
        
        // Enhanced for-loop to loop the sorted customer list
        for (Customer customer : GymManagementApp.customerList) 
        {
            // Storing related data which are customer ID, name, phone number and type of customer
            rowData[0] = customer.getID();
            rowData[1] = customer.getFirstName() + " " + customer.getLastName();
            rowData[2] = customer.getPhone();
            rowData[3] = customer.getTypeOfCustomer();
            
            // Adding the data into the list/table
            model.addRow(rowData);
        }
    }
    
    // Function used to check whether there is changes betweeen the current inputs and existing data
    // The function returns true if there exists at least a change
    // Is used when:
    // 1) modifying data
    private boolean checkDataChanged()
    {
        // Check there is changes on the first name of the selected customer before and after
        if (!selectedCustomer.getFirstName().equals(firstNameField.getText()))
            return true;
        // Check there is changes on the last name of the selected customer before and after
        if (!selectedCustomer.getLastName().equals(lastNameField.getText()))
            return true;
        // Check there is changes on the address of the selected customer before and after
        if (!selectedCustomer.getAddress().equals(addressField.getText()))
            return true;
        // Check there is changes on the phone number of the selected customer before and after
        if (!selectedCustomer.getPhone().equals(phoneNumberField.getText()))
            return true;
        // Check there is changes on the email of the selected customer before and after
        if (!selectedCustomer.getEmail().equals(emailField.getText()))
            return true;
        // Check there is changes on the customer ID number of the selected customer before and after
        if (selectedCustomer.getCustomerID() != Integer.valueOf(customerIDNumberField.getText()))
            return true;
        // Check there is changes on the username of the selected customer before and after
        if (!selectedCustomer.getCustomerUsername().equals(customerUsernameField.getText()))
            return true;
        // Check there is changes on the password of the selected customer before and after
        if (!selectedCustomer.getCustomerPassword().equals(customerPasswordField.getText()))
            return true;
        
        // Check if the radio button for select "STUDENT" as customer type is selected
        if (customerTypeStudent.isSelected())
        {
            // This block will run if the current customer type selected is "STUDENT"
            // Check if the selected customer is an object of Student class
            // Downcasting the selectedCustomer object to Student class and store in Student object named student
            if (selectedCustomer instanceof Student student)
            {
                // If the selected customer is an object of Student class
                // Check there is changes on the student ID of the selected student type customer before and after
                if (!student.getStudentID().equals(customerStudentIDField.getText()))
                    return true;
                // Check there is changes on the institute name of the selected student type customer before and after
                if (!student.getInstituteName().equals(instituteNameField.getText()))
                    return true;
                // Check there is changes on the expeceted year of graduation of the selected student type customer before and after
                if (student.getExpectedYearOfGraduation() != Integer.valueOf(expectedYearOfGraduationField.getText()))
                    return true;
            }
            // If the selected customer is not an object of Student class (is from Customer class)
            else
                // Do nothing and return true to indicate there is changes in the type of customer of existing customer from customer type "NORMAL" to "STUDENT"
                return true;
        }
        // If the radio button for select "STUDENT" as customer type is not selected
        // The radio button for select "NORMAL" as customer type is selected
        else
        {
            // This block will run if the current customer type selected is "NORMAL" as these data is only related to student type customer, NOT normal type
            // Check there is changes on the customer type of the selected customer before and after
            if (!selectedCustomer.getTypeOfCustomer().equalsIgnoreCase("Normal"))
                return true;
        }
        
        // Return false to indicate there is no changes in all the data before and after
        return false;
    }
    
    // Function used to check whether the customer ID number used to modify/add customer is used already
    // The function returns false if the customer ID number is not used yet
    // Is used when:
    // 1) modifying data
    // 2) adding new customer
    private boolean isCustomerIDNumberUsed(int customerIDNumber)
    {
        // Enhanced for-loop to loop over customer list
        for (Customer customer : GymManagementApp.customerList)
            // Check if the customer ID number held by the Customer object is same with the customer ID number passed to the function
            if (customer.getCustomerID() == customerIDNumber)
                // returns true if the they are the same
                return true;
        // Returns false if cannot find any Customer object is having the same customer ID number with the customer ID number passed to the function
        // Indicates the ID number is can be used
        return false;
    }
    
    // Function used to check whether the customer username used to modify/add customer is used already
    // The function returns false if the customer username is not used yet
    // Is used when:
    // 1) modifying data
    // 2) adding new customer
    private boolean isCustomerUsernameUsed(String customerUsername)
    {
        // Enhanced for-loop to loop over customer list
        for (Customer customer : GymManagementApp.customerList)
            // Check if the customer username held by the Customer object is same with the customer username passed to the function
            if (customer.getCustomerUsername().equals(customerUsername))
                // returns true if the they are the same
                return true;
        // Returns false if cannot find any Customer object is having the same customer username with the customer username passed to the function
        // Indicates the username can be used
        return false;
    }
    
    // Function used to check whether there exists a required input fields is not filled or filled with blankspace only
    // The function returns true if there exists at least a required field is not filled or filled with blankspace only
    // Is used when:
    // 1) included in checkDataValid() function
    private boolean checkDataBlank()
    {
        // Check if the field of first name is filled
        if (firstNameField.getText().isBlank())
            return true;
        // Check if the field of last name is filled
        if (lastNameField.getText().isBlank())
            return true;
        // Check if the field of address is filled
        if (addressField.getText().isBlank())
            return true;
        // Check if the field of phone number is filled
        if (phoneNumberField.getText().isBlank())
            return true;
        // Check if the field of email is filled
        if (emailField.getText().isBlank())
            return true;
        // Check if the field of customer ID number field is filled
        if (customerIDNumberField.getText().isBlank())
            return true;
        // Check if the field of customer username is filled
        if (customerUsernameField.getText().isBlank())
            return true;
        // Check if the field of customer password is filled
        if (customerPasswordField.getText().isBlank())
            return true;
        
        // Check if the radio button for select "STUDENT" as customer type is selected
        if (customerTypeStudent.isSelected())
        {
            // This block will run if the current customer type selected is "STUDENT" as these fields is only related to student type customer, NOT normal type
            // Check if the field of customer student ID is filled
            if (customerStudentIDField.getText().isBlank())
                return true;
            // Check if the field of institute name is filled
            if (instituteNameField.getText().isBlank())
                return true;
            // Check if the field of expected year of graduation is filled
            if (expectedYearOfGraduationField.getText().isBlank())
                return true;
        }
        
        // Return false to indicate there is no input fields is not filled or filled with blankspace only
        return false;
    }
    
    // Function used to check whether the input in each input field is valid
    // The funciton returns true if there exists at least a field is invalid (including the field with no input or filled with blankspace)
    // Is used when:
    // 1) modifying data
    // 2) adding new customer
    private boolean checkDataValid()
    {
        // Checking whether each input field is filled with content and not filled with blankspace only
        if (checkDataBlank())
            return true;
        // Checking whether the input for customerIDNumber input field is number
        if (!GymManagementApp.checkIsNumeric(customerIDNumberField.getText()))
            return true;
        // Check if the radio button for select "STUDENT" as customer type is selected
        if (customerTypeStudent.isSelected())
            // Checking whether the input for expectedYearOfGraduation input field is number
            if (!GymManagementApp.checkIsNumeric(expectedYearOfGraduationField.getText()))
               return true;
        
        // Return false to indicate all inputs are valid
        return false;
    }
    
    // Function used to display the customer form when trying to add a new customer to the system
    // Is used when:
    // 1) adding new customer
    private void displayNewCustomerForm()
    {
        // Set the customer form to be visible in the screen
        customerDetailPanel.setVisible(true);
        
        // Initialize all available text fields with empty String
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        customerIDNumberField.setText("");
        customerIDField.setText("");
        customerUsernameField.setText("");
        customerPasswordField.setText("");
        customerStudentIDField.setText("");
        instituteNameField.setText("");
        expectedYearOfGraduationField.setText("");
        
        // Initialize the default type of customer as "NORMAL"
        customerTypeNormal.setSelected(true);
        // Disable the input fields related to student type customer as default type of customer is selected as "NORMAL" to avoid user from editing those fields
        disableStudentSectionFields();
    }
    
    // This function is used to display the customer detail panel and the customer detail based on the selected customer selected from the list/table
    // Is used when 
    // 1) the user select a data row from the customer list/table
    private void displaySelectedCustomerDetail()
    {
        // Set the customer form to be visible in the screen
        customerDetailPanel.setVisible(true);
        
        // Variable used to store the row number of the data row selected from the list/table
        int rowSelected = customerTable.getSelectedRow();
        // variable used to obtain the data in the first column of the row selected (which is customer ID)
        String selectedCustomerID = (String)customerTable.getValueAt(rowSelected, 0);
        
        // Assign the customer by using getCustomer() function by using the customer ID obtained from the list/table
        selectedCustomer = GymManagementApp.getCustomer(selectedCustomerID);
        
        // Check whether there is a customer object returned
        if (selectedCustomer == null)
            // Exit the function if no customer object reeturned
            return;
        
        // Disable all available text fields by using disableAllTextFields() function to disable user from modifying data before clicking on "Modify" button
        disableAllTextFields();
        
        // Assign the data to display the data in form format
        // Assign the first name of the selected customer to the firstNameField
        firstNameField.setText(selectedCustomer.getFirstName());
        // Assign the last name of the selected customer to the lastNameField
        lastNameField.setText(selectedCustomer.getLastName());
        // Assign the address of the selected customer to the addressField
        addressField.setText(selectedCustomer.getAddress());
        // Assign the phone number of the selected customer to the phoneNumberField
        phoneNumberField.setText(selectedCustomer.getPhone());
        // Assign the email of the selected customer to the emailField
        emailField.setText(selectedCustomer.getEmail());
        // Assign the customer ID number of the selected customer to the customerIDNumberField (**The field only accept String input, using Integer.toString() to convert integer number into a String)
        customerIDNumberField.setText(Integer.toString(selectedCustomer.getCustomerID()));
        // Assign the customer ID of the selected customer to the customerIDField
        customerIDField.setText(selectedCustomer.getID());
        // Assign the username of the selected customer to the customerUsernameField
        customerUsernameField.setText(selectedCustomer.getCustomerUsername());
        // Assign the password of the selected customer to the customerPasswordField
        customerPasswordField.setText(selectedCustomer.getCustomerPassword());
        
        // Check if the selected customer is an object of Student class
        // Downcasting the selectedCustomer object to Student class and store in Student object named selectedStudent
        if (selectedCustomer instanceof Student selectedStudent)
        {
            // Set the radio button represents student type of customer to be selected
            customerTypeStudent.setSelected(true);
            // Assign the student ID of the selected student type customer to the customerStudentIDField
            customerStudentIDField.setText(selectedStudent.getStudentID());
            // Assign the institute name of the selected student type customer to the instituteNameField
            instituteNameField.setText(selectedStudent.getInstituteName());
            // Assign the expected year of graduation of the selected student type customer to the expectedYearOfGraduationField
            expectedYearOfGraduationField.setText(Integer.toString(selectedStudent.getExpectedYearOfGraduation()));
        }
        // If the selected customer is not an object of Student class (selected customer is an object of Customer class)
        else
        {
            // Set the radio button represents normal type of customer to be selected
            customerTypeNormal.setSelected(true);
            // Set the customerStudentIDField with empty String
            customerStudentIDField.setText("");
            // Set the instituteNameField with empty String
            instituteNameField.setText("");
            // Set the expectedYearOfGraduationField with empty String
            expectedYearOfGraduationField.setText("");
        }
    }
    
    // This function is used to enable all the input fields / text fields to allow users to modify or add new data
    // Is used when:
    // 1) modifying data
    // 2) add new customer
    private void enableAllTextFields()
    {
        // Enable all the text/input fields, making all the text/input fields are editable by users
        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        addressField.setEnabled(true);
        phoneNumberField.setEnabled(true);
        emailField.setEnabled(true);
        customerIDNumberField.setEnabled(true);
        customerUsernameField.setEnabled(true);
        customerPasswordField.setEnabled(true);
        customerTypeNormal.setEnabled(true);
        customerTypeStudent.setEnabled(true);
        
        // Check if the radio button for select "STUDENT" as customer type is selected
        if (customerTypeStudent.isSelected())
            // If the radio button for select "STUDENT" as customer type is selected
            // Enable the input fields that are related to student type customer
            enableStudentSectionFields();
    }
    
    // This function is used to disable all the input fields / text fields to prevent users from modifying the data
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void disableAllTextFields()
    {
        // Disable all the text/input fields, making all the text/input fields are NOT editable by users
        firstNameField.setEnabled(false);
        lastNameField.setEnabled(false);
        addressField.setEnabled(false);
        phoneNumberField.setEnabled(false);
        emailField.setEnabled(false);
        customerIDNumberField.setEnabled(false);
        customerUsernameField.setEnabled(false);
        customerPasswordField.setEnabled(false);
        customerTypeNormal.setEnabled(false);
        customerTypeStudent.setEnabled(false);
        customerStudentIDField.setEnabled(false);
        instituteNameField.setEnabled(false);
        expectedYearOfGraduationField.setEnabled(false);
    }
    
    // This function is used to enable all the input fields / text fields to allow users to modify or add new data
    // Is used when:
    // 1) modifying data - when radio button to select "STUDENT" for type of customer is selected
    // 2) add new customer - when radio button to select "STUDENT" for type of customer is selected
    private void enableStudentSectionFields()
    {
        // Enable all the text/input fields related to student type of customer, making all the related text/input fields are editable by users
        customerStudentIDField.setEnabled(true);
        instituteNameField.setEnabled(true);
        expectedYearOfGraduationField.setEnabled(true);
    }
    
    // This function is used to disable all the input fields / text fields related to student type customer to prevent users from modifying the data related to student type customer
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    // 2) radio button to select "NORMAL" for type of customer is selected
    private void disableStudentSectionFields()
    {
        // Disable all the text/input fields related to student type of customer, making all the related text/input fields are NOT editable by users
        customerStudentIDField.setEnabled(false);
        instituteNameField.setEnabled(false);
        expectedYearOfGraduationField.setEnabled(false);
        
        // Reassign empty String to the affected fields
        customerStudentIDField.setText("");
        instituteNameField.setText("");
        expectedYearOfGraduationField.setText("");
    }
    
    // This function is used to enable all utility buttons such as add button and return to menu button
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void enableAllUtilityButtons()
    {
        // Enable return to admin menu button
        returnButton.setEnabled(true);
        // Enable add customer button
        addCustomerButton.setEnabled(true);
        // Enable table/list of customer function (Allow select data row)
        customerTable.setEnabled(true);
    }
    
    // This function is used to disable all utility buttons such as add button and return to menu button to avoid users accidentally close the window while still editing data
    // Is used when:
    // 1) modifying data
    // 2) add new customer
    private void disableAllUtilityButtons()
    {
        // Disable return to admin menu button
        returnButton.setEnabled(false);
        // Disable add customer button
        addCustomerButton.setEnabled(false);
        // Disable table/list of customer function (Prevent select data row)
        customerTable.setEnabled(false);
    }
    
    // This function is used to update the function of jButton1 based on the circumstances (Button with dynamic function)
    // This function will clear the current action performed by jButton1 and update the function of the button
    // There exists 3 functions/modes of the button:
    // 1) Button used to trigger action allows users modifying existing data of the customer
    // 2) Button used to confirm the modified data and update the information of the customer
    // 3) Button used to confirm the data to be used to add a new customer to the system
    // **NOTE: This function will update jButton1's pair, which is jButton2 function**
    // **Function pairs (jButton1 / jButton2):
    // 1) Modify / Delete
    // 2) Confirm / Cancel (For modifying data)
    // 3) Confirm / Cancel (For adding new customer)
    // Parameter "mode" is used to determine the function to be updated
    // mode 1 -> update jButton1 to have function/mode 1
    // mode 2 -> update jButton1 to have function/mode 2
    // mode 3 -> update jButton1 to have function/mode 3
    private void updatejButton1(int mode)
    {
        // Enhanced for-loop to loop over current ActionListeners of jButton1
        // This loop is used to clear all the current ActionListeners of jButton1
        for (ActionListener actionListener : jButton1.getActionListeners()) 
        {
            // Remove the current ActionListener of jButton1 acquired from the loop
            jButton1.removeActionListener(actionListener);
        }
        
        // Switch to switch the function/ActionListener to be applied on jButton1
        switch(mode)
        {
            // When parameter "mode" is equal to 1
            case 1 ->
            {
                // Change the text on jButton1 to "Modify"
                jButton1.setText("Modify");
                // Update the jButton1's pair, jButton2's function (The function pair (jButton1 / jButton2) is: Modify / Delete)
                updatejButton2(1);
                // Disable all text/input fields to prevent users from modifying the data and currently allow viewing only
                disableAllTextFields();
                // Enable all utility buttons function as currently doesn't have any modify or add action in progress
                enableAllUtilityButtons();
                
                // Adding new ActionListener to jButton1 (the function of jButton1)
                jButton1.addActionListener((ActionEvent e) -> 
                {
                    // Update jButton1's function to mode 2, which is confirm button used when modifying data
                    updatejButton1(2);
                });
            }
            // When parameter "mode" is equal to 2
            case 2 -> 
            {
                // Change the text on jButton1 to "Confirm"
                jButton1.setText("Confirm");
                // Update the jButton1's pair, jButton2's function (The function pair (jButton1 / jButton2) is: Confirm / Cancel) (For modifying data)
                updatejButton2(2);
                // Enable all text/input fields to allow users to modify the data
                enableAllTextFields();
                // Disable all utility buttons function as currently modifying data is in progress
                disableAllUtilityButtons();
                
                // Adding new ActionListener to jButton1 (the function of jButton1)
                jButton1.addActionListener((ActionEvent e) -> 
                {
                    // Check if the datatype of the inputs in all input fields are valid
                    // If there exists data invalid, the boolean "true" will be returned
                    if (checkDataValid())
                        // If there exists data invalid, a popup window will be generated and notify the users regarding the possible reasons of causing data invalid
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the data until all data input is valid
                        JOptionPane.showConfirmDialog(this, "There exists input fields not filled or filled with blankspace or customer ID Number or customer student ID(applicable to student type of customer only) is not integer! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Check if the customer ID number is used by other Customer object in the customer list except of the current selected customer
                    // If there exists Customer object other than the selected customer is using the inputted customer ID number, the boolean "true" will be returned
                    else if (isCustomerIDNumberUsed(Integer.valueOf(customerIDNumberField.getText())) && selectedCustomer.getCustomerID() != Integer.valueOf(customerIDNumberField.getText()))
                        // If there exists Customer object other than the selected Customer is using the inputted customer ID number, a popup window will be generated and notify the users to change the Customer ID number used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the customer ID number until an unused customer ID number or same customer ID number with the current selected customer is inputted
                        JOptionPane.showConfirmDialog(this, "The customer ID number is used by other customer already! Please change the customer ID number!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid and the customer ID number is valid
                    // Check if the customer username is used by other Customer object in the customer list except of the current selected customer
                    // If there exists Customer object other than the selected customer is using the inputted customer username, the boolean "true" will be returned
                    else if (isCustomerUsernameUsed(customerUsernameField.getText()) && !selectedCustomer.getCustomerUsername().equals(customerUsernameField.getText()))
                        // If there exists Customer object other than the selected Customer is using the inputted customer username, a popup window will be generated and notify the users to change the Customer username used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the customer username until an unused customer username or same customer username with the current selected customer is inputted
                        JOptionPane.showConfirmDialog(this, "The customer username is used by other customer already! Please change the customer username!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Checking for whether there exists changes in the data input in all fields will be proceed
                    // If there exists changes in the data, the boolean "true" will be returned
                    else if (checkDataChanged())
                    {
                        // If there exists changes in the data, a popup window will be generated and notify the users to confirm the modified data/input is the one they want to used to change the data of the selected customer
                        // The users can select "Yes" to confirm the modified data/input; "No" and close the window to return to modify the data until they input the desired data used to change the data of the selected customer
                        // Variable used to store the option picked by users
                        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to make the changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                        // Check if the user is selecting "Yes"
                        if (answer == JOptionPane.YES_OPTION)
                        {
                            // Set the data for first name of the selected customer to the inputted first name
                            selectedCustomer.setFirstName(firstNameField.getText());
                            // Set the data for last name of the selected customer to the inputted last name
                            selectedCustomer.setLastName(lastNameField.getText());
                            // Set the data for address of the selected customer to the inputted address
                            selectedCustomer.setAddress(addressField.getText());
                            // Set the data for phone number of the selected customer to the inputted phone number
                            selectedCustomer.setPhone(phoneNumberField.getText());
                            // Set the data for email of the selected customer to the inputted email
                            selectedCustomer.setEmail(emailField.getText());
                            // Set the data for customer ID number of the selected customer to the inputted customer ID number
                            selectedCustomer.setCustomerID(Integer.valueOf(customerIDNumberField.getText()));
                            // Set the data for customer username of the selected customer to the inputted customer username
                            selectedCustomer.setCustomerUsername(customerUsernameField.getText());
                            // Set the data for customer password of the selected customer to the inputted customeer password
                            selectedCustomer.setCustomerPassword(customerPasswordField.getText());
                            
                            // Check if the radio button for select "NORMAL" as customer type is selected
                            if (customerTypeNormal.isSelected())
                            {
                                // Check if the selected customer is a Student object
                                // Downcasting the selectedCustomer object to Student class and store in Student object named student
                                if (selectedCustomer instanceof Student student)
                                {            
                                    // If the selected customer is a Student object
                                    // radio button for select "NORMAL" as customer type is selected resulting the type of customer changed from student type to normal type
                                    // For-loop to loop over customer list
                                    for (int i = 0; i < GymManagementApp.customerList.size(); i++) 
                                    {
                                        // Check if there is customer object with the same ID as the ID of Student object
                                        if (GymManagementApp.customerList.get(i).getID().equals(student.getID()))
                                        {
                                            // If there is customer object with the same ID as the ID of Student object
                                            // Set the element at the index that found customer object with the same ID as the ID of Student object to a new Customer object that take Student object student as parameter
                                            // The constructor of Customer that takes Student object will convert the Student object to Customer object
                                            GymManagementApp.customerList.set(i, new Customer(student));
                                            // Break the loop as there is no need to loop the customer list till the end
                                            break;
                                        }
                                    }
                                }
                            }
                            // If the radio button for select "STUDENT" as customer type is selected
                            else
                            {
                                // Check if the selected customer is a Student object
                                // Downcasting the selectedCustomer object to Student class and store in Student object named student
                                if (selectedCustomer instanceof Student student)
                                {
                                    // Set the data for customer student ID of the selected student type customer to the inputted customer student ID
                                    student.setStudentID(customerStudentIDField.getText());
                                    // Set the data for institute name of the selected student type customer to the inputted institute name
                                    student.setInstituteName(customerStudentIDField.getText());
                                    // Set the data for expected year of graduation of the selected student type customer to the inputted expected year of graduation
                                    student.setExpectedYearOfGraduation(Integer.valueOf(expectedYearOfGraduationField.getText()));
                                }
                                // If the selected customer is not a Student object (is a Customer object)
                                else
                                {
                                    // If the selected customer is not a Student object
                                    // radio button for select "STUDENT" as customer type is selected resulting the type of customer changed from normal type to student type
                                    // For-loop to loop over customer list
                                    for (int i = 0; i < GymManagementApp.customerList.size(); i++) 
                                    {
                                        // Check if there is customer object with the same ID as the ID of selected customer
                                        if (GymManagementApp.customerList.get(i).getID().equalsIgnoreCase(selectedCustomer.getID()))
                                        {
                                            // If there is customer object with the same ID as the ID of selected customer
                                            // Set the element at the index that found customer object with the same ID as the ID of selected customer to a new Student object that take Customer object selected student as one of the parameters
                                            // The constructor of Student that takes Customer object will convert the Customer object to Student object
                                            GymManagementApp.customerList.set(i, new Student(selectedCustomer, customerStudentIDField.getText(), 
                                            instituteNameField.getText(), Integer.valueOf(expectedYearOfGraduationField.getText())));
                                            // Break the loop as there is no need to loop the customer list till the end
                                            break;
                                        }
                                    }
                                }
                            }

                            // Assign the variable selectedCustomer to null as the action performed to change the data of current selected customeer is complete
                            selectedCustomer = null;
                            // Update the data in the customer list/table as the data for the previous selected customer is changed, update is needed to display the new data
                            updateTableRow();
                        }
                    }
                    // If the inputted data are valid but there is no changes between the inputted data and the existing data
                    // Assuming the users doesn't want to change the data for the selected customer and close the customerDetailPanel and enable back all the utility buttons function by updating jButton1's function to mode 1
                    else
                    {
                        // Update jButton1's function to mode 1, which is modify button to trigger modify function
                        updatejButton1(1);
                    }
                });
            }
            // When parameter "mode" is equal to 3
            case 3 -> 
            {
                // Change the text on jButton1 to "Confirm"
                jButton1.setText("Confirm");
                // Update the jButton1's pair, jButton2's function (The function pair (jButton1 / jButton2) is: Confirm / Cancel) (For adding data)
                updatejButton2(3);
                // Enable all text/input fields to allow users to key in data
                enableAllTextFields();
                // Disable all utility buttons function as currently adding new customer function is in progress
                disableAllUtilityButtons();
                
                // Adding new ActionListener to jButton1 (the function of jButton1)
                jButton1.addActionListener((ActionEvent e) -> 
                {
                    // Check if the datatype of the inputs in all input fields are valid
                    // If there exists data invalid, the boolean "true" will be returned
                    if (checkDataValid())
                        // If there exists data invalid, a popup window will be generated and notify the users regarding the possible reasons of causing data invalid
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to input the data until all data input is valid
                        JOptionPane.showConfirmDialog(this, "There exists input fields not filled or filled with blankspace or customer ID number or customer student ID(applicable to student type of customer only) is not integer! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Check if the customer ID number is used by other Customer object in the customer list
                    // If there exists Customer object is using the inputted customer ID number, the boolean "true" will be returned
                    else if (isCustomerIDNumberUsed(Integer.valueOf(customerIDNumberField.getText())))
                        // If there exists Customer object is using the inputted customer ID number, a popup window will be generated and notify the users to change the customer ID number used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the customer ID number until an unused customer ID number is inputted
                        JOptionPane.showConfirmDialog(this, "The customer ID number is used by other customer already! Please change the customer ID number!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid and customer ID number is valid
                    // Check if the customer username is used by other Customer object in the customer list
                    // If there exists Customer object is using the inputted customer username, the boolean "true" will be returned
                    else if (isCustomerUsernameUsed(customerUsernameField.getText()))
                        // If there exists Customer object is using the inputted customer username, a popup window will be generated and notify the users to change the customer username used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the customer username until an unused customer username is inputted
                        JOptionPane.showConfirmDialog(this, "The customer username is used by other customer already! Please change the customer username!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    else
                    {
                        // If all data is valid, a popup window will be generated and notify the users to confirm the inputted data is the one they want to used to add new customer to the system
                        // The users can select "Yes" to confirm the inputted data used to add new customer; "No" and close the window to return to input data until they input the desired data used to add the new customer to the system
                        // Variable used to store the option picked by users
                        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to add the new customer?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                        // If the users selected "Yes"
                        if (answer == JOptionPane.YES_OPTION)
                        {
                            // Check if the radio button for select "NORMAL" as customer type is selected
                            if (customerTypeNormal.isSelected())
                                // Adding new Customer object to the customer list by using the inputted data to initialize the added Customer object
                                GymManagementApp.customerList.add(new Customer(firstNameField.getText(), lastNameField.getText(), 
                                    addressField.getText(), phoneNumberField.getText(), emailField.getText(), 
                                    Integer.valueOf(customerIDNumberField.getText()), customerUsernameField.getText(), 
                                    customerPasswordField.getText()));
                            // If the radio button for select "STUDENT" as customer type is selected
                            else
                                // Adding new Student object to the customer list by using the inputted data to initialize the added Student object
                                GymManagementApp.customerList.add(new Student(firstNameField.getText(), lastNameField.getText(), 
                                    addressField.getText(), phoneNumberField.getText(), emailField.getText(), 
                                    Integer.valueOf(customerIDNumberField.getText()), customerUsernameField.getText(), 
                                    customerPasswordField.getText(), customerStudentIDField.getText(), instituteNameField.getText(),
                                    Integer.valueOf(expectedYearOfGraduationField.getText())));

                            // Update the data in the customer list/table as new customer data is added
                            updateTableRow();
                        }
                    }
                });
            }
        }
    }
    
    // This function is used to update the function of jButton2 based on the circumstances (Button with dynamic function)
    // This function will clear the current action performed by jButton2 and update the function of the button
    // There exists 3 functions/modes of the button:
    // 1) Button used to trigger action allows users to delete the data of the selected customer
    // 2) Button used to cancel the process of modifying data of the selected customer (void the process)
    // 3) Button used to cancel the process of adding new customer into the system (void the process)
    // **NOTE: This function is only called by updatejButton1() function to update jButton2's function**
    // **Function pairs (jButton1 / jButton2):
    // 1) Modify / Delete
    // 2) Confirm / Cancel (For modifying data)
    // 3) Confirm / Cancel (For adding new customer)
    // Parameter "mode" is used to determine the function to be updated
    // mode 1 -> update jButton2 to have function/mode 1
    // mode 2 -> update jButton2 to have function/mode 2
    // mode 3 -> update jButton2 to have function/mode 3
    private void updatejButton2(int mode)
    {
        // Enhanced for-loop to loop over current ActionListeners of jButton2
        // This loop is used to clear all the current ActionListeners of jButton2
        for (ActionListener actionListener : jButton2.getActionListeners()) 
        {
            // Remove the current ActionListener of jButton2 acquired from the loop
            jButton2.removeActionListener(actionListener);
        }
        
        // Switch to switch the function/ActionListener to be applied on jButton2
        switch(mode)
        {
            // When parameter "mode" is equal to 1 
            case 1 ->
            {
                // Change the text of jButton2 to "Delete"
                jButton2.setText("Delete");
                
                // Adding new ActionListener to jButton2 (the function of jButton2)
                jButton2.addActionListener((ActionEvent e) -> 
                {
                    // A popup window will be generated and notify the users to confirm they want to delete the selected customer from the system
                    // The users can select "Yes" to confirm the delete selected customer function; "No" and close the window to void the delete selected customer action
                    // Variable used to store the option picked by users
                    int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the customer from the system?\n(WARNING: This might also delete subscription related to the customer)", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
                    // Check if the user selected "Yes"
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        // Enhanced for-loop to loop over the customer list
                        for (Customer customer : GymManagementApp.customerList) 
                        {
                            // Check if there exists a customer in the system with the same ID with the selected customer's ID
                            if (customer.getID().equals(selectedCustomer.getID()))
                            {
                                // If there  exists a customer in the system with the same ID with the selected customer's ID
                                // Remove subscription related to the deleted customer object
                                GymManagementApp.deleteAllRelatedSubscription(customer);
                                // Remove the customer object from the list
                                GymManagementApp.customerList.remove(customer);
                                // Update the data in the cusomer list/table as a customer data is deleted
                                updateTableRow();
                                // Break the loop as there is no need to loop the customer list till the end
                                break;
                            }
                        }
                    }
                });
            }
            // When parameter "mode" is equal to 2
            case 2 ->
            {
                // Change the text of jButton2 to "Cancel"
                jButton2.setText("Cancel");
                
                // Adding new ActionListener to jButton1 (the function of jButton2)
                jButton2.addActionListener((ActionEvent e) -> 
                {
                    // A popup window will be generated and notify the users to confirm they want to cancel the modifying data process
                    // The users can select "Yes" to confirm to cancel the process; "No" and close the window to return users back to modifying data process
                    // Variable used to store the option picked by users
                    int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to discard the changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        
                    // Check if the user selected "Yes"
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        // Update the jButton2's pair, jButton1's function (The function pair (jButton1 / jButton2) is: Modify / Delete)
                        updatejButton1(1);
                        // Display the selected customer detail again to update the detail back to the existing data instead of the data modified during the process of modifying data
                        displaySelectedCustomerDetail();
                    }
                });
            }
            // When parameter "mode" is equal to 3
            case 3 ->
            {
                // Change the text of jButton2 to "Cancel"
                jButton2.setText("Cancel");
                
                // Adding new ActionListener to jButton1 (the function of jButton2)
                jButton2.addActionListener((ActionEvent e) -> 
                {
                    // A popup window will be generated and notify the users to confirm they want to cancel the adding new customer process
                    // The users can select "Yes" to confirm to cancel the process; "No" and close the window to return users back to adding new customer process
                    // Variable used to store the option picked by users
                    int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to discard the changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        
                    // Check if the users selected "Yes"
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        // Update the jButton2's pair, jButton1's function (The function pair (jButton1 / jButton2) is: Modify / Delete)
                        updatejButton1(1);
                        // Update the data in the customer list/table to get the most current customer list and hide the customer detail panel
                        updateTableRow();
                    }
                });
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerTypeButtonGroup = new javax.swing.ButtonGroup();
        customerDetailPanel = new javax.swing.JPanel();
        customerDetailFormPanel = new javax.swing.JPanel();
        customerDetailLabel = new javax.swing.JLabel();
        firstNameLabel = new javax.swing.JLabel();
        firstNameField = new javax.swing.JTextField();
        lastNameLabel = new javax.swing.JLabel();
        lastNameField = new javax.swing.JTextField();
        addressLabel = new javax.swing.JLabel();
        addressField = new javax.swing.JTextField();
        phoneNumberLabel = new javax.swing.JLabel();
        phoneNumberField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        customerIDNumberLabel = new javax.swing.JLabel();
        customerIDNumberField = new javax.swing.JTextField();
        customerIDLabel = new javax.swing.JLabel();
        customerIDField = new javax.swing.JTextField();
        customerUsernameLabel = new javax.swing.JLabel();
        customerUsernameField = new javax.swing.JTextField();
        customerPasswordLabel = new javax.swing.JLabel();
        customerPasswordField = new javax.swing.JTextField();
        customerTypeLabel = new javax.swing.JLabel();
        customerStudentIDLabel = new javax.swing.JLabel();
        customerStudentIDField = new javax.swing.JTextField();
        instituteNameLabel = new javax.swing.JLabel();
        instituteNameField = new javax.swing.JTextField();
        expectedYearOfGraduationField = new javax.swing.JTextField();
        expectedYearOfGraduationDLabel1 = new javax.swing.JLabel();
        customerTypeNormal = new javax.swing.JRadioButton();
        customerTypeStudent = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        customerListPanel = new javax.swing.JPanel();
        customerListLabel = new javax.swing.JLabel();
        customerTableScrollPane = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        addCustomerButton = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Manage Customers");
        setResizable(false);

        customerDetailFormPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        customerDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        customerDetailLabel.setText("<html><u>Customer Detail:</u>");

        firstNameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        firstNameLabel.setText("First Name:");

        firstNameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        firstNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        lastNameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        lastNameLabel.setText("Last Name:");

        lastNameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        lastNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        addressLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        addressLabel.setText("Address:");

        addressField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        addressField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        addressField.setMargin(new java.awt.Insets(0, 6, 2, 6));

        phoneNumberLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        phoneNumberLabel.setText("Phone Number:");

        phoneNumberField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        phoneNumberField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        emailLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        emailLabel.setText("Email Address:");

        emailField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        emailField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        customerIDNumberLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerIDNumberLabel.setText("Customer ID Number:");

        customerIDNumberField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerIDNumberField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        customerIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerIDLabel.setText("Customer ID:");

        customerIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        customerIDField.setEnabled(false);

        customerUsernameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerUsernameLabel.setText("Customer Username:");

        customerUsernameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerUsernameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        customerPasswordLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerPasswordLabel.setText("Customer Password:");

        customerPasswordField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerPasswordField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        customerTypeLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerTypeLabel.setText("Customer Type:");

        customerStudentIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerStudentIDLabel.setText("Customer Student ID:");

        customerStudentIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerStudentIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        instituteNameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        instituteNameLabel.setText("Institute Name:");

        instituteNameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        instituteNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        expectedYearOfGraduationField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        expectedYearOfGraduationField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        expectedYearOfGraduationDLabel1.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        expectedYearOfGraduationDLabel1.setText("Expected Year of Graduation:");

        customerTypeNormal.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerTypeNormal.setText("Normal");
        customerTypeNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerTypeNormalActionPerformed(evt);
            }
        });

        customerTypeStudent.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerTypeStudent.setText("Student");
        customerTypeStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerTypeStudentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customerDetailFormPanelLayout = new javax.swing.GroupLayout(customerDetailFormPanel);
        customerDetailFormPanel.setLayout(customerDetailFormPanelLayout);
        customerDetailFormPanelLayout.setHorizontalGroup(
            customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(firstNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastNameField))
                    .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(addressLabel)
                        .addGap(3, 3, 3)
                        .addComponent(addressField))
                    .addComponent(customerDetailLabel)
                    .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(emailLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailField))
                    .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(customerUsernameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerUsernameField))
                    .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(customerIDNumberLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerIDNumberField, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(phoneNumberLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phoneNumberField))
                    .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(customerPasswordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerPasswordField))
                    .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(customerTypeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerTypeNormal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerTypeStudent)
                        .addGap(24, 24, 24)
                        .addComponent(customerStudentIDLabel)
                        .addGap(5, 5, 5)
                        .addComponent(customerStudentIDField))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(instituteNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(instituteNameField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expectedYearOfGraduationDLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expectedYearOfGraduationField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        customerDetailFormPanelLayout.setVerticalGroup(
            customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerDetailFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameField)
                    .addComponent(lastNameField)
                    .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneNumberField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerIDNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerIDNumberField)
                    .addComponent(customerIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerIDField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerUsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerUsernameField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerTypeNormal)
                    .addComponent(customerTypeStudent)
                    .addComponent(customerStudentIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerStudentIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(customerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(instituteNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instituteNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(expectedYearOfGraduationDLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(expectedYearOfGraduationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton1.setText("jButton1");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton2.setText("jButton2");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout customerDetailPanelLayout = new javax.swing.GroupLayout(customerDetailPanel);
        customerDetailPanel.setLayout(customerDetailPanelLayout);
        customerDetailPanelLayout.setHorizontalGroup(
            customerDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(customerDetailFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerDetailPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(21, 21, 21))
        );
        customerDetailPanelLayout.setVerticalGroup(
            customerDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerDetailPanelLayout.createSequentialGroup()
                .addComponent(customerDetailFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        customerListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        customerListLabel.setText("<html><u>Customer List</u>");

        customerTable.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N
        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Phone", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        customerTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        customerTable.setInheritsPopupMenu(true);
        customerTable.setName(""); // NOI18N
        customerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerTableMouseClicked(evt);
            }
        });
        customerTableScrollPane.setViewportView(customerTable);
        if (customerTable.getColumnModel().getColumnCount() > 0) {
            customerTable.getColumnModel().getColumn(0).setResizable(false);
            customerTable.getColumnModel().getColumn(0).setPreferredWidth(1);
            customerTable.getColumnModel().getColumn(1).setResizable(false);
            customerTable.getColumnModel().getColumn(2).setResizable(false);
            customerTable.getColumnModel().getColumn(3).setResizable(false);
        }
        customerTable.getAccessibleContext().setAccessibleDescription("");

        addCustomerButton.setText("Add");
        addCustomerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addCustomerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addCustomerButtonMouseClicked(evt);
            }
        });

        returnButton.setText("<< Back to Menu");
        returnButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        returnButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
        returnButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                returnButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout customerListPanelLayout = new javax.swing.GroupLayout(customerListPanel);
        customerListPanel.setLayout(customerListPanelLayout);
        customerListPanelLayout.setHorizontalGroup(
            customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerListLabel)
                    .addGroup(customerListPanelLayout.createSequentialGroup()
                        .addComponent(returnButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addCustomerButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerListPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(customerTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        customerListPanelLayout.setVerticalGroup(
            customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCustomerButton)
                    .addComponent(returnButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(customerDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(customerDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerListPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // This function is called whenever the user click on the content in the customer list/table
    // This function is used to display the selected customer detail when the user select a row of data in the table/list
    private void customerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMouseClicked
        // Check if the table/list is enabled
        if (customerTable.isEnabled())
        {
            // Display the selected customer detail by using displaySelectedCustomerDetail() function
            displaySelectedCustomerDetail();
            // Update the function of jButton1 (dynamic button) to allow modify function after clicking by using updatejButton1(1) function
            updatejButton1(1);
        }
    }//GEN-LAST:event_customerTableMouseClicked

    // This function is called whenever the user click on the add customer button
    // This function is used to display a new form for user to input data for the new customer that is going to be added into the system
    private void addCustomerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addCustomerButtonMouseClicked
        // Display a new form used to allow users using the form to input new customer data and add the customer to the system
        displayNewCustomerForm();
        // Update the function of jButton1 (dynamic button) to allow submit/confirm/add customer function after clicking by using updatejButton1(3) function
        updatejButton1(3);
    }//GEN-LAST:event_addCustomerButtonMouseClicked

    // This function is called whenever the user click on the return to menu button
    // This function is used to close the manage customer window and return user to the menu
    private void returnButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnButtonMouseClicked
        // Create again the menu window
        if (StaffMainMenu.loggedInStaff != null)
            new StaffMainMenu();
        else 
            new AdminMainMenu();
        // Dispose the window when the return to menu button is clicked (**This action will close the manage customer window)
        this.dispose();
    }//GEN-LAST:event_returnButtonMouseClicked

    // This function is called whenever the user selects radio button for selecting "NORMAL" as type of customer in the form
    // This function is used to disable student type customer-related input fields to indicate those information is not required
    private void customerTypeNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerTypeNormalActionPerformed
        // Disable student type customer-related input fields
        disableStudentSectionFields();
    }//GEN-LAST:event_customerTypeNormalActionPerformed

    // This function is called whenever the user selects radio button for selecting "STUDENTS" as type of customer in the form
    // This function is used to enable student type customer-related input fields to indicate those information is required
    private void customerTypeStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerTypeStudentActionPerformed
        // Enable student type customer-related input fields
        enableStudentSectionFields();
        
        // Check whether the radio button for selecting "STUDENT" as type of customer is selected before clicking on the radio button
        if (!customerTypeStudent.isSelected())
        {
            // If the radio button for selecting "STUDENT" as type of customer is NOT selected before clicking on the radio button
            // Initialize all student type customer-related input fields with empty String
            customerStudentIDField.setText("");
            instituteNameField.setText("");
            expectedYearOfGraduationField.setText("");
        }
    }//GEN-LAST:event_customerTypeStudentActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCustomerButton;
    private javax.swing.JTextField addressField;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JPanel customerDetailFormPanel;
    private javax.swing.JLabel customerDetailLabel;
    private javax.swing.JPanel customerDetailPanel;
    private javax.swing.JTextField customerIDField;
    private javax.swing.JLabel customerIDLabel;
    private javax.swing.JTextField customerIDNumberField;
    private javax.swing.JLabel customerIDNumberLabel;
    private javax.swing.JLabel customerListLabel;
    private javax.swing.JPanel customerListPanel;
    private javax.swing.JTextField customerPasswordField;
    private javax.swing.JLabel customerPasswordLabel;
    private javax.swing.JTextField customerStudentIDField;
    private javax.swing.JLabel customerStudentIDLabel;
    private javax.swing.JTable customerTable;
    private javax.swing.JScrollPane customerTableScrollPane;
    private javax.swing.ButtonGroup customerTypeButtonGroup;
    private javax.swing.JLabel customerTypeLabel;
    private javax.swing.JRadioButton customerTypeNormal;
    private javax.swing.JRadioButton customerTypeStudent;
    private javax.swing.JTextField customerUsernameField;
    private javax.swing.JLabel customerUsernameLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel expectedYearOfGraduationDLabel1;
    private javax.swing.JTextField expectedYearOfGraduationField;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField instituteNameField;
    private javax.swing.JLabel instituteNameLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField phoneNumberField;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JButton returnButton;
    // End of variables declaration//GEN-END:variables
}