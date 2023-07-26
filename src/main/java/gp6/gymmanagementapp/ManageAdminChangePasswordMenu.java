package gp6.gymmanagementapp;

import javax.swing.JOptionPane;

public class ManageAdminChangePasswordMenu extends javax.swing.JFrame {

    public static String adminCurrentPassword;
    
    public ManageAdminChangePasswordMenu() {
        initComponents();
        adminCurrentPassword = GymManagementApp.adminPassword;
        this.setVisible(true);
    }

    public void verifyCurrentPassword()
    {
        if(currentPass.getText().equals(adminCurrentPassword))
        {
            //verify if the new password setted is corrected
            verifyNewPassword();
        }
        else
            JOptionPane.showMessageDialog(null, "The current password is wrong! Please try again.");
    }
    
    public void verifyNewPassword()
    {   
        //Password not allowed to be blank
        if(newPass.getText().equals("") && confirmPass.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Password cannot be blank! Please try again.");
        }
        //New password not allowed to be same
        else if(newPass.getText().equals(currentPass.getText()))
        {
             JOptionPane.showMessageDialog(null, "New password cannot be same as the current one! Please try again.");
        }
        //Check if new password is same as confirm password
        else if(newPass.getText().equals(confirmPass.getText()))
        {
            
            GymManagementApp.adminPassword = confirmPass.getText();
            popPassChange();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "The new password and confirm password does not match! Please try again.");
        }
    }
    
    public void popPassChange()
    {
        JOptionPane.showMessageDialog(null, "Password changed!");
        
        new AdminMainMenu();
        
        this.dispose();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        confirmB = new javax.swing.JButton();
        cancelB = new javax.swing.JButton();
        newPass = new javax.swing.JPasswordField();
        confirmPass = new javax.swing.JPasswordField();
        currentPass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Change Admin Password");

        jLabel1.setText("Enter your current password:");

        jLabel2.setText("Enter your new password:");

        jLabel3.setText("Confirm your new password:");

        jLabel4.setFont(new java.awt.Font("MS UI Gothic", 0, 24)); // NOI18N
        jLabel4.setText("Change Password");

        confirmB.setText("Confirm");
        confirmB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBActionPerformed(evt);
            }
        });

        cancelB.setText("Cancel");
        cancelB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBActionPerformed(evt);
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
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(newPass)
                            .addComponent(confirmPass, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(currentPass)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(confirmB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(cancelB)
                .addGap(74, 74, 74))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(currentPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(newPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(confirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmB)
                    .addComponent(cancelB))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBActionPerformed
        new AdminMainMenu();
        
        this.dispose();
    }//GEN-LAST:event_cancelBActionPerformed

    private void confirmBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBActionPerformed
        verifyCurrentPassword();
    }//GEN-LAST:event_confirmBActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelB;
    private javax.swing.JButton confirmB;
    private javax.swing.JPasswordField confirmPass;
    private javax.swing.JPasswordField currentPass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField newPass;
    // End of variables declaration//GEN-END:variables
}
