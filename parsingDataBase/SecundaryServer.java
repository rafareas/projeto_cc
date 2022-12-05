package parsingDataBase;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SecundaryServer {
    public static void main(String[] args) throws IOException {
         Socket socket = new Socket("localhost", 5555);

         //envia o dominio para o primary server
         PrintWriter pr = new PrintWriter(socket.getOutputStream());
         pr.println("exemple.com");
         pr.flush();

        //recebe o input do primary Server
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        //recebe o tamanho da base de dados
        String tam = bf.readLine();
        System.out.println("entries: " + tam);

        //manda a confirmação do tamanho da base de dados
        pr = new PrintWriter(socket.getOutputStream());
        pr.println(tam);
        pr.flush();

        //recebe linha por linha os dados da base de dados
        for (int count = 1; count <= Integer.parseInt(tam);count++){
            String str = bf.readLine();
            System.out.println(count + ": " + str);
        }

        //fecha o socket
        socket.close();
    }
}



