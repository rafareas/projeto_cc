package parsingConfServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class ConfigurationConf {

    private ArrayList<ConfElements> confElements = new ArrayList<>();

    public void setConfElements(String str) {

        //"C:/Users/rafael/Documents/UNIVERSIDADE/Comunicação de Computadores/CC_project/parsingConfServer/ficheiroConf.txt"

        try {
            FileReader arq = new FileReader(str);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            while (linha != null) {
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(linha.split(" ")));

                if (!(list.get(0).equals("#"))) {
                    confElements.add(new ConfElements(list.get(0), list.get(1), list.get(2)));
                }
                linha = lerArq.readLine();
            }

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        //System.out.println(confElements);
    }


    //
    public boolean isSP() {
        for (int counter = 0; counter < confElements.size();counter++){
            if (confElements.get(counter).getTipoValor().equals("DB")){
                return true;
            }
        }
        return false;
    }

    //deve procurar entradas SS para saber quais são os IPs
    //autorizados a fazer o download da DB (transferência de zona).
    public ArrayList<String> searchSSIP(){

    }
}


