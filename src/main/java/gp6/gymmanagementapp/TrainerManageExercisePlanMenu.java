/*
This class is used to generate the manage exercise plan menu used by logged-in trainer
*/
package gp6.gymmanagementapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TrainerManageExercisePlanMenu extends javax.swing.JFrame {

    // Variable used to store the current selected exercise plan based on the row selected in the exercise plan list
    private ExercisePlan selectedExercisePlan;
    // Variable used to store the current selected equipment used in the selected exercise plan
    private Equipment selectedEquipment;
    
    /**
     * Creates new form TrainerManageExercisePlanMenu
     */
    public TrainerManageExercisePlanMenu() {
        initComponents();
        // Set the menu to be visible on the screen
        this.setVisible(true);
        // Used to update the content in the list/table of exercise plan
        updateTableRow();
    }

    // Function used to update the content in the list/table of exercise plan
    // Is used when:
    // 1) the menu is first initialized
    // 2) there is update in the data of a exercise plan
    // 3) when there is cancel function used when modifying or adding exercise plan
    private void updateTableRow()
    {
        // Hiding the exercisePlanDetailPanel when there is no exercise plan selected from the exercise plan list
        exercisePlanDetailPanel.setVisible(false);
        // Update the jButton1 into mode 1, which grants delete function for the user to delete the selected exercise plan
        updatejButton1(1);
        
        // Sorting the exercise plan list based on the exercise plan ID number of the exercise plan
        Collections.sort(GymManagementApp.exercisePlanList, (ExercisePlan exercisePlan1, ExercisePlan exercisePlan2) -> exercisePlan1.getPlanID() - exercisePlan2.getPlanID());
        
        // Getting the model of table used to display the exercise plan list
        DefaultTableModel model = (DefaultTableModel) exercisePlanTable.getModel();
        // Clear the content in the list/table
        model.setRowCount(0);
        
        // Variables used to store the data to be contained in the list/table which has 2 columns: ID and Description
        Object[] rowData = new Object[2];
        
        // For-loop to loop the sorted exercise plan list
        for (int i=0; i < GymManagementApp.exercisePlanList.size(); i++) 
        {
            // Conditional statement to only select exercise plan related to logged-in trainer
            if(GymManagementApp.exercisePlanList.get(i).getTrainer() == TrainerMainMenu.loggedInTrainer)
            {
                // Storing related data which are exercise plan ID and description
                rowData[0] = GymManagementApp.exercisePlanList.get(i).getPlanID();
                rowData[1] = GymManagementApp.exercisePlanList.get(i).getPlanDescription();
            
                // Adding the data into the list/table
                model.addRow(rowData);
            }
        }
    }
    
    // Function used to check whether there is changes betweeen the current inputs and existing data
    // The function returns true if there exists at least a change
    // Is used when:
    // 1) modifying data
    // NOTE: **Trainer part is excluded as trainer shouldn't be able to change directly from this menu, only delete the exercise plan is allowed
    private boolean checkDataChanged()
    {
        // Check there is changes on the price of the selected exercise plan before and after
        if (selectedExercisePlan.getPrice() != Float.valueOf(priceField.getText()))
            return true;
        // Check there is changes on the description of the selected exercise plan before and after
        if (!selectedExercisePlan.getPlanDescription().equals(descriptionField.getText()))
            return true;
        // Check there is changes on the exercise day of the selected exercise plan before and after
        // Enhanced-loop to loop over the recorded exercise day in the selected exercise plan
        for (int day : selectedExercisePlan.getExerciseDay())
        {    
            // Used for determine which checking should be performed
            switch(day)
            {
                // When there exist 1 (Monday is exercise day)
                case 1 -> 
                {
                    // Check if monday checkbox is checked
                    if (!mondayCheck.isSelected())
                        return true;    // returns true if the checkbox is not checked means the inputted value is different
                }
                // When there exist 2 (Tuesday is exercise day)
                case 2 -> 
                {
                    // Check if tuesday checkbox is checked
                    if (!tuesdayCheck.isSelected())
                        return true;    // returns true if the checkbox is not checked means the inputted value is different
                }
                // When there exist 3 (Wednesday is exercise day)
                case 3 -> 
                {
                    // Check if wednesday checkbox is checked
                    if (!wednesdayCheck.isSelected())
                        return true;    // returns true if the checkbox is not checked means the inputted value is different
                }
                // When there exist 4 (Thursday is exercise day)
                case 4 -> 
                {
                    // Check if thursday checkbox is checked
                    if (!thursdayCheck.isSelected())
                        return true;    // returns true if the checkbox is not checked means the inputted value is different
                }
                // When there exist 5 (Friday is exercise day)
                case 5 -> 
                {
                    // Check if friday checkbox is checked
                    if (!fridayCheck.isSelected())
                        return true;    // returns true if the checkbox is not checked means the inputted value is different
                }
                // When there exist 6 (Saturday is exercise day)
                case 6 -> 
                {
                    // Check if saturday checkbox is checked
                    if (!saturdayCheck.isSelected())
                        return true;    // returns true if the checkbox is not checked means the inputted value is different
                }
                // When there exist 7 (Sunday is exercise day)
                case 7 -> 
                {
                    // Check if sunday checkbox is checked
                    if (!sundayCheck.isSelected())
                        return true;    // returns true if the checkbox is not checked means the inputted value is different
                }
            }
        }
        // Check there is changes on the start time of the selected exercise plan before and after
        // Check if the existing data on the hour and minute of start time is equal with the current inputted values
        if (selectedExercisePlan.getStartTime().getHour() != Integer.valueOf(startHourSelection.getValue().toString()) && selectedExercisePlan.getStartTime().getMinute() != Integer.valueOf(startMinuteSelection.getValue().toString()))
            return true;
        
        // Check if there is changes on the expected duration of the selected exercise plan before and after
        if (selectedExercisePlan.getExpectedDuration() != Integer.valueOf(durationSelection.getValue().toString()))
            return true;
            
        // Check if there is changes on the equipment used in the selected exercise plan before and after
        return (selectedExercisePlan.getEquipment().getEquipmentID() != Integer.valueOf(equipmentIDField.getText()));
    }
    
    // Function used to check whether the exercise plan ID number used to modify/add exercise plan is used already
    // The function returns false if the exercise plan ID number is not used yet
    // Is used when:
    // 1) modifying data
    // 2) adding new exercise plan
    private boolean isExercisePlanIDNumberUsed(int exercisePlanIDNumber)
    {
        // Enhanced for-loop to loop over exercise list
        for (ExercisePlan exercisePlan : GymManagementApp.exercisePlanList)
            // Check if the exercise plan ID number held by the ExercisePlan object is same with the exercise plan ID number passed to the function
            if (exercisePlan.getPlanID() == exercisePlanIDNumber)
                // returns true if the they are the same
                return true;
        // Returns false if cannot find any ExercisePlan object is having the same exercise plan ID number with the exercise plan ID number passed to the function
        // Indicates the ID number can be used
        return false;
    }
    
    // Function used to check whether at least an exercise day is checked
    // This function returns true if no exercise day selection is checked
    // Is used when:
    // 1) modifying data
    // 2) adding new exercise plan
    private boolean checkExerciseDayChecked()
    {
        // Check whether there exists at least a checkbox for exercise day is checked
        // return false if there is at least one exercise day checkbox is checked
        // Check if checkbox for monday is checked
        if (mondayCheck.isSelected())
            return false;
        // Check if checkbox for tuesday is checked
        if (tuesdayCheck.isSelected())
            return false;
        // Check if checkbox for wednesday is checked
        if (wednesdayCheck.isSelected())
            return false;
        // Check if checkbox for thursday is checked
        if (thursdayCheck.isSelected())
            return false;
        // Check if checkbox for friday is checked
        if (fridayCheck.isSelected())
            return false;
        // Check if checkbox for saturday is checked
        if (saturdayCheck.isSelected())
            return false;
        // Check if checkbox for sunday is checked 
        // NOTE: **NOT (!) is required to return the correct boolean, if the checkbox is selected, boolean expression will be true. NOT (!) is added to invert the boolean expression from true to false.
        return (!sundayCheck.isSelected());    
    }
    
    // Function used to check whether there exists an input field is not filled or filled with blankspace only
    // The function returns true if there exists at least a field is not filled or filled with blankspace only
    // Is used when:
    // 1) included in checkDataValid() function
    // NOTE: **NOT include exercise day section as exercise day section need another generated window to remind user
    private boolean checkDataBlank()
    {
        // Check if the field of price is filled
        if (priceField.getText().isBlank())
            return true;
        // Check if the field of description is filled
        if (descriptionField.getText().isBlank())
            return true;
        // Check if the field of equipment ID is filled
        if (equipmentIDField.getText().isBlank())
            return true;
        // Check if the field of trainer ID is filled
        return (trainerIDField.getText().isBlank());
    }
    
    // Function used to check whether the input in each input field is valid
    // The funciton returns true if there exists at least a field is invalid (including the field with no input or filled with blankspace)
    // Is used when:
    // 1) modifying data
    // 2) adding new trainer
    private boolean checkDataIsNumber()
    {
        // Checking whether the input for price input field is number
        return (!GymManagementApp.checkIsPrice(priceField.getText()));
    }
    
    // Function used to display the exercise plan form when trying to add a new exercise plan to the system
    // Is used when:
    // 1) adding new exercise plan
    private void displayNewExercisePlanForm()
    {
        // Automatically generate planID based on available ID
        int availableID = 1;
        while (availableID < (GymManagementApp.exercisePlanList.size()+1))
        {
            if(!isExercisePlanIDNumberUsed(availableID))
            {
                break;
            }
            else
                availableID++;
        }
        
        // Set the trainer form to be visible in the screen
        exercisePlanDetailPanel.setVisible(true);
        
        // Automatically set the planID of new plan with generated ID
        planIDField.setText(String.valueOf(availableID));
        
        // Initialize all available text fields with empty String
        priceField.setText("");
        descriptionField.setText("");
        equipmentIDField.setText("");
        equipmentNameField.setText("");
        trainerIDField.setText("");
        trainerNameField.setText("");
        trainerPhoneField.setText("");
        trainerEmailField.setText("");
        
        // Initialize all checkboxes for exercise day to be not selected
        mondayCheck.setSelected(false);
        tuesdayCheck.setSelected(false);
        wednesdayCheck.setSelected(false);
        thursdayCheck.setSelected(false);
        fridayCheck.setSelected(false);
        saturdayCheck.setSelected(false);
        sundayCheck.setSelected(false);
        
        // Initialize the expected duration selection to select "1"
        durationSelection.setValue(1);
        // Initialize the start hour selection to select "88"
        startHourSelection.setValue(8);
        // Initialize the start minute selection to select "0"
        startMinuteSelection.setValue(0);
        // Initialize the end hour selection to select "9"
        // Initialize the end minute selection to select "0"
        // By using updateEndTime()
        updateEndTime();
        
        // Set the selectedEquipment to null as new exercise plan does not have an equipment yet
        selectedExercisePlan = null;
        // Set the selectedEquipment to null as new exercise plan does not have an equipment yet
        selectedEquipment = null;
        
        // Display the trainerIDField based on logged-in trainer
        trainerIDField.setText(TrainerMainMenu.loggedInTrainer.getID());
        // Display the trainerNameField based on logged-in trainer
        trainerNameField.setText(TrainerMainMenu.loggedInTrainer.getFirstName() + " " + TrainerMainMenu.loggedInTrainer.getLastName());
        // Display the trainerPhoneField based on logged-in trainer
        trainerPhoneField.setText(TrainerMainMenu.loggedInTrainer.getPhone());
        // Display the trainerEmailField based on logged-in trainer
        trainerEmailField.setText(TrainerMainMenu.loggedInTrainer.getEmail());
        
    }
    
    // This function is used to display the exercise plan detail panel and the exercise plan detail based on the selected exercise plan selected from the list/table
    // Is used when 
    // 1) the user select a data row from the exercise plan list/table
    private void displaySelectedExercisePlanDetail()
    {
        // Set the exercise plan form to be visible in the screen
        exercisePlanDetailPanel.setVisible(true);
        
        // Variable used to store the row number of the data row selected from the list/table
        int rowSelected = exercisePlanTable.getSelectedRow();
        // variable used to obtain the data in the first column of the row selected (which is exercise plan ID)
        int selectedExercisePlanID = (int)exercisePlanTable.getValueAt(rowSelected, 0);
        
        // Assign the exercise plan by using getExercisePlan() function by using the exercise plan ID obtained from the list/table
        selectedExercisePlan = GymManagementApp.getExercisePlan(selectedExercisePlanID);
        
        // Check whether there is a ExercisePlan object returned
        if (selectedExercisePlan == null)
            // Exit the function if no ExercisePlan object returned
            return;
        
        // Disable all available text fields by using disableAllTextFields() function to disable user from modifying data before clicking on "Modify" button
        disableAllTextFields();
        
        // Initialize all checkboxes for exercise day to be not selected
        mondayCheck.setSelected(false);
        tuesdayCheck.setSelected(false);
        wednesdayCheck.setSelected(false);
        thursdayCheck.setSelected(false);
        fridayCheck.setSelected(false);
        saturdayCheck.setSelected(false);
        sundayCheck.setSelected(false);
        
        // Assign the data to display the data in form format
        // Assign the plan ID of the selected exercise plan to the planIDField
        planIDField.setText(String.valueOf(selectedExercisePlan.getPlanID()));
        // Assign the price of the selected exercise plan to the priceField
        priceField.setText(String.valueOf(selectedExercisePlan.getPrice()));
        // Assign the description of the selected exercise plan to the descriptionField
        descriptionField.setText(selectedExercisePlan.getPlanDescription());
        // Assign the expected duration of the selected exercise plan to the durationSelection drop down list
        durationSelection.setValue(selectedExercisePlan.getExpectedDuration());
        // Assign the hour of start time of the selected exercise plan to the startHourSelection
        startHourSelection.setValue(selectedExercisePlan.getStartTime().getHour());
        // Assign the minute of start time of the selected exercise plan to the startMinuteSelection
        startMinuteSelection.setValue(selectedExercisePlan.getStartTime().getMinute());
        
        // Assign the hour of end time of the selected exercise plan to the endHourSelection
        endHourSelection.setValue(selectedExercisePlan.getEndTime().getHour());
        // Assign the minute of end time of the selected exercise plan to the endMinuteSelection
        endMinuteSelection.setValue(selectedExercisePlan.getEndTime().getMinute());
        
        // Update the object of selectedEquipment based on the equipment data stored in the exercise plan
        selectedEquipment = selectedExercisePlan.getEquipment();
        
        // Assign the equipment ID number of the equipment used in the selected exercise plan to the equipmentIDField
        // Assign the equipment name of the equipment used in the selected exercise plan to the equipmentNameField
        updateEquipmentFields();
        
        // Assign the trainer ID of the trainer of the selected exercise plan to the trainerIDField
        // Assign the name of the trainer of the selected exercise plan to the trainerNameField
        // Assign the phone number of the trainer of the selected exercise plan to the trainerPhoneField
        // Assign the email of the trainer of the selected exercise plan to the trainerEmailField
        updateTrainerFields();
        
        // Checking checkboxes for each day according to the exercise day recorded in the selected exercise plan
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
    }
    
    // This function is used to enable all the input fields / text fields to allow users to modify or add new data
    // Is used when:
    // 1) modifying data
    // 2) add new exercise plan
    private void enableAllTextFields()
    {
        // Enable all the text/input fields, making all the text/input fields are editable by users
        priceField.setEnabled(true);
        descriptionField.setEnabled(true);
        mondayCheck.setEnabled(true);
        tuesdayCheck.setEnabled(true);
        wednesdayCheck.setEnabled(true);
        thursdayCheck.setEnabled(true);
        fridayCheck.setEnabled(true);
        saturdayCheck.setEnabled(true);
        sundayCheck.setEnabled(true);
        durationSelection.setEnabled(true);
        startHourSelection.setEnabled(true);
        startMinuteSelection.setEnabled(true);
    }
    
    // This function is used to disable all the input fields / text fields to prevent users from modifying the data
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void disableAllTextFields()
    {
        // Disable all the text/input fields, making all the text/input fields are NOT editable by users
        planIDField.setEnabled(false);
        priceField.setEnabled(false);
        descriptionField.setEnabled(false);
        mondayCheck.setEnabled(false);
        tuesdayCheck.setEnabled(false);
        wednesdayCheck.setEnabled(false);
        thursdayCheck.setEnabled(false);
        fridayCheck.setEnabled(false);
        saturdayCheck.setEnabled(false);
        sundayCheck.setEnabled(false);
        durationSelection.setEnabled(false);
        startHourSelection.setEnabled(false);
        startMinuteSelection.setEnabled(false);
        disableEquipmentSection();
    }
    
    // This function is used to enable all the input fields / text fields related to equipment when modifying/adding the data
    // Is used when:
    // 1) modifying data
    // 2) adding new exercise plan
    private void enableEquipmentSection()
    {
        selectEquipmentButton.setEnabled(true);
    }
    
    // This function is used to disable all the input fields / text fields related to equipment to prevent users from modifying the data
    // Is used when:
    // 1) include in disableAllTextFields()
    private void disableEquipmentSection()
    {
        selectEquipmentButton.setEnabled(false);
    }
    
    // This function is used to initialize all equipment section input fields and deselect the selected equipment
    // Is used when:
    // 1) everytime the expected duration or start time changed
    private void initializeEquipmentSection()
    {
        // Set equipment related fields to empty string
        equipmentIDField.setText("");
        equipmentNameField.setText("");
        // Set selectedEquipment to null
        selectedEquipment = null;
    }
    
    // This function is used to enable all utility buttons such as add button and return to menu button
    // Is used when:
    // 1) user is viewing the data only, NOT modifying or adding data
    private void enableAllUtilityButtons()
    {
        // Enable return to trainer menu button
        returnButton.setEnabled(true);
        // Enable add trainer button
        addExercisePlanButton.setEnabled(true);
        // Enable table/list of trainer function (Allow select data row)
        exercisePlanTable.setEnabled(true);
    }
    
    // This function is used to disable all utility buttons such as add button and return to menu button to avoid users accidentally close the window while still editing data
    // Is used when:
    // 1) modifying data
    // 2) add new exercise plan
    private void disableAllUtilityButtons()
    {
        // Disable return to admin menu button
        returnButton.setEnabled(false);
        // Disable add trainer button
        addExercisePlanButton.setEnabled(false);
        // Disable table/list of trainer function (Prevent select data row)
        exercisePlanTable.setEnabled(false);
    }
    
    // This function is used to auto update the end time whenever there is changes on expected duration or start time
    // Is used when:
    // 1) modifying data (when there is changes on expected duration or start time)
    // 2) add new exercise plan (when there is changes on expected duration or start time)
    private void updateEndTime()
    {
        // Update the endHourSelection based on the value of startHourSelection and durationSelection (The end time should be value of startHourSelection +  value of durationSelection)
        endHourSelection.setValue(Integer.valueOf(startHourSelection.getValue().toString()) + Integer.valueOf(durationSelection.getValue().toString()));
        // Update the endMinuteSelection to the value of startMinuteSelection (since the duration should be in full hour)
        endMinuteSelection.setValue(Integer.valueOf(startMinuteSelection.getValue().toString()));
    }
    
    // This function is used to check whether the equipment is not used by other exercise plan during the same exercise day and same time period
    // This function returns true when there is time clashed
    // Is used when (included in updateAvailableEquipmentList()):
    // 1) modifying data (when select equipment button is clicked)
    // 2) add new exercise plan (when select equipment button is clicked)
    private boolean checkTimeAvailable(LocalTime startTime, LocalTime endTime, ExercisePlan exercisePlan)
    {        
        // Check if either start time is in between the checked exercise plan's start time(inclusive) and end time(exclusive) or end time is in between the checked exercise plan's start time(exclusive) and end time(exclusive)
        // NOTES: **This checking does not cover the situation where the checked exercise plan's time period is in between the start time and end time, therefore, other checking should be used if this checked condition returns false
        if ((startTime.compareTo(exercisePlan.getStartTime()) >= 0 && startTime.compareTo(exercisePlan.getEndTime()) < 0) || (endTime.compareTo(exercisePlan.getStartTime()) > 0 && endTime.compareTo(exercisePlan.getEndTime()) < 0))
        {
            return true;    // Returns true to indicate time clashed
        }
        else
        {
            // If the above condition returns false
            // Variable used to store the duration difference between current duration selected with the exercise plan's expected duration
            int durationDifference = Integer.valueOf(durationSelection.getValue().toString()) - exercisePlan.getExpectedDuration();

            // Check if the start time is less than exercise plan's start time
            if (startTime.compareTo(exercisePlan.getStartTime()) < 0)
            {
                // Check if the duration difference is over 0 (indicate the new duration is greater than the checked exercise plan's expected durationn)
                if (durationDifference > 0)
                {
                    // Check if the duration between start time until the checked exercise plan's start time is lower or equal to the duration difference
                    // If true, that means this new time period will also have time clashed with the checked exercise plan
                    if (startTime.until(exercisePlan.getStartTime(), java.time.temporal.ChronoUnit.HOURS) <= durationDifference)
                        return true;
                }
            }
        }
        
        // return false to indicate no time clashed
        return false;
    }
    
    // This function is used to update the available equipment list to be selected by user based on the time period and exercise day selected by user
    // Is used when:
    // 1) modifying data (when select equipment button is clicked)
    // 2) add new exercise plan (when select equipment button is clicked)
    private boolean updateAvailableEquipmentList()
    {
        // Variable used to store and initialize the start time and end time of the selected exercise plan to be passed to the checking function
        LocalTime startTime = LocalTime.of(Integer.valueOf(startHourSelection.getValue().toString()), Integer.valueOf(startMinuteSelection.getValue().toString()));
        LocalTime endTime = LocalTime.of(Integer.valueOf(endHourSelection.getValue().toString()), Integer.valueOf(endMinuteSelection.getValue().toString()));
        
        // ArrayList used to store the available equipment and displayed in the select equipment list
        ArrayList<Equipment> availableEquipment = new ArrayList();
        
        // Enhanced for-loop to loop the equipment list
        for (Equipment equipment : GymManagementApp.equipmentList)
        {
            // Boolean used to check whether this equipment should be treated as available during the time period in certain exercise day(s)
            boolean shouldAdd = true;
            
            // Enhanced for-loop to loop over the exercise plan list
            for (ExercisePlan exercisePlan : GymManagementApp.exercisePlanList)
            {
                // Check if currently is adding new exercise plan or modifying selected exercise plan
                if (selectedExercisePlan != null)
                {
                    // If is modifying exercise plan
                    // Check if the selectedExercisePlan has the same ID with the current looped exercise plan or not
                    if (selectedExercisePlan.getPlanID() == exercisePlan.getPlanID())
                    {
                        // If true, the loop will be skipped by using continue
                        continue;
                    }
                }

                // Check if the current looped exercise plan has the equipment with same ID with the current looped equipment
                if (equipment.getEquipmentID() == exercisePlan.getEquipment().getEquipmentID())
                {
                    // Boolean used to check whether will have day clashed to use the equipment
                    boolean dayClash = false;
                    
                    // Enhanced for-loop to loop over the exercise day recorded in the current checked(looped) exercise plan
                    for (int exerciseDay : exercisePlan.getExerciseDay()) 
                    {
                        // Switch exercise day and check whether the current selected exercise day has clashed with the recorded exercise day for the looped exercise plan
                        switch (exerciseDay)
                        {
                            // Check on monday
                            case 1 ->
                            {
                                // If monday checkbox is selected, means has clashed exercise day
                                if (mondayCheck.isSelected())
                                    dayClash = true;
                            }
                            // Check on tuesday
                            case 2 ->
                            {
                                // If monday checkbox is selected, means has clashed exercise day
                                if (tuesdayCheck.isSelected())
                                    dayClash = true;
                            }
                            // Check on wednesday
                            case 3 ->
                            {
                                // If wednesday checkbox is selected, means has clashed exercise day
                                if (wednesdayCheck.isSelected())
                                    dayClash = true;
                            }
                            // Check on thursday
                            case 4 ->
                            {
                                // If thursday checkbox is selected, means has clashed exercise day
                                if (thursdayCheck.isSelected())
                                    dayClash = true;
                            }
                            // Check on friday
                            case 5 ->
                            {
                                // If friday checkbox is selected, means has clashed exercise day
                                if (fridayCheck.isSelected())
                                    dayClash = true;
                            }
                            // Check on saturday
                            case 6 ->
                            {
                                // If saturday checkbox is selected, means has clashed exercise day
                                if (saturdayCheck.isSelected())
                                    dayClash = true;
                            }
                            // Check on sunday
                            case 7 ->
                            {
                                // If sunday checkbox is selected, means has clashed exercise day
                                if (sundayCheck.isSelected())
                                    dayClash = true;
                            }
                        }
                        
                        // Check if there is day clash or not
                        // If true, there is no need to loop over other exercise day, use break to break the exercise day loop
                        if (dayClash)
                            break;
                    }
                    
                    // Check if there is day clash
                    if (dayClash)
                    {
                        // If there is day clash
                        // Check if the time period entered is available, will return true if not available
                        if (checkTimeAvailable(startTime, endTime, exercisePlan))
                        {
                            // If not available
                            // Set boolean shouldAdd to false to indicate the equipment should not be added into the available equipment list
                            shouldAdd = false;
                        }
                    }
                    
                    // Check if shouldAdd boolean still true
                    // If the boolean is false, break the exercise plan loop as there is no need to check other exercise plan
                    if (!shouldAdd)
                        break;
                }
            }
            
            // Check if shouldAdd boolean is still true or not after checking all the condition by using multiple for-loops
            if (shouldAdd)
                // If still true, add the current looped equipment to the available equipment list
                availableEquipment.add(equipment);
        }
        
        // Check if the available equipment list is empty or not
        if (!availableEquipment.isEmpty())
        {
            // Sorting the available equipment list based on the equipment ID number of the equipment
            Collections.sort(availableEquipment, (Equipment equipment1, Equipment equipment2) -> equipment1.getEquipmentID() - equipment2.getEquipmentID());

            // Getting the model of table used to display the equipment list
            DefaultTableModel model = (DefaultTableModel) equipmentTable.getModel();
            // Clear the content in the list/table
            model.setRowCount(0);

            // Variables used to store the data to be contained in the list/table which has 3 columns: ID, Description and Trainer Name
            Object[] rowData = new Object[2];

            // Enhanced for-loop to loop the sorted available equipment list
            for (Equipment equipment : availableEquipment) 
            {
                // Storing related data which are equipment ID and equipment name
                rowData[0] = equipment.getEquipmentID();
                rowData[1] = equipment.getEquipmentName();

                // Adding the data into the list/table
                model.addRow(rowData);
            }
            
            // Return true to indicate there exists at least one available equipment during the time
            return true;
        }
        
        // Return false to indicate there is no available equipment during the time period
        return false;
    }
    
    // This function is used to update the equipment details in the exercise plan detail panel
    // Is used when:
    // 1) select exercise plan from the table (update the equipment details based on the information stored in the exercise plan)
    // 2) modifying data (select new equipment from the select equipment window)
    // 3) adding new exercise plan (select new equipment from the select equipment window)
    private void updateEquipmentFields()
    {
        // Update the equipmentIDField based on the selectedEquipment's information
        equipmentIDField.setText(String.valueOf(selectedEquipment.getEquipmentID()));
        // Update the equipmentNameField based on the selectedEquipment's information
        equipmentNameField.setText(selectedEquipment.getEquipmentName());
    }
    
    
    // This function is used to update the trainer details in the exercise plan detail panel
    // Is used when:
    // 1) select exercise plan from the table (update the trainer details based on the information of logged-in trainer)
    // 2) adding new exercise plan (automatically setting trainer's information based on logged-in trainer)
    private void updateTrainerFields()
    {
        // Update the trainerIDField based on the selectedTrainer's information
        trainerIDField.setText(TrainerMainMenu.loggedInTrainer.getID());
        // Update the trainerNameField based on the selectedTrainer's information
        trainerNameField.setText(TrainerMainMenu.loggedInTrainer.getFirstName() + " " + TrainerMainMenu.loggedInTrainer.getLastName());
        // Update the trainerPhoneField based on the selectedTrainer's information
        trainerPhoneField.setText(TrainerMainMenu.loggedInTrainer.getPhone());
        // Update the trainerEmailField based on the selectedTrainer's information
        trainerEmailField.setText(TrainerMainMenu.loggedInTrainer.getEmail());
    }
    
    
    // This function is used to update the function of jButton1 based on the circumstances (Button with dynamic function)
    // This function will clear the current action performed by jButton1 and update the function of the button
    // There exists 3 functions/modes of the button:
    // 1) Button used to trigger action allows users modifying existing data of the exercise plan
    // 2) Button used to confirm the modified data and update the information of the exercise plan
    // 3) Button used to confirm the data to be used to add a new exercise plan to the system
    // **NOTE: This function will update jButton1's pair, which is jButton2 function**
    // **Function pairs (jButton1 / jButton2):
    // 1) Modify / Delete
    // 2) Confirm / Cancel (For modifying data)
    // 3) Confirm / Cancel (For adding new exercise plan)
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
                // Enable equipment fields to allow users to modify the equipment used
                enableEquipmentSection();
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
                    // Check if there exists at least one exercise day is checked
                    // If there is no exercise day checkboxes checked, the boolean "true" will be returned
                    else if (checkExerciseDayChecked())
                        // If there is no exercise day checkboxes checked, a popup window will be generated and notify the users to select at least one exercise day
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to select the exercise day
                        JOptionPane.showConfirmDialog(this, "At least one exercise day needs to be selected! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // Check if there is datatype invalid in certain input fields
                    // If there exists data invalid, the boolean "true" will be returned
                    else if (checkDataIsNumber())
                        // If there exists data invalid, a popup window will be generated and notify the users regarding the possible reasons of causing data invalid
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the data until all data input is valid
                        JOptionPane.showConfirmDialog(this, "Start time, end time, plan ID and price should be in number! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Check if the exercise plan ID number is used by other ExercisePlan object in the exercise plan list except of the current selected exercise plan
                    // If there exists ExercisePlan object other than the selected exercise plan is using the inputted exercise plan ID number, the boolean "true" will be returned
                    else if (isExercisePlanIDNumberUsed(Integer.valueOf(planIDField.getText())) && selectedExercisePlan.getPlanID() != Integer.valueOf(planIDField.getText()))
                        // If there exists ExercisePlan object other than the selected exercise plan is using the inputted exercise plan ID number, a popup window will be generated and notify the users to change the exercise plan ID number used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the exercise plan ID number until an unused exercise plan ID number or same exercise plan ID number with the current selected exercise plan is inputted
                        JOptionPane.showConfirmDialog(this, "The exercise plan ID number is used by other exercise plan already! Please change the exercise plan ID number!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid and exercise plan ID used are also valid
                    // Checking for whether there exists changes in the data input in all fields will be proceed
                    // If there exists changes in the data, the boolean "true" will be returned
                    else if (checkDataChanged())
                    {
                        // If there exists changes in the data, a popup window will be generated and notify the users to confirm the modified data/input is the one they want to used to change the data of the selected exercise plan
                        // The users can select "Yes" to confirm the modified data/input; "No" and close the window to return to modify the data until they input the desired data used to change the data of the selected exercise plan
                        // Variable used to store the option picked by users
                        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to make the changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                        // Check if the user is selecting "Yes"
                        if (answer == JOptionPane.YES_OPTION)
                        {
                                // Set the data for exercise plan ID of the selected exercise plan to the inputted plan ID
                                selectedExercisePlan.setPlanID(Integer.valueOf(planIDField.getText()));
                                // Set the data for price of the selected exercise plan to the inputted price
                                selectedExercisePlan.setPrice(Float.valueOf(priceField.getText()));
                                // Set the data for description of the selected exercise plan to the inputted description
                                selectedExercisePlan.setPlanDescription(descriptionField.getText());
                                
                                // Set the data for the exercise day of the selected exercise plan based on the selection of exercise day
                                // Variable used to store the size of array to be created based on the number of selection of exercise day
                                int exerciseDayCount = 0;
                                
                                // Check if checkbox for monday is checked
                                if (mondayCheck.isSelected())
                                    exerciseDayCount++;
                                // Check if checkbox for tuesday is checked
                                if (tuesdayCheck.isSelected())
                                    exerciseDayCount++;
                                // Check if checkbox for wednesday is checked
                                if (wednesdayCheck.isSelected())
                                    exerciseDayCount++;
                                // Check if checkbox for thursday is checked
                                if (thursdayCheck.isSelected())
                                    exerciseDayCount++;
                                // Check if checkbox for friday is checked
                                if (fridayCheck.isSelected())
                                    exerciseDayCount++;
                                // Check if checkbox for saturday is checked
                                if (saturdayCheck.isSelected())
                                    exerciseDayCount++;
                                // Check if checkbox for sunday is checked
                                if (sundayCheck.isSelected())
                                    exerciseDayCount++;
                                
                                // Declare a variable used to store the exercise day
                                int[] exerciseDay = new int[exerciseDayCount];
                                // Counter used to determine the index of the array
                                int counter = 0;
                                
                                // Check if the counter is still lower than maximun size of the array and checkbox for monday is checked
                                if (counter < exerciseDayCount && mondayCheck.isSelected())
                                    exerciseDay[counter++] = 1; // Assign "1" that represents monday is an exercise day
                                // Check if the counter is still lower than maximun size of the array and checkbox for tuesday is checked
                                if (counter < exerciseDayCount && tuesdayCheck.isSelected())
                                    exerciseDay[counter++] = 2; // Assign "2" that represents tuesday is an exercise day
                                // Check if the counter is still lower than maximun size of the array and checkbox for wednesday is checked
                                if (counter < exerciseDayCount && wednesdayCheck.isSelected())
                                    exerciseDay[counter++] = 3; // Assign "3" that represents wednesday is an exercise day
                                // Check if the counter is still lower than maximun size of the array and checkbox for thursday is checked
                                if (counter < exerciseDayCount && thursdayCheck.isSelected())
                                    exerciseDay[counter++] = 4; // Assign "4" that represents thursday is an exercise day
                                // Check if the counter is still lower than maximun size of the array and checkbox for friday is checked
                                if (counter < exerciseDayCount && fridayCheck.isSelected())
                                    exerciseDay[counter++] = 5; // Assign "5" that represents friday is an exercise day
                                // Check if the counter is still lower than maximun size of the array and checkbox for saturday is checked
                                if (counter < exerciseDayCount && saturdayCheck.isSelected())
                                    exerciseDay[counter++] = 6; // Assign "6" that represents saturday is an exercise day
                                // Check if the counter is still lower than maximun size of the array and checkbox for sunday is checked
                                if (counter < exerciseDayCount && sundayCheck.isSelected())
                                    exerciseDay[counter++] = 7; // Assign "7" that represents sunday is an exercise day
                                
                                // Set the exercise day of the selected exercise plan
                                selectedExercisePlan.setExerciseDay(exerciseDay);
                                
                                // Set the data for expected duration of the selected exercise plan to the duration selected
                                selectedExercisePlan.setExpectedDuration(Integer.valueOf(durationSelection.getValue().toString()));
                                
                                // Set the start time and end time of the selected exercise plan to the start time inputted
                                // Variables used to store the hour and minute of the start time of the exercise plan
                                int startHour = Integer.valueOf(startHourSelection.getValue().toString());
                                int startMinute = Integer.valueOf(startMinuteSelection.getValue().toString());
                                // Variables used to store the hour and minute of the end time of the exercise plan
                                int endHour = Integer.valueOf(endHourSelection.getValue().toString());
                                int endMinute = Integer.valueOf(endMinuteSelection.getValue().toString());
                                
                                // Set the data for the start time and end time of the selected exercise plan to the selected values
                                selectedExercisePlan.setTime(LocalTime.of(startHour, startMinute), LocalTime.of(endHour, endMinute));
                                       
                                // Set the data for equipment of the selected exercise plan based on the equipment selected
                                selectedExercisePlan.setEquipment(selectedEquipment);

                            // Assign the variable selectedExercisePlan and selectedEquipment to null as the action performed to change the data of current selected exercise plan is complete
                            selectedExercisePlan = null;
                            selectedEquipment = null;
                            // Update the data in the exercise plan list/table as the data for the previous selected exercise plan is changed, update is needed to display the new data
                            updateTableRow();
                        }
                    }
                    // If the inputted data are valid but there is no changes between the inputted data and the existing data
                    // Assuming the users doesn't want to change the data for the selected exercise plan and close the exercisePlanDetailPanel and enable back all the utility buttons function by updating jButton1's function to mode 1
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
                // Enable equipment fields to allow users to select the equipment used
                enableEquipmentSection();
                // Disable all utility buttons function as currently adding new trainer function is in progress
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
                    // Check if there exists at least one exercise day is checked
                    // If there is no exercise day checkboxes checked, the boolean "true" will be returned
                    else if (checkExerciseDayChecked())
                        // If there is no exercise day checkboxes checked, a popup window will be generated and notify the users to select at least one exercise day
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to select the exercise day
                        JOptionPane.showConfirmDialog(this, "At least one exercise day needs to be selected! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // Check if there is datatype invalid in certain input fields
                    // If there exists data invalid, the boolean "true" will be returned
                    else if (checkDataIsNumber())
                        // If there exists data invalid, a popup window will be generated and notify the users regarding the possible reasons of causing data invalid
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the data until all data input is valid
                        JOptionPane.showConfirmDialog(this, "Start time, end time, plan ID and price should be in number! Please check again!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    // If all the data is valid
                    // Check if the exercise plan ID number is used by other ExercisePlan object in the exercise plan list
                    // If there exists ExercisePlan object is using the inputted exercise plan ID number, the boolean "true" will be returned
                    else if (isExercisePlanIDNumberUsed(Integer.valueOf(planIDField.getText())))
                        // If there exists ExercisePlan object is using the inputted exercise plan ID number, a popup window will be generated and notify the users to change the exercise plan ID number used
                        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify the exercise plan ID number until an unused exercise plan ID number is inputted
                        JOptionPane.showConfirmDialog(this, "The exercise plan ID number is used by other exercise plan already! Please change the exercise plan ID number!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    else
                    {
                        // If all data is valid, a popup window will be generated and notify the users to confirm the inputted data is the one they want to used to add new exercise plan to the system
                        // The users can select "Yes" to confirm the inputted data used to add new exercise plan; "No" and close the window to return to input data until they input the desired data used to add the new exercise plan to the system
                        // Variable used to store the option picked by users
                        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to add the new exercise plan?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                        // If the users selected "Yes"
                        if (answer == JOptionPane.YES_OPTION)
                        {
                            // Set the data for the exercise day of the selected exercise plan based on the selection of exercise day
                            // Variable used to store the size of array to be created based on the number of selection of exercise day
                            int exerciseDayCount = 0;

                            // Check if checkbox for monday is checked
                            if (mondayCheck.isSelected())
                                exerciseDayCount++;
                            // Check if checkbox for tuesday is checked
                            if (tuesdayCheck.isSelected())
                                exerciseDayCount++;
                            // Check if checkbox for wednesday is checked
                            if (wednesdayCheck.isSelected())
                                exerciseDayCount++;
                            // Check if checkbox for thursday is checked
                            if (thursdayCheck.isSelected())
                                exerciseDayCount++;
                            // Check if checkbox for friday is checked
                            if (fridayCheck.isSelected())
                                exerciseDayCount++;
                            // Check if checkbox for saturday is checked
                            if (saturdayCheck.isSelected())
                                exerciseDayCount++;
                            // Check if checkbox for sunday is checked
                            if (sundayCheck.isSelected())
                                exerciseDayCount++;

                            // Declare a variable used to store the exercise day
                            int[] exerciseDay = new int[exerciseDayCount];
                            // Counter used to determine the index of the array
                            int counter = 0;

                            // Check if the counter is still lower than maximun size of the array and checkbox for monday is checked
                            if (counter < exerciseDayCount && mondayCheck.isSelected())
                                exerciseDay[counter++] = 1; // Assign "1" that represents monday is an exercise day
                            // Check if the counter is still lower than maximun size of the array and checkbox for tuesday is checked
                            if (counter < exerciseDayCount && tuesdayCheck.isSelected())
                                exerciseDay[counter++] = 2; // Assign "2" that represents tuesday is an exercise day
                            // Check if the counter is still lower than maximun size of the array and checkbox for wednesday is checked
                            if (counter < exerciseDayCount && wednesdayCheck.isSelected())
                                exerciseDay[counter++] = 3; // Assign "3" that represents wednesday is an exercise day
                            // Check if the counter is still lower than maximun size of the array and checkbox for thursday is checked
                            if (counter < exerciseDayCount && thursdayCheck.isSelected())
                                exerciseDay[counter++] = 4; // Assign "4" that represents thursday is an exercise day
                            // Check if the counter is still lower than maximun size of the array and checkbox for friday is checked
                            if (counter < exerciseDayCount && fridayCheck.isSelected())
                                exerciseDay[counter++] = 5; // Assign "5" that represents friday is an exercise day
                            // Check if the counter is still lower than maximun size of the array and checkbox for saturday is checked
                            if (counter < exerciseDayCount && saturdayCheck.isSelected())
                                exerciseDay[counter++] = 6; // Assign "6" that represents saturday is an exercise day
                            // Check if the counter is still lower than maximun size of the array and checkbox for sunday is checked
                            if (counter < exerciseDayCount && sundayCheck.isSelected())
                                exerciseDay[counter++] = 7; // Assign "7" that represents sunday is an exercise day
                            
                            // Set the start time and end time of the selected exercise plan to the start time inputted
                            // Variables used to store the hour and minute of the start time of the exercise plan
                            int startHour = Integer.valueOf(startHourSelection.getValue().toString());
                            int startMinute = Integer.valueOf(startMinuteSelection.getValue().toString());
                            // Variables used to store the hour and minute of the end time of the exercise plan
                            int endHour = Integer.valueOf(endHourSelection.getValue().toString());
                            int endMinute = Integer.valueOf(endMinuteSelection.getValue().toString());
                            
                            // Adding new ExercisePlan object to the exercise plan list by using the inputted data to initialize the added ExercisePlan object
                            GymManagementApp.exercisePlanList.add(new ExercisePlan(Integer.valueOf(planIDField.getText()), TrainerMainMenu.loggedInTrainer, selectedEquipment, 
                                    descriptionField.getText(), Integer.valueOf(durationSelection.getValue().toString()), Float.valueOf(priceField.getText()), 
                                    LocalTime.of(startHour, startMinute), LocalTime.of(endHour, endMinute), exerciseDay));
                            
                            // Update the data in the exercise plan list/table as new exercise plan data is added
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
                    int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the trainer from the system?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
                    // Check if the user selected "Yes"
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        // Enhanced for-loop to loop over the exercise plan list
                        for (ExercisePlan exercisePlan : GymManagementApp.exercisePlanList) 
                        {
                            // Check if there exists a exercise plan in the system with the same ID with the selected exercise plan's ID
                            if (exercisePlan.getPlanID() == selectedExercisePlan.getPlanID())
                            {
                                // If there exists a exercise plan in the system with the same ID with the selected exercise plan's ID
                                // Remove the ExercisePlan object from the list
                                GymManagementApp.exercisePlanList.remove(exercisePlan);
                                // Update the data in the exercise plan list/table as a exercise plan data is deleted
                                updateTableRow();
                                // Break the loop as there is no need to loop the exercise plan list till the end
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
                        // Display the selected exercise plan detail again to update the detail back to the existing data instead of the data modified during the process of modifying data
                        displaySelectedExercisePlanDetail();
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
                        // Update the data in the trainer list/table to get the most current exercise plan list and hide the exercise plan detail panel
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

        selectEquipmentWindow = new javax.swing.JFrame();
        selectEquipmentPanel = new javax.swing.JPanel();
        equipmentTableScrollPane = new javax.swing.JScrollPane();
        equipmentTable = new javax.swing.JTable();
        equipmentListLabel = new javax.swing.JLabel();
        equipmentSelectButton = new javax.swing.JButton();
        equipmentCancelButton = new javax.swing.JButton();
        exercisePlanDetailPanel = new javax.swing.JPanel();
        exercisePlanDetailFormPanel = new javax.swing.JPanel();
        exercisePlanDetailLabel = new javax.swing.JLabel();
        planIDLabel = new javax.swing.JLabel();
        planIDField = new javax.swing.JTextField();
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
        expectedDurationLabel = new javax.swing.JLabel();
        startTimeLabel = new javax.swing.JLabel();
        endTimeLabel = new javax.swing.JLabel();
        timeIndicator1 = new javax.swing.JLabel();
        timeIndicator2 = new javax.swing.JLabel();
        EquipmentDetailLabel = new javax.swing.JLabel();
        equipmentIDLabel = new javax.swing.JLabel();
        equipmentIDField = new javax.swing.JTextField();
        equipmentNameLabel = new javax.swing.JLabel();
        equipmentNameField = new javax.swing.JTextField();
        selectEquipmentButton = new javax.swing.JButton();
        trainerDetailLabel = new javax.swing.JLabel();
        trainerIDLabel = new javax.swing.JLabel();
        trainerIDField = new javax.swing.JTextField();
        trainerNameLabel = new javax.swing.JLabel();
        trainerNameField = new javax.swing.JTextField();
        trainerPhoneLabel = new javax.swing.JLabel();
        trainerPhoneField = new javax.swing.JTextField();
        trainerEmailField = new javax.swing.JTextField();
        trainerEmailLabel = new javax.swing.JLabel();
        durationSelection = new javax.swing.JSpinner();
        startHourSelection = new javax.swing.JSpinner();
        endHourSelection = new javax.swing.JSpinner();
        startMinuteSelection = new javax.swing.JSpinner();
        endMinuteSelection = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        exercisePlanListPanel = new javax.swing.JPanel();
        exercisePlanListLabel = new javax.swing.JLabel();
        exercisePlanTableScrollPane = new javax.swing.JScrollPane();
        exercisePlanTable = new javax.swing.JTable();
        addExercisePlanButton = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();

        selectEquipmentWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        selectEquipmentWindow.setTitle("Select Available Equipment");
        selectEquipmentWindow.setAlwaysOnTop(true);
        selectEquipmentWindow.setLocation(new java.awt.Point(550, 200));
        selectEquipmentWindow.setMinimumSize(new java.awt.Dimension(400, 440));
        selectEquipmentWindow.setResizable(false);

        equipmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Equipment Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        equipmentTable.getTableHeader().setReorderingAllowed(false);
        equipmentTableScrollPane.setViewportView(equipmentTable);

        equipmentListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        equipmentListLabel.setText("<html><u>Equipment List</u>");

        equipmentSelectButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        equipmentSelectButton.setText("Select");
        equipmentSelectButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        equipmentSelectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                equipmentSelectButtonMouseClicked(evt);
            }
        });

        equipmentCancelButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        equipmentCancelButton.setText("Cancel");
        equipmentCancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        equipmentCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                equipmentCancelButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout selectEquipmentPanelLayout = new javax.swing.GroupLayout(selectEquipmentPanel);
        selectEquipmentPanel.setLayout(selectEquipmentPanelLayout);
        selectEquipmentPanelLayout.setHorizontalGroup(
            selectEquipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectEquipmentPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(equipmentSelectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(equipmentCancelButton)
                .addGap(23, 23, 23))
            .addGroup(selectEquipmentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(equipmentTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, selectEquipmentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(equipmentListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        selectEquipmentPanelLayout.setVerticalGroup(
            selectEquipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectEquipmentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(equipmentListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(equipmentTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(selectEquipmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(equipmentSelectButton)
                    .addComponent(equipmentCancelButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout selectEquipmentWindowLayout = new javax.swing.GroupLayout(selectEquipmentWindow.getContentPane());
        selectEquipmentWindow.getContentPane().setLayout(selectEquipmentWindowLayout);
        selectEquipmentWindowLayout.setHorizontalGroup(
            selectEquipmentWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
            .addGroup(selectEquipmentWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(selectEquipmentWindowLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(selectEquipmentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        selectEquipmentWindowLayout.setVerticalGroup(
            selectEquipmentWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
            .addGroup(selectEquipmentWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(selectEquipmentWindowLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(selectEquipmentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Manage Exercise Plans");
        setResizable(false);

        exercisePlanDetailFormPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        exercisePlanDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        exercisePlanDetailLabel.setText("<html><u>Exercise Plan Detail:</u>");

        planIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        planIDLabel.setText("Plan ID:");

        planIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        planIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        priceLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        priceLabel.setText("Price:");

        priceField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        priceField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        descriptionLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        descriptionLabel.setText("Description:");

        descriptionFieldScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        descriptionField.setColumns(20);
        descriptionField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        descriptionField.setLineWrap(true);
        descriptionField.setRows(5);
        descriptionField.setMargin(new java.awt.Insets(4, 6, 2, 6));
        descriptionFieldScrollPane.setViewportView(descriptionField);

        exerciseDayLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        exerciseDayLabel.setText("Exercise Day:");

        sundayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        sundayCheck.setText("Sunday");
        sundayCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sundayCheckMouseClicked(evt);
            }
        });

        mondayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        mondayCheck.setText("Monday");
        mondayCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mondayCheckMouseClicked(evt);
            }
        });

        tuesdayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        tuesdayCheck.setText("Tuesday");
        tuesdayCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tuesdayCheckMouseClicked(evt);
            }
        });

        wednesdayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        wednesdayCheck.setText("Wednesday");
        wednesdayCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wednesdayCheckMouseClicked(evt);
            }
        });

        thursdayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        thursdayCheck.setText("Thursday");
        thursdayCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thursdayCheckMouseClicked(evt);
            }
        });

        fridayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        fridayCheck.setText("Friday");
        fridayCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fridayCheckMouseClicked(evt);
            }
        });

        saturdayCheck.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        saturdayCheck.setText("Saturday");
        saturdayCheck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saturdayCheckMouseClicked(evt);
            }
        });

        expectedDurationLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        expectedDurationLabel.setText("Expected Duration (in hour):");

        startTimeLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startTimeLabel.setText("Start Time:");

        endTimeLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endTimeLabel.setText("End Time:");

        timeIndicator1.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        timeIndicator1.setText(":");

        timeIndicator2.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        timeIndicator2.setText(":");

        EquipmentDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        EquipmentDetailLabel.setText("<html><u>Equipment Detail:</u>");

        equipmentIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        equipmentIDLabel.setText("Equipment ID:");

        equipmentIDField.setEditable(false);
        equipmentIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        equipmentIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        equipmentNameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        equipmentNameLabel.setText("Equipment Name:");

        equipmentNameField.setEditable(false);
        equipmentNameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        equipmentNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        equipmentNameField.setName(""); // NOI18N
        equipmentNameField.setPreferredSize(new java.awt.Dimension(209, 21));

        selectEquipmentButton.setText("Select Equipment");
        selectEquipmentButton.setToolTipText("");
        selectEquipmentButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectEquipmentButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectEquipmentButtonMouseClicked(evt);
            }
        });

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

        trainerEmailField.setEditable(false);
        trainerEmailField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerEmailField.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        trainerEmailLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerEmailLabel.setText("Email:");

        durationSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        durationSelection.setModel(new javax.swing.SpinnerNumberModel(1, 1, 3, 1));
        durationSelection.setVerifyInputWhenFocusTarget(false);
        durationSelection.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                durationSelectionStateChanged(evt);
            }
        });

        startHourSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startHourSelection.setModel(new javax.swing.SpinnerNumberModel(8, 8, 20, 1));
        startHourSelection.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                startHourSelectionStateChanged(evt);
            }
        });

        endHourSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endHourSelection.setModel(new javax.swing.SpinnerNumberModel(8, 8, 23, 1));
        endHourSelection.setEnabled(false);

        startMinuteSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startMinuteSelection.setModel(new javax.swing.SpinnerNumberModel(0, 0, 55, 5));
        startMinuteSelection.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                startMinuteSelectionStateChanged(evt);
            }
        });

        endMinuteSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endMinuteSelection.setModel(new javax.swing.SpinnerNumberModel(0, 0, 55, 5));
        endMinuteSelection.setEnabled(false);

        javax.swing.GroupLayout exercisePlanDetailFormPanelLayout = new javax.swing.GroupLayout(exercisePlanDetailFormPanel);
        exercisePlanDetailFormPanel.setLayout(exercisePlanDetailFormPanelLayout);
        exercisePlanDetailFormPanelLayout.setHorizontalGroup(
            exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(selectEquipmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                        .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(exercisePlanDetailLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                    .addComponent(planIDLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(planIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(priceLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(priceField))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                    .addComponent(expectedDurationLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(durationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(startTimeLabel)
                                        .addComponent(endTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(12, 12, 12)
                                    .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                            .addComponent(startHourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(timeIndicator1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(startMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                            .addComponent(endHourSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(timeIndicator2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(endMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                    .addComponent(exerciseDayLabel)
                                    .addGap(18, 18, 18)
                                    .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                            .addComponent(thursdayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(fridayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(saturdayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                            .addComponent(sundayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(mondayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tuesdayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(wednesdayCheck))))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                    .addComponent(equipmentIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(equipmentIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(equipmentNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(equipmentNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                    .addComponent(descriptionLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(descriptionFieldScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                        .addComponent(trainerPhoneLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(trainerPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(trainerEmailLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(trainerEmailField))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                                        .addComponent(trainerIDLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(trainerIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(trainerNameLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(trainerNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(trainerDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EquipmentDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        exercisePlanDetailFormPanelLayout.setVerticalGroup(
            exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                .addComponent(exercisePlanDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(planIDField)
                    .addComponent(priceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(planIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descriptionFieldScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exerciseDayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sundayCheck)
                    .addComponent(mondayCheck)
                    .addComponent(tuesdayCheck)
                    .addComponent(wednesdayCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thursdayCheck)
                    .addComponent(fridayCheck)
                    .addComponent(saturdayCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(expectedDurationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeIndicator1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(durationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startMinuteSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startHourSelection))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeIndicator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endHourSelection)
                    .addComponent(endMinuteSelection)
                    .addComponent(endTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EquipmentDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(equipmentIDField)
                    .addComponent(equipmentNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(equipmentIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(equipmentNameField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectEquipmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(trainerDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainerIDField)
                    .addComponent(trainerIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerNameField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainerPhoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerPhoneField)
                    .addComponent(trainerEmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainerEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        jButton1.setText("jButton1");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton2.setText("jButton2");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout exercisePlanDetailPanelLayout = new javax.swing.GroupLayout(exercisePlanDetailPanel);
        exercisePlanDetailPanel.setLayout(exercisePlanDetailPanelLayout);
        exercisePlanDetailPanelLayout.setHorizontalGroup(
            exercisePlanDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanDetailPanelLayout.createSequentialGroup()
                .addComponent(exercisePlanDetailFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exercisePlanDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(16, 16, 16))
        );
        exercisePlanDetailPanelLayout.setVerticalGroup(
            exercisePlanDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanDetailPanelLayout.createSequentialGroup()
                .addComponent(exercisePlanDetailFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        exercisePlanListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        exercisePlanListLabel.setText("<html><u>Exercise Plan List</u>");

        exercisePlanTable.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N
        exercisePlanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        exercisePlanTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exercisePlanTable.setInheritsPopupMenu(true);
        exercisePlanTable.setName(""); // NOI18N
        exercisePlanTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        exercisePlanTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        exercisePlanTable.getTableHeader().setReorderingAllowed(false);
        exercisePlanTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exercisePlanTableMouseClicked(evt);
            }
        });
        exercisePlanTableScrollPane.setViewportView(exercisePlanTable);
        if (exercisePlanTable.getColumnModel().getColumnCount() > 0) {
            exercisePlanTable.getColumnModel().getColumn(0).setResizable(false);
            exercisePlanTable.getColumnModel().getColumn(1).setResizable(false);
        }

        addExercisePlanButton.setText("Add");
        addExercisePlanButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addExercisePlanButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addExercisePlanButtonMouseClicked(evt);
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

        javax.swing.GroupLayout exercisePlanListPanelLayout = new javax.swing.GroupLayout(exercisePlanListPanel);
        exercisePlanListPanel.setLayout(exercisePlanListPanelLayout);
        exercisePlanListPanelLayout.setHorizontalGroup(
            exercisePlanListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanListPanelLayout.createSequentialGroup()
                .addGroup(exercisePlanListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(exercisePlanListPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(exercisePlanListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(exercisePlanListPanelLayout.createSequentialGroup()
                                .addComponent(exercisePlanListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(exercisePlanListPanelLayout.createSequentialGroup()
                                .addComponent(returnButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addExercisePlanButton))))
                    .addComponent(exercisePlanTableScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        exercisePlanListPanelLayout.setVerticalGroup(
            exercisePlanListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exercisePlanListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exercisePlanTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addExercisePlanButton)
                    .addComponent(returnButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exercisePlanListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exercisePlanDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(exercisePlanDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exercisePlanListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sundayCheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sundayCheckMouseClicked
        // Check if the checkbox is enabled
        if (sundayCheck.isEnabled())
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new day and time period
        initializeEquipmentSection();
    }//GEN-LAST:event_sundayCheckMouseClicked

    private void mondayCheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mondayCheckMouseClicked
        // Check if the checkbox is enabled
        if (mondayCheck.isEnabled())
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new day and time period
        initializeEquipmentSection();
    }//GEN-LAST:event_mondayCheckMouseClicked

    private void tuesdayCheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tuesdayCheckMouseClicked
        // Check if the checkbox is enabled
        if (tuesdayCheck.isEnabled())
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new day and time period
        initializeEquipmentSection();
    }//GEN-LAST:event_tuesdayCheckMouseClicked

    private void wednesdayCheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wednesdayCheckMouseClicked
        // Check if the checkbox is enabled
        if (wednesdayCheck.isEnabled())
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new day and time period
        initializeEquipmentSection();
    }//GEN-LAST:event_wednesdayCheckMouseClicked

    private void thursdayCheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thursdayCheckMouseClicked
        // Check if the checkbox is enabled
        if (thursdayCheck.isEnabled())
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new day and time period
        initializeEquipmentSection();
    }//GEN-LAST:event_thursdayCheckMouseClicked

    private void fridayCheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fridayCheckMouseClicked
        // Check if the checkbox is enabled
        if (fridayCheck.isEnabled())
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new day and time period
        initializeEquipmentSection();
    }//GEN-LAST:event_fridayCheckMouseClicked

    private void saturdayCheckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saturdayCheckMouseClicked
        // Check if the checkbox is enabled
        if (saturdayCheck.isEnabled())
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new day and time period
        initializeEquipmentSection();
    }//GEN-LAST:event_saturdayCheckMouseClicked

    private void selectEquipmentButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectEquipmentButtonMouseClicked
        // Check if button is enabled
        if (selectEquipmentButton.isEnabled())
        {
            // Check if at least one exercise day is selected
            if (!checkExerciseDayChecked())
            {
                // If at least one exercise day is selected
                // Update the table list used to display the available equipments based on the selected exercise day and exercise time
                // Check if there exists at least one available equipment during the time period
                if (updateAvailableEquipmentList())
                {
                    // Make the select equipment window visible
                    selectEquipmentWindow.setVisible(true);

                    // Disabled the main window
                    this.setEnabled(false);
                }
                else
                {
                    // If there is no available equipment during the time period selected, a popup window will be generated and notify the users to select other time period
                    // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to select another time period
                    JOptionPane.showConfirmDialog(this, "No available equipment during the time period selected! Please change to another time period!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            // If there is no exercise day selected, a popup window will be generated and notify the users to select at least one exercise day
            // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to select at least one exercise day
            JOptionPane.showConfirmDialog(this, "Please select at least one exercise day before selecting equipment!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_selectEquipmentButtonMouseClicked

    private void durationSelectionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_durationSelectionStateChanged
        // Auto-update end time based on the expected duration and start time inputted
        updateEndTime();
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new time period
        initializeEquipmentSection();
    }//GEN-LAST:event_durationSelectionStateChanged

    private void startHourSelectionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_startHourSelectionStateChanged
        // Auto-update end time based on the expected duration and start time inputted
        updateEndTime();
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new time period
        initializeEquipmentSection();
    }//GEN-LAST:event_startHourSelectionStateChanged

    private void startMinuteSelectionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_startMinuteSelectionStateChanged
        // Auto-update end time based on the expected duration and start time inputted
        updateEndTime();
        // Deselect the selectedEquipment as new time has been set and the previous assigned equipment is not necessary available in the new time period
        initializeEquipmentSection();
    }//GEN-LAST:event_startMinuteSelectionStateChanged

    private void exercisePlanTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exercisePlanTableMouseClicked
        // Check if the table/list is enabled
        if (exercisePlanTable.isEnabled())
        {
            // Display the selected exercise plan detail by using displaySelectedTrainerDetail() function
            displaySelectedExercisePlanDetail();
            // Update the function of jButton1 (dynamic button) to allow modify function after clicking by using updatejButton1(1) function
            updatejButton1(1);
        }
    }//GEN-LAST:event_exercisePlanTableMouseClicked

    private void addExercisePlanButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addExercisePlanButtonMouseClicked
        // Display a new form used to allow users using the form to input new exercise plan data and add the exercise plan to the system
        displayNewExercisePlanForm();
        // Update the function of jButton1 (dynamic button) to allow submit/confirm/add exercise plan function after clicking by using updatejButton1(3) function
        updatejButton1(3);
    }//GEN-LAST:event_addExercisePlanButtonMouseClicked

    private void returnButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnButtonMouseClicked
        // Create again the trainer main menu window
        new TrainerMainMenu();
        // Dispose the window when the return to trainer menu button is clicked (**This action will close the manage exercise plan window)
        this.dispose();
    }//GEN-LAST:event_returnButtonMouseClicked

    private void equipmentSelectButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_equipmentSelectButtonMouseClicked
        // Variable used to store the row number of the data row selected from the list/table
        int rowSelected = equipmentTable.getSelectedRow();

        // Check if there is item selected in the table
        if (rowSelected >= 0)
        {
            // variable used to obtain the data in the first column of the row selected (which is equipment ID)
            int selectedEquipmentID = (int)equipmentTable.getValueAt(rowSelected, 0);

            // Assign the selectedEquipment with equipment with same equipment ID with selectedEquipmentID
            selectedEquipment = GymManagementApp.getEquipment(selectedEquipmentID);

            // Hide the select equipment window
            selectEquipmentWindow.setVisible(false);

            // Enabled the main window
            this.setEnabled(true);
            this.requestFocus();

            // Update the equipment fields with the selected equipment information
            updateEquipmentFields();
        }
        else
        // If there is no item selected, a popup window will be generated and notify the users to select an item from the list
        // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to select at least one item from the list
        JOptionPane.showConfirmDialog(this, "Please select an equipment from the list!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_equipmentSelectButtonMouseClicked

    // This function is called when the cancel select equipment button in the select equipment window is clicked
    // This function is used to close the select equipment window
    private void equipmentCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_equipmentCancelButtonMouseClicked
        // Dispose the select equipment window
        selectEquipmentWindow.dispose();
        // Enabled the main window
        this.setEnabled(true);
        this.requestFocus();
    }//GEN-LAST:event_equipmentCancelButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EquipmentDetailLabel;
    private javax.swing.JButton addExercisePlanButton;
    private javax.swing.JTextArea descriptionField;
    private javax.swing.JScrollPane descriptionFieldScrollPane;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JSpinner durationSelection;
    private javax.swing.JSpinner endHourSelection;
    private javax.swing.JSpinner endMinuteSelection;
    private javax.swing.JLabel endTimeLabel;
    private javax.swing.JButton equipmentCancelButton;
    private javax.swing.JTextField equipmentIDField;
    private javax.swing.JLabel equipmentIDLabel;
    private javax.swing.JLabel equipmentListLabel;
    private javax.swing.JTextField equipmentNameField;
    private javax.swing.JLabel equipmentNameLabel;
    private javax.swing.JButton equipmentSelectButton;
    private javax.swing.JTable equipmentTable;
    private javax.swing.JScrollPane equipmentTableScrollPane;
    private javax.swing.JLabel exerciseDayLabel;
    private javax.swing.JPanel exercisePlanDetailFormPanel;
    private javax.swing.JLabel exercisePlanDetailLabel;
    private javax.swing.JPanel exercisePlanDetailPanel;
    private javax.swing.JLabel exercisePlanListLabel;
    private javax.swing.JPanel exercisePlanListPanel;
    private javax.swing.JTable exercisePlanTable;
    private javax.swing.JScrollPane exercisePlanTableScrollPane;
    private javax.swing.JLabel expectedDurationLabel;
    private javax.swing.JCheckBox fridayCheck;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox mondayCheck;
    private javax.swing.JTextField planIDField;
    private javax.swing.JLabel planIDLabel;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JButton returnButton;
    private javax.swing.JCheckBox saturdayCheck;
    private javax.swing.JButton selectEquipmentButton;
    private javax.swing.JPanel selectEquipmentPanel;
    private javax.swing.JFrame selectEquipmentWindow;
    private javax.swing.JSpinner startHourSelection;
    private javax.swing.JSpinner startMinuteSelection;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JCheckBox sundayCheck;
    private javax.swing.JCheckBox thursdayCheck;
    private javax.swing.JLabel timeIndicator1;
    private javax.swing.JLabel timeIndicator2;
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
