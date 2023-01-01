package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerPrimaryTest {

    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[256];

        // a chave é a base de dados em qual vai buscar e no valor é o path da base de dados
        Map<String,String> baseDeDados = new HashMap<>();
        baseDeDados.put("root","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/root.txt");
        baseDeDados.put("earth.","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/earthDB.txt");
        baseDeDados.put("moon.","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/moonDB.txt");
        baseDeDados.put("europa.earth.","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/europaEarthDB.txt");
        baseDeDados.put("america.moon.","C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/CC-TP2-G05.04-MATERIAL/baseDeDados/americaMoonDB.txt");
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
        String[] splitDNS = dns.split(";");
        String headerData =  splitDNS[0];
        String queryInfo = splitDNS[1];

        String[] header = headerData.split(",");
        String[] data = queryInfo.split(",");

        String msgID = header[0];
        String flag = header[1];
        int responseCode = Integer.parseInt(header[2]);
        int n_values = Integer.parseInt(header[3]);
        int n_authoraties = Integer.parseInt(header[4]);
        int n_extra_values = Integer.parseInt(header[5]);
        String domain = data[0];
        String type = data[1];
        System.out.println(domain);
        System.out.println(type);

        String currentDomain = "root";

        dataBase = read.ReadDataBase(baseDeDados.get(currentDomain));

        String[] domainSplit = domain.split("[.]");

        //é necessário mandar as respostas tds as vzs por esse servidor 
        ArrayList<String> responseValues = new ArrayList<>();
        ArrayList<String> authoritiesValues = new ArrayList<>();
        ArrayList<String> extraValues = new ArrayList<>();
        ArrayList<String> extraValuesA = new ArrayList<>();

        //criar uma condição para a base de dados

        // flag = 0 : não encontrou o domínio na base de dados ; flag = 1 : encontrou o domínio na base de dados
        //while (true){
            int cd = 1;
            int achou = 0;
            for (int i = 0 ; i < dataBase.size() ; i++){
                if (dataBase.get(i).getParametro().equals(domain)){
                    responseValues = read.getResponseValues(dataBase,responseValues,domain,type);
                    System.out.println("Response Values: " + responseValues);

                    authoritiesValues = read.getAuthoritiesValues(dataBase,authoritiesValues,domain);
                    System.out.println("Authorities values: " + authoritiesValues);

                    extraValues = read.getExtraValues(dataBase,responseValues,extraValues);
                    extraValuesA = read.getExtraValues(dataBase,authoritiesValues,extraValuesA);

                    extraValues.addAll(extraValuesA);
                    System.out.println("Extra-values: " + extraValues);
                    achou = 1;
                }
            }
            if (achou != 1){
                System.out.println("Não encontrou no " + currentDomain);
                currentDomain = domainSplit[domainSplit.length - cd] + ".";
                System.out.println(currentDomain);
            }

            ArrayList<String> header_List = new ArrayList<>();

            header_List.add(msgID + ",");
            header_List.add(flag + ",");
            header_List.add(String.valueOf(responseCode) + ",");
            header_List.add(String.valueOf(responseValues.size()) + ",");
            header_List.add(String.valueOf(authoritiesValues.size()) + ",");
            header_List.add(String.valueOf(extraValues.size()) + ";");
            header_List.add(domain + ",");
            header_List.add(type + ";");


            StringBuilder head = new StringBuilder();
            for(int i = 0; i < header_List.size(); i++){
                head.append(header_List.get(i));
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

            StringBuilder result = new StringBuilder();
            result.append(head).append(response_Values).append(authorities_Values).append(extra_Values);
            System.out.println(result);
            DatagramPacket dpsp = new DatagramPacket(result.toString().getBytes(), result.toString().getBytes().length,InetAddress.getByName("127.0.0.1"),DpReceive.getPort());
            ds.send(dpsp);
        //}

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
