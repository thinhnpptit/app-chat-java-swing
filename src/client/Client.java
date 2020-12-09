package client;

import model.User;

/**
 *
 * @author MyPC
 */
public class Client {

    private String userName;    /*tên client*/
    TextConnection tc;
    FileConnection fc;
    LoginConnection lc;
    public model.User user = new User();


    public void setConnections(ClientFrame cf) {
//        chạy cac thread text và file
        tc = new TextConnection(cf, this);
        fc = new FileConnection(cf, this);

        Thread textThread = new Thread(tc);
        textThread.start();
        Thread fileThread = new Thread(fc);
        fileThread.start();

    }
    
    public void setLoginConnections(LoginFrm lf) {
//        chạy cac thread text và file
       
        lc = new LoginConnection(lf, this);
        Thread loginThread = new Thread(lc);
        loginThread.start();
    }

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        //gọi đến giao diện login

        LoginFrm login = new LoginFrm(this);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }
}
