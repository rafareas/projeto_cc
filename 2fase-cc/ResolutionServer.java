package parsingDataBase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ResolutionServer {
    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[256];

        ReadDataBase read = new ReadDataBase();
        DatagramPacket DpReceive = new DatagramPacket(buf,buf.length);
        DatagramSocket ds = new DatagramSocket(5555);

        //recebeu a data em byte buf
        ds.receive(DpReceive);

        //dados envidados do cliente
        StringBuilder sb = data(buf);
        //mensagem dns
        String originalDns = sb.toString();
        System.out.println("Recebeu a mensagem do cliente: " + originalDns);


        String currentIP = "127.0.0.13"; // esse é o ip do root
        InetAddress ip = InetAddress.getByName(currentIP);

        DatagramSocket dss = new DatagramSocket();
        DatagramPacket dpp = new DatagramPacket(buf, buf.length,ip,6666);
        dss.send(dpp);

        buf = new byte[256];
        sb = data(buf);

        String dns = sb.toString();

        int responseCode = 0;
        while (true){
            buf = new byte[512];
            buf = dns.getBytes();

            buf = new byte[512];
            DatagramPacket DpSR = new DatagramPacket(buf, buf.length);
            dss.receive(DpSR);

            StringBuilder sbSR = data(buf);
            String dnsSR = sbSR.toString();

            System.out.println("recebeu: " + dnsSR);

            String[] splitDNS = dnsSR.split(";");

            String messageHeader = splitDNS[0];
            String[] splitHeader = messageHeader.split(",");
            String msgID = splitHeader[0];
            String flag = splitHeader[1];
            responseCode = Integer.parseInt(splitHeader[2]);
            int n_values = Integer.parseInt(splitHeader[3]);
            int n_authoraties = Integer.parseInt(splitHeader[4]);
            int n_extra_values = Integer.parseInt(splitHeader[5]);
            String domain = splitHeader[6];
            String type = splitHeader[7];

            String messageAuthoritiesValues = splitDNS[1];
            ArrayList<String> atvalues = new ArrayList<>();
            String messageExtraValues = splitDNS[2];

            if (responseCode != 0) {
                // pegar o NS SP e enviar para esse ip
                ArrayList<String> list = new ArrayList<>();
                String[] ev = messageExtraValues.split(",");
                for (int i = 0; i < ev.length; i++) {
                    list.add(ev[i]);
                }
                ArrayList<String> splitEx = new ArrayList<>(Arrays.asList(list.get(0).split(" ")));

                currentIP = splitEx.get(2);

                ip = InetAddress.getByName(currentIP);
                dpp = new DatagramPacket(originalDns.getBytes(), originalDns.getBytes().length, ip, 6666);
                dss.send(dpp);

            } else {
                // a query ja tem as informações e manda agr para o cliente o resultado
                String responseValues = splitDNS[1];
                String authoratiesValues = splitDNS[2];
                String extraValues = splitDNS[3];

                String[] splitRV = responseValues.split(",");
                String[] splitAV = authoratiesValues.split(",");
                String[] splitEV = extraValues.split(",");

                ArrayList<String> header_List = new ArrayList<>();
                ArrayList<String> response_values = new ArrayList<>();
                ArrayList<String> authoraties_values = new ArrayList<>();
                ArrayList<String> extra_values = new ArrayList<>();

                for (int i = 0; i < splitRV.length; i++){
                    response_values.add(splitRV[i]);
                }
                for (int i = 0; i < splitAV.length; i++){
                    authoraties_values.add(splitAV[i]);
                }
                for (int i = 0; i < splitEV.length; i++){
                    extra_values.add(splitEV[i]);
                }

                header_List.add(msgID);
                header_List.add(flag);
                header_List.add(String.valueOf(responseCode));
                header_List.add(String.valueOf(n_values));
                header_List.add(String.valueOf(n_authoraties));
                header_List.add(String.valueOf(n_extra_values));
                header_List.add(domain);
                header_List.add(type);


                StringBuilder header = new StringBuilder();
                for (int i = 0; i < header_List.size(); i++) {
                    header.append(header_List.get(i));
                    if (i == (header_List.size() - 1))
                        header.append(";\n");
                    else
                        header.append(", ");
                }

                StringBuilder rv = new StringBuilder();
                for(int i = 0; i < response_values.size(); i++){
                    rv.append(response_values.get(i));
                    if(i == (response_values.size() -1))
                        rv.append(";\n");
                    else
                        rv.append(",\n");
                }

                StringBuilder av = new StringBuilder();
                for(int i = 0; i < authoraties_values.size(); i++){
                    av.append(authoraties_values.get(i));
                    if(i == (authoraties_values.size() -1))
                        av.append(";\n");
                    else
                        av.append(",\n");
                }


                StringBuilder ev = new StringBuilder();
                for(int i = 0; i < extra_values.size(); i++){
                    ev.append(extra_values.get(i));
                    if(i == (extra_values.size() -1))
                        ev.append(";\n");
                    else
                        ev.append(",\n");
                }

                StringBuilder resultado = new StringBuilder();
                resultado.append(header).append(rv).append(av).append(ev);

                int port = DpReceive.getPort();

                DatagramPacket DpSend = new DatagramPacket(resultado.toString().getBytes(), resultado.toString().getBytes().length, InetAddress.getByName("127.0.0.1"), port);
                ds.send(DpSend);

                break;
            }
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
