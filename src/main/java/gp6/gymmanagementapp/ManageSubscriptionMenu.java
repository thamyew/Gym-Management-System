/*
This class is used to generate the manage subscription menu used by gym dministrators
*/
package gp6.gymmanagementapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageSubscriptionMenu extends javax.swing.JFrame 
{
    // Variable used to store the current selected subscription based on the row selected in the subscription list
    private Subscription selectedSubscription;
    // Variable used to store the current selected subscription in the selected subscription
    private ExercisePlan selectedExercisePlan;
    // Variable used to store the current selected customer in the selected subscription
    private Customer selectedCustomer;
    // Variable used to store the date String in startDateField, used for checking whether there is value changed in startDateField
    private String prevStartDate;
    
    /**
     * Creates new form AdminManageSubscriptionMenu
     */
    public ManageSubscriptionMenu() 
    {
        initComponents();
        // Set the menu to be visible on the screen
        this.setVisible(true);
        // Used to update the content in the list/table of subscription list
        updateTableRow();
        // Used to update the content in the list/table of customer
        updateCustomerList();
    }
    
    // Function used to update the content in the list/table of subscription
    // Is used when:
    // 1) the menu is first initialized
    // 2) there is update in the data of a subscription
    // 3) when there is cancel function used when modifying or adding subscription
    private void updateTableRow()
    {
        // Hiding the subscriptionDetailPanel when there is no subscription selected from the subscription list
        subscriptionDetailPanel.setVisible(false);
        // Update the jButton1 into mode 1, which grants delete function for the user to delete the selected subscription
        updatejButton1(1);
        
        // Sorting the subscription list based on the subscription ID number of the subscription
        Collections.sort(GymManagementApp.subscriptionList, (Subscription subscription1, Subscription subscription2) -> subscription1.getSubscriptionID() - subscription2.getSubscriptionID());
        
        // Getting the model of table used to display the subscription list
        DefaultTableModel model = (DefaultTableModel) subscriptionTable.getModel();
        // Clear the content in the list/table
        model.setRowCount(0);
        
        // Variables used to store the data to be contained in the list/table which has 5 columns: subscription ID, subscribed subscription ID, subscription start date and end date, ID of customer subscribed to the plan
        Object[] rowData = new Object[5];
        
        // Enhanced for-loop to loop the sorted subscription list
        for (Subscription subscription : GymManagementApp.subscriptionList) 
        {
            // Storing related data which are subscription ID, subscribed subscription ID, subscription start date and end date, ID of customer subscribed to the plan
            rowData[0] = subscription.getSubscriptionID();
            rowData[1] = subscription.getExercisePlan().getPlanID();
            rowData[2] = subscription.getDatePurchased().toString();
            rowData[3] = subscription.getDateEnd().toString();
            rowData[4] = subscription.getCustomer().getID();
            
            // Adding the data into the list/table
            model.addRow(rowData);
        }
    }
    
    // Function used to update the content in the list/table of customer to be selected by user
    // Is used when:
    // 1) the menu is first initialized
    private void updateCustomerList()
    {
        // Sorting the customer list based on the customer ID number of the customer
        Collections.sort(GymManagementApp.customerList, (Customer customer1, Customer customer2) -> customer1.getCustomerID() - customer2.getCustomerID());
        
        // Getting the model of table used to display the customer list
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        // Clear the content in the list/table
        model.setRowCount(0);
        
        // Variables used to store the data to be contained in the list/table which has 3 columns: customer ID, name and phone number
        Object[] rowData = new Object[3];
        
        // Enhanced for-loop to loop the sorted customer list
        for (Customer customer : GymManagementApp.customerList) 
        {
            // Storing related data which are customer ID, name and phone number
            rowData[0] = customer.getID();
            rowData[1] = customer.getFirstName() + " " + customer.getLastName();
            rowData[2] = customer.getTypeOfCustomer();
            
            // Adding the data into the list/table
            model.addRow(rowData);
        }
    }
    
    // This function is used to check whether the exercise plan is not used by other subscriptions during the same date period
    // This function returns true when there is date clashed
    // Is used when (included in updateExercisePlanList()):
    // 1) add new subscription (when select exercise plan button is clicked)
    private boolean checkDateAvailable(LocalDate startDate, LocalDate endDate, Subscription subscription)
    {        
        // Check if either start date is in between the checked subscription's start date(inclusive) and end date(inclusive) or end date is in between the checked subscription's start date(inclusive) and end date(inclusive)
        // NOTES: **This checking does not cover the situation where the checked subscription's duration is in between the start date and end date, therefore, other checking should be used if this checked condition returns false
        if (((startDate.isAfter(subscription.getDatePurchased()) || startDate.isEqual(subscription.getDatePurchased())) && (startDate.isBefore(subscription.getDateEnd())) || startDate.isEqual(subscription.getDateEnd()))
                || ((endDate.isAfter(subscription.getDatePurchased()) || endDate.isEqual(subscription.getDatePurchased())) && (endDate.isBefore(subscription.getDateEnd())) || endDate.isEqual(subscription.getDateEnd())))
        {
            return true;    // Returns true to indicate date clashed
        }
        else
        {
            // Check if start date is before the subscription start date and end date is after the subscription end date
            if (startDate.isBefore(subscription.getDatePurchased()) && endDate.isAfter(subscription.getDateEnd()))
                return true;        // Returns true to indicate date clashed
        }
        
        // return false to indicate no date clashed
        return false;
    }
    
    // Function used to update the content in the list/table of subscription to be selected by user
    // Is used when:
    // 1) whenever user click on select exercise plan button
    private boolean updateExercisePlanList()
    {
        // Array List used to store available exercise plan based on current start date and end date inputted
        ArrayList<ExercisePlan> tempExercisePlanList = new ArrayList();
        
        // Enhanced for-loop to loop over exercise plan list
        for (ExercisePlan exercisePlan : GymManagementApp.exercisePlanList)
        {
            // Temporary boolean used to determine whether the exercise plan should be made available during the checked date periodd
            boolean shouldAdd = true;
            
            // Enhanced for-loop to loop over subscription list
            for (Subscription subscription : GymManagementApp.subscriptionList)
            {
                // Check if the subscription has the same exercise plan with the current looped exercise plan
                if (subscription.getExercisePlan().getPlanID() == exercisePlan.getPlanID())
                {
                    // If is the same, check whether there is date clashedd between the new date period with the existing subscription one
                    if (checkDateAvailable(LocalDate.parse(startDateField.getText()), LocalDate.parse(endDateField.getText()), subscription))
                    {
                        // If there is date clashed, assign shouldAdd variable to false and break the loop of subscription
                        shouldAdd = false;
                        break;
                    }
                }
            }
            
            // If after lopping all the subscriptino, the exercise plan is available during the date period
            if (shouldAdd)
                // Add the exercise plan to the exercise plan list used to select by user
                tempExercisePlanList.add(exercisePlan);
        }
        
        // Check if the temp exercise plan list is empty or not
        if (tempExercisePlanList.isEmpty())
            // Return false if it is empty
            return false;
        
        // Sorting the temporary exercise plan list based on the exercise plan ID number of the exercise plan
        Collections.sort(tempExercisePlanList, (ExercisePlan exercisePlan1, ExercisePlan exercisePlan2) -> exercisePlan1.getPlanID() - exercisePlan2.getPlanID());
        
        // Getting the model of table used to display the exercise plan list
        DefaultTableModel model = (DefaultTableModel) exercisePlanTable.getModel();
        // Clear the content in the list/table
        model.setRowCount(0);
        
        // Variables used to store the data to be contained in the list/table which has 4 columns: exercise plan ID, exercise plan description, its trainer name and equipment used
        Object[] rowData = new Object[4];
        
        // Enhanced for-loop to loop the sorted temporary exercise plan list
        for (ExercisePlan exercisePlan : tempExercisePlanList) 
        {
            // Storing related data which are exercise plan ID, exercise plan description, its trainer name and equipment used
            rowData[0] = exercisePlan.getPlanID();
            rowData[1] = exercisePlan.getPlanDescription();
            rowData[2] = exercisePlan.getTrainer().getFirstName() + " " + exercisePlan.getTrainer().getLastName();
            rowData[3] = exercisePlan.getEquipment().getEquipmentName();
            
            // Adding the data into the list/table
            model.addRow(rowData);
        }
        
        // Return true as there exists exercise plan available during selected period
        return true;
    }
    
    // Function used to check whether data inputted to start date field is in correct date format
    // Is used when:
    // 1) Whenever after user using the startDateField
    private boolean checkDateFormatValid()
    {
        // Try parsing the string into LocalDate
        try
        {
            LocalDate.parse(startDateField.getText());
        }
        catch (DateTimeParseException e)
        {
            // If there is error, will return false to the main function
            return false;
        }
        // If there is no erro in parsing string into LocalDate, return true
        return true;
    }
    
    // Function used to check whether the date inputted is before current system's date
    private boolean checkStartDateValid()
    {
        // Check if startDateField date is before the current system's date, returns true if it is before the date
        return (LocalDate.parse(startDateField.getText()).isBefore(LocalDate.now()));
    }
    
    // Function used to check whether there is changes betweeen the current inputs and existing data
    // The function returns true if there exists at least a change
    // Is used when:
    // 1) modifying data
    // NOTE: **Trainer part is excluded as trainer shouldn't be able to change directly from this menu, only delete the subscription is allowed
    private boolean checkDataChanged()
    {
        // Check there is changes on the plan ID of the selected subscription before and after
        return (Integer.valueOf(subscriptionIDField.getText()) != selectedSubscription.getSubscriptionID());
    }
    
    // Function used to check whether the subscription ID number used to modify/add subscription is used already
    // The function returns false if the subscription ID number is not used yet
    // Is used when:
    // 1) modifying data
    // 2) adding new subscription
    private boolean isSubscriptionIDNumberUsed(int subscriptionIDNumber)
    {
        // Enhanced for-loop to loop over subscription list
        for (Subscription subscription : GymManagementApp.subscriptionList)
            // Check if the subscription ID number held by the Subscription object is same with the subscription ID number passed to the function
            if (subscription.getSubscriptionID() == subscriptionIDNumber)
                // returns true if the they are the same
                return true;
        // Returns false if cannot find any Subscription object is having the same subscription ID number with the subscription ID number passed to the function
        // Indicates the ID number can be used
        return false;
    }

    // Function used to check whether there exists an input field is not filled or filled with blankspace only
    // The function returns true if there exists at least a field is not filled or filled with blankspace only
    // Is used when:
    // 1) included in checkDataValid() function
    private boolean checkDataBlank()
    {
        // Check if the field of subscription ID is filled
        if (subscriptionIDField.getText().isBlank())
            return true;
        // Check if the field of total payment is filled
        if (totalPaymentField.getText().isBlank())
            return true;
        // Check if the field of start date is filled
        if (startDateField.getText().isBlank())
            return true;
        // Check if the field of customer ID is filled
        if (customerIDField.getText().isBlank())
            return true;
        // Check if the field of subscription ID is filled
        return (exercisePlanIDField.getText().isBlank());
    }
    
    // Function used to check whether the input in each input field is valid
    // The funciton returns true if there exists at least a field is invalid (including the field with no input or filled with blankspace)
    // Is used when:
    // 1) modifying data
    // 2) adding new subscription
    private boolean checkDataIsNumber()
    {
        // Check if data fields have blank
        if (checkDataBlank())
            return true;
        // Checking whether the input for subscriptionID input field is number
        return (!GymManagementApp.checkIsNumeric(subscriptionIDField.getText()));
    }
    
    // Function used to auto update the end date based on the subscription duration and start date inputted
    // Is used when:
    // 1) adding new subscription
    private void updateEndDate()
    {
        // Update selected exercise plan to null and update detail section if current selected exercise plan is not null
        if (selectedExercisePlan != null)
        {
            selectedExercisePlan = null;
            updateExercisePlanAndTrainerFields();
        }
        // Update the endDateField
        endDateField.setText(LocalDate.parse(startDateField.getText()).plusMonths(Long.valueOf(subscriptionDurationSelection.getValue().toString())).toString());
    }
    
    // Function used to auto update the total amount paid based on the subscription plan price and duration of the subscription
    // Is used when:
    // 1) When there is changes on exercise plan and customer (NOTES: Only can be happened when both exercise plan and customer are selected)
    private void updateTotalPaymentField()
    {
        // Check if selected customer and selected exercise plan is not null
        if (selectedCustomer != null && selectedExercisePlan != null)
        {
            // Temporary variable used to store the total payment calculated in String datatype
            float totalPayment = selectedExercisePlan.getPrice(selectedCustomer) * Float.valueOf(subscriptionDurationSelection.getValue().toString());
            // Update the total payment field with the total payment calculated
            totalPaymentField.setText(String.format("%.2f", totalPayment));
        }
    }
    
    // Function used to display the subscription form when trying to add a new subscription to the system
    // Is used when:
    // 1) adding new subscription
    private void displayNewSubscriptionForm()
    {
        // Set the subscription form to be visible in the screen
        subscriptionDetailPanel.setVisible(true);
        
        // Initialize all available text fields with empty String
        subscriptionIDField.setText("");
        totalPaymentField.setText("");
        subscriptionDurationSelection.setValue(1);
        startDateField.setText(LocalDate.now().toString());
        updateEndDate();
        exercisePlanIDField.setText("");
        priceField.setText("");
        descriptionField.setText("");
        startHourSelection.setValue(8);
        startMinuteSelection.setValue(0);
        endHourSelection.setValue(9);
        endMinuteSelection.setValue(0);
        trainerIDField.setText("");
        trainerNameField.setText("");
        trainerPhoneField.setText("");
        trainerEmailField.setText("");
        customerIDField.setText("");
        customerNameField.setText("");
        customerPhoneField.setText("");
        customerEmailField.setText("");
        
        // Initialize all checkboxes for exercise day to be not selected
        mondayCheck.setSelected(false);
        tuesdayCheck.setSelected(false);
        wednesdayCheck.setSelected(false);
        thursdayCheck.setSelected(false);
        fridayCheck.setSelected(false);
        saturdayCheck.setSelected(false);
        sundayCheck.setSelected(false);
        
        // Set the selectedSubscription to null as currently adding new subscription
        selectedSubscription = null;
        // Set the selectedExercisePlan to null as new subscription does not have an subscription yet
        selectedExercisePlan = null;
        // Set the selectedCustomer to null as new subscription does not have a customer yet
        selectedCustomer = null;
    }
    
    // This function is used to display the subscription detail panel and the subscription detail based on the selected subscription selected from the list/table
    // Is used when 
    // 1) the user select a data row from the subscription list/table
    private void displaySelectedSubscriptionDetail()
    {
        // Set the subscription form to be visible in the screen
        subscriptionDetailPanel.setVisible(true);
        
        // Variable used to store the row number of the data row selected from the list/table
        int rowSelected = subscriptionTable.getSelectedRow();
        // variable used to obtain the data in the first column of the row selected (which is subscription ID)
        int selectedSubscriptionID = (int)subscriptionTable.getValueAt(rowSelected, 0);
        
        // Assign the subscription by using getSubscription() function by using the subscription ID obtained from the list/table
        selectedSubscription = GymManagementApp.getSubscription(selectedSubscriptionID);
        
        // Check whether there is a Subscription object returned
        if (selectedSubscription == null)
            // Exit the function if no Subscription object returned
            return;
        
        // Disable all available text fields by using disableAllTextFields() function to disable user from modifying data before clicking on "Modify" button
        disableAllTextFields();
        
        // Assign the data to display the data in form format
        // Assign the subscription ID of the selected subscription to the subscriptionIDField
        subscriptionIDField.setText(String.valueOf(selectedSubscription.getSubscriptionID()));
        // Assign the start date of the selected subscription to the startDateField
        startDateField.setText(selectedSubscription.getDatePurchased().toString());
        // Assign the end date of the selected subscription to the endDateField
        endDateField.setText(selectedSubscription.getDateEnd().toString());
        
        // Get the time between start date and end date of the subscription
        Period timeBetween = selectedSubscription.getDatePurchased().until(selectedSubscription.getDateEnd());
        // Assign the subscription duration based on the start date and end date of the selected subscription to the subscriptionDurationSelection
        subscriptionDurationSelection.setValue(timeBetween.getMonths());
        
        // Update the object of selectedCustomer based on the customer data stored in the subscription
        selectedCustomer = selectedSubscription.getCustomer();
        // Assign the data to customer-related fields
        updateCustomerFields();
        
        // Update the object of selectedExercisePlan based on the subscription data stored in the subscription
        selectedExercisePlan = selectedSubscription.getExercisePlan();
        // Assign the data to subscription and trainer-related fields
        updateExercisePlanAndTrainerFields();
        
        // Assign the total payment of the selected subscription to the totalPaymentField
        totalPaymentField.setText(String.valueOf(selectedSubscription.getTotalAmountPaid()));
    }
    
    // This function is used to enable all the input fields / text fields to allow users to modify or add new data
    // Is used when:
    // 1) modifying data
    // 2) add new subscription
    private void enableAllTextFields()
    {
        // Enable all the text/input fields, making all the text/input fields are editable by users
        subscriptionIDField.setEnabled(true);
    }
    
    // This function is used to disable all the input fields / text fields to prevent users from modifying the data
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void disableAllTextFields()
    {
        // Disable all the text/input fields and buttons, making all the text/input fields are NOT editable by users
        subscriptionIDField.setEnabled(false);
        subscriptionDurationSelection.setEnabled(false);
        startDateField.setEnabled(false);
        selectCustomerButton.setEnabled(false);
        selectExercisePlanButton.setEnabled(false);
    }

    // This function is used to enable all utility buttons such as add button and return to menu button
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void enableAllUtilityButtons()
    {
        // Enable return to menu button
        returnButton.setEnabled(true);
        // Enable add subscription button
        addSubscriptionButton.setEnabled(true);
        // Enable table/list of subscription function (Allow select data row)
        subscriptionTable.setEnabled(true);
    }
    
    // This function is used to disable all utility buttons such as add button and return to menu button to avoid users accidentally close the window while still editing data
    // Is used when:
    // 1) modifying data
    // 2) add new subscription
    private void disableAllUtilityButtons()
    {
        // Disable return to menu button
        returnButton.setEnabled(false);
        // Disable add subscription button
        addSubscriptionButton.setEnabled(false);
        // Disable table/list of subscription function (Prevent select data row)
        subscriptionTable.setEnabled(false);
    }
    
    // This function is used to update the customer details in the subscription detail panel
    // Is used when:
    // 1) select subscription from the table (update the customer details based on the information stored in the subscription)
    // 2) modifying data (select new customer from the select customer window)
    // 3) adding new subscription (select new customer from the select customer window)
    private void updateCustomerFields()
    {
        // Update the customerIDField based on the selectedCustomer's information
        customerIDField.setText(selectedCustomer.getID());
        // Update the customerNameField based on the selectedCustomer's information
        customerNameField.setText(selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
        // Update the customerPhoneField based on the selectedCustomer's information
        customerPhoneField.setText(selectedCustomer.getPhone());
        // Update the customerEmailField based on the selectedCustomer's information
        customerEmailField.setText(selectedCustomer.getEmail());
    }
    
    // This function is used to update the subscription and its trainer details in the detail panel
    // Is used when:
    // 1) select subscription from the table (update the subscription and trainer details based on the information stored in the subscription)
    // 2) adding new subscription (whenever choosing different subscription from the select subscription window)
    private void updateExercisePlanAndTrainerFields()
    {
        if (selectedExercisePlan != null)
        {
            // Update the exercisePlanIDField based on the selected subscription's information
            exercisePlanIDField.setText(String.valueOf(selectedExercisePlan.getPlanID()));
            // Update the priceField based on the selected subscription's information based on the customer type
            priceField.setText(String.format("%.2f", selectedExercisePlan.getPrice(selectedCustomer)));
            // Update the descriptionField based on the selected subscription's information
            descriptionField.setText(selectedExercisePlan.getPlanDescription());

            // Initialize all checkboxes for exercise day to be not selected
            mondayCheck.setSelected(false);
            tuesdayCheck.setSelected(false);
            wednesdayCheck.setSelected(false);
            thursdayCheck.setSelected(false);
            fridayCheck.setSelected(false);
            saturdayCheck.setSelected(false);
            sundayCheck.setSelected(false);

            // Checking checkboxes for each day according to the exercise day recorded in the selected subscription
            for (int day : selectedExercisePlan.getExerciseDay())
            {
                // Used for determine which checking should be performed
                switch(day)
                {
                    // When there exist 1 (Monday is exercise day), set mondayCheck to true (Checkbox for Monday is checked)
                    case 1 -> mondayCheck.setSelected(true);
                    // When there exist 2 (Tuesday is exercise day), set tuesdayCheck to true (Checkbox for Tuesday is checked)
                    case 2 -> tuesdayCheck.setSelected(true);
                    // When there exist 3 (Wednesday is exercise day), set wednesdayCheck to true (Checkbox for Wednesday is checked)
                    case 3 -> wednesdayCheck.setSelected(true);
                    // When there exist 4 (Thursday is exercise day), set thursdayCheck to true (Checkbox for Thursday is checked)
                    case 4 -> thursdayCheck.setSelected(true);
                    // When there exist 5 (Friday is exercise day), set fridayCheck to true (Checkbox for Friday is checked)
                    case 5 -> fridayCheck.setSelected(true);
                    // When there exist 6 (Saturday is exercise day), set saturdayCheck to true (Checkbox for Saturday is checked)
                    case 6 -> saturdayCheck.setSelected(true);
                    // When there exist 7 (Sunday is exercise day), set sundayCheck to true (Checkbox for Sunday is checked)
                    case 7 -> sundayCheck.setSelected(true);
                }
            }
            // Update the start time fields based on the selected subscription's information
            startHourSelection.setValue(selectedExercisePlan.getStartTime().getHour());
            startMinuteSelection.setValue(selectedExercisePlan.getStartTime().getMinute());
            // Update the end time fields based on the selected subscription's information
            endHourSelection.setValue(selectedExercisePlan.getEndTime().getHour());
            endMinuteSelection.setValue(selectedExercisePlan.getEndTime().getMinute());

            // Update the trainerIDField based on the selected subscription's information
            trainerIDField.setText(selectedExercisePlan.getTrainer().getID());
            // Update the trainerNameField based on the subscription's information
            trainerNameField.setText(selectedExercisePlan.getTrainer().getFirstName() + " " + selectedExercisePlan.getTrainer().getLastName());
            // Update the trainerPhoneField based on the subscription's information
            trainerPhoneField.setText(selectedExercisePlan.getTrainer().getPhone());
            // Update the trainerEmailField based on the subscription's information
            trainerEmailField.setText(selectedExercisePlan.getTrainer().getEmail());
        }
        else
        {
            // Initialize the exercise plan to empty
            exercisePlanIDField.setText("");
            priceField.setText("");
            descriptionField.setText("");

            // Initialize all checkboxes for exercise day to be not selected
            mondayCheck.setSelected(false);
            tuesdayCheck.setSelected(false);
            wednesdayCheck.setSelected(false);
            thursdayCheck.setSelected(false);
            fridayCheck.setSelected(false);
            saturdayCheck.setSelected(false);
            sundayCheck.setSelected(false);

            startHourSelection.setValue(8);
            startMinuteSelection.setValue(0);
            endHourSelection.setValue(9);
            endMinuteSelection.setValue(0);

            trainerIDField.setText("");
            trainerNameField.setText("");
            trainerPhoneField.setText("");
            trainerEmailField.setText("");
        }
    }
    
    // This function is used to update the function of jButton1 based on the circumstances (Button with dynamic function)
    // This function will clear the current action performed by jButton1 and update the function of the button
    // There exists 3 functions/modes of the button:
    // 1) Button used to trigger action allows users modifying existing data of the subscription
    // 2) Button used to confirm the modified data and update the information of the subscription
    // 3) Button used to confirm the data to be used to add a new subscription to the system
    // **NOTE: This function will update jButton1's pair, which is jButton2 function**
    // **Function pairs (jButton1 / jButton2):
    // 1) Modify / Delete
    // 2) Confirm / Cancel (For modifying data)
    // 3) Confirm / Cancel (For adding new subscription)
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
                    // Check if there is blank in the input fields
                    // If there exists input fileds are blank, the boolean "true" will be returned
                    if (checkDataBlank())
                        // If there exists input field is blank, a popup window will be generated and notify the users
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the data until all data input is filled
                        JOptionPane.showConfirmDialog(this, "There exists input fields not filled or filled with blankspace! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // Check if there is datatype invalid in certain input fields
                    // If there exists data invalid, the boolean "true" will be returned
                    else if (checkDataIsNumber())
                        // If there exists data invalid, a popup window will be generated and notify the users regarding the possible reasons of causing data invalid
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the data until all data input is valid
                        JOptionPane.showConfirmDialog(this, "Subscription ID should be in number! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Check if the subcsription ID number is used by other Subscription object in the subscription list except of the current selected subscription
                    // If there exists Subscription object other than the selected subscription is using the inputted subscription ID number, the boolean "true" will be returned
                    else if (isSubscriptionIDNumberUsed(Integer.valueOf(subscriptionIDField.getText())) && selectedSubscription.getSubscriptionID() != Integer.valueOf(subscriptionIDField.getText()))
                        // If there exists Subscription object other than the selected subscription is using the inputted subscription ID number, a popup window will be generated and notify the users to change the subscription ID number used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the subscription ID number until an unused subscription ID number or same subscription ID number with the current selected subscription is inputted
                        JOptionPane.showConfirmDialog(this, "The subscription ID number is used by other subscription already! Please change the subscription ID number!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid and subscription ID used are also valid
                    // Checking for whether there exists changes in the data input in all fields will be proceed
                    // If there exists changes in the data, the boolean "true" will be returned
                    else if (checkDataChanged())
                    {
                        // If there exists changes in the data, a popup window will be generated and notify the users to confirm the modified data/input is the one they want to used to change the data of the selected subscription
                        // The users can select "Yes" to confirm the modified data/input; "No" and close the window to return to modify the data until they input the desired data used to change the data of the selected subscription
                        // Variable used to store the option picked by users
                        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to make the changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                        // Check if the user is selecting "Yes"
                        if (answer == JOptionPane.YES_OPTION)
                        {
                            // Set the data for subscription ID of the selected subscription to the inputted plan ID
                            selectedSubscription.setSubscriptionID(Integer.valueOf(subscriptionIDField.getText()));
                            
                            // Assign the variable selectedSubscription, selectedExercisePlan and selectedCustomer to null as the action performed to change the data of current selected subscription is complete
                            selectedSubscription = null;
                            selectedExercisePlan = null;
                            selectedCustomer = null;
                            
                            // Update the data in the subscription list/table as the data for the previous selected subscription is changed, update is needed to display the new data
                            updateTableRow();
                        }
                    }
                    // If the inputted data are valid but there is no changes between the inputted data and the existing data
                    // Assuming the users doesn't want to change the data for the selected subscription and close the subscriptionDetailPanel and enable back all the utility buttons function by updating jButton1's function to mode 1
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
                subscriptionDurationSelection.setEnabled(true);
                startDateField.setEnabled(true);
                // Enable select customer button to select the customer of the subscription
                selectCustomerButton.setEnabled(true);
                // Enable select exercise plan button to select the exercise plan to subscribe
                selectExercisePlanButton.setEnabled(true);
                // Disable all utility buttons function as currently adding new subscription function is in progress
                disableAllUtilityButtons();
                
                // Adding new ActionListener to jButton1 (the function of jButton1)
                jButton1.addActionListener((ActionEvent e) -> 
                {
                    // Check if all the input fields are filled
                    // If there exists input fields not filled, the boolean "true" will be returned
                    if (checkDataBlank())
                        // If there exists input fields not filled, a popup window will be generated and notify the users
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to input the data until all input fields are filled
                        JOptionPane.showConfirmDialog(this, "There exists input fields not filled or filled with blankspace! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // Check if there is datatype invalid in certain input fields
                    // If there exists data invalid, the boolean "true" will be returned
                    else if (checkDataIsNumber())
                        // If there exists data invalid, a popup window will be generated and notify the users regarding the possible reasons of causing data invalid
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the data until all data input is valid
                        JOptionPane.showConfirmDialog(this, "Ssubscription ID should be in number! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Check if the subscription ID number is used by other Subscription object in the subscription list
                    // If there exists Subscription object is using the inputted subscription ID number, the boolean "true" will be returned
                    else if (isSubscriptionIDNumberUsed(Integer.valueOf(subscriptionIDField.getText())))
                        // If there exists Subscription object is using the inputted subscription ID number, a popup window will be generated and notify the users to change the subscription ID number used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the subscription ID number until an unused subscription ID number is inputted
                        JOptionPane.showConfirmDialog(this, "The subscription ID number is used by other subscription already! Please change the subscription ID number!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    else
                    {
                        // If all data is valid, a popup window will be generated and notify the users to confirm the inputted data is the one they want to used to add new subscription to the system
                        // The users can select "Yes" to confirm the inputted data used to add new subscription; "No" and close the window to return to input data until they input the desired data used to add the new subscription to the system
                        // Variable used to store the option picked by users
                        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to add the new subscription?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                        // If the users selected "Yes"
                        if (answer == JOptionPane.YES_OPTION)
                        {
                            // Adding new Subscription object to the subscription list by using the inputted data to initialize the added Subscription object
                            GymManagementApp.subscriptionList.add(new Subscription(Integer.valueOf(subscriptionIDField.getText()), selectedCustomer, selectedExercisePlan, 
                                    LocalDate.parse(startDateField.getText()), LocalDate.parse(endDateField.getText()), Float.valueOf(totalPaymentField.getText())));
                            
                            // Assign the variable selectedSubscription, selectedExercisePlan and selectedCustomer to null as the action performed to change the data of current selected subscription is complete
                            selectedSubscription = null;
                            selectedExercisePlan = null;
                            selectedCustomer = null;
                            
                            // Update the data in the subscription list/table as new subscription data is added
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
    // 3) Confirm / Cancel (For adding new subscription)
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
                    // A popup window will be generated and notify the users to confirm they want to delete the selected subscription from the system
                    // The users can select "Yes" to confirm the delete selected subscription function; "No" and close the window to void the delete selected subscription action
                    // Variable used to store the option picked by users
                    int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the subscription from the system?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
                    // Check if the user selected "Yes"
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        // Enhanced for-loop to loop over the subscription list
                        for (Subscription subscription : GymManagementApp.subscriptionList) 
                        {
                            // Check if there exists a subscription in the system with the same ID with the selected subscription's ID
                            if (subscription.getSubscriptionID() == selectedSubscription.getSubscriptionID())
                            {
                                // If there exists a subscription in the system with the same ID with the selected subscription's ID
                                // Remove the Subscription object from the list
                                GymManagementApp.subscriptionList.remove(subscription);                                
                                // Assign the variable selectedSubscription, selectedExercisePlan and selectedCustomer to null as the action performed to change the data of current selected subscription is complete
                                selectedSubscription = null;
                                selectedExercisePlan = null;
                                selectedCustomer = null;
                                // Update the data in the subscription list/table as a subscription data is deleted
                                updateTableRow();
                                // Break the loop as there is no need to loop the subscription list till the end
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
                        // Display the selected subscription detail again to update the detail back to the existing data instead of the data modified during the process of modifying data
                        displaySelectedSubscriptionDetail();
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
                        // Assign the variable selectedSubscription, selectedExercisePlan and selectedCustomer to null as the action performed to change the data of current selected subscription is complete
                        selectedSubscription = null;
                        selectedExercisePlan = null;
                        selectedCustomer = null;
                        // Update the data in the trainer list/table to get the most current subscription list and hide the subscription detail panel
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

        selectExercisePlanWindow = new javax.swing.JFrame();
        selectExercisePlanPanel = new javax.swing.JPanel();
        exercisePlanTableScrollPane = new javax.swing.JScrollPane();
        exercisePlanTable = new javax.swing.JTable();
        exercisePlanListLabel = new javax.swing.JLabel();
        exercisePlanSelectButton = new javax.swing.JButton();
        exercisePlanCancelButton = new javax.swing.JButton();
        selectCustomerWindow = new javax.swing.JFrame();
        selectCustomerPanel = new javax.swing.JPanel();
        customerTableScrollPane = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        customerListLabel = new javax.swing.JLabel();
        customerSelectButton = new javax.swing.JButton();
        customerCancelButton = new javax.swing.JButton();
        subscriptionDetailPanel = new javax.swing.JPanel();
        subscriptionDetailFormPanel = new javax.swing.JPanel();
        subscriptionDetailLabel = new javax.swing.JLabel();
        subscriptionIDLabel = new javax.swing.JLabel();
        subscriptionIDField = new javax.swing.JTextField();
        totalPaymentLabel = new javax.swing.JLabel();
        totalPaymentField = new javax.swing.JTextField();
        subscriptionDurationLabel = new javax.swing.JLabel();
        subscriptionDurationSelection = new javax.swing.JSpinner();
        startDateLabel = new javax.swing.JLabel();
        startDateField = new javax.swing.JTextField();
        endDateLabel = new javax.swing.JLabel();
        endDateField = new javax.swing.JTextField();
        exercisePlanDetailLabel = new javax.swing.JLabel();
        exercisePlanIDLabel = new javax.swing.JLabel();
        exercisePlanIDField = new javax.swing.JTextField();
        priceLabel = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionFieldScrollPane = new javax.swing.JScrollPane();
        descriptionField = new javax.swing.JTextArea();
        exerciseDayLabel = new javax.swing.JLabel();
        sundayCheck = new javax.swing.JCheckBox();
        mondayCheck = new javax.swing.JCheckBox();
        tuesdayCheck = new javax.swing.JCheckBox();
        wednesdayCheck = new javax.swing.JCheckBox();
        thursdayCheck = new javax.swing.JCheckBox();
        fridayCheck = new javax.swing.JCheckBox();
        saturdayCheck = new javax.swing.JCheckBox();
        startTimeLabel = new javax.swing.JLabel();
        startHourSelection = new javax.swing.JSpinner();
        startMinuteSelection = new javax.swing.JSpinner();
        endTimeLabel = new javax.swing.JLabel();
        endHourSelection = new javax.swing.JSpinner();
        endMinuteSelection = new javax.swing.JSpinner();
        timeIndicator1 = new javax.swing.JLabel();
        timeIndicator2 = new javax.swing.JLabel();
        trainerDetailLabel = new javax.swing.JLabel();
        trainerIDLabel = new javax.swing.JLabel();
        trainerIDField = new javax.swing.JTextField();
        trainerNameLabel = new javax.swing.JLabel();
        trainerNameField = new javax.swing.JTextField();
        trainerPhoneLabel = new javax.swing.JLabel();
        trainerPhoneField = new javax.swing.JTextField();
        trainerEmailLabel = new javax.swing.JLabel();
        trainerEmailField = new javax.swing.JTextField();
        selectExercisePlanButton = new javax.swing.JButton();
        customerDetailLabel = new javax.swing.JLabel();
        customerIDLabel = new javax.swing.JLabel();
        customerIDField = new javax.swing.JTextField();
        customerNameLabel = new javax.swing.JLabel();
        customerNameField = new javax.swing.JTextField();
        customerPhoneLabel = new javax.swing.JLabel();
        customerPhoneField = new javax.swing.JTextField();
        customerEmailLabel = new javax.swing.JLabel();
        customerEmailField = new javax.swing.JTextField();
        selectCustomerButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        subscriptionListPanel = new javax.swing.JPanel();
        subscriptionListLabel = new javax.swing.JLabel();
        subscriptionTableScrollPane = new javax.swing.JScrollPane();
        subscriptionTable = new javax.swing.JTable();
        addSubscriptionButton = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();

        selectExercisePlanWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        selectExercisePlanWindow.setTitle("Select Available Exercise Plan");
        selectExercisePlanWindow.setLocation(new java.awt.Point(550, 200));
        selectExercisePlanWindow.setMinimumSize(new java.awt.Dimension(530, 440));
        selectExercisePlanWindow.setResizable(false);

        exercisePlanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Plan Description", "Trainer Name", "Equipment Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        exercisePlanTable.setPreferredSize(new java.awt.Dimension(452, 280));
        exercisePlanTable.getTableHeader().setReorderingAllowed(false);
        exercisePlanTableScrollPane.setViewportView(exercisePlanTable);
        if (exercisePlanTable.getColumnModel().getColumnCount() > 0) {
            exercisePlanTable.getColumnModel().getColumn(0).setResizable(false);
            exercisePlanTable.getColumnModel().getColumn(0).setPreferredWidth(15);
            exercisePlanTable.getColumnModel().getColumn(1).setResizable(false);
            exercisePlanTable.getColumnModel().getColumn(1).setPreferredWidth(130);
            exercisePlanTable.getColumnModel().getColumn(2).setResizable(false);
            exercisePlanTable.getColumnModel().getColumn(2).setPreferredWidth(130);
            exercisePlanTable.getColumnModel().getColumn(3).setResizable(false);
            exercisePlanTable.getColumnModel().getColumn(3).setPreferredWidth(130);
        }

        exercisePlanListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        exercisePlanListLabel.setText("<html><u>Exercise Plan List</u>");

        exercisePlanSelectButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        exercisePlanSelectButton.setText("Select");
        exercisePlanSelectButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exercisePlanSelectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exercisePlanSelectButtonMouseClicked(evt);
            }
        });

        exercisePlanCancelButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        exercisePlanCancelButton.setText("Cancel");
        exercisePlanCancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exercisePlanCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exercisePlanCancelButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout selectExercisePlanPanelLayout = new javax.swing.GroupLayout(selectExercisePlanPanel);
        selectExercisePlanPanel.setLayout(selectExercisePlanPanelLayout);
        selectExercisePlanPanelLayout.setHorizontalGroup(
            selectExercisePlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectExercisePlanPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(exercisePlanSelectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exercisePlanCancelButton)
                .addGap(23, 23, 23))
            .addGroup(selectExercisePlanPanelLayout.createSequentialGroup()
                .addGroup(selectExercisePlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(selectExercisePlanPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(exercisePlanTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(selectExercisePlanPanelLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(exercisePlanListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        selectExercisePlanPanelLayout.setVerticalGroup(
            selectExercisePlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectExercisePlanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exercisePlanListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exercisePlanTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(selectExercisePlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exercisePlanSelectButton)
                    .addComponent(exercisePlanCancelButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout selectExercisePlanWindowLayout = new javax.swing.GroupLayout(selectExercisePlanWindow.getContentPane());
        selectExercisePlanWindow.getContentPane().setLayout(selectExercisePlanWindowLayout);
        selectExercisePlanWindowLayout.setHorizontalGroup(
            selectExercisePlanWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
            .addGroup(selectExercisePlanWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(selectExercisePlanWindowLayout.createSequentialGroup()
                    .addComponent(selectExercisePlanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );
        selectExercisePlanWindowLayout.setVerticalGroup(
            selectExercisePlanWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
            .addGroup(selectExercisePlanWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, selectExercisePlanWindowLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectExercisePlanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        selectCustomerWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        selectCustomerWindow.setTitle("Select Customer");
        selectCustomerWindow.setLocation(new java.awt.Point(550, 200));
        selectCustomerWindow.setMinimumSize(new java.awt.Dimension(400, 440));
        selectCustomerWindow.setResizable(false);

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Name", "Type of Customer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerTableScrollPane.setViewportView(customerTable);
        if (customerTable.getColumnModel().getColumnCount() > 0) {
            customerTable.getColumnModel().getColumn(0).setResizable(false);
            customerTable.getColumnModel().getColumn(0).setPreferredWidth(15);
            customerTable.getColumnModel().getColumn(1).setResizable(false);
            customerTable.getColumnModel().getColumn(1).setPreferredWidth(130);
            customerTable.getColumnModel().getColumn(2).setResizable(false);
            customerTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        customerListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        customerListLabel.setText("<html><u>Customer List</u>");

        customerSelectButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerSelectButton.setText("Select");
        customerSelectButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerSelectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerSelectButtonMouseClicked(evt);
            }
        });

        customerCancelButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerCancelButton.setText("Cancel");
        customerCancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerCancelButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout selectCustomerPanelLayout = new javax.swing.GroupLayout(selectCustomerPanel);
        selectCustomerPanel.setLayout(selectCustomerPanelLayout);
        selectCustomerPanelLayout.setHorizontalGroup(
            selectCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectCustomerPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(customerSelectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(customerCancelButton)
                .addGap(23, 23, 23))
            .addGroup(selectCustomerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, selectCustomerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(customerListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );
        selectCustomerPanelLayout.setVerticalGroup(
            selectCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectCustomerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(selectCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerSelectButton)
                    .addComponent(customerCancelButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout selectCustomerWindowLayout = new javax.swing.GroupLayout(selectCustomerWindow.getContentPane());
        selectCustomerWindow.getContentPane().setLayout(selectCustomerWindowLayout);
        selectCustomerWindowLayout.setHorizontalGroup(
            selectCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
            .addGroup(selectCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(selectCustomerWindowLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(selectCustomerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        selectCustomerWindowLayout.setVerticalGroup(
            selectCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
            .addGroup(selectCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(selectCustomerWindowLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(selectCustomerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Manage Subscriptions");
        setResizable(false);

        subscriptionDetailFormPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        subscriptionDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        subscriptionDetailLabel.setText("<html><u>Subscription Detail:</u>");

        subscriptionIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionIDLabel.setText("Subscription ID:");

        subscriptionIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        totalPaymentLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        totalPaymentLabel.setText("Total Amount Paid:");

        totalPaymentField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        totalPaymentField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        totalPaymentField.setEnabled(false);

        subscriptionDurationLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionDurationLabel.setText("Subscription Duration (months):");

        subscriptionDurationSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionDurationSelection.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));
        subscriptionDurationSelection.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                subscriptionDurationSelectionStateChanged(evt);
            }
        });

        startDateLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startDateLabel.setText("Start Date:");

        startDateField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startDateField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        startDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                startDateFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                startDateFieldFocusLost(evt);
            }
        });

        endDateLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endDateLabel.setText("End Date:");

        endDateField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endDateField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        endDateField.setEnabled(false);

        exercisePlanDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        exercisePlanDetailLabel.setText("<html><u>Exercise Plan Detail:</u>");

        exercisePlanIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        exercisePlanIDLabel.setText("Exercise ID:");

        exercisePlanIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        exercisePlanIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        exercisePlanIDField.setEnabled(false);

        priceLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        priceLabel.setText("Plan Price:");

        priceField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        priceField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        priceField.setEnabled(false);

        descriptionLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        descriptionLabel.setText("Exercise Description:");

        descriptionFieldScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        descriptionField.setColumns(20);
        descriptionField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        descriptionField.setLineWrap(true);
        descriptionField.setRows(5);
        descriptionField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        descriptionField.setEnabled(false);
        descriptionField.setMargin(new java.awt.Insets(4, 6, 2, 6));
        descriptionFieldScrollPane.setViewportView(descriptionField);

        exerciseDayLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        exerciseDayLabel.setText("Exercise Day:");

        sundayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        sundayCheck.setText("Sunday");
        sundayCheck.setEnabled(false);

        mondayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        mondayCheck.setText("Monday");
        mondayCheck.setEnabled(false);

        tuesdayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        tuesdayCheck.setText("Tuesday");
        tuesdayCheck.setEnabled(false);

        wednesdayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        wednesdayCheck.setText("Wednesday");
        wednesdayCheck.setEnabled(false);

        thursdayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        thursdayCheck.setText("Thursday");
        thursdayCheck.setEnabled(false);

        fridayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        fridayCheck.setText("Friday");
        fridayCheck.setEnabled(false);

        saturdayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        saturdayCheck.setText("Saturday");
        saturdayCheck.setEnabled(false);

        startTimeLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startTimeLabel.setText("Start Time:");

        startHourSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startHourSelection.setModel(new javax.swing.SpinnerNumberModel(8, 8, 20, 1));
        startHourSelection.setEnabled(false);

        startMinuteSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startMinuteSelection.setModel(new javax.swing.SpinnerNumberModel(0, 0, 55, 5));
        startMinuteSelection.setEnabled(false);

        endTimeLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endTimeLabel.setText("End Time:");

        endHourSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endHourSelection.setModel(new javax.swing.SpinnerNumberModel(8, 8, 23, 1));
        endHourSelection.setEnabled(false);

        endMinuteSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endMinuteSelection.setModel(new javax.swing.SpinnerNumberModel(0, 0, 55, 5));
        endMinuteSelection.setEnabled(false);

        timeIndicator1.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        timeIndicator1.setText(":");

        timeIndicator2.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        timeIndicator2.setText(":");

        trainerDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        trainerDetailLabel.setText("<html><u>Trainer Detail:</u>");

        trainerIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerIDLabel.setText("Trainer ID:");

        trainerIDField.setEditable(false);
        trainerIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        trainerNameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerNameLabel.setText("Trainer Name:");

        trainerNameField.setEditable(false);
        trainerNameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        trainerPhoneLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerPhoneLabel.setText("Phone Number:");

        trainerPhoneField.setEditable(false);
        trainerPhoneField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerPhoneField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        trainerEmailLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerEmailLabel.setText("Email:");

        trainerEmailField.setEditable(false);
        trainerEmailField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerEmailField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        selectExercisePlanButton.setText("Select Exercise Plan");
        selectExercisePlanButton.setToolTipText("");
        selectExercisePlanButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectExercisePlanButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectExercisePlanButtonMouseClicked(evt);
            }
        });

        customerDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        customerDetailLabel.setText("<html><u>Customer Detail:</u>");

        customerIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerIDLabel.setText("Customer ID:");

        customerIDField.setEditable(false);
        customerIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        customerNameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerNameLabel.setText("Customer Name:");

        customerNameField.setEditable(false);
        customerNameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        customerPhoneLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerPhoneLabel.setText("Phone Number:");

        customerPhoneField.setEditable(false);
        customerPhoneField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerPhoneField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        customerEmailLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerEmailLabel.setText("Email:");

        customerEmailField.setEditable(false);
        customerEmailField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        customerEmailField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        selectCustomerButton.setText("Select Customer");
        selectCustomerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectCustomerButton.setPreferredSize(new java.awt.Dimension(162, 22));
        selectCustomerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectCustomerButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout subscriptionDetailFormPanelLayout = new javax.swing.GroupLayout(subscriptionDetailFormPanel);
        subscriptionDetailFormPanel.setLayout(subscriptionDetailFormPanelLayout);
        subscriptionDetailFormPanelLayout.setHorizontalGroup(
            subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(trainerDetailLabel)
                    .addComponent(subscriptionDetailLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(startDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(endDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endDateField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(trainerIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerNameField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(trainerPhoneLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerEmailLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerEmailField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(descriptionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descriptionFieldScrollPane))
                    .addComponent(customerDetailLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(exercisePlanIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exercisePlanIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceField))
                    .addComponent(exercisePlanDetailLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(customerIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerNameField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(customerPhoneLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerEmailLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerEmailField))
                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(subscriptionIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subscriptionIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPaymentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(totalPaymentField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                                .addComponent(startTimeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startHourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(timeIndicator1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(endTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endHourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeIndicator2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                                .addComponent(exerciseDayLabel)
                                .addGap(18, 18, 18)
                                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                                        .addComponent(sundayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mondayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tuesdayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(wednesdayCheck))
                                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                                        .addComponent(thursdayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fridayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(saturdayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                                .addComponent(subscriptionDurationLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(subscriptionDurationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectExercisePlanButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectCustomerButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        subscriptionDetailFormPanelLayout.setVerticalGroup(
            subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                .addComponent(subscriptionDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subscriptionIDField)
                    .addComponent(subscriptionIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPaymentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPaymentField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subscriptionDurationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subscriptionDurationSelection))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(endDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(endDateField)
                    .addComponent(startDateField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerIDField)
                    .addComponent(customerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerNameField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerPhoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerPhoneField)
                    .addComponent(customerEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exercisePlanDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exercisePlanIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exercisePlanIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descriptionFieldScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exerciseDayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sundayCheck)
                    .addComponent(mondayCheck)
                    .addComponent(tuesdayCheck)
                    .addComponent(wednesdayCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thursdayCheck)
                    .addComponent(fridayCheck)
                    .addComponent(saturdayCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startHourSelection)
                    .addComponent(startMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeIndicator1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(endHourSelection)
                    .addComponent(timeIndicator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endMinuteSelection))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trainerDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainerIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerIDField)
                    .addComponent(trainerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerNameField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainerPhoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerPhoneField)
                    .addComponent(trainerEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectExercisePlanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton1.setText("jButton1");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton2.setText("jButton2");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout subscriptionDetailPanelLayout = new javax.swing.GroupLayout(subscriptionDetailPanel);
        subscriptionDetailPanel.setLayout(subscriptionDetailPanelLayout);
        subscriptionDetailPanelLayout.setHorizontalGroup(
            subscriptionDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionDetailPanelLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(subscriptionDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subscriptionDetailPanelLayout.createSequentialGroup()
                        .addComponent(subscriptionDetailFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subscriptionDetailPanelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(16, 16, 16))))
        );
        subscriptionDetailPanelLayout.setVerticalGroup(
            subscriptionDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subscriptionDetailFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        subscriptionListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        subscriptionListLabel.setText("<html><u>Subscription List</u>");

        subscriptionTable.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N
        subscriptionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subscription ID", "Plan ID", "Start Date", "End Date", "Customer ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        subscriptionTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        subscriptionTable.setInheritsPopupMenu(true);
        subscriptionTable.setName(""); // NOI18N
        subscriptionTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        subscriptionTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        subscriptionTable.getTableHeader().setReorderingAllowed(false);
        subscriptionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subscriptionTableMouseClicked(evt);
            }
        });
        subscriptionTableScrollPane.setViewportView(subscriptionTable);
        if (subscriptionTable.getColumnModel().getColumnCount() > 0) {
            subscriptionTable.getColumnModel().getColumn(0).setResizable(false);
            subscriptionTable.getColumnModel().getColumn(1).setResizable(false);
            subscriptionTable.getColumnModel().getColumn(2).setResizable(false);
            subscriptionTable.getColumnModel().getColumn(3).setResizable(false);
            subscriptionTable.getColumnModel().getColumn(4).setResizable(false);
        }
        subscriptionTable.getAccessibleContext().setAccessibleDescription("");

        addSubscriptionButton.setText("Add");
        addSubscriptionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addSubscriptionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSubscriptionButtonMouseClicked(evt);
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

        javax.swing.GroupLayout subscriptionListPanelLayout = new javax.swing.GroupLayout(subscriptionListPanel);
        subscriptionListPanel.setLayout(subscriptionListPanelLayout);
        subscriptionListPanelLayout.setHorizontalGroup(
            subscriptionListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subscriptionListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subscriptionListPanelLayout.createSequentialGroup()
                        .addComponent(subscriptionListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(subscriptionListPanelLayout.createSequentialGroup()
                        .addComponent(returnButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addSubscriptionButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subscriptionListPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(subscriptionTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        subscriptionListPanelLayout.setVerticalGroup(
            subscriptionListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subscriptionListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subscriptionTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subscriptionListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addSubscriptionButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(returnButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subscriptionListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subscriptionDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subscriptionDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subscriptionListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    // This function is called whenever the user click on the content in the subscription list/table
    // This function is used to display the selected subscription detail when the user select a row of data in the table/list
    private void subscriptionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subscriptionTableMouseClicked
        // Check if the table/list is enabled
        if (subscriptionTable.isEnabled())
        {
            // Display the selected subscription detail by using displaySelectedSubscriptionDetail() function
            displaySelectedSubscriptionDetail();
            // Update the function of jButton1 (dynamic button) to allow modify function after clicking by using updatejButton1(1) function
            updatejButton1(1);
        }
    }//GEN-LAST:event_subscriptionTableMouseClicked
    
    // This function is called whenever the user click on the add subscription button
    // This function is used to display a new form for user to input data for the new subscription that is going to be added into the system
    // This function will disable all the utility buttons
    private void addSubscriptionButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSubscriptionButtonMouseClicked
        // Display a new form used to allow users using the form to input new subscription data and add the subscription to the system
        displayNewSubscriptionForm();
        // Update the function of jButton1 (dynamic button) to allow submit/confirm/add subscription function after clicking by using updatejButton1(3) function
        updatejButton1(3);
    }//GEN-LAST:event_addSubscriptionButtonMouseClicked
    
    // This function is called whenever the user click on the return to menu button
    // This function is used to close the manage subscription window and return user to the menu
    private void returnButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnButtonMouseClicked
        // Create again the menu window
        if (StaffMainMenu.loggedInStaff != null)
            new StaffMainMenu();
        else 
            new AdminMainMenu();
        // Dispose the window when the return to menu button is clicked (**This action will close the manage trainer window)
        this.dispose();
    }//GEN-LAST:event_returnButtonMouseClicked
    
    // This function is called when the select exercise plan button in the select exercise plan window is clicked
    // This function is used to update selectedExercisePlan based on the selection by user on the select exercise plan table
    private void exercisePlanSelectButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exercisePlanSelectButtonMouseClicked
        // Variable used to store the row number of the data row selected from the list/table
        int rowSelected = exercisePlanTable.getSelectedRow();
        
        // Check if there is item selected in the table
        if (rowSelected >= 0)
        {
            // variable used to obtain the data in the first column of the row selected (which is exercise plan ID)
            int selectedExercisePlanID = (int)exercisePlanTable.getValueAt(rowSelected, 0);
            
            // Assign the selectedEquipment with equipment with same equipment ID with selectedEquipmentID
            selectedExercisePlan = GymManagementApp.getExercisePlan(selectedExercisePlanID);
            
            // Dispose the select equipment window
            selectExercisePlanWindow.dispose();
            
            // Enabled and focus on the main window
            this.setEnabled(true);
            this.requestFocus();

            // Update the equipment fields with the selected equipment information
            updateExercisePlanAndTrainerFields();
            
            // Update total peyment field if applicable
            updateTotalPaymentField();
        }
        else
            // If there is no item selected, a popup window will be generated and notify the users to select an item from the list
            // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to select at least one item from the list
            JOptionPane.showConfirmDialog(this, "Please select an equipment from the list!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_exercisePlanSelectButtonMouseClicked

    // This function is called when the cancel select equipment button in the select equipment window is clicked
    // This function is used to close the select equipment window
    private void exercisePlanCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exercisePlanCancelButtonMouseClicked
        // Dispose the select equipment window
        selectExercisePlanWindow.dispose();
        // Enabled and focus on the main window
        this.setEnabled(true);
        this.requestFocus();
    }//GEN-LAST:event_exercisePlanCancelButtonMouseClicked

    // This function is called when the select trainer button in the select trainer window is clicked
    // This function is used to update selectedTrainer based on the selection by user on the select trainer table
    private void customerSelectButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerSelectButtonMouseClicked
        // Variable used to store the row number of the data row selected from the list/table
        int rowSelected = customerTable.getSelectedRow();
        
        // Check if there is item selected in the table
        if (rowSelected >= 0)
        {
            // variable used to obtain the data in the first column of the row selected (which is customer ID)
            String selectedCustomerID = (String)customerTable.getValueAt(rowSelected, 0);
            
            // Assign the selectedCustomer with trainer with same customer ID with selectedCustomerID
            selectedCustomer = GymManagementApp.getCustomer(selectedCustomerID);
            
            // Dispose the select customer window
            selectCustomerWindow.dispose();
            
            // Enabled and focus on the main window
            this.setEnabled(true);
            this.requestFocus();

            // Update the customer fields with the selected customer information
            updateCustomerFields();
            
            // Check if exercise plan is selected already or not
            if (selectedExercisePlan != null)
                // If yes, update also the fields as different type of customer will have different price
                updateExercisePlanAndTrainerFields();
            
            // Update total peyment field if applicable
            updateTotalPaymentField();
        }
        else
            // If there is no item selected, a popup window will be generated and notify the users to select an item from the list
            // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to select at least one item from the list
            JOptionPane.showConfirmDialog(this, "Please select an equipment from the list!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_customerSelectButtonMouseClicked

    // This function is called when the cancel select trainer button in the select trainer window is clicked
    // This function is used to close the select trainer window
    private void customerCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerCancelButtonMouseClicked
        // Dispose the select trainer window
        selectCustomerWindow.dispose();
        // Enabled and focus on the main window
        this.setEnabled(true);
        this.requestFocus();
    }//GEN-LAST:event_customerCancelButtonMouseClicked

    // This function is called when the select customer button is clicked
    // This function is used to call the select customer window
    private void selectCustomerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectCustomerButtonMouseClicked
        // Check if select customer button is enabled
        if (selectCustomerButton.isEnabled())
        {
            // Check if customer list is empty or not
            if (!GymManagementApp.customerList.isEmpty())
            {
                // If the list is not empty
                // Make the select customer window visible
                selectCustomerWindow.setVisible(true);
                
                // Disabled the main window
                this.setEnabled(false);
            }
            else
            {
                // If there is no customer in the list, a popup window will be generated and notify the users to add customer first before using this option
                // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify subscription detail
                JOptionPane.showConfirmDialog(this, "There is no customer in the list! Please add customer into the system first!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_selectCustomerButtonMouseClicked
//GEN-FIRST:event_selectEquipmentButtonMouseClicked
//GEN-LAST:event_selectEquipmentButtonMouseClicked

    // This function is called when the select select exercise plan and dates button is clicked
    // This function is used to call the select exercise plan window
    private void selectExercisePlanButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectExercisePlanButtonMouseClicked
        // Check if select exercise plan and dates button is enabled
        if (selectExercisePlanButton.isEnabled())
        {
            // Check if exercise plan list is empty or not
            if (!GymManagementApp.exercisePlanList.isEmpty())
            {
                // Update available exercise plan list based on the date period used if there exists available exercise plans during the period selectedd
                if (updateExercisePlanList())
                {
                    // If the list is not empty
                    // Make the select exercise plan window visible
                    selectExercisePlanWindow.setVisible(true);

                    // Disabled the main window
                    this.setEnabled(false);
                }
                else
                {
                    // If there is no exercise plan in the list, a popup window will be generated and notify the users to change subscription period
                    // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify subscription detail
                    JOptionPane.showConfirmDialog(this, "There is no exercise plan available during the selected period! Please choose other period!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                // If there is no exercise plan in the list, a popup window will be generated and notify the users to add exercise plan first before using this option
                // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify subscription detail
                JOptionPane.showConfirmDialog(this, "There is no exercise plan in the list! Please add exercise plan into the system first!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }         
    }//GEN-LAST:event_selectExercisePlanButtonMouseClicked
    
    // This function is called whenever startDateField is losing focus
    // This function is used to checked whether the date input is in desired date format
    // This function will update the end date field if data inputted in startDateField is valid
    private void startDateFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_startDateFieldFocusLost
        // Check if there is changes between value previous and after
        if (!prevStartDate.equals(startDateField.getText()))
        {
            // Check if current entered data in startDateField is correct format
            if (!checkDateFormatValid())
            {
                // If the input format is invalid, a popup window will be generated and notify the users
                // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify data in startDateField
                JOptionPane.showConfirmDialog(this, "Please enter a valid date format!\n(Example: YYYY-MM-DD)", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                // Request focus again on startDateField to force user on modifying the startDateField to a valid input format
                startDateField.requestFocus();
            }
            // If data entered in startDateFIeld is correct, check whether the date inputted is before the current system's date
            else if (checkStartDateValid())
            {
                // If the inputted date is before system date, a popup window will be generated and notify the users
                // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify data in startDateField
                JOptionPane.showConfirmDialog(this, "The date entered should equal or after system's date!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                // Request focus again on startDateField to force user on modifying the startDateField to a valid date
                startDateField.requestFocus();
            }
            else
            {
                // Update end date based on the current valid inputted date in startDateField and subscriptionDurationSelection
                updateEndDate();
            }
        }
    }//GEN-LAST:event_startDateFieldFocusLost

    // This function is called whenever subscriptionDurationSelection has value changed
    // This function is used to update the date displayed in endDateField
    private void subscriptionDurationSelectionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_subscriptionDurationSelectionStateChanged
        // Update end date
        updateEndDate();
        // Update total peyment field if applicable
        updateTotalPaymentField();
    }//GEN-LAST:event_subscriptionDurationSelectionStateChanged

    // This function is called whenever startDateField is gaining focus
    // This function prerequisite to check whether there is value changed before and after using startDateField
    private void startDateFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_startDateFieldFocusGained
        // Assign the current start date when the start date field is focused
        prevStartDate = startDateField.getText();
    }//GEN-LAST:event_startDateFieldFocusGained
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSubscriptionButton;
    private javax.swing.JButton customerCancelButton;
    private javax.swing.JLabel customerDetailLabel;
    private javax.swing.JTextField customerEmailField;
    private javax.swing.JLabel customerEmailLabel;
    private javax.swing.JTextField customerIDField;
    private javax.swing.JLabel customerIDLabel;
    private javax.swing.JLabel customerListLabel;
    private javax.swing.JTextField customerNameField;
    private javax.swing.JLabel customerNameLabel;
    private javax.swing.JTextField customerPhoneField;
    private javax.swing.JLabel customerPhoneLabel;
    private javax.swing.JButton customerSelectButton;
    private javax.swing.JTable customerTable;
    private javax.swing.JScrollPane customerTableScrollPane;
    private javax.swing.JTextArea descriptionField;
    private javax.swing.JScrollPane descriptionFieldScrollPane;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField endDateField;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JSpinner endHourSelection;
    private javax.swing.JSpinner endMinuteSelection;
    private javax.swing.JLabel endTimeLabel;
    private javax.swing.JLabel exerciseDayLabel;
    private javax.swing.JButton exercisePlanCancelButton;
    private javax.swing.JLabel exercisePlanDetailLabel;
    private javax.swing.JTextField exercisePlanIDField;
    private javax.swing.JLabel exercisePlanIDLabel;
    private javax.swing.JLabel exercisePlanListLabel;
    private javax.swing.JButton exercisePlanSelectButton;
    private javax.swing.JTable exercisePlanTable;
    private javax.swing.JScrollPane exercisePlanTableScrollPane;
    private javax.swing.JCheckBox fridayCheck;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox mondayCheck;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JButton returnButton;
    private javax.swing.JCheckBox saturdayCheck;
    private javax.swing.JButton selectCustomerButton;
    private javax.swing.JPanel selectCustomerPanel;
    private javax.swing.JFrame selectCustomerWindow;
    private javax.swing.JButton selectExercisePlanButton;
    private javax.swing.JPanel selectExercisePlanPanel;
    private javax.swing.JFrame selectExercisePlanWindow;
    private javax.swing.JTextField startDateField;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JSpinner startHourSelection;
    private javax.swing.JSpinner startMinuteSelection;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JPanel subscriptionDetailFormPanel;
    private javax.swing.JLabel subscriptionDetailLabel;
    private javax.swing.JPanel subscriptionDetailPanel;
    private javax.swing.JLabel subscriptionDurationLabel;
    private javax.swing.JSpinner subscriptionDurationSelection;
    private javax.swing.JTextField subscriptionIDField;
    private javax.swing.JLabel subscriptionIDLabel;
    private javax.swing.JLabel subscriptionListLabel;
    private javax.swing.JPanel subscriptionListPanel;
    private javax.swing.JTable subscriptionTable;
    private javax.swing.JScrollPane subscriptionTableScrollPane;
    private javax.swing.JCheckBox sundayCheck;
    private javax.swing.JCheckBox thursdayCheck;
    private javax.swing.JLabel timeIndicator1;
    private javax.swing.JLabel timeIndicator2;
    private javax.swing.JTextField totalPaymentField;
    private javax.swing.JLabel totalPaymentLabel;
    private javax.swing.JLabel trainerDetailLabel;
    private javax.swing.JTextField trainerEmailField;
    private javax.swing.JLabel trainerEmailLabel;
    private javax.swing.JTextField trainerIDField;
    private javax.swing.JLabel trainerIDLabel;
    private javax.swing.JTextField trainerNameField;
    private javax.swing.JLabel trainerNameLabel;
    private javax.swing.JTextField trainerPhoneField;
    private javax.swing.JLabel trainerPhoneLabel;
    private javax.swing.JCheckBox tuesdayCheck;
    private javax.swing.JCheckBox wednesdayCheck;
    // End of variables declaration//GEN-END:variables
}