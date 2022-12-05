package parsingDataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerDNS {

    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[256];
        ReadDataBase read = new ReadDataBase();
        ArrayList<DBelement> dataBase = new ArrayList<>();
        dataBase = read.ReadDataBase("C:/Users/rafael/Documents/UNIVERSIDADE/Comunicação de Computadores/projeto_CC/projeto_cc/parsingDataBase/earthDataBase.txt");

        DatagramPacket DpReceive = new DatagramPacket(buf,buf.length);
        DatagramSocket ds = new DatagramSocket(5555);

        //recebeu a data em byte buf
        ds.receive(DpReceive);

        //dados envidados do cliente
        StringBuilder sb = data(buf);
        //mensagem dns
        String dns = sb.toString();
        System.out.println(dns);

        //metodo que faz split da string enviada do cliente
        String[] arq = dns.split(";");
        String msgId = arq[0];
        String ip = arq[1];
        String domain = arq[2];
        String type = arq[3];
        ArrayList<String> responseValues = new ArrayList<>();
        ArrayList<String> authoritiesValues = new ArrayList<>();
        ArrayList<String> extraValues = new ArrayList<>();
        ArrayList<String> extraValuesA = new ArrayList<>();

        // Constroi uma lista de responseValues : ArrayList<String> responseValues = new ArrayList<>();
        responseValues = read.getResponseValues(dataBase,responseValues,domain,type);

        // Constroi uma lista de authoritiesValues : ArrayList<String> authoritiesValues = new ArrayList<>();
        authoritiesValues = read.getAuthoritiesValues(dataBase,authoritiesValues,domain,type);

        //Constroi uma lista de extraValues do tipo A para os responseValues
        extraValues = read.getExtraValues(dataBase,responseValues,extraValues);

        //Constroi uma lista de ExtraValues do tipo A para os authoritiesValues
        extraValuesA = read.getExtraValues(dataBase,authoritiesValues,extraValuesA);

        //faz um join das listas
        extraValues.addAll(extraValuesA);
        System.out.println(extraValues);

        DatagramPacket DpSend = null;

        ArrayList<String> addResult = new ArrayList<>();

        addResult.add(msgId);
        addResult.add(String.valueOf(responseValues.size()));
        addResult.add(String.valueOf(authoritiesValues.size()));
        addResult.add(String.valueOf(extraValues.size()));
        addResult.add(type);


        addResult.add(responseValues.toString());
        addResult.add(authoritiesValues.toString());
        addResult.add(extraValues.toString());



        String resultado = addResult.toString();

        buf = resultado.getBytes();

        int port = DpReceive.getPort();

        DpSend = new DatagramPacket(buf, buf.length, InetAddress.getByName("127.0.0.1"),port);

        ds.send(DpSend);

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
