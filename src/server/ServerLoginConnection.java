/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.Client;
import dao.UserDAO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thinh
 */
public class ServerLoginConnection implements Runnable {

    DataInputStream dis;
    DataOutputStream dos;
    ServerLogin server;
    private Socket socket;
    private static final int PORT_NUMBER = 5555;

    public ServerLoginConnection(ServerLogin server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        try {

            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            while (!socket.isClosed()) {
                while (dis.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.getStackTrace();
                    }
                }
                String textIn = dis.readUTF();
//                System.out.println("login nhan tu clien la: " + textIn);
                String process[] = textIn.split("\\$");
                String type = process[0];
                String username = process[1];
                String pass = process[2];
//                System.out.println("tach chuoi login " + type + username + pass);
                if (type.equals("login")) {
                    UserDAO udao = new UserDAO();
                    Client client = new Client();
                    client.user.setUsername(username);
                    client.user.setPassword(pass);
                    int check = udao.checkUserLogin(client.user);
                    dos.writeInt(check);
                    dos.flush();
                }
            }

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
}
