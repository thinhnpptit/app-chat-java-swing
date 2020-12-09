package server;

import java.io.IOException;

/**
 *
 * @author MyPC
 */
public class Server {
    
    public static void main(String[] args) throws IOException {
        ServerLogin sl = new ServerLogin();
        Thread loginThread = new Thread(sl);
        loginThread.start();
        
       TextServer ts = new TextServer();
       Thread textThread = new Thread(ts);
       textThread.start();
       FileServer fs = new FileServer();
       Thread fileThread = new Thread(fs);
       fileThread.start();
    }
}
