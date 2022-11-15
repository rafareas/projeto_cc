package parsingConfServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ConfigurationConf {

    private ArrayList<ConfElements> confElements = new ArrayList<>();
    private String path;


    public void setConfElements(String str) {
        System.out.printf("\nConteúdo do arquivo do ficheiro de configuração: \n");

        //"C:/Users/rafael/Documents/UNIVERSIDADE/Comunicação de Computadores/CC_project/parsingConfServer/ficheiroConf.txt"

        try {
            FileReader arq = new FileReader(str);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            while (linha != null) {
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(linha.split(" ")));

                if (!(list.get(0).equals("#"))) {
                    //ArrayList<ConfElements> confElements = new ArrayList<ConfElements>();

                    confElements.add(new ConfElements(list.get(0), list.get(1), list.get(2)));


                }
                linha = lerArq.readLine();

            }

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        System.out.println(confElements);
    }

    public String getDomainDB() {
        System.out.println("entrou1\n");
        for (Iterator iter = confElements.iterator(); iter.hasNext(); ) {
            System.out.println("entrou2\n");
            System.out.println(confElements.get(2));
            if (confElements.get(1).equals("DB")) {
                System.out.println("entrou3\n");
                return String.valueOf(confElements.get(1));
            }
            System.out.println(iter.next());
        }
        return null;
    }
}


