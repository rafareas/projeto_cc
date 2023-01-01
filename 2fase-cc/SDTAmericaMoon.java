package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class SDTAmericaMoon {
    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[256];

        //armazenamento das base de dados
        ReadDataBase readMoon = new ReadDataBase();
        ArrayList<DBelement> dataBaseMoon = new ArrayList<>(readMoon.ReadDataBase("C:/Users/rafael/Documents/UNIVERSIDADE/Comunicacao_de_Computadores/2fase-cc/baseDeDados/americaMoonDB.txt"));

        //recebe a query do servidor de resolucao
        DatagramPacket DpReceive = new DatagramPacket(buf, buf.length);
        DatagramSocket DsReceive = new DatagramSocket(6666, InetAddress.getByName("127.0.0.5"));
        DsReceive.receive(DpReceive);

        StringBuilder sb = data(buf);
        String dns = sb.toString();
        System.out.println("O STD recebeu a query: " + dns);

        //faz split do header e da data
        String[] splitDNS = dns.split(";");
        String headerInfo = splitDNS[0];
        String dataInfo = splitDNS[1];

        String[] header = headerInfo.split(",");
        String[] data = dataInfo.split(",");
        //guarda todos os valores da query recebida do SR
        String msgId = header[0];
        String flag = header[1];
        int response_code = Integer.parseInt(header[2]);
        int n_values = Integer.parseInt(header[3]);
        int n_authoraties = Integer.parseInt(header[4]);
        int n_extra_values = Integer.parseInt(header[5]);

        //faz o split do domain para fazer uma verificação na base de dados
        String domain = data[0];
        String[] domainSplit = domain.split("[.]");
        //tipo da query recebida
        String type = data[1];

        ArrayList<String> responseValues = new ArrayList<>();
        ArrayList<String> authoritiesValues = new ArrayList<>();
        ArrayList<String> extraValues = new ArrayList<>();
        ArrayList<String> extraValuesA = new ArrayList<>();

        //faz uma procura nas base de dados se existe o domínio e o tipo recebido da query
        //se o resultado for 0, não achou, se for 1, o domínio existe nessa base de dados
        int procuraMoon = readMoon.searchDataBase(dataBaseMoon, domain, type);
        int find = 0;
        int current = 1;
        String domainAtt = "";
        String domainT = "";

        //nao achou
        if (procuraMoon == 0) {
            int procuraDomain = readMoon.searchDomainDataBase(dataBaseMoon, domain);
            //se for 0 não achou, se for 1 achou
            if (procuraDomain == 0) {
                int count = domainSplit.length;
                while (count > 0) {
                    domainAtt = domainSplit[domainSplit.length - current] + "." + domainAtt;
                    procuraDomain = readMoon.searchDomainDataBase(dataBaseMoon, domainAtt);
                    if (procuraDomain == 1) {
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
            //se achar o dominio e o tipo
            find = 2;
            domainT = domain;
        }

        System.out.println("resultado : " + find);

        ArrayList<String> addResult = new ArrayList<>();
        if (find == 1) {
            response_code = 1;
            responseValues = readMoon.getResponseValues(dataBaseMoon, responseValues, domainT, type);
            System.out.println(responseValues);
            authoritiesValues = readMoon.getAuthoritiesValues(dataBaseMoon, authoritiesValues, domainT);
            System.out.println(authoritiesValues);
            extraValues = readMoon.getExtraValues(dataBaseMoon, responseValues, extraValues);
            extraValuesA = readMoon.getExtraValues(dataBaseMoon, authoritiesValues, extraValuesA);

            extraValues.addAll(extraValuesA);
            System.out.println(extraValues);

            addResult.add(msgId);
            addResult.add(flag);
            addResult.add(String.valueOf(response_code));
            addResult.add(String.valueOf(responseValues.size()));
            addResult.add(String.valueOf(authoritiesValues.size()));
            addResult.add(String.valueOf(extraValues.size()));
            addResult.add(domainT);
            addResult.add(type);

            StringBuilder header_info = new StringBuilder();
            for (int i = 0; i < addResult.size(); i++) {
                header_info.append(addResult.get(i));
                if (i == (addResult.size() - 1))
                    header_info.append(";");
                else
                    header_info.append(",");
            }

            StringBuilder response_Values = new StringBuilder();
            for (int i = 0; i < responseValues.size(); i++) {
                response_Values.append(responseValues.get(i));
                if (i == (responseValues.size() - 1))
                    response_Values.append(";");
                else
                    response_Values.append(",");
            }

            StringBuilder authorities_Values = new StringBuilder();
            for (int i = 0; i < authoritiesValues.size(); i++) {
                authorities_Values.append(authoritiesValues.get(i));
                if (i == (authoritiesValues.size() - 1))
                    authorities_Values.append(";");
                else
                    authorities_Values.append(",");
            }

            StringBuilder extra_Values = new StringBuilder();
            for (int i = 0; i < authoritiesValues.size(); i++) {
                extra_Values.append(extraValues.get(i));
                if (i == (authoritiesValues.size() - 1))
                    extra_Values.append(";");
                else
                    extra_Values.append(",");
            }

            StringBuilder resultado = new StringBuilder();
            resultado.append(header_info).append(response_Values).append(authorities_Values).append(extra_Values);
            System.out.println("resultado: " + resultado);

            int port = DpReceive.getPort();

            DatagramPacket DpSend = new DatagramPacket(resultado.toString().getBytes(), resultado.toString().getBytes().length, InetAddress.getByName("127.0.0.1"), port);

            DsReceive.send(DpSend);
        }

        if (find == 2){
            response_code = 0;
            responseValues = readMoon.getResponseValues(dataBaseMoon, responseValues, domainT, type);
            authoritiesValues = readMoon.getAuthoritiesValues(dataBaseMoon, authoritiesValues, domainT);
            extraValues = readMoon.getExtraValues(dataBaseMoon, responseValues, extraValues);
            extraValuesA = readMoon.getExtraValues(dataBaseMoon, authoritiesValues, extraValuesA);

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
            for (int i = 0; i < addResult.size(); i++) {
                header_info.append(addResult.get(i));
                if (i == (addResult.size() - 1))
                    header_info.append(";");
                else
                    header_info.append(",");
            }

            StringBuilder response_Values = new StringBuilder();
            for (int i = 0; i < responseValues.size(); i++) {
                response_Values.append(responseValues.get(i));
                if (i == (responseValues.size() - 1))
                    response_Values.append(";");
                else
                    response_Values.append(",");
            }

            StringBuilder authorities_Values = new StringBuilder();
            for (int i = 0; i < authoritiesValues.size(); i++) {
                authorities_Values.append(authoritiesValues.get(i));
                if (i == (authoritiesValues.size() - 1))
                    authorities_Values.append(";");
                else
                    authorities_Values.append(",");
            }

            StringBuilder extra_Values = new StringBuilder();
            for (int i = 0; i < authoritiesValues.size(); i++) {
                extra_Values.append(extraValues.get(i));
                if (i == (authoritiesValues.size() - 1))
                    extra_Values.append(";");
                else
                    extra_Values.append(",");
            }

            StringBuilder resultado = new StringBuilder();
            resultado.append(header_info).append(response_Values).append(authorities_Values).append(extra_Values);
            System.out.println("resultado " + resultado);

            int port = DpReceive.getPort();

            DatagramPacket DpSend = new DatagramPacket(resultado.toString().getBytes(), resultado.toString().getBytes().length, InetAddress.getByName("127.0.0.1"), port);

            DsReceive.send(DpSend);
        }

    }
    public static StringBuilder data ( byte[] a){
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
