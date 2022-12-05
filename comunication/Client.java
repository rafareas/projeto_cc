package comunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {
    public Client() throws IOException {

        InetAddress serverAdd = InetAddress.getByName("127.0.0.1");
        int portServer = 5555;
        MyAppProto map = new MyAppProto("1","s","5 5");
        String pdu = map.toString();

        DatagramPacket reg = new DatagramPacket(pdu.getBytes(),pdu.getBytes().length,serverAdd,portServer);

        DatagramSocket socket = new DatagramSocket(portServer, serverAdd);

        socket.send(reg);

    }
}
