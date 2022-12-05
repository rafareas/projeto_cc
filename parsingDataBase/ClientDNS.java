package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class ClientDNS {
    public static void main(String[] args) throws IOException {
        //deveria passar arg[0]
        byte[] buf = new byte[256];
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        String name = args[1];
        String type = args[2];
        DatagramSocket ds = new DatagramSocket();

        Random rand = new Random();
        int max = 65535;
        int rand_msgID = rand.nextInt(max);
        String msgID = String.valueOf(rand_msgID);

        ComLineInfo cl_info = new ComLineInfo(ip,msgID,name,type);

        String pdu = cl_info.toString();
        System.out.println("Enviou udp: " + pdu);
        buf = pdu.getBytes();

        DatagramPacket DPsend = new DatagramPacket(buf,buf.length,ip,5555);
        ds.send(DPsend);

        buf = new byte[256];

        DatagramPacket DPreceive = new DatagramPacket(buf, buf.length);

        ds.receive(DPreceive);

        System.out.println(new String(buf,buf.length));
    }
}
