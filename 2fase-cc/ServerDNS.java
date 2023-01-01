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
        dataBase = read.ReadDataBase("C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/2fase-cc/baseDeDados/europaEarthDB.txt");

        DatagramPacket DpReceive = new DatagramPacket(buf,buf.length);
        DatagramSocket ds = new DatagramSocket(5555,InetAddress.getByName("127.0.0.1"));

        //recebeu a data em byte buf
        ds.receive(DpReceive);

        //dados envidados do cliente
        StringBuilder sb = data(buf);
        //mensagem dns
        String dns = sb.toString();
        System.out.println("Recebeu a mensagem do cliente: " + dns);

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

        String[] domainSplit = domain.split("[.]");
        String nameMaq;

        //qual é o parametro name? é o só o dominio ou pode ser também o nome da maquina?
        System.out.println("QUERY-INFO.NAME: " + domain);
        System.out.println("QUERY-INFO.TYPE: " + type);

        // Constroi uma lista de responseValues : ArrayList<String> responseValues = new ArrayList<>();
        responseValues = read.getResponseValues(dataBase,responseValues,domain,type);
        System.out.println("Response Values: " + responseValues);
        // Constroi uma lista de authoritiesValues : ArrayList<String> authoritiesValues = new ArrayList<>();
        authoritiesValues = read.getAuthoritiesValues(dataBase,authoritiesValues,domain);
        System.out.println("Authorities values: " + authoritiesValues);
        //Constroi uma lista de extraValues do tipo A para os responseValues
        extraValues = read.getExtraValues(dataBase,responseValues,extraValues);
        //System.out.println("Extra Values 1: " + extraValues);
        //Constroi uma lista de ExtraValues do tipo A para os authoritiesValues
        extraValuesA = read.getExtraValues(dataBase,authoritiesValues,extraValuesA);
        //System.out.println("Extra Values 2: " + extraValuesA);
        //faz um join das listas
        extraValues.addAll(extraValuesA);
        System.out.println("Extra-values: " + extraValues);

        DatagramPacket DpSend = null;
        int port = DpReceive.getPort();

        ArrayList<String> header_List = new ArrayList<>();

        header_List.add(msgId);
        header_List.add(String.valueOf(responseValues.size()));
        header_List.add(String.valueOf(authoritiesValues.size()));
        header_List.add(String.valueOf(extraValues.size()));
        header_List.add(type);

        StringBuilder header = new StringBuilder();
        for(int i = 0; i < header_List.size(); i++){
            header.append(header_List.get(i));
            if(i == (header_List.size() -1))
                header.append(";\n");
            else
                header.append(", ");
        }

        StringBuilder response_Values = new StringBuilder();
        for(int i = 0; i < responseValues.size(); i++){
            response_Values.append(responseValues.get(i));
            if(i == (responseValues.size() -1))
                response_Values.append(";\n");
            else
                response_Values.append(",\n");
        }

        StringBuilder authorities_Values = new StringBuilder();
        for(int i = 0; i < authoritiesValues.size(); i++){
            authorities_Values.append(authoritiesValues.get(i));
            if(i == (authoritiesValues.size() -1))
                authorities_Values.append(";\n");
            else
                authorities_Values.append(",\n");
        }

        StringBuilder extra_Values = new StringBuilder();
        for(int i = 0; i < authoritiesValues.size(); i++){
            extra_Values.append(extraValues.get(i));
            if(i == (authoritiesValues.size() -1))
                extra_Values.append(";\n");
            else
                extra_Values.append(",\n");
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append(header).append(response_Values).append(authorities_Values).append(extra_Values);

        /*
        byte[] newBuf = new byte[2048];
        newBuf = resultado.toString().getBytes();
         */

        System.out.println(resultado);

        DpSend = new DatagramPacket(resultado.toString().getBytes(), resultado.toString().getBytes().length, InetAddress.getByName("127.0.0.1"),port);

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
