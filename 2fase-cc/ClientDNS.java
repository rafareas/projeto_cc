package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import static parsingDataBase.SDTEarth.data;

public class ClientDNS {
    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[256];
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        String name = args[1];
        String type = args[2];
        String flag = args[3];
        int response_code = 0;
        int n_values = 0;
        int n_authoraties = 0;
        int n_extra_values = 0;

        DatagramSocket ds = new DatagramSocket();

        Random rand = new Random();
        int max = 65535;
        int rand_msgID = rand.nextInt(max);
        String msgID = String.valueOf(rand_msgID);

        ComLineInfo cl_info = new ComLineInfo(msgID,flag,response_code,n_values,n_authoraties,n_extra_values,name,type);

        String pdu = cl_info.toString();
        System.out.println("Enviou udp: " + pdu);
        buf = pdu.getBytes();

        DatagramPacket DPsend = new DatagramPacket(buf,buf.length,InetAddress.getByName("127.0.0.1"),5555);
        ds.send(DPsend);

        buf = new byte[512];

        DatagramPacket DPreceive = new DatagramPacket(buf, buf.length);
        ds.receive(DPreceive);

        StringBuilder sb = data(buf);
        String dns = sb.toString();
        System.out.println("\nO cliente recebeu do servidor a query: \n\n" + dns);
    }
}
