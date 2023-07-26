/*
This class is used to generate the manage subscription menu used by gym dministrators
*/
package gp6.gymmanagementapp;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewSubscriptionMenu extends javax.swing.JFrame 
{
    // Variable used to store the selected subscription from the subscription list
    private Subscription selectedSubscription;
    /**
     * Creates new form ViewSubscriptionMenu
     */
    public ViewSubscriptionMenu() 
    {
        initComponents();
        // Set the menu to be visible on the screen
        this.setVisible(true);
        // Used to update the content in the list/table of subscription list if the logged in customer has subscribe some exercise plans before
        updateTableRow();
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
        
        // Variable used to store the subscription related to the logged in customer
        ArrayList<Subscription> tempSubscriptionList = new ArrayList();
        
        // Enhanced for-loop to loop over all subscription list
        for (Subscription subscription : GymManagementApp.subscriptionList)
            // Check if the customer ID registered in the subscription is the same as the logged in customer's ID
            if (subscription.getCustomer().getCustomerID() == CustomerMainMenu.loggedInCustomer.getCustomerID())
                // Add the looped subscription if they are the same
                tempSubscriptionList.add(subscription);
        
        // Check if the whether the logged in customer has subscribe to a plan
        if (tempSubscriptionList.isEmpty())
        {
            // If there is no subscription subscribed, a popup window will be generated and notify the users about they don't have subscribed plans
            // The users can select either "Yes" or "Cancel" or close the window as three options will lead to same result which is returning users to the customer main menu
            JOptionPane.showConfirmDialog(this, "You have no subscribed exercise plan!", "Error Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            
            // Dispose this menu
            this.dispose();
            
            // Return back to customer main menu
            new CustomerMainMenu();
        }
        
        // Sorting the temp subscription list based on the subscription ID number of the subscription
        Collections.sort(tempSubscriptionList, (Subscription subscription1, Subscription subscription2) -> subscription1.getSubscriptionID() - subscription2.getSubscriptionID());
        
        // Getting the model of table used to display the subscription list
        DefaultTableModel model = (DefaultTableModel) subscriptionTable.getModel();
        // Clear the content in the list/table
        model.setRowCount(0);
        
        // Variables used to store the data to be contained in the list/table which has 5 columns: subscription ID, subscribed subscription ID, subscription start date and end date
        Object[] rowData = new Object[4];
        
        // Enhanced for-loop to loop the sorted subscription list
        for (Subscription subscription : tempSubscriptionList)
        {
            // Storing related data which are subscription ID, subscribed subscription ID, subscription start date and end date, ID of customer subscribed to the plan
            rowData[0] = subscription.getSubscriptionID();
            rowData[1] = subscription.getExercisePlan().getPlanID();
            rowData[2] = subscription.getDatePurchased().toString();
            rowData[3] = subscription.getDateEnd().toString();
            
            // Adding the data into the list/table
            model.addRow(rowData);
        }
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
        
        // Assign the data to display the data in form format
        // Assign the subscription ID of the selected subscription to the subscriptionIDField
        subscriptionIDField.setText(String.valueOf(selectedSubscription.getSubscriptionID()));
        // Assign the start date of the selected subscription to the startDateField
        startDateField.setText(selectedSubscription.getDatePurchased().toString());
        // Assign the end date of the selected subscription to the endDateField
        endDateField.setText(selectedSubscription.getDateEnd().toString());
        
        // Assign the data to exercise plan and trainer-related fields
        updateExercisePlanAndTrainerFields();
        
        // Assign the total payment of the selected subscription to the totalPaymentField
        totalPaymentField.setText(String.valueOf(selectedSubscription.getTotalAmountPaid()));
    }
    
    // This function is used to update the subscription and its trainer details in the detail panel
    // Is used when:
    // 1) select subscription from the table (update the subscription and trainer details based on the information stored in the subscription)
    // 2) adding new subscription (whenever choosing different subscription from the select subscription window)
    private void updateExercisePlanAndTrainerFields()
    {
            // Update the exercisePlanIDField based on the selected subscription's information
            exercisePlanIDField.setText(String.valueOf(selectedSubscription.getExercisePlan().getPlanID()));
            // Update the priceField based on the selected subscription's information based on the customer type
            priceField.setText(String.format("%.2f", selectedSubscription.getExercisePlan().getPrice(CustomerMainMenu.loggedInCustomer)));
            // Update the descriptionField based on the selected subscription's information
            descriptionField.setText(selectedSubscription.getExercisePlan().getPlanDescription());

            // Initialize all checkboxes for exercise day to be not selected
            mondayCheck.setSelected(false);
            tuesdayCheck.setSelected(false);
            wednesdayCheck.setSelected(false);
            thursdayCheck.setSelected(false);
            fridayCheck.setSelected(false);
            saturdayCheck.setSelected(false);
            sundayCheck.setSelected(false);

            // Checking checkboxes for each day according to the exercise day recorded in the selected subscription
            for (int day : selectedSubscription.getExercisePlan().getExerciseDay())
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
            startHourSelection.setValue(selectedSubscription.getExercisePlan().getStartTime().getHour());
            startMinuteSelection.setValue(selectedSubscription.getExercisePlan().getStartTime().getMinute());
            // Update the end time fields based on the selected subscription's information
            endHourSelection.setValue(selectedSubscription.getExercisePlan().getEndTime().getHour());
            endMinuteSelection.setValue(selectedSubscription.getExercisePlan().getEndTime().getMinute());

            // Update the trainerIDField based on the selected subscription's information
            trainerIDField.setText(selectedSubscription.getExercisePlan().getTrainer().getID());
            // Update the trainerNameField based on the subscription's information
            trainerNameField.setText(selectedSubscription.getExercisePlan().getTrainer().getFirstName() + " " + selectedSubscription.getExercisePlan().getTrainer().getLastName());
            // Update the trainerPhoneField based on the subscription's information
            trainerPhoneField.setText(selectedSubscription.getExercisePlan().getTrainer().getPhone());
            // Update the trainerEmailField based on the subscription's information
            trainerEmailField.setText(selectedSubscription.getExercisePlan().getTrainer().getEmail());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        subscriptionDetailPanel = new javax.swing.JPanel();
        subscriptionDetailFormPanel = new javax.swing.JPanel();
        subscriptionDetailLabel = new javax.swing.JLabel();
        subscriptionIDLabel = new javax.swing.JLabel();
        subscriptionIDField = new javax.swing.JTextField();
        totalPaymentLabel = new javax.swing.JLabel();
        totalPaymentField = new javax.swing.JTextField();
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
        subscriptionListPanel = new javax.swing.JPanel();
        subscriptionListLabel = new javax.swing.JLabel();
        subscriptionTableScrollPane = new javax.swing.JScrollPane();
        subscriptionTable = new javax.swing.JTable();
        returnButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("View Owned Subscriptions");
        setResizable(false);

        subscriptionDetailFormPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        subscriptionDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        subscriptionDetailLabel.setText("<html><u>Subscription Detail:</u>");

        subscriptionIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionIDLabel.setText("Subscription ID:");

        subscriptionIDField.setEditable(false);
        subscriptionIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        subscriptionIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        subscriptionIDField.setEnabled(false);

        totalPaymentLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        totalPaymentLabel.setText("Total Amount Paid:");

        totalPaymentField.setEditable(false);
        totalPaymentField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        totalPaymentField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        totalPaymentField.setEnabled(false);

        startDateLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startDateLabel.setText("Start Date:");

        startDateField.setEditable(false);
        startDateField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        startDateField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        startDateField.setEnabled(false);

        endDateLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endDateLabel.setText("End Date:");

        endDateField.setEditable(false);
        endDateField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        endDateField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        endDateField.setEnabled(false);

        exercisePlanDetailLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        exercisePlanDetailLabel.setText("<html><u>Exercise Plan Detail:</u>");

        exercisePlanIDLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        exercisePlanIDLabel.setText("Exercise ID:");

        exercisePlanIDField.setEditable(false);
        exercisePlanIDField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        exercisePlanIDField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        exercisePlanIDField.setEnabled(false);

        priceLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        priceLabel.setText("Plan Price:");

        priceField.setEditable(false);
        priceField.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        priceField.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        priceField.setEnabled(false);

        descriptionLabel.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        descriptionLabel.setText("Exercise Description:");

        descriptionFieldScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        descriptionField.setEditable(false);
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

        javax.swing.GroupLayout subscriptionDetailFormPanelLayout = new javax.swing.GroupLayout(subscriptionDetailFormPanel);
        subscriptionDetailFormPanel.setLayout(subscriptionDetailFormPanelLayout);
        subscriptionDetailFormPanelLayout.setHorizontalGroup(
            subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(subscriptionDetailFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subscriptionDetailLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(startDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(endDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endDateField))
                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(trainerIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerNameField))
                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(trainerPhoneLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerEmailLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainerEmailField))
                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(descriptionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descriptionFieldScrollPane))
                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(exercisePlanIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exercisePlanIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceField))
                    .addComponent(exercisePlanDetailLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addComponent(subscriptionIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subscriptionIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPaymentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totalPaymentField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subscriptionDetailFormPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(trainerDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(subscriptionDetailFormPanelLayout.createSequentialGroup()
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
                                        .addComponent(saturdayCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                    .addComponent(startDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(endDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(endDateField)
                    .addComponent(startDateField))
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
                .addContainerGap())
        );

        javax.swing.GroupLayout subscriptionDetailPanelLayout = new javax.swing.GroupLayout(subscriptionDetailPanel);
        subscriptionDetailPanel.setLayout(subscriptionDetailPanelLayout);
        subscriptionDetailPanelLayout.setHorizontalGroup(
            subscriptionDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionDetailPanelLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(subscriptionDetailFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        subscriptionDetailPanelLayout.setVerticalGroup(
            subscriptionDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subscriptionDetailFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subscriptionListLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        subscriptionListLabel.setText("<html><u>Subscription List</u>");

        subscriptionTable.setFont(new java.awt.Font("MS UI Gothic", 0, 14)); // NOI18N
        subscriptionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subscription ID", "Plan ID", "Start Date", "End Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        }
        subscriptionTable.getAccessibleContext().setAccessibleDescription("");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subscriptionListPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(subscriptionTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(subscriptionListPanelLayout.createSequentialGroup()
                        .addGroup(subscriptionListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(subscriptionListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(returnButton))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        subscriptionListPanelLayout.setVerticalGroup(
            subscriptionListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subscriptionListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subscriptionListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subscriptionTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnButton)
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
        // Display the selected subscription detail by using displaySelectedSubscriptionDetail() function
            displaySelectedSubscriptionDetail();
    }//GEN-LAST:event_subscriptionTableMouseClicked
   
    // This function is called whenever the user click on the return to menu button
    // This function is used to close the manage subscription window and return user to the menu
    private void returnButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnButtonMouseClicked
        // Create again the menu window
        new CustomerMainMenu();
        // Dispose the window when the return to menu button is clicked (**This action will close the manage trainer window)
        this.dispose();
    }//GEN-LAST:event_returnButtonMouseClicked
//GEN-FIRST:event_selectEquipmentButtonMouseClicked
//GEN-LAST:event_selectEquipmentButtonMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea descriptionField;
    private javax.swing.JScrollPane descriptionFieldScrollPane;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField endDateField;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JSpinner endHourSelection;
    private javax.swing.JSpinner endMinuteSelection;
    private javax.swing.JLabel endTimeLabel;
    private javax.swing.JLabel exerciseDayLabel;
    private javax.swing.JLabel exercisePlanDetailLabel;
    private javax.swing.JTextField exercisePlanIDField;
    private javax.swing.JLabel exercisePlanIDLabel;
    private javax.swing.JCheckBox fridayCheck;
    private javax.swing.JCheckBox mondayCheck;
    private javax.swing.JTextField priceField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JButton returnButton;
    private javax.swing.JCheckBox saturdayCheck;
    private javax.swing.JTextField startDateField;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JSpinner startHourSelection;
    private javax.swing.JSpinner startMinuteSelection;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JPanel subscriptionDetailFormPanel;
    private javax.swing.JLabel subscriptionDetailLabel;
    private javax.swing.JPanel subscriptionDetailPanel;
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