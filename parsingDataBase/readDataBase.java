package parsingDataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class readDataBase {
    public static void main(String[] args) {
        System.out.printf("\nConteúdo do arquivo texto:\n");
        try{
            FileReader arq = new FileReader("C:/Users/rafael/Documents/UNIVERSIDADE/Comunicação de Computadores/CC_project/parsing/baseDados.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            while (linha != null){
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(linha.split(" ")));

                if (!(list.get(0).equals("#"))){
                    int ttl = parseInt(list.get(3));
                    int prioridade = parseInt(list.get(4));

                    ArrayList<DBelement> dataBase = new ArrayList<>();

                    dataBase.add(new DBelement(list.get(0),list.get(1),list.get(2),ttl,prioridade));

                    System.out.println(dataBase);
                }

                linha = lerArq.readLine();

                /*
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(linha.split(" ")));

                DBelement db = new DBelement(list.get(0),list.get(1),list.get(2),list.get(3));

                linha = lerArq.readLine();

                System.out.println(db);

                 */
            }

        }catch (IOException e){
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
/*
        ArrayList<String> list = new ArrayList<>(Arrays.asList(str.split(" ")));

        DataBase db = new DataBase(list.get(0), list.get(1), list.get(2), list.get(3));

        System.out.println(db);

 */
    }
}
