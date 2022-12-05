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
    public ArrayList<String> isSP() {
        ArrayList<String> ip = new ArrayList<String>();
        for (int counter = 0; counter < confElements.size();counter++){
            if (confElements.get(counter).getTipoValor().equals("DB")){
                System.out.println("É um SP!");
                for (int count = 0; count < confElements.size();count++){
                    if (confElements.get(count).getTipoValor().equals("SS")){
                        ip.add(confElements.get(count).getValorAssParametro());
                    }
                }
                System.out.println(ip);
                return ip;
            }else {
                System.out.println("Não é um SP, logo é um SS!");
                for (int count = 0; count < confElements.size();count++){
                    if (confElements.get(count).getTipoValor().equals("SP")){
                        ip.add(confElements.get(count).getValorAssParametro());
                    }
                }
                return ip;
            }
        }
        return null;
    }


}


