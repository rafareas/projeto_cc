package parsingDataBase;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PrimaryServer {
    public static void main(String[] args) throws IOException {
        //abre um server socket e fica a espera de conecçao
        ServerSocket serverSocket = new ServerSocket(5555);
        //secundary server entrou em comunicaçao com o primaru server
        Socket socket = serverSocket.accept();
        System.out.println("cliente conectou");

        //lê a base de dados
        ReadDataBase rdb = new ReadDataBase();
        ArrayList<DBelement> dBelements = rdb.ReadDataBase("C:/Users/rafael/Documents/UNIVERSIDADE/Comunicação de Computadores/projeto_CC/projeto_cc/parsingDataBase/baseDados.txt");

        //pega valores enviados do Secundary Server
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        //envia o dominio
        String str = bf.readLine();
        System.out.println("dominio: " + str);

        //manda para o Secundary Server o tamanho da base de dados
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        String tamDB = String.valueOf(dBelements.size());
        pr.println(tamDB);
        pr.flush();

        //recebe a confirmação do SS o tamanho da base de dados
        str = bf.readLine();
        System.out.println("ok: " + str);

        //manda linha por linha os dados da Base de dados
        for (int count = 0; count < Integer.parseInt(tamDB); count++){
            pr = new PrintWriter(socket.getOutputStream());
            pr.println(dBelements.get(count));
            pr.flush();
        }

        //fecha o socket
        socket.close();
    }
}





