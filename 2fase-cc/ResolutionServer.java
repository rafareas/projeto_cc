package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ResolutionServer {
    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[256];
        int flag = 0;
        Map<String,String> baseDeDados = new HashMap<>();
        baseDeDados.put("root","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/root.txt");
        baseDeDados.put("earth.","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/earthDB.txt");
        baseDeDados.put("moon.","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/moonDB.txt");
        baseDeDados.put("europa.earth.","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/europaEarthDB.txt");
        baseDeDados.put("america.moon.","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/americaMoonDB.txt");

        DatagramPacket DpReceive = new DatagramPacket(buf,buf.length);
        DatagramSocket ds = new DatagramSocket(5555);

        //recebeu a data em byte buf
        ds.receive(DpReceive);

        //dados envidados do cliente
        StringBuilder sb = data(buf);
        //mensagem dns
        String dns = sb.toString();
        System.out.println("Recebeu a mensagem do cliente: " + dns);

        String[] splitDNS = dns.split(";");
        String domain = splitDNS[2];
        String type = splitDNS[3];

        String search = domain + " " + type + " " + baseDeDados.get("root");

        buf = new byte[256];
        buf = search.getBytes();
        DatagramSocket dss = new DatagramSocket();
        DatagramPacket dpp = new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"),6666);
        dss.send(dpp);

            while (flag == 0){

        }


        DatagramPacket DpSend = null;
        int port = DpReceive.getPort();

       // DpSend = new DatagramPacket();

        ds.send(DpSend);

        ds.close();

    }

    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
