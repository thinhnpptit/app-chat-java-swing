package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

/**
 *
 * @author MyPC
 */
public class TextConnection implements Runnable {

    private Client client;
    private Socket socket;
    private static final String HOST = "192.168.43.208";
    private static final int PORT_NUMBER = 8080;
    private DataInputStream din;
    private DataOutputStream dout;

    private final ClientFrame GUI;

    TextConnection(ClientFrame cf, Client c) {
        /*Khởi tạo socket*/
        client = c;
        try {
            socket = new Socket(HOST, PORT_NUMBER);
        } catch (IOException ex) {
            Logger.getLogger(FileConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        GUI = cf;
    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            /*Gửi chuỗi newuser*/
            sendStringToServer("newuser$" + client.user.getUsername());
            /*Tạm nghỉ thread khi luồng rảnh*/
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
                    /*Tách chuỗi vd: newuser$user@user@ -> type: newuser, msg: user@user@*/
                    String msgFromServer = din.readUTF();
                    String[] process = msgFromServer.split("\\$");
                    String type = process[0];
                    String msg = process[1];
                    if (type.equals("newuser")) {
                        processUpdateUser(msg);
                    } else if (type.equals("close")) {
                        processClose_UpdateUser(msg);
                    } else if (type.equals("text")) {
                        displayOnChatbox(msg);
                    } else if (type.equals("file")) {
                        displayFileOnChatbox(msg);
                    }
                }
            }
        } catch (IOException ex) {
            ex.getStackTrace();
            close();
        }
    }

    /*Cập nhật danh sách user*/
    public void processUpdateUser(String msg) {
        String selectedItem = getSelectedItem();    //Lưu selecteditem
        clearDisplayUsers();        // xóa dnah sách hiện tại 
        String[] listUser = msg.split("@");     // tách msg theo ký tự @ để ra danh sách user
        int sum=0;
        for (String user : listUser) {
            sum++;
            refreshUser(user);
        }
        GUI.setLabel(sum);
        setSelectedItem(selectedItem);
    }
//Đóng thread và cập nhật danh sách user

    public void processClose_UpdateUser(String msg) {
        String[] split = msg.split("#");
        String closedUser = split[0];
        String msg2 = split[1];
        if (client.user.getUsername().equals(closedUser)) {  // nếu trùng closedUser: ngắt kết nối
            close();
        }
        String selectedItem = getSelectedItem();
        if ((selectedItem.equals(closedUser))) {    // Nếu selecteditem trùng với clesedUser
            selectedItem = "Everyone";              //chuyển thành everyone
        }
        clearDisplayUsers();
        String[] listUser = msg2.split("@");
        int sum=0;
        for (String user : listUser) {
            sum++;
            refreshUser(user);
        }
        setSelectedItem(selectedItem);
        GUI.setLabel(sum);
    }

    public void sendStringToServer(String text) {
        /*Gửi chuỗi đến server*/
        try {
//            System.out.println(text);
            dout.writeUTF(text);
            dout.flush();
        } catch (IOException ex) {
            ex.getStackTrace();
            close();
        }
    }

// Hiển thị tin nhắn lên chatbox
//sen@rep@mes
    public void displayFileOnChatbox(String text) {
        String[] split = text.split("@");
        String sender = split[0];
        String recipient = split[1];
        String message = split[2];
        String fileTime = split[3];
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        HTMLDocument doc = (HTMLDocument) GUI.getChatbox().getStyledDocument();
        try {
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()),
                    "<p style=\"font-size:14px\"><b style=\"color:red;\">" + sender + "</b> to <b style=\"color:blue;\">"
                    + recipient + "</b> <i style=\"color:gray;\"> vào " + dtf.format(time) + "</i>: <a href=\""
                    + message + "@" + client.user.getUsername()+ "@" + fileTime +  "@" + sender+"\">" + message + "</a><br></p>");
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(TextConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayOnChatbox(String text) {
        String[] split = text.split("@");
        String sender = split[0];
        String recipient = split[1];
        String message = split[2];
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        HTMLDocument doc = (HTMLDocument) GUI.getChatbox().getStyledDocument();
        try {
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()),
                    "<p style=\"font-size:14px\"><b style=\"color:red;\">" + sender + "</b> to <b style=\"color:blue;\">"
                    + recipient + "</b> <i style=\"color:gray;\"> vào " + dtf.format(time) + "</i>: "
                    + message + "<br></p>");
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(TextConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getSocket() {
        return socket;
    }
// Ngắt kết nối

    public void close() {
        try {
            din.close();
            dout.close();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    public String getSelectedItem() {
        return GUI.getSelectedItem();
    }

    public void setSelectedItem(String item) {
        GUI.setSelectedItem(item);
    }

    public void clearDisplayUsers() {
        GUI.clearUser();
    }
// Cập nhật danh sách user trên giao diện(list và combobox)

    public void refreshUser(String user) {
        if (!user.equals(client.user.getUsername())) {
            GUI.refreshComboUser(user);
        }
        GUI.refreshListUser(user);
        
    }
}
