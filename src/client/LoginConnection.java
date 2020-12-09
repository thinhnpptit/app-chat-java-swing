/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author thinh
 */
public class LoginConnection implements Runnable {

    private Client client;
    private Socket socket;
    private static final String HOST = "192.168.43.208";
    private static final int PORT_NUMBER = 5555;
    private DataInputStream din;
    private DataOutputStream dout;
    int check = 0;
    LoginFrm loginFrm;

    LoginConnection(LoginFrm lf, Client c) {
        /*Khởi tạo socket*/
        client = c;
        try {
            socket = new Socket(HOST, PORT_NUMBER);
        } catch (IOException ex) {
            Logger.getLogger(FileConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        loginFrm = lf;
    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            while (!socket.isClosed()) {
                while (din.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.getStackTrace();
                        close();
                    }
                }
//                Đọc chuỗi từ server 
                if (din.available() > 0) {

                    int msgFromServer = din.readInt();
                    check = msgFromServer;
                    System.out.println("check server send to client: " + check);
                    switch (check) {
                        case 0:
                            JOptionPane.showMessageDialog(loginFrm, "Incorrect username and/or password!");
                            break;
                        case 1:
//                    Client client = new Client();
//                    client.setUserName(username);
                            ClientFrame cf = new ClientFrame(client);
                            cf.setVisible(true);
                            loginFrm.dispose();
                            break;
                        default:
                            JOptionPane.showMessageDialog(loginFrm, "Incorrect username and/or password!");
                            break;
                    }
//                      
                }
            }
        } catch (IOException ex) {
            ex.getStackTrace();
            close();
        }
    }

    public int getResult() {
        return check;
    }

    public void sendStringToServer(String text) {
        /*Gửi chuỗi đến server*/
        try {
            System.out.println(text);
            dout.writeUTF(text);
            dout.flush();
        } catch (IOException ex) {
            ex.getStackTrace();
            close();
        }
    }

    public void close() {
        try {
            din.close();
            dout.close();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

}
