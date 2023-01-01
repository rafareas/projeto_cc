package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ST {
    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[256];

        ReadDataBase read = new ReadDataBase();
        ArrayList<DBelement> dataBase = new ArrayList<>(read.ReadDataBase("C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/2fase-cc/baseDeDados/root.txt"));;

        DatagramPacket DpReceive = new DatagramPacket(buf, buf.length);
        DatagramSocket DsReceive = new DatagramSocket(6666, InetAddress.getByName("127.0.0.13"));

        DsReceive.receive(DpReceive);

        StringBuilder sb = data(buf);

        String dns = sb.toString();
        System.out.println("O ST recebeu a query: " + dns);

        String[] splitDNS = dns.split(";");
        String headerInfo =  splitDNS[0];
        String dataInfo = splitDNS[1];

        String[] header = headerInfo.split(",");
        String[] data = dataInfo.split(",");

        String msgId = header[0];
        String flag = header[1];
        int response_code = Integer.parseInt(header[2]);
        int n_values = Integer.parseInt(header[3]);
        int n_authoraties = Integer.parseInt(header[4]);
        int n_extra_values = Integer.parseInt(header[5]);

        String domain = data[0];
        String[] domainSplit = domain.split("[.]");
        String type = data[1];

        ArrayList<String> responseValues = new ArrayList<>();
        ArrayList<String> authoritiesValues = new ArrayList<>();
        ArrayList<String> extraValues = new ArrayList<>();
        ArrayList<String> extraValuesA = new ArrayList<>();


        int procuraRoot = read.searchDataBase(dataBase,domain,type);
        int find = 0;
        int current = 1;
        String domainAtt = "";
        String domainT = "";

        System.out.println("procura: " + procuraRoot);
        if (procuraRoot == 0){
            int procuraDomain = read.searchDomainDataBase(dataBase,domain);
            if (procuraDomain == 0){
                int count = domainSplit.length;
                while (count > 0){
                    domainAtt = domainSplit[domainSplit.length - current] + "." + domainAtt;
                    procuraDomain = read.searchDomainDataBase(dataBase,domainAtt);
                    if (procuraDomain == 1){
                        domainT = domainAtt;
                        find = 1;
                    }
                    current++;
                    count--;
                }
            }else {
                find = 1;
                domainT = domain;
            }
        }else {
            find = 2;
            domainT = domain;
        }

        System.out.println("find: " + find);
        ArrayList<String> addResult = new ArrayList<>();

        if (find == 1){
            response_code = 1;
            //responseValues = read.getResponseValues(dataBase,responseValues,domainT,type);
            String domainN = "";
            String dom = "";
            int atual = 1;
            int count = domainSplit.length;
            int procuraD;
            while (count > 0) {
                domainN = domainSplit[domainSplit.length - atual] + "." + domainN;
                procuraD = read.searchNSDataBase(dataBase, domainN);
                if (procuraD == 1) {
                    dom = domainN;
                }
                atual++;
                count--;
            }
            authoritiesValues = read.getAuthoritiesValues(dataBase, authoritiesValues, dom);
            System.out.println("authorities values: " + authoritiesValues);
            extraValues = read.getExtraValues(dataBase,responseValues,extraValues);
            extraValuesA = read.getExtraValues(dataBase,authoritiesValues,extraValuesA);

            extraValues.addAll(extraValuesA);

            addResult.add(msgId);
            addResult.add(flag);
            addResult.add(String.valueOf(response_code));
            addResult.add(String.valueOf(responseValues.size()));
            addResult.add(String.valueOf(authoritiesValues.size()));
            addResult.add(String.valueOf(extraValues.size()));
            addResult.add(domainT);
            addResult.add(type);

            StringBuilder header_info = new StringBuilder();
            for(int i = 0; i < addResult.size(); i++){
                header_info.append(addResult.get(i));
                if(i == (addResult.size() -1))
                    header_info.append(";");
                else
                    header_info.append(",");
            }

            StringBuilder response_Values = new StringBuilder();
            for(int i = 0; i < responseValues.size(); i++){
                response_Values.append(responseValues.get(i));
                if(i == (responseValues.size() -1))
                    response_Values.append(";");
                else
                    response_Values.append(",");
            }

            StringBuilder authorities_Values = new StringBuilder();
            for(int i = 0; i < authoritiesValues.size(); i++){
                authorities_Values.append(authoritiesValues.get(i));
                if(i == (authoritiesValues.size() -1))
                    authorities_Values.append(";");
                else
                    authorities_Values.append(",");
            }

            StringBuilder extra_Values = new StringBuilder();
            for(int i = 0; i < authoritiesValues.size(); i++){
                extra_Values.append(extraValues.get(i));
                if(i == (authoritiesValues.size() -1))
                    extra_Values.append(";");
                else
                    extra_Values.append(",");
            }

            StringBuilder resultado = new StringBuilder();
            resultado.append(header_info).append(response_Values).append(authorities_Values).append(extra_Values);
            System.out.println(resultado);


            int port = DpReceive.getPort();

            DatagramPacket DpSend = new DatagramPacket(resultado.toString().getBytes(), resultado.toString().getBytes().length, InetAddress.getByName("127.0.0.1"),port);

            DsReceive.send(DpSend);
        }
        if (find == 2){
            response_code = 0;
            responseValues = read.getResponseValues(dataBase,responseValues,domainT,type);
            String domainN = "";
            String dom = "";
            int atual = 1;
            int count = domainSplit.length;
            int procuraD;
            while (count > 0) {
                domainN = domainSplit[domainSplit.length - atual] + "." + domainN;
                procuraD = read.searchNSDataBase(dataBase, domainN);
                if (procuraD == 1) {
                    dom = domainN;
                }
                atual++;
                count--;
            }
            authoritiesValues = read.getAuthoritiesValues(dataBase, authoritiesValues, dom);
            System.out.println("authorities values: " + authoritiesValues);
            extraValues = read.getExtraValues(dataBase,responseValues,extraValues);
            extraValuesA = read.getExtraValues(dataBase,authoritiesValues,extraValuesA);

            extraValues.addAll(extraValuesA);

            addResult.add(msgId);
            addResult.add(flag);
            addResult.add(String.valueOf(response_code));
            addResult.add(String.valueOf(responseValues.size()));
            addResult.add(String.valueOf(authoritiesValues.size()));
            addResult.add(String.valueOf(extraValues.size()));
            addResult.add(domainT);
            addResult.add(type);

            StringBuilder header_info = new StringBuilder();
            for(int i = 0; i < addResult.size(); i++){
                header_info.append(addResult.get(i));
                if(i == (addResult.size() -1))
                    header_info.append(";");
                else
                    header_info.append(",");
            }

            StringBuilder response_Values = new StringBuilder();
            for(int i = 0; i < responseValues.size(); i++){
                response_Values.append(responseValues.get(i));
                if(i == (responseValues.size() -1))
                    response_Values.append(";");
                else
                    response_Values.append(",");
            }

            StringBuilder authorities_Values = new StringBuilder();
            for(int i = 0; i < authoritiesValues.size(); i++){
                authorities_Values.append(authoritiesValues.get(i));
                if(i == (authoritiesValues.size() -1))
                    authorities_Values.append(";");
                else
                    authorities_Values.append(",");
            }

            StringBuilder extra_Values = new StringBuilder();
            for(int i = 0; i < authoritiesValues.size(); i++){
                extra_Values.append(extraValues.get(i));
                if(i == (authoritiesValues.size() -1))
                    extra_Values.append(";");
                else
                    extra_Values.append(",");
            }

            StringBuilder resultado = new StringBuilder();
            resultado.append(header_info).append(response_Values).append(authorities_Values).append(extra_Values);
            System.out.println(resultado);


            int port = DpReceive.getPort();

            DatagramPacket DpSend = new DatagramPacket(resultado.toString().getBytes(), resultado.toString().getBytes().length, InetAddress.getByName("127.0.0.1"),port);

            DsReceive.send(DpSend);
        }

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