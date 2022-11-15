package comunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public Server() throws IOException {
        byte[] buf = new byte[256];
        DatagramSocket serverS = new DatagramSocket(5555);
        DatagramPacket receiver = new DatagramPacket(buf, buf.length);

        serverS.receive(receiver);



    }
}
