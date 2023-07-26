/*
This class is used to generate the manage exercise plan menu used by gym dministrators
*/
package gp6.gymmanagementapp;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SubscribeExercisePlanMenu extends javax.swing.JFrame 
{
    // Variable used to store the current selected exercise plan based on the row selected in the exercise plan list
    private ExercisePlan selectedExercisePlan;
    // Variable used to store the date String in startDateField, used for checking whether there is value changed in startDateField
    private String prevStartDate;
    // Variables used to store the start date and end date selected and duration
    private LocalDate startDate;
    private LocalDate endDate;
    private int selectedDuration;
    
    /**
     * Creates new form AdminManageExercisePlanMenu
     */
    public SubscribeExercisePlanMenu() 
    {
        initComponents();
        // Set the menu to be visible on the screen
        this.setVisible(true);
        // Set the default start date field input to system's today date and update end date field
        startDateField.setText(LocalDate.now().toString());
        updateEndDate();
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
        priceField.setText(String.valueOf(String.format("%.2f", selectedExercisePlan.getPrice(CustomerMainMenu.loggedInCustomer))));
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
        
        // Assign the equipment ID number of the equipment used in the selected exercise plan to the equipmentIDField
        // Assign the equipment name of the equipment used in the selected exercise plan to the equipmentNameField
        equipmentIDField.setText(String.valueOf(selectedExercisePlan.getEquipment().getEquipmentID()));
        // Update the equipmentNameField based on the selectedEquipment's information
        equipmentNameField.setText(selectedExercisePlan.getEquipment().getEquipmentName());
        
        // Assign the trainer ID of the trainer of the selected exercise plan to the trainerIDField
        // Assign the name of the trainer of the selected exercise plan to the trainerNameField
        // Assign the phone number of the trainer of the selected exercise plan to the trainerPhoneField
        // Assign the email of the trainer of the selected exercise plan to the trainerEmailField
        trainerIDField.setText(selectedExercisePlan.getTrainer().getID());
        trainerNameField.setText(selectedExercisePlan.getTrainer().getFirstName() + " " + selectedExercisePlan.getTrainer().getLastName());
        trainerPhoneField.setText(selectedExercisePlan.getTrainer().getPhone());
        trainerEmailField.setText(selectedExercisePlan.getTrainer().getEmail());
        
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
        
        // Update the start date and end date field in subscription detail based on the previous selected dates
        subscriptionStartDateField.setText(startDate.toString());
        subscriptionEndDateField.setText(endDate.toString());
        
        // Update the total payment needed to pay based on the selected subscription duration and exercise plan
        totalPaymentField.setText(String.valueOf(String.format("%.2f", Float.valueOf(priceField.getText()) * selectedDuration)));
    }
    
    // Function used to auto update the end date based on the subscription duration and start date inputted
    // Is used when:
    // 1) browsing exercise plan to subscribe
    private void updateEndDate()
    {
        // Update the endDateField
        endDateField.setText(LocalDate.parse(startDateField.getText()).plusMonths(Long.valueOf(subscriptionDurationSelection.getValue().toString())).toString());
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        subscribeExercisePlanWindow = new javax.swing.JFrame();
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
        subscriptionDetailLabel = new javax.swing.JLabel();
        subscriptionStartDateLabel = new javax.swing.JLabel();
        subscriptionStartDateField = new javax.swing.JTextField();
        subscriptionEndDateLabel = new javax.swing.JLabel();
        subscriptionEndDateField = new javax.swing.JTextField();
        totalPaymentLabel = new javax.swing.JLabel();
        totalPaymentField = new javax.swing.JTextField();
        ApplyButton = new javax.swing.JButton();
        exercisePlanListPanel = new javax.swing.JPanel();
        exercisePlanListLabel = new javax.swing.JLabel();
        exercisePlanTableScrollPane = new javax.swing.JScrollPane();
        exercisePlanTable = new javax.swing.JTable();
        returnButton = new javax.swing.JButton();
        selectSubscriptionDuration = new javax.swing.JPanel();
        selectSubscriptionDurationLabel = new javax.swing.JLabel();
        subscriptionDurationLabel = new javax.swing.JLabel();
        subscriptionDurationSelection = new javax.swing.JSpinner();
        startDateLabel = new javax.swing.JLabel();
        startDateField = new javax.swing.JTextField();
        endDateLabel = new javax.swing.JLabel();
        endDateField = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        subscribeExercisePlanWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        subscribeExercisePlanWindow.setTitle("Browse Exercise Plans");
        subscribeExercisePlanWindow.setLocation(new java.awt.Point(250, 75));
        subscribeExercisePlanWindow.setMinimumSize(new java.awt.Dimension(925, 650));
        subscribeExercisePlanWindow.setPreferredSize(new java.awt.Dimension(925, 650));
        subscribeExercisePlanWindow.setResizable(false);

        exercisePlanDetailFormPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        exercisePlanDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        exercisePlanDetailLabel.setText("<html><u>Exercise Plan Detail:</u>");

        planIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        planIDLabel.setText("Plan ID:");

        planIDField.setEditable(false);
        planIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        planIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        planIDField.setEnabled(false);

        priceLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        priceLabel.setText("Price (per Month):");

        priceField.setEditable(false);
        priceField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        priceField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        priceField.setEnabled(false);

        descriptionLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        descriptionLabel.setText("Description:");

        descriptionFieldScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        descriptionField.setEditable(false);
        descriptionField.setColumns(20);
        descriptionField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        descriptionField.setLineWrap(true);
        descriptionField.setRows(5);
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
        equipmentIDField.setEnabled(false);

        equipmentNameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        equipmentNameLabel.setText("Equipment Name:");

        equipmentNameField.setEditable(false);
        equipmentNameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        equipmentNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        equipmentNameField.setEnabled(false);
        equipmentNameField.setName(""); // NOI18N
        equipmentNameField.setPreferredSize(new java.awt.Dimension(209, 21));

        trainerDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        trainerDetailLabel.setText("<html><u>Trainer Detail:</u>");

        trainerIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerIDLabel.setText("Trainer ID:");

        trainerIDField.setEditable(false);
        trainerIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        trainerIDField.setEnabled(false);

        trainerNameLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerNameLabel.setText("Trainer Name:");

        trainerNameField.setEditable(false);
        trainerNameField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerNameField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        trainerNameField.setEnabled(false);

        trainerPhoneLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerPhoneLabel.setText("Phone Number:");

        trainerPhoneField.setEditable(false);
        trainerPhoneField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerPhoneField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        trainerPhoneField.setEnabled(false);

        trainerEmailField.setEditable(false);
        trainerEmailField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerEmailField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        trainerEmailField.setEnabled(false);

        trainerEmailLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        trainerEmailLabel.setText("Email:");

        durationSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        durationSelection.setModel(new javax.swing.SpinnerNumberModel(1, 1, 3, 1));
        durationSelection.setEnabled(false);
        durationSelection.setVerifyInputWhenFocusTarget(false);

        startHourSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startHourSelection.setModel(new javax.swing.SpinnerNumberModel(8, 8, 20, 1));
        startHourSelection.setEnabled(false);

        endHourSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endHourSelection.setModel(new javax.swing.SpinnerNumberModel(8, 8, 23, 1));
        endHourSelection.setEnabled(false);

        startMinuteSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startMinuteSelection.setModel(new javax.swing.SpinnerNumberModel(0, 0, 55, 5));
        startMinuteSelection.setEnabled(false);

        endMinuteSelection.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endMinuteSelection.setModel(new javax.swing.SpinnerNumberModel(0, 0, 55, 5));
        endMinuteSelection.setEnabled(false);

        subscriptionDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        subscriptionDetailLabel.setText("<html><u>Subscription Detail:</u>");

        subscriptionStartDateLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionStartDateLabel.setText("Start Date:");

        subscriptionStartDateField.setEditable(false);
        subscriptionStartDateField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionStartDateField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        subscriptionStartDateField.setEnabled(false);

        subscriptionEndDateLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionEndDateLabel.setText("End Date:");

        subscriptionEndDateField.setEditable(false);
        subscriptionEndDateField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionEndDateField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        subscriptionEndDateField.setEnabled(false);

        totalPaymentLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        totalPaymentLabel.setText("Total Amount Paid:");

        totalPaymentField.setEditable(false);
        totalPaymentField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        totalPaymentField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        totalPaymentField.setEnabled(false);

        javax.swing.GroupLayout exercisePlanDetailFormPanelLayout = new javax.swing.GroupLayout(exercisePlanDetailFormPanel);
        exercisePlanDetailFormPanel.setLayout(exercisePlanDetailFormPanelLayout);
        exercisePlanDetailFormPanelLayout.setHorizontalGroup(
            exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exercisePlanDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
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
                    .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
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
                    .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(equipmentIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(equipmentIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(equipmentNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(equipmentNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                            .addComponent(planIDLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(planIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(priceLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(priceField))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                            .addComponent(descriptionLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(descriptionFieldScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(trainerDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EquipmentDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
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
                            .addComponent(trainerNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exercisePlanDetailFormPanelLayout.createSequentialGroup()
                            .addComponent(subscriptionStartDateLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(subscriptionStartDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(subscriptionEndDateLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(subscriptionEndDateField))
                        .addComponent(subscriptionDetailLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(exercisePlanDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(totalPaymentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPaymentField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subscriptionDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subscriptionStartDateLabel)
                    .addComponent(subscriptionStartDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subscriptionEndDateLabel)
                    .addComponent(subscriptionEndDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exercisePlanDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalPaymentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPaymentField))
                .addContainerGap())
        );

        ApplyButton.setText("Apply");
        ApplyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ApplyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ApplyButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout exercisePlanDetailPanelLayout = new javax.swing.GroupLayout(exercisePlanDetailPanel);
        exercisePlanDetailPanel.setLayout(exercisePlanDetailPanelLayout);
        exercisePlanDetailPanelLayout.setHorizontalGroup(
            exercisePlanDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanDetailPanelLayout.createSequentialGroup()
                .addGroup(exercisePlanDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exercisePlanDetailFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ApplyButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        exercisePlanDetailPanelLayout.setVerticalGroup(
            exercisePlanDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanDetailPanelLayout.createSequentialGroup()
                .addComponent(exercisePlanDetailFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ApplyButton)
                .addContainerGap())
        );

        exercisePlanListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        exercisePlanListLabel.setText("<html><u>Exercise Plan List</u>");

        exercisePlanTable.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N
        exercisePlanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Description", "Trainer Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        exercisePlanTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exercisePlanTable.setInheritsPopupMenu(true);
        exercisePlanTable.setName(""); // NOI18N
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
            exercisePlanTable.getColumnModel().getColumn(0).setPreferredWidth(1);
            exercisePlanTable.getColumnModel().getColumn(1).setResizable(false);
            exercisePlanTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            exercisePlanTable.getColumnModel().getColumn(2).setResizable(false);
            exercisePlanTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        returnButton.setText("<< Back to Select Dates");
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exercisePlanListPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exercisePlanTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(exercisePlanListPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(exercisePlanListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(exercisePlanListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(returnButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        exercisePlanListPanelLayout.setVerticalGroup(
            exercisePlanListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exercisePlanListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exercisePlanListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exercisePlanTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout subscribeExercisePlanWindowLayout = new javax.swing.GroupLayout(subscribeExercisePlanWindow.getContentPane());
        subscribeExercisePlanWindow.getContentPane().setLayout(subscribeExercisePlanWindowLayout);
        subscribeExercisePlanWindowLayout.setHorizontalGroup(
            subscribeExercisePlanWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscribeExercisePlanWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exercisePlanListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exercisePlanDetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        subscribeExercisePlanWindowLayout.setVerticalGroup(
            subscribeExercisePlanWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscribeExercisePlanWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subscribeExercisePlanWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(exercisePlanDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exercisePlanListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Select Subscription Duration");
        setResizable(false);

        selectSubscriptionDurationLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 18)); // NOI18N
        selectSubscriptionDurationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectSubscriptionDurationLabel.setText("Select Subscription Duration");
        selectSubscriptionDurationLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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

        confirmButton.setText("Confirm");
        confirmButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmButtonMouseClicked(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout selectSubscriptionDurationLayout = new javax.swing.GroupLayout(selectSubscriptionDuration);
        selectSubscriptionDuration.setLayout(selectSubscriptionDurationLayout);
        selectSubscriptionDurationLayout.setHorizontalGroup(
            selectSubscriptionDurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, selectSubscriptionDurationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(selectSubscriptionDurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(selectSubscriptionDurationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, selectSubscriptionDurationLayout.createSequentialGroup()
                        .addComponent(subscriptionDurationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subscriptionDurationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, selectSubscriptionDurationLayout.createSequentialGroup()
                        .addComponent(startDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startDateField))
                    .addGroup(selectSubscriptionDurationLayout.createSequentialGroup()
                        .addComponent(endDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endDateField)))
                .addContainerGap())
            .addGroup(selectSubscriptionDurationLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(confirmButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelButton)
                .addGap(31, 31, 31))
        );
        selectSubscriptionDurationLayout.setVerticalGroup(
            selectSubscriptionDurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, selectSubscriptionDurationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectSubscriptionDurationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(selectSubscriptionDurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subscriptionDurationSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subscriptionDurationLabel))
                .addGap(4, 4, 4)
                .addGroup(selectSubscriptionDurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startDateLabel)
                    .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(selectSubscriptionDurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endDateLabel)
                    .addComponent(endDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(selectSubscriptionDurationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmButton)
                    .addComponent(cancelButton))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectSubscriptionDuration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectSubscriptionDuration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   
    private void exercisePlanTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exercisePlanTableMouseClicked
        // Display the selected exercise plan detail
        displaySelectedExercisePlanDetail();
    }//GEN-LAST:event_exercisePlanTableMouseClicked

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

    private void startDateFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_startDateFieldFocusGained
        // Storing temporary start date
        prevStartDate = startDateField.getText();
    }//GEN-LAST:event_startDateFieldFocusGained

    private void confirmButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmButtonMouseClicked
        // Check if start date is valid
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
            // Storing the selected start date to startDate variable
            startDate = LocalDate.parse(startDateField.getText());
            // Storing the selected end date to endDate variable
            endDate = LocalDate.parse(endDateField.getText());
            // Storing the selected duration
            selectedDuration = (int)subscriptionDurationSelection.getValue();

            // Dispose this window
            this.dispose();

            // Update available exercise plan list based on the date period used if there exists available exercise plans during the period selectedd
            if (updateExercisePlanList())
            {
                // If the list is not empty
                // Set exercise plan detail panel to not visible
                exercisePlanDetailPanel.setVisible(false);
                // Open the select exercise plan window
                subscribeExercisePlanWindow.setVisible(true);

                // Disabled the main window
                this.setEnabled(false);
            }
            else
            {
                // If there is no exercise plan in the list, a popup window will be generated and notify the users to change subscription period
                // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is allowing users to return to modify subscription period
                JOptionPane.showConfirmDialog(this, "There is no exercise plan available during the selected period! Please choose other period!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_confirmButtonMouseClicked

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        // Dispose this window
        this.dispose();
        // Return to customer main menu
        new CustomerMainMenu();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void subscriptionDurationSelectionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_subscriptionDurationSelectionStateChanged
        // Update End Date
        updateEndDate();
    }//GEN-LAST:event_subscriptionDurationSelectionStateChanged

    private void returnButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnButtonMouseClicked
        // Dispose subscribe exercise plan window
        subscribeExercisePlanWindow.dispose();
        // Enable select date panel again
        this.setVisible(true);
        this.setEnabled(true);
    }//GEN-LAST:event_returnButtonMouseClicked

    private void ApplyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ApplyButtonMouseClicked
        // Prompt message to let user to confirm subscribe to the exercise plan
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to subscribe to the plan?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        // Check if the user is selecting "Yes"
        if (answer == JOptionPane.YES_OPTION)
        {
            // Sorting the subscription list based on the subscription ID number of the subscription
            Collections.sort(GymManagementApp.subscriptionList, (Subscription subscription1, Subscription subscription2) -> subscription1.getSubscriptionID() - subscription2.getSubscriptionID());
            // Automatically generate subscription ID based on available ID
            int availableID = 1;
            // Enhanced for-loop to loop over subscription list
            for (Subscription subscription : GymManagementApp.subscriptionList)
            {
                // Check if the current looped subscription has the same ID with the current looped available ID
                if (availableID != subscription.getSubscriptionID())
                    // If not equal, the ID is not used, break the loop as new available ID found
                    break;
                
                // Increment to available ID to test the available ID
                availableID++;
            }
            
            // Add new subscription to the system
            GymManagementApp.subscriptionList.add(new Subscription(availableID, CustomerMainMenu.loggedInCustomer, selectedExercisePlan, 
                                    startDate, endDate, Float.valueOf(totalPaymentField.getText())));
            
            // Message to notify users that they have successfully apply for the exercise plan
            JOptionPane.showConfirmDialog(this, "New subscription has been applied!\nYou can check it in 'View Owned Subscription' in the customer main menu!\nThank You!", "Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            
            // Dispose this menu
            this.dispose();
            // Dispose browse exercise plan menu
            subscribeExercisePlanWindow.dispose();
            // Return back to customer main menu
            new CustomerMainMenu();
        }
    }//GEN-LAST:event_ApplyButtonMouseClicked
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApplyButton;
    private javax.swing.JLabel EquipmentDetailLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton confirmButton;
    private javax.swing.JTextArea descriptionField;
    private javax.swing.JScrollPane descriptionFieldScrollPane;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JSpinner durationSelection;
    private javax.swing.JTextField endDateField;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JSpinner endHourSelection;
    private javax.swing.JSpinner endMinuteSelection;
    private javax.swing.JLabel endTimeLabel;
    private javax.swing.JTextField equipmentIDField;
    private javax.swing.JLabel equipmentIDLabel;
    private javax.swing.JTextField equipmentNameField;
    private javax.swing.JLabel equipmentNameLabel;
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
    private javax.swing.JCheckBox mondayCheck;
    private javax.swing.JTextField planIDField;
    private javax.swing.JLabel planIDLabel;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JButton returnButton;
    private javax.swing.JCheckBox saturdayCheck;
    private javax.swing.JPanel selectSubscriptionDuration;
    private javax.swing.JLabel selectSubscriptionDurationLabel;
    private javax.swing.JTextField startDateField;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JSpinner startHourSelection;
    private javax.swing.JSpinner startMinuteSelection;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JFrame subscribeExercisePlanWindow;
    private javax.swing.JLabel subscriptionDetailLabel;
    private javax.swing.JLabel subscriptionDurationLabel;
    private javax.swing.JSpinner subscriptionDurationSelection;
    private javax.swing.JTextField subscriptionEndDateField;
    private javax.swing.JLabel subscriptionEndDateLabel;
    private javax.swing.JTextField subscriptionStartDateField;
    private javax.swing.JLabel subscriptionStartDateLabel;
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