
package gp6.gymmanagementapp;

import javax.swing.JOptionPane;

public class AdminMainMenu extends javax.swing.JFrame 
{
    // Variable used to store the Staff object that is logging in to the system
    public static String AdminUserN;
    public static String AdminPassW;
    
    /**
     * Creates new form AdminMainMenu
     */
    public AdminMainMenu() 
    {
        initComponents();
        // Set the menu to be visible on the screen
        this.setVisible(true);
        // Change the greeting target by using the login credential from the admin login menu
        staffName.setText("Admin");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeText = new javax.swing.JLabel();
        staffName = new javax.swing.JLabel();
        promptLabel = new javax.swing.JLabel();
        manageTrainerButton = new javax.swing.JButton();
        manageCustomerButton = new javax.swing.JButton();
        manageExercisePlanButton = new javax.swing.JButton();
        manageSubscriptionButton = new javax.swing.JButton();
        manageEquipmentButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        manageStaffDetail = new javax.swing.JButton();
        changeGymName = new javax.swing.JButton();
        changePassword = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Admin Main Menu");
        setResizable(false);

        welcomeText.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        welcomeText.setText("Welcome,");

        staffName.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N

        promptLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 18)); // NOI18N
        promptLabel.setText("What would you like to do today?");

        manageTrainerButton.setText("<html><center>Manage<br>Trainers");
        manageTrainerButton.setToolTipText("");
        manageTrainerButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manageTrainerButton.setContentAreaFilled(false);
        manageTrainerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageTrainerButton.setFocusPainted(false);
        manageTrainerButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manageTrainerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageTrainerButtonMouseClicked(evt);
            }
        });

        manageCustomerButton.setText("<html><center>Manage<br>Customers");
        manageCustomerButton.setToolTipText("");
        manageCustomerButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manageCustomerButton.setContentAreaFilled(false);
        manageCustomerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageCustomerButton.setFocusPainted(false);
        manageCustomerButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manageCustomerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageCustomerButtonMouseClicked(evt);
            }
        });

        manageExercisePlanButton.setText("<html><center>Manage<br>Exercise Plans");
        manageExercisePlanButton.setToolTipText("");
        manageExercisePlanButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manageExercisePlanButton.setContentAreaFilled(false);
        manageExercisePlanButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageExercisePlanButton.setFocusPainted(false);
        manageExercisePlanButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manageExercisePlanButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageExercisePlanButtonMouseClicked(evt);
            }
        });

        manageSubscriptionButton.setText("<html><center>Manage<br>Subscriptions");
        manageSubscriptionButton.setToolTipText("");
        manageSubscriptionButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manageSubscriptionButton.setContentAreaFilled(false);
        manageSubscriptionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageSubscriptionButton.setFocusPainted(false);
        manageSubscriptionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manageSubscriptionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageSubscriptionButtonMouseClicked(evt);
            }
        });

        manageEquipmentButton.setText("<html><center>Manage<br>Equipment");
        manageEquipmentButton.setToolTipText("");
        manageEquipmentButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manageEquipmentButton.setContentAreaFilled(false);
        manageEquipmentButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageEquipmentButton.setFocusPainted(false);
        manageEquipmentButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manageEquipmentButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageEquipmentButtonMouseClicked(evt);
            }
        });

        logoutButton.setForeground(new java.awt.Color(255, 0, 0));
        logoutButton.setText("Logout");
        logoutButton.setToolTipText("");
        logoutButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logoutButton.setContentAreaFilled(false);
        logoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        manageStaffDetail.setText("<html><center>Manage <br>Staff Detail");
        manageStaffDetail.setToolTipText("");
        manageStaffDetail.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        manageStaffDetail.setContentAreaFilled(false);
        manageStaffDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageStaffDetail.setFocusPainted(false);
        manageStaffDetail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manageStaffDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageStaffDetailMouseClicked(evt);
            }
        });

        changeGymName.setText("<html><center>Change<br>Gym Name");
        changeGymName.setToolTipText("");
        changeGymName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        changeGymName.setContentAreaFilled(false);
        changeGymName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        changeGymName.setFocusPainted(false);
        changeGymName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        changeGymName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeGymNameMouseClicked(evt);
            }
        });

        changePassword.setText("<html><center>Change<br>Password");
        changePassword.setToolTipText("");
        changePassword.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        changePassword.setContentAreaFilled(false);
        changePassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        changePassword.setFocusPainted(false);
        changePassword.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        changePassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changePasswordMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(promptLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(welcomeText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(staffName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(logoutButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(manageSubscriptionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(manageStaffDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(changeGymName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(changePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(manageTrainerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(manageCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(manageEquipmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(manageExercisePlanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(welcomeText, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(staffName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(promptLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageTrainerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageEquipmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageExercisePlanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageSubscriptionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageStaffDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeGymName, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void manageTrainerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageTrainerButtonMouseClicked
        // Initialize manage trainer menu when "Manage Trainers" button is clicked
        new ManageTrainerMenu();
        // Dispose the window when the user decide to manage trainer
        this.dispose();
    }//GEN-LAST:event_manageTrainerButtonMouseClicked

    private void manageCustomerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageCustomerButtonMouseClicked
        // Initialize manage customer menu when "Manage Customers" button is clicked
        new ManageCustomerMenu();
        // Dispose the window when the user decide to manage customer
        this.dispose();
    }//GEN-LAST:event_manageCustomerButtonMouseClicked

    private void manageExercisePlanButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageExercisePlanButtonMouseClicked
        // Initialize manage exercise plan menu when "Manage Exercise Plans" button is clicked
        new ManageExercisePlanMenu();
        // Dispose the window when the user decide to manage exercise plan
        this.dispose();
    }//GEN-LAST:event_manageExercisePlanButtonMouseClicked

    private void manageSubscriptionButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageSubscriptionButtonMouseClicked
        // Initialize manage exercise plan menu when "Manage Subscriptions" button is clicked
        new ManageSubscriptionMenu();
        // Dispose the window when the user decide to manage subscription
        this.dispose();
    }//GEN-LAST:event_manageSubscriptionButtonMouseClicked

    private void manageEquipmentButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageEquipmentButtonMouseClicked
        // Initialize manage equipment menu when "Manage Equipment" button is clicked
        new ManageEquipmentMenu();
        // Dispose the window when the user decide to manage equipment
        this.dispose();
    }//GEN-LAST:event_manageEquipmentButtonMouseClicked

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // Initialize a logout warning message to confirm users want to logout the system or not
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout the system?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (answer == JOptionPane.YES_OPTION)
        {
            GymManagementApp.writeFiles();
        }           
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void manageStaffDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageStaffDetailMouseClicked
        new ManageStaffDetailMenu();
        
        this.dispose();
    }//GEN-LAST:event_manageStaffDetailMouseClicked

    private void changeGymNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeGymNameMouseClicked
        new ManageChangeGymName();
        
        this.dispose();
    }//GEN-LAST:event_changeGymNameMouseClicked

    private void changePasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changePasswordMouseClicked
        new ManageAdminChangePasswordMenu();
        
        this.dispose();
    }//GEN-LAST:event_changePasswordMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeGymName;
    private javax.swing.JButton changePassword;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton manageCustomerButton;
    private javax.swing.JButton manageEquipmentButton;
    private javax.swing.JButton manageExercisePlanButton;
    private javax.swing.JButton manageStaffDetail;
    private javax.swing.JButton manageSubscriptionButton;
    private javax.swing.JButton manageTrainerButton;
    private javax.swing.JLabel promptLabel;
    private javax.swing.JLabel staffName;
    private javax.swing.JLabel welcomeText;
    // End of variables declaration//GEN-END:variables
}