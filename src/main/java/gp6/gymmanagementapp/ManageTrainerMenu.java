/*
This class is used to generate the manage trainer menu used by gym dministrators
*/
package gp6.gymmanagementapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageTrainerMenu extends javax.swing.JFrame 
{
    // Variable used to store the current selected trainer based on the row selected in the trainer list
    private Trainer selectedTrainer;
    
    /**
     * Creates new form AdminManageTrainerMenu
     */
    public ManageTrainerMenu() 
    {
        initComponents();
        // Set the menu to be visible on the screen
        this.setVisible(true);
        
        // Used to update the content in the list/table of trainer
        updateTableRow();
    }
    
    // Function used to update the content in the list/tablee of trainer
    // Is used when:
    // 1) the menu is first initialized
    // 2) there is update in the data of a trainer
    // 3) when there is cancel function used when modifying or adding trainer
    private void updateTableRow()
    {
        // Hiding the trainerDetailPanel when there is no trainer selected from the trainer list
        trainerDetailPanel.setVisible(false);
        // Update the jButton1 into mode 1, which grants delete function for the user to delete the selected trainer
        updatejButton1(1);
        
        // Sorting the trainer list based on the trainer ID number of the trainer
        Collections.sort(GymManagementApp.trainerList, (Trainer trainer1, Trainer trainer2) -> trainer1.getTrainerID() - trainer2.getTrainerID());
        
        // Getting the model of table used to display the trainer list
        DefaultTableModel model = (DefaultTableModel) trainerTable.getModel();
        // Clear the content in the list/table
        model.setRowCount(0);
        
        // Variables used to store the data to be contained in the list/table which has 3 columns: ID, Name and Phone
        Object[] rowData = new Object[3];
        
        // Enhanced for-loop to loop the sorted trainer list
        for (Trainer trainer : GymManagementApp.trainerList) 
        {
            // Storing related data which are trainer ID, name and phone number
            rowData[0] = trainer.getID();
            rowData[1] = trainer.getFirstName() + " " + trainer.getLastName();
            rowData[2] = trainer.getPhone();
            
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
        // Check there is changes on the first name of the selected trainer before and after
        if (!selectedTrainer.getFirstName().equals(firstNameField.getText()))
            return true;
        // Check there is changes on the last name of the selected trainer before and after
        if (!selectedTrainer.getLastName().equals(lastNameField.getText()))
            return true;
        // Check there is changes on the address of the selected trainer before and after
        if (!selectedTrainer.getAddress().equals(addressField.getText()))
            return true;
        // Check there is changes on the phone number of the selected trainer before and after
        if (!selectedTrainer.getPhone().equals(phoneNumberField.getText()))
            return true;
        // Check there is changes on the email of the selected trainer before and after
        if (!selectedTrainer.getEmail().equals(emailField.getText()))
            return true;
        // Check there is changes on the trainer ID number of the selected trainer before and after
        if (selectedTrainer.getTrainerID() != Integer.valueOf(trainerIDNumberField.getText()))
            return true;
        // Check there is changes on the username of the selected trainer before and after
        if (!selectedTrainer.getTrainerUsername().equals(trainerUsernameField.getText()))
            return true;
        // Check there is changes on the password of the selected trainer before and after
        return !selectedTrainer.getTrainerPassword().equals(trainerPasswordField.getText());
    }
    
    // Function used to check whether the trainer ID number used to modify/add trainer is used already
    // The function returns false if the trainer ID number is not used yet
    // Is used when:
    // 1) modifying data
    // 2) adding new trainer
    private boolean isTrainerIDNumberUsed(int trainerIDNumber)
    {
        // Enhanced for-loop to loop over trainer list
        for (Trainer trainer : GymManagementApp.trainerList)
            // Check if the trainer ID number held by the Trainer object is same with the trainer ID number passed to the function
            if (trainer.getTrainerID() == trainerIDNumber)
                // returns true if the they are the same
                return true;
        // Returns false if cannot find any Trainer object is having the same trainer ID number with the trainer ID number passed to the function
        // Indicates the ID number can be used
        return false;
    }
    
    // Function used to check whether the trainer username used to modify/add trainer is used already
    // The function returns false if the trainer username is not used yet
    // Is used when:
    // 1) modifying data
    // 2) adding new trainer
    private boolean isTrainerUsernameUsed(String trainerUsername)
    {
        // Enhanced for-loop to loop over trainer list
        for (Trainer trainer : GymManagementApp.trainerList)
            // Check if the trainer username held by the Trainer object is same with the trainer username passed to the function
            if (trainer.getTrainerUsername().equals(trainerUsername))
                // returns true if the they are the same
                return true;
        // Returns false if cannot find any Trainer object is having the same trainer username with the trainer username passed to the function
        // Indicates the username can be used
        return false;
    }
    
    // Function used to check whether there exists an input field is not filled or filled with blankspace only
    // The function returns true if there exists at least a field is not filled or filled with blankspace only
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
        // Check if the field of trainer ID number field is filled
        if (trainerIDNumberField.getText().isBlank())
            return true;
        // Check if the field of trainer username is filled
        if (trainerUsernameField.getText().isBlank())
            return true;
        // Check if the field of trainer password is filled
        return trainerPasswordField.getText().isBlank();
    }
    
    // Function used to check whether the input in each input field is valid
    // The funciton returns true if there exists at least a field is invalid (including the field with no input or filled with blankspace)
    // Is used when:
    // 1) modifying data
    // 2) adding new trainer
    private boolean checkDataValid()
    {
        // Checking whether each input field is filled with content and not filled with blankspace only
        if (checkDataBlank())
            return true;
        else
            // Checking whether the input for trainerIDNumber input field is number
            return (!GymManagementApp.checkIsNumeric(trainerIDNumberField.getText()));
    }
    
    // Function used to display the trainer form when trying to add a new trainer to the system
    // Is used when:
    // 1) adding new trainer
    private void displayNewTrainerForm()
    {
        // Set the trainer form to be visible in the screen
        trainerDetailPanel.setVisible(true);
        
        // Initialize all available text fields with empty String
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        trainerIDNumberField.setText("");
        trainerIDField.setText("");
        trainerUsernameField.setText("");
        trainerPasswordField.setText("");
    }
    
    // This function is used to display the trainer detail panel and the trainer detail based on the selected trainer selected from the list/table
    // Is used when 
    // 1) the user select a data row from the trainer list/table
    private void displaySelectedTrainerDetail()
    {
        // Set the trainer form to be visible in the screen
        trainerDetailPanel.setVisible(true);
        
        // Variable used to store the row number of the data row selected from the list/table
        int rowSelected = trainerTable.getSelectedRow();
        // variable used to obtain the data in the first column of the row selected (which is trainer ID)
        String selectedTrainerID = (String)trainerTable.getValueAt(rowSelected, 0);
        
        // Assign the trainer by using getTrainer() function by using the trainer ID obtained from the list/table
        selectedTrainer = GymManagementApp.getTrainer(selectedTrainerID);
        
        // Check whether there is a trainer object returned
        if (selectedTrainer == null)
            // Exit the function if no trainer object reeturned
            return;
        
        // Disable all available text fields by using disableAllTextFields() function to disable user from modifying data before clicking on "Modify" button
        disableAllTextFields();
        
        // Assign the data to display the data in form format
        // Assign the first name of the selected trainer to the firstNameField
        firstNameField.setText(selectedTrainer.getFirstName());
        // Assign the last name of the selected trainer to the lastNameField
        lastNameField.setText(selectedTrainer.getLastName());
        // Assign the address of the selected trainer to the addressField
        addressField.setText(selectedTrainer.getAddress());
        // Assign the phone number of the selected trainer to the phoneNumberField
        phoneNumberField.setText(selectedTrainer.getPhone());
        // Assign the email of the selected trainer to the emailField
        emailField.setText(selectedTrainer.getEmail());
        // Assign the trainer ID number of the selected trainer to the trainerIDNumberField (**The field only accept String input, using Integer.toString() to convert integer number into a String)
        trainerIDNumberField.setText(Integer.toString(selectedTrainer.getTrainerID()));
        // Assign the trainer ID of the selected trainer to the trainerIDField
        trainerIDField.setText(selectedTrainer.getID());
        // Assign the username of the selected trainer to the trainerUsernameField
        trainerUsernameField.setText(selectedTrainer.getTrainerUsername());
        // Assign the password of the selected trainer to the trainerPasswordField
        trainerPasswordField.setText(selectedTrainer.getTrainerPassword());
    }
    
    // This function is used to enable all the input fields / text fields to allow users to modify or add new data
    // Is used when:
    // 1) modifying data
    // 2) add new trainer
    private void enableAllTextFields()
    {
        // Enable all the text/input fields, making all the text/input fields are editable by users
        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        addressField.setEnabled(true);
        phoneNumberField.setEnabled(true);
        emailField.setEnabled(true);
        trainerIDNumberField.setEnabled(true);
        trainerUsernameField.setEnabled(true);
        trainerPasswordField.setEnabled(true);
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
        trainerIDNumberField.setEnabled(false);
        trainerUsernameField.setEnabled(false);
        trainerPasswordField.setEnabled(false);
    }
    
    // This function is used to enable all utility buttons such as add button and return to menu button
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void enableAllUtilityButtons()
    {
        // Enable return to admin menu button
        returnButton.setEnabled(true);
        // Enable add trainer button
        addTrainerButton.setEnabled(true);
        // Enable table/list of trainer function (Allow select data row)
        trainerTable.setEnabled(true);
    }
    
    // This function is used to disable all utility buttons such as add button and return to menu button to avoid users accidentally close the window while still editing data
    // Is used when:
    // 1) modifying data
    // 2) add new trainer
    private void disableAllUtilityButtons()
    {
        // Disable return to admin menu button
        returnButton.setEnabled(false);
        // Disable add trainer button
        addTrainerButton.setEnabled(false);
        // Disable table/list of trainer function (Prevent select data row)
        trainerTable.setEnabled(false);
    }
    
    // This function is used to update the function of jButton1 based on the circumstances (Button with dynamic function)
    // This function will clear the current action performed by jButton1 and update the function of the button
    // There exists 3 functions/modes of the button:
    // 1) Button used to trigger action allows users modifying existing data of the trainer
    // 2) Button used to confirm the modified data and update the information of the trainer
    // 3) Button used to confirm the data to be used to add a new trainer to the system
    // **NOTE: This function will update jButton1's pair, which is jButton2 function**
    // **Function pairs (jButton1 / jButton2):
    // 1) Modify / Delete
    // 2) Confirm / Cancel (For modifying data)
    // 3) Confirm / Cancel (For adding new trainer)
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
                        JOptionPane.showConfirmDialog(this, "There exists input fields not filled or filled with blankspace or Trainer ID Number is not integer! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Check if the trainer ID number is used by other Trainer object in the trainer list except of the current selected trainer
                    // If there exists Trainer object other than the selected trainer is using the inputted trainer ID number, the boolean "true" will be returned
                    else if (isTrainerIDNumberUsed(Integer.valueOf(trainerIDNumberField.getText())) && selectedTrainer.getTrainerID() != Integer.valueOf(trainerIDNumberField.getText()))
                        // If there exists Trainer object other than the selected trainer is using the inputted trainer ID number, a popup window will be generated and notify the users to change the trainer ID number used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the trainer ID number until an unused trainer ID number or same trainer ID number with the current selected trainer is inputted
                        JOptionPane.showConfirmDialog(this, "The trainer ID number is used by other trainer already! Please change the trainer ID number!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid and the trainer ID number is valid
                    // Check if the trainer username is used by other Trainer object in the trainer list except of the current selected trainer
                    // If there exists Trainer object other than the selected trainer is using the inputted trainer username, the boolean "true" will be returned
                    else if (isTrainerUsernameUsed(trainerUsernameField.getText()) && !selectedTrainer.getTrainerUsername().equals(trainerUsernameField.getText()))
                        // If there exists Trainer object other than the selected trainer is using the inputted trainer username, a popup window will be generated and notify the users to change the trainer username used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the trainer username until an unused trainer username or same trainer username with the current selected trainer is inputted
                        JOptionPane.showConfirmDialog(this, "The trainer username is used by other trainer already! Please change the trainer username!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);                    
                    // If all the data is valid and both trainer ID number and username used are also valid
                    // Checking for whether there exists changes in the data input in all fields will be proceed
                    // If there exists changes in the data, the boolean "true" will be returned
                    else if (checkDataChanged())
                    {
                        // If there exists changes in the data, a popup window will be generated and notify the users to confirm the modified data/input is the one they want to used to change the data of the selected trainer
                        // The users can select "Yes" to confirm the modified data/input; "No" and close the window to return to modify the data until they input the desired data used to change the data of the selected trainer
                        // Variable used to store the option picked by users
                        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to make the changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                        // Check if the user is selecting "Yes"
                        if (answer == JOptionPane.YES_OPTION)
                        {
                            // Set the data for first name of the selected trainer to the inputted first name
                            selectedTrainer.setFirstName(firstNameField.getText());
                            // Set the data for last name of the selected trainer to the inputted last name
                            selectedTrainer.setLastName(lastNameField.getText());
                            // Set the data for address of the selected trainer to the inputted address
                            selectedTrainer.setAddress(addressField.getText());
                            // Set the data for phone number of the selected trainer to the inputted phone number
                            selectedTrainer.setPhone(phoneNumberField.getText());
                            // Set the data for email of the selected trainer to the inputted email
                            selectedTrainer.setEmail(emailField.getText());
                            // Set the data for trainer ID number of the selected trainer to the inputted trainer ID number
                            selectedTrainer.setTrainerID(Integer.valueOf(trainerIDNumberField.getText()));
                            // Set the data for trainer username of the selected trainer to the inputted trainer username
                            selectedTrainer.setTrainerUsername(trainerUsernameField.getText());
                            // Set the data for trainer password of the selected trainer to the inputted trainer password
                            selectedTrainer.setTrainerPassword(trainerPasswordField.getText());

                            // Assign the variable selectedTrainer to null as the action performed to change the data of current selected trainer is complete
                            selectedTrainer = null;
                            // Update the data in the trainer list/table as the data for the previous selected trainer is changed, update is needed to display the new data
                            updateTableRow();
                        }
                    }
                    // If the inputted data are valid but there is no changes between the inputted data and the existing data
                    // Assuming the users doesn't want to change the data for the selected trainer and close the trainerDetailPanel and enable back all the utility buttons function by updating jButton1's function to mode 1
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
                // Disable all utility buttons function as currently adding new trainer function is in progress
                disableAllUtilityButtons();
                
                // Adding new ActionListener to jButton1 (the function of jButton1)
                jButton1.addActionListener((ActionEvent e) -> 
                {
                    // Check if the datatype of the inputs in all input fields are valid
                    // If there exists data invalid, the boolean "true" will be returned
                    if (checkDataValid())
                        // If there exists data invalid, a popup window will be generated and notify the users regarding the possible reasons of causing data invalid
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to input the data until all data input is valid
                        JOptionPane.showConfirmDialog(this, "There exists input fields not filled or filled with blankspace or trainer ID number is not integer! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Check if the trainer ID number is used by other Trainer object in the trainer list
                    // If there exists Trainer object is using the inputted trainer ID number, the boolean "true" will be returned
                    else if (isTrainerIDNumberUsed(Integer.valueOf(trainerIDNumberField.getText())))
                        // If there exists Trainer object is using the inputted trainer ID number, a popup window will be generated and notify the users to change the trainer ID number used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the trainer ID number until an unused trainer ID number is inputted
                        JOptionPane.showConfirmDialog(this, "The trainer ID number is used by other trainer already! Please change the trainer ID number!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid and trainer ID number used is valid
                    // Check if the trainer username is used by other Trainer object in the trainer list
                    // If there exists Trainer object is using the inputted trainer username, the boolean "true" will be returned
                    else if (isTrainerUsernameUsed(trainerUsernameField.getText()))
                        // If there exists Trainer object is using the inputted trainer username, a popup window will be generated and notify the users to change the trainer username used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the trainer username until an unused trainer username is inputted
                        JOptionPane.showConfirmDialog(this, "The trainer username is used by other trainer already! Please change the trainer username!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    else
                    {
                        // If all data is valid, a popup window will be generated and notify the users to confirm the inputted data is the one they want to used to add new trainer to the system
                        // The users can select "Yes" to confirm the inputted data used to add new trainer; "No" and close the window to return to input data until they input the desired data used to add the new trainer to the system
                        // Variable used to store the option picked by users
                        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to add the new trainer?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                        // If the users selected "Yes"
                        if (answer == JOptionPane.YES_OPTION)
                        {
                            // Adding new Trainer object to the trainer list by using the inputted data to initialize the added Trainer object
                            GymManagementApp.trainerList.add(new Trainer(firstNameField.getText(), lastNameField.getText(), 
                                    addressField.getText(), phoneNumberField.getText(), emailField.getText(), 
                                    Integer.valueOf(trainerIDNumberField.getText()), trainerUsernameField.getText(), 
                                    trainerPasswordField.getText()));
                            
                            // Update the data in the trainer list/table as new trainer data is added
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
    // 1) Button used to trigger action allows users to delete the data of the selected trainer
    // 2) Button used to cancel the process of modifying data of the selected trainer (void the process)
    // 3) Button used to cancel the process of adding new trainer into the system (void the process)
    // **NOTE: This function is only called by updatejButton1() function to update jButton2's function**
    // **Function pairs (jButton1 / jButton2):
    // 1) Modify / Delete
    // 2) Confirm / Cancel (For modifying data)
    // 3) Confirm / Cancel (For adding new trainer)
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
                    // A popup window will be generated and notify the users to confirm they want to delete the selected trainer from the system
                    // The users can select "Yes" to confirm the delete selected trainer function; "No" and close the window to void the delete selected trainer action
                    // Variable used to store the option picked by users
                    int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the trainer from the system?\n(WARNING: This might also delete exercise plans/subscriptions related to the trainer)", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
                    // Check if the user selected "Yes"
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        // Enhanced for-loop to loop over the trainer list
                        for (Trainer trainer : GymManagementApp.trainerList) 
                        {
                            // Check if there exists a trainer in the system with the same ID with the selected trainer's ID
                            if (trainer.getID().equals(selectedTrainer.getID()))
                            {
                                // If there  exists a trainer in the system with the same ID with the selected trainer's ID
                                // Remove all exercise plans related to the deleted trainer and subscription related to the deleted exercise plans
                                GymManagementApp.deleteAllRelatedExercisePlan(trainer);
                                // Remove the trainer object from the list
                                GymManagementApp.trainerList.remove(trainer);
                                // Update the data in the trainer list/table as a trainer data is deleted
                                updateTableRow();
                                // Break the loop as there is no need to loop the trainer list till the end
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
                        // Display the selected trainer detail again to update the detail back to the existing data instead of the data modified during the process of modifying data
                        displaySelectedTrainerDetail();
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
                    // A popup window will be generated and notify the users to confirm they want to cancel the adding new trainer process
                    // The users can select "Yes" to confirm to cancel the process; "No" and close the window to return users back to adding new trainer process
                    // Variable used to store the option picked by users
                    int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to discard the changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        
                    // Check if the users selected "Yes" 
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        // Update the jButton2's pair, jButton1's function (The function pair (jButton1 / jButton2) is: Modify / Delete)
                        updatejButton1(1);
                        // Update the data in the trainer list/table to get the most current trainer list and hide the trainer detail panel
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

        trainerDetailPanel = new javax.swing.JPanel();
        trainerDetailFormPanel = new javax.swing.JPanel();
        trainerDetailLabel = new javax.swing.JLabel();
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
        trainerIDNumberLabel = new javax.swing.JLabel();
        trainerIDNumberField = new javax.swing.JTextField();
        trainerIDLabel = new javax.swing.JLabel();
        trainerIDField = new javax.swing.JTextField();
        trainerUsernameLabel = new javax.swing.JLabel();
        trainerUsernameField = new javax.swing.JTextField();
        trainerPasswordLabel = new javax.swing.JLabel();
        trainerPasswordField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        trainerListPanel = new javax.swing.JPanel();
        trainerListLabel = new javax.swing.JLabel();
        trainerTableScrollPane = new javax.swing.JScrollPane();
        trainerTable = new javax.swing.JTable();
        addTrainerButton = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Manage Trainers");
        setResizable(false);

        trainerDetailFormPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        trainerDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        trainerDetailLabel.setText("<html><u>Trainer Detail:</u>");

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

        trainerIDNumberLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerIDNumberLabel.setText("Trainer ID Number:");

        trainerIDNumberField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerIDNumberField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        trainerIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerIDLabel.setText("Trainer ID:");

        trainerIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        trainerIDField.setEnabled(false);

        trainerUsernameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerUsernameLabel.setText("Trainer Username:");

        trainerUsernameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerUsernameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        trainerPasswordLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerPasswordLabel.setText("Trainer Password:");

        trainerPasswordField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerPasswordField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout trainerDetailFormPanelLayout = new javax.swing.GroupLayout(trainerDetailFormPanel);
        trainerDetailFormPanel.setLayout(trainerDetailFormPanelLayout);
        trainerDetailFormPanelLayout.setHorizontalGroup(
            trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainerDetailFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, trainerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(firstNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastNameField))
                    .addGroup(trainerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(addressLabel)
                        .addGap(3, 3, 3)
                        .addComponent(addressField))
                    .addComponent(trainerDetailLabel)
                    .addGroup(trainerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(emailLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailField))
                    .addGroup(trainerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(phoneNumberLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phoneNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(trainerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(trainerIDNumberLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerIDNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(trainerIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerIDField))
                    .addGroup(trainerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(trainerUsernameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerUsernameField))
                    .addGroup(trainerDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(trainerPasswordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerPasswordField)))
                .addContainerGap())
        );
        trainerDetailFormPanelLayout.setVerticalGroup(
            trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainerDetailFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trainerDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameField)
                    .addComponent(lastNameField)
                    .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneNumberField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainerIDNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerIDNumberField)
                    .addComponent(trainerIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerIDField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainerUsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerUsernameField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trainerDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainerPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("jButton1");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton2.setText("jButton2");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout trainerDetailPanelLayout = new javax.swing.GroupLayout(trainerDetailPanel);
        trainerDetailPanel.setLayout(trainerDetailPanelLayout);
        trainerDetailPanelLayout.setHorizontalGroup(
            trainerDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainerDetailPanelLayout.createSequentialGroup()
                .addComponent(trainerDetailFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, trainerDetailPanelLayout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(11, 11, 11))
        );
        trainerDetailPanelLayout.setVerticalGroup(
            trainerDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainerDetailPanelLayout.createSequentialGroup()
                .addComponent(trainerDetailFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(trainerDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        trainerListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        trainerListLabel.setText("<html><u>Trainer List</u>");

        trainerTable.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N
        trainerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        trainerTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        trainerTable.setInheritsPopupMenu(true);
        trainerTable.setName(""); // NOI18N
        trainerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        trainerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        trainerTable.getTableHeader().setReorderingAllowed(false);
        trainerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trainerTableMouseClicked(evt);
            }
        });
        trainerTableScrollPane.setViewportView(trainerTable);
        if (trainerTable.getColumnModel().getColumnCount() > 0) {
            trainerTable.getColumnModel().getColumn(0).setResizable(false);
            trainerTable.getColumnModel().getColumn(0).setPreferredWidth(1);
            trainerTable.getColumnModel().getColumn(1).setResizable(false);
            trainerTable.getColumnModel().getColumn(2).setResizable(false);
        }
        trainerTable.getAccessibleContext().setAccessibleDescription("");

        addTrainerButton.setText("Add");
        addTrainerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addTrainerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTrainerButtonMouseClicked(evt);
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

        javax.swing.GroupLayout trainerListPanelLayout = new javax.swing.GroupLayout(trainerListPanel);
        trainerListPanel.setLayout(trainerListPanelLayout);
        trainerListPanelLayout.setHorizontalGroup(
            trainerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainerListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(trainerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(trainerListPanelLayout.createSequentialGroup()
                        .addComponent(trainerListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(trainerListPanelLayout.createSequentialGroup()
                        .addComponent(returnButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addTrainerButton)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, trainerListPanelLayout.createSequentialGroup()
                .addComponent(trainerTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        trainerListPanelLayout.setVerticalGroup(
            trainerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trainerListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trainerListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trainerTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trainerListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addTrainerButton)
                    .addComponent(returnButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trainerListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trainerDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(trainerDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    // This function is called whenever the user click on the content in the trainer list/table
    // This function is used to display the selected trainer detail when the user select a row of data in the table/list
    private void trainerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trainerTableMouseClicked
        // Check if the table/list is enabled
        if (trainerTable.isEnabled())
        {
            // Display the selected trainer detail by using displaySelectedTrainerDetail() function
            displaySelectedTrainerDetail();
            // Update the function of jButton1 (dynamic button) to allow modify function after clicking by using updatejButton1(1) function
            updatejButton1(1);
        }
    }//GEN-LAST:event_trainerTableMouseClicked
    
    // This function is called whenever the user click on the add trainer button
    // This function is used to display a new form for user to input data for the new trainer that is going to be added into the system
    // This function will disable all the utility buttons
    private void addTrainerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTrainerButtonMouseClicked
        // Display a new form used to allow users using the form to input new trainer data and add the trainer to the system
        displayNewTrainerForm();
        // Update the function of jButton1 (dynamic button) to allow submit/confirm/add trainer function after clicking by using updatejButton1(3) function
        updatejButton1(3);
    }//GEN-LAST:event_addTrainerButtonMouseClicked
    
    // This function is called whenever the user click on the return to menu button
    // This function is used to close the manage trainer window and return user to the menu
    private void returnButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnButtonMouseClicked
        // Create again the menu window
        if (StaffMainMenu.loggedInStaff != null)
            new StaffMainMenu();
        else 
            new AdminMainMenu();
        // Dispose the window when the return to menu button is clicked (**This action will close the manage trainer window)
        this.dispose();
    }//GEN-LAST:event_returnButtonMouseClicked
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTrainerButton;
    private javax.swing.JTextField addressField;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField phoneNumberField;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JButton returnButton;
    private javax.swing.JPanel trainerDetailFormPanel;
    private javax.swing.JLabel trainerDetailLabel;
    private javax.swing.JPanel trainerDetailPanel;
    private javax.swing.JTextField trainerIDField;
    private javax.swing.JLabel trainerIDLabel;
    private javax.swing.JTextField trainerIDNumberField;
    private javax.swing.JLabel trainerIDNumberLabel;
    private javax.swing.JLabel trainerListLabel;
    private javax.swing.JPanel trainerListPanel;
    private javax.swing.JTextField trainerPasswordField;
    private javax.swing.JLabel trainerPasswordLabel;
    private javax.swing.JTable trainerTable;
    private javax.swing.JScrollPane trainerTableScrollPane;
    private javax.swing.JTextField trainerUsernameField;
    private javax.swing.JLabel trainerUsernameLabel;
    // End of variables declaration//GEN-END:variables
}