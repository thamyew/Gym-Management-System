
package gp6.gymmanagementapp;

import javax.swing.JOptionPane;

public class CustomerMainMenu extends javax.swing.JFrame 
{
    // Variable used to store the Customer object that is logging in to the system
    public static Customer loggedInCustomer = null;
    
    /**
     * Creates new form CustomerMainMenu
     */
    public CustomerMainMenu() 
    {
        initComponents();
        // Set the menu to be visible on the screen
        this.setVisible(true);
        // Change the greeting target by using the login credential from the customer login menu
        staffName.setText("customer " + loggedInCustomer.getFirstName() + " " + loggedInCustomer.getLastName());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeText = new javax.swing.JLabel();
        staffName = new javax.swing.JLabel();
        promptLabel = new javax.swing.JLabel();
        subscribeExercisePlanButton = new javax.swing.JButton();
        viewOwnedSubscriptionsButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Staff Main Menu");
        setResizable(false);

        welcomeText.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        welcomeText.setText("Welcome,");

        staffName.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N

        promptLabel.setFont(new java.awt.Font("MS UI Gothic", 0, 18)); // NOI18N
        promptLabel.setText("What would you like to do today?");

        subscribeExercisePlanButton.setText("<html><center>Subscribe New<br>Exercise Plan");
        subscribeExercisePlanButton.setToolTipText("");
        subscribeExercisePlanButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        subscribeExercisePlanButton.setContentAreaFilled(false);
        subscribeExercisePlanButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        subscribeExercisePlanButton.setFocusPainted(false);
        subscribeExercisePlanButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        subscribeExercisePlanButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subscribeExercisePlanButtonMouseClicked(evt);
            }
        });

        viewOwnedSubscriptionsButton.setText("<html><center>View Owned<br>Subscriptions");
        viewOwnedSubscriptionsButton.setToolTipText("");
        viewOwnedSubscriptionsButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        viewOwnedSubscriptionsButton.setContentAreaFilled(false);
        viewOwnedSubscriptionsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        viewOwnedSubscriptionsButton.setFocusPainted(false);
        viewOwnedSubscriptionsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        viewOwnedSubscriptionsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewOwnedSubscriptionsButtonMouseClicked(evt);
            }
        });
        viewOwnedSubscriptionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewOwnedSubscriptionsButtonActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(promptLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(welcomeText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(staffName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(subscribeExercisePlanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(viewOwnedSubscriptionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewOwnedSubscriptionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subscribeExercisePlanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 39, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void subscribeExercisePlanButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subscribeExercisePlanButtonMouseClicked
        // Initialize subscribe exercise plan menu when "Subscribe New Exercise Plan" button is clicked
        new SubscribeExercisePlanMenu();
        // Dispose the window when the user decide to manage trainer
        this.dispose();
    }//GEN-LAST:event_subscribeExercisePlanButtonMouseClicked

    private void viewOwnedSubscriptionsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewOwnedSubscriptionsButtonMouseClicked
        // Initialize view subscription menu when "View Owned Subscription" button is clicked
        new ViewSubscriptionMenu();
        // Dispose the window when the user decide to manage customer
        this.dispose();
    }//GEN-LAST:event_viewOwnedSubscriptionsButtonMouseClicked

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // Initialize a logout warning message to confirm users want to logout the system or not
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout the system?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (answer == JOptionPane.YES_OPTION)
            GymManagementApp.writeFiles(); // Write all current data to files and exit the application
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void viewOwnedSubscriptionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewOwnedSubscriptionsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewOwnedSubscriptionsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel promptLabel;
    private javax.swing.JLabel staffName;
    private javax.swing.JButton subscribeExercisePlanButton;
    private javax.swing.JButton viewOwnedSubscriptionsButton;
    private javax.swing.JLabel welcomeText;
    // End of variables declaration//GEN-END:variables
}