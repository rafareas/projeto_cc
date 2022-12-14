package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerPrimaryTest {

    public static void main(String[] args) throws IOException {

        byte[] buf = new byte[256];
        ReadDataBase read = new ReadDataBase();
        ArrayList<DBelement> dataBase = new ArrayList<>();
        //dataBase = read.ReadDataBase("C:/Users/rafael/Documents/UNIVERSIDADE/Comunicação de Computadores/CC-TP2-G05.04-MATERIAL/europaEarthDB.txt");

        DatagramPacket DpReceive = new DatagramPacket(buf,buf.length);
        DatagramSocket ds = new DatagramSocket(6666);

        //recebeu a data em byte buf
        ds.receive(DpReceive);

        //dados envidados do cliente
        StringBuilder sb = data(buf);
        //mensagem dns
        String dns = sb.toString();
        System.out.println("Recebeu a mensagem do servidor de resoluçao: " + dns);

        //metodo que faz split da string enviada do cliente
        String[] arq = dns.split(" ");
        String domain = arq[0];
        String type = arq[1];
        String path = arq[2];

        dataBase = read.ReadDataBase(path);
        System.out.println(dataBase);

        String[] domainSplit = domain.split("[.]");
        String nameMaq;

        //criar uma condição para a base de dados



        ArrayList<String> responseValues = new ArrayList<>();
        ArrayList<String> authoritiesValues = new ArrayList<>();
        ArrayList<String> extraValues = new ArrayList<>();
        ArrayList<String> extraValuesA = new ArrayList<>();
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
