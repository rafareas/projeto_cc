package parsingConfServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class readConf {

    private static ArrayList<ConfElements> confElements = new ArrayList<>();

    public static void main(String[] args) {
        System.out.printf("\nConteúdo do arquivo texto:\n");

        try{
            FileReader arq = new FileReader("C:/Users/rafael/Documents/UNIVERSIDADE/Comunicação de Computadores/CC_project/parsingConfServer/ficheiroConf.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            while (linha != null){
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(linha.split(" ")));

                if (!(list.get(0).equals("#"))){
                    //ArrayList<ConfElements> confElements = new ArrayList<ConfElements>();

                    confElements.add(new ConfElements(list.get(0),list.get(1),list.get(2)));


                }
                linha = lerArq.readLine();

            }

        }catch (IOException e){
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        System.out.println(confElements);
    }
}
