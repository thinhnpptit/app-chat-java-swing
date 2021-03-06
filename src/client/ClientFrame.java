package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;

/**
 *
 * @author MyPC
 */
public class ClientFrame extends javax.swing.JFrame {

    private Client client;

    /**
     * Creates new form Client
     */
    public ClientFrame() {
        initComponents();
    }

    public ClientFrame(Client c) {
        client = c;
        initComponents();
        client.setConnections(this);
        jLabel1.setText(client.user.getUsername());
        addWindowListener(new WindowAdapter() {/*khi đóng client gửi chuỗi đến server*/
            @Override
            public void windowClosing(WindowEvent e) {
                client.tc.sendStringToServer("close$" + client.user.getUsername());
                client.fc.sendStringToServer("close$" + client.user.getUsername());
            }
        });

    }

    public void refreshListUser(String name) {
        listUser.append(name + "\n");
    }

    public void refreshListNotify(String text) {
        listNotify.append("\n"+text  );
    }

    public void refreshComboUser(String name) {
        comboUser.addItem(name);
    }

    public String getSelectedItem() {
        return (String) comboUser.getModel().getSelectedItem();
    }

    public void setSelectedItem(String item) {
        comboUser.getModel().setSelectedItem((Object) item);
    }

    public void clearUser() {
        listUser.setText("");
        String choices[] = {"Everyone"};
        comboUser.setModel(new DefaultComboBoxModel(choices));
    }

    public JTextPane getChatbox() {
        return chatbox;
    }
    public void setLabel(int s){
        String a = Integer.toString(s);
        jLabel2.setText(a);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        comboUser = new javax.swing.JComboBox<>();
        fileButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        sendMessageBox = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listUser = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatbox = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        listNotify = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CHAT");

        comboUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Everyone" }));
        comboUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUserActionPerformed(evt);
            }
        });

        fileButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fileButton.setText("FILE");
        fileButton.setToolTipText("");
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        sendMessageBox.setColumns(20);
        sendMessageBox.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        sendMessageBox.setLineWrap(true);
        sendMessageBox.setRows(5);
        sendMessageBox.setWrapStyleWord(true);
        jScrollPane4.setViewportView(sendMessageBox);

        sendButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sendButton.setText("SEND");
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendButtonMouseClicked(evt);
            }
        });
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        listUser.setEditable(false);
        listUser.setColumns(20);
        listUser.setFont(new java.awt.Font(".VnTeknicalH", 0, 16)); // NOI18N
        listUser.setLineWrap(true);
        listUser.setRows(5);
        listUser.setWrapStyleWord(true);
        jScrollPane3.setViewportView(listUser);

        chatbox.setEditable(false);
        chatbox.setContentType("text/html"); // NOI18N
        chatbox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        chatbox.setText("<html>\r\n\n  <head>\r\n\r<style>\n\tfont-size:18px;\n</style>\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      \r\n    </p>\r\n  </body>\r\n</html>\r\n");
        chatbox.setDragEnabled(true);
        chatbox.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                chatboxHyperlinkUpdate(evt);
            }
        });
        jScrollPane2.setViewportView(chatbox);

        listNotify.setColumns(20);
        listNotify.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
        listNotify.setLineWrap(true);
        listNotify.setRows(5);
        listNotify.setText("THÔNG BÁO");
        listNotify.setWrapStyleWord(true);
        jScrollPane1.setViewportView(listNotify);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Tên :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setText("Số lượng: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboUser, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendButton)
                            .addComponent(fileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboUserActionPerformed

    private void sendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendButtonMouseClicked
        // TODO add your handling code here:
        String text = sendMessageBox.getText();
        if (text.length() < 1) {
            sendMessageBox.setText("");
            sendMessageBox.setCaretPosition(0);
        } else {
            /*Gửi tin nhắn text*/
            String sender = client.user.getUsername();
            String recipient = getSelectedItem();
            /*Chuỗi có dạng: text$nguoigui@nguoinhan@tinnhan*/
            String msg = "text$" + sender + "@" + recipient + "@" + text;
            client.tc.sendStringToServer(msg);
            /*thread text gửi đến textserver*/

            sendMessageBox.setText("");
            sendMessageBox.setCaretPosition(0);
        }
    }//GEN-LAST:event_sendButtonMouseClicked

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String filename = f.getName();
            String sender = client.user.getUsername();
            String recipient = getSelectedItem();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS");
            LocalDateTime time = LocalDateTime.now();
            String msg = "file$" + sender + "@" + recipient + "@" + filename + "@" + dtf.format(time);
            client.tc.sendStringToServer(msg);
            
            String path = f.getAbsolutePath();
            client.fc.sendFileToServer(filename + "@" + path + "@" + dtf.format(time));
        }
    }//GEN-LAST:event_fileButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendButtonActionPerformed

    private void chatboxHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_chatboxHyperlinkUpdate
        // TODO add your handling code here:
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            client.fc.fileMeDaddy(evt.getDescription());
//            System.out.println(evt.getDescription());
        }
    }//GEN-LAST:event_chatboxHyperlinkUpdate

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ClientFrame().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane chatbox;
    private javax.swing.JComboBox<String> comboUser;
    private javax.swing.JButton fileButton;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea listNotify;
    private javax.swing.JTextArea listUser;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea sendMessageBox;
    // End of variables declaration//GEN-END:variables
}
