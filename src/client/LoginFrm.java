/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author thinh
 */
import dao.UserDAO;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.User;

public class LoginFrm extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lbSignUp;
    String name = "";
    Client client;

    public LoginFrm(Client c) {
        super("Login");
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Login");

        JPanel pnMain = new JPanel();
        pnMain.setBackground(new Color(0, 204, 204));
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Login");
        lblHome.setForeground(new Color(0, 102, 102));
        Font font = new Font("Tahoma", Font.BOLD, 36);
        lblHome.setFont(font);
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(36.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel pnUsername = new JPanel();
        pnUsername.setBackground(new Color(0, 204, 204));
        pnUsername.setLayout(new FlowLayout());
        pnUsername.add(new JLabel("Username:"));
        pnUsername.add(txtUsername);
        pnMain.add(pnUsername);

        JPanel pnPass = new JPanel();
        pnPass.setBackground(new Color(0, 204, 204));
        pnPass.setLayout(new FlowLayout());
        pnPass.add(new JLabel("Password:"));
        pnPass.add(txtPassword);
        pnMain.add(pnPass);

        btnLogin.addActionListener(this);
        JPanel pnLogin = new JPanel();
        pnLogin.setBackground(new Color(0, 204, 204));
        pnLogin.setLayout(new FlowLayout());
        pnLogin.add(btnLogin);
        pnMain.add(pnLogin);

        //pnMain.add(Box.createRigidArea(new Dimension(0,10)));
        JPanel pnSignUp = new JPanel();
        pnSignUp.setLayout(new FlowLayout());
        pnSignUp.setBackground(new Color(0, 204, 204));
        lbSignUp = new JLabel("Sign Up");
        lbSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                (new SignUpFrm()).setVisible(true);
                setVisible(false);
            }
        });
        pnSignUp.add(lbSignUp);
        pnMain.add(pnSignUp);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));
        //pnMain.add(Box.createRigidArea(new Dimension(0,20)));

        this.setSize(400, 300);
        this.setLocation(0, 0);
        this.setContentPane(pnMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        client = c;
        client.setLoginConnections(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JButton) && (((JButton) e.getSource()).equals(btnLogin))) {
            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());
//            User user = new User();
            client.user.setUsername(username);
            client.user.setPassword(password);

            client.lc.sendStringToServer("login$" + client.user.getUsername()+ "$"+ client.user.getPassword());
        }
        this.dispose();
    }

//    public String getNameUser() {
//        return txtUsername.getText();
//    }


public static void main(String[] args) {
//        LoginFrm myFrame = new LoginFrm();
//        myFrame.setLocationRelativeTo(null);
//        myFrame.setVisible(true);
    }
}
