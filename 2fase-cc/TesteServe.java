package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static parsingDataBase.ServerDNS.data;

public class TesteServe {
public static void main(String[]args) throws IOException {
        byte[] buf = new byte[256];
        DatagramPacket DpReceive = new DatagramPacket(buf,buf.length);
        DatagramSocket ds = new DatagramSocket(5555, InetAddress.getByName("127.0.0.2"));

        //recebeu a data em byte buf
        ds.receive(DpReceive);

        StringBuilder sb = data(buf);
        //mensagem dns
        String dns = sb.toString();
        System.out.println("Recebeu a mensagem do cliente: " + dns);
        }

}
