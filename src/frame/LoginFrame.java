/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;
import com.formdev.flatlaf.FlatLightLaf;
import database.Koneksi;
import frame.UserMainFrame;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import model.Pegawai;

/**
 *
 * @author anyariz02
 */
public class LoginFrame extends javax.swing.JFrame {

           
    public LoginFrame() {
        initComponents();
        RoundedFrame();
    }
    
    public void RoundedFrame(){
         setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfUsername = new custom.textfield.TextField();
        tfPassword = new custom.textfield.PasswordField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(45, 45, 45));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel2)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel2)
                .addContainerGap(260, Short.MAX_VALUE))
        );

        bg.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 470));

        jLabel1.setBackground(new java.awt.Color(45, 45, 45));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/kotakotak.png"))); // NOI18N
        bg.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 250, 260));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/closelogin.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        bg.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, -1, -1));

        tfUsername.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tfUsername.setLabelText("Username");
        tfUsername.setLineColor(new java.awt.Color(45, 45, 45));
        tfUsername.setSelectionColor(new java.awt.Color(45, 45, 45));
        bg.add(tfUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 240, -1));

        tfPassword.setEchoChar('\u25cf');
        tfPassword.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tfPassword.setLabelText("Password");
        tfPassword.setLineColor(new java.awt.Color(45, 45, 45));
        tfPassword.setSelectionColor(new java.awt.Color(45, 45, 45));
        tfPassword.setShowAndHide(true);
        bg.add(tfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 240, -1));

        jButton1.setBackground(new java.awt.Color(45, 45, 45));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        bg.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, 100, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        int pilihan = JOptionPane.showConfirmDialog(
                null,
                "Apakah anda yakin ingin keluar?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );
        if(pilihan==0){
         System.exit(0);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Koneksi koneksi = new Koneksi();
        Connection con = koneksi.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String jabatan = "";

        String user = tfUsername.getText();
        String pass = tfPassword.getText();

        try {
            String sql = "SELECT * FROM user WHERE username= ? AND password=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            
            if(rs.next()){
                if(user.equals(rs.getString("username")) &&
                    pass.equals(rs.getString("password"))){
                    jabatan = rs.getString("jabatan");
                    
                    JOptionPane.showMessageDialog(null, "Login Berhasil");
                    
                     if(jabatan.equals("user")){
                        UserMainFrame mf = new UserMainFrame(user);
                        mf.setVisible(true);
                        
                 }else if(jabatan.equals("admin")){
                         AdminMainFrame amf= new AdminMainFrame(user);
                         amf.setVisible(true);
                 }
                    dispose();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Username atau password salah");
            }
        } catch (Exception e) {
            System.err.println("error : " +e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
         try {
           UIManager.setLookAndFeel(new FlatLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
         
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private custom.textfield.PasswordField tfPassword;
    private custom.textfield.TextField tfUsername;
    // End of variables declaration//GEN-END:variables
}