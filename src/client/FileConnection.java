package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.FileServerConnection;

/**
 *
 * @author MyPC
 */
public class FileConnection implements Runnable {

    private Client client;
    private Socket socket;
    private static final String HOST = "192.168.43.208";
    private static final int PORT_NUMBER = 6666;
    private DataInputStream din;
    private DataOutputStream dout;
    private final ClientFrame GUI;

    FileConnection(ClientFrame cf, Client c) {
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
            sendStringToServer("newuser$" + client.user.getUsername());
            while (!socket.isClosed()) {
                while (din.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.getStackTrace();
                        close();
                    }
                }
//                Đọc tin nhắn từ server của cac client 
                if (din.available() > 0) {
                    String msgFromServer = din.readUTF();
                    String[] process = msgFromServer.split("\\$");
                    String type = process[0];
                    String msg = process[1];
                    if (type.equals("download")) {
                        writeFile(msg);
                    } else if (type.equals("done")) {
                        showSuccess(msg);
                    }

                }

            }
        } catch (IOException ex) {
            ex.getStackTrace();
            close();
        }
    }

    private void showSuccess(String msg) {
        System.out.println(msg);
        String[] split = msg.split("@");
        String filename = split[0];
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        LocalDateTime doneTime = LocalDateTime.now();
        GUI.refreshListNotify("Tải lên " + filename + " thành công - " + dtf.format(doneTime));
    }

    private void writeFile(String msg) {    //test.txt@size
        String[] split = msg.split("@");
        String filename = split[0];
        long size = Long.parseLong(split[1]);

        String[] split2 = filename.split("\\.");
        String name = split2[0];
        String format = split2[1];
        Path path = Paths.get("clientfile//" + client.user.getUsername());
        try {
            Files.createDirectories(path);
        } catch (IOException ex) {
            Logger.getLogger(FileConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file = new File(path + "/" + filename);
        try (OutputStream out = new FileOutputStream(file)) {
            byte buf[] = new byte[4096];
            int n;
            while ((n = din.read(buf)) != -1) {
                out.write(buf, 0, n);
                size -= n;
                if (size == 0) {
                    break;
                }
//                            System.out.println(size);
            }
//            out.close();
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendFileToServer(String msg) {

        try {
//            System.out.println(msg);
            String[] process = msg.split("@");
            String filename = process[0];
            String path = process[1];
            String time = process[2];
            File file = new File(path);
            byte[] fileContent = Files.readAllBytes(file.toPath());
//            file$filename@legth@username
            sendStringToServer("file$" + filename + "@" + file.length() + "@" + client.user.getUsername()+ "@" + time);
            dout.write(fileContent, 0, fileContent.length);
        } catch (IOException ex) {
            ex.getStackTrace();
            close();
        }
    }

    void fileMeDaddy(String href) {
//        download$test.txt@user-request@time )
        String text = "download$" + href;
//        System.out.println(text);
        sendStringToServer(text);
    }

    public void sendStringToServer(String text) {
        try {
            dout.writeUTF(text);
//            System.out.println(text);
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
