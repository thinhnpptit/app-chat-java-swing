package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MyPC
 */
public class FileServerConnection implements Runnable {

    Socket socket;
    FileServer server;
    DataInputStream din;
    DataOutputStream dout;
    boolean shouldRun = true;

    public FileServerConnection(FileServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
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
                    }
                }
                String textIn = din.readUTF();
//                System.out.println(textIn);
                String process[] = textIn.split("\\$");
                String type = process[0];
                String msg = process[1];
                if (type.equals("newuser")) {
                    server.connections.put(msg, this);
                } else if (type.equals("close")) {
                    close_sendUserList(msg, textIn);
                } else if (type.equals("file")) { //name@
//                    System.out.println("file"+msg);
                    writeFile(msg);
                } else if (type.equals("download")) { //download$test.txt@a
//                    System.out.println("download"+msg);
                    sendFile(msg);
                }
            }
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private void sendFile(String filename) {   //test.txt@recipient@time
        System.out.println(filename);
        String s = filename;
        String[] s1 = s.split("@");
        String dclmn = s1[0];   //test
        String recipient = s1[1];
        String time = s1[2];
        String sender = s1[3];

        String[] s2 = dclmn.split("\\.");
        String trueName = s2[0];

        File dir = new File("serverfile");
        File filesList[] = dir.listFiles();
        for (File file : filesList) {
            System.out.println(file.getName());
            if (file.getName().startsWith(trueName + "@" + sender + "@" + time)) {
                System.out.println("yes");
                try {
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    sendStringToDownloadOrNotify("download$" + dclmn + "@" + file.length(), recipient);
                    dout.write(fileContent, 0, fileContent.length);
                    System.out.println("done send file");
                } catch (IOException ex) {
                    Logger.getLogger(FileServerConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
//            file$filename@legth@username
    }
// Lưu file từ client gửi đến

    private void writeFile(String msg) {    //filename@size@username@111111
        String[] split = msg.split("@");
        String filename = split[0];
        long size = Long.parseLong(split[1]);
        String username = split[2];
        String time = split[3];

        String[] split2 = filename.split("\\.");
        String name = split2[0];
        String format = split2[1];

        File file = new File("serverfile//" + name + "@" + username + "@" + time + "." + format);
        try (OutputStream out = new FileOutputStream(file)) {
            byte buf[] = new byte[4096];
            int n;
            while ((n = din.read(buf)) != -1) {
                out.write(buf, 0, n);
                Thread.sleep(1000);
                size -= n;
                if (size == 0) {
                    break;
                }
//                            System.out.println(size);
            }
//            out.close();
            dout = new DataOutputStream(socket.getOutputStream());

            sendStringToDownloadOrNotify("done$" + filename, username);
            System.out.println("done$" + filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FileServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendStringToDownloadOrNotify(String message, String sender) {
        for (Map.Entry<String, FileServerConnection> client : server.connections.entrySet()) {
            if (client.getKey().equals(sender)) {
                client.getValue().sendStringToClient(message);
            }

        }
    }

    private void sendStringToOneClient(String message, String recipient, String sender) {
        for (Map.Entry<String, FileServerConnection> client : server.connections.entrySet()) {
            if (client.getKey().equals(recipient) || client.getKey().equals(sender)) {
                client.getValue().sendStringToClient(message);
            }

        }
    }

    private void sendStringToAllClients(String textIn) {
        for (Map.Entry<String, FileServerConnection> client : server.connections.entrySet()) {
//            System.out.println(client.getKey());
            client.getValue().sendStringToClient(textIn);
        }
    }

    private void sendStringToClient(String text) {
        try {
            dout.writeUTF(text);
            dout.flush();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private void close_sendUserList(String msg, String textIn) {
        String clients = textIn + "#";
        String toRemove = "";
        for (Map.Entry<String, FileServerConnection> client : server.connections.entrySet()) {
            if (client.getKey().equals(msg)) {
                toRemove = client.getKey();
            } else {
                clients += client.getKey() + "@";
            }
        }
        server.connections.remove(toRemove);
//                    System.out.println(clients);
//        sendStringToAllClients(clients);
    }
}
