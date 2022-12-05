package parsingDataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class ReadDataBase {
    private ArrayList<DBelement> dataBase = new ArrayList<>();

    public ArrayList<DBelement> ReadDataBase(String str) {
        //C:/Users/rafael/Documents/UNIVERSIDADE/Comunicação de Computadores/projeto_CC/projeto_cc/parsingDataBase/baseDados.txt
        try {
            FileReader arq = new FileReader(str);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            while (linha != null) {
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(linha.split(" ")));

                if (!(list.get(0).equals("#"))) {
                    dataBase.add(new DBelement(list.get(0), list.get(1), list.get(2),list.get(3)));
                }
                linha = lerArq.readLine();
                }

            }catch(IOException e){
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
            }
        return dataBase;
    }

    public ArrayList<String> getResponseValues(ArrayList<DBelement> dataBase, ArrayList<String>responseValues, String domain, String type){
        for (int count = 0; count<dataBase.size(); count++){
            if (dataBase.get(count).getParametro().equals(domain)){
                if (dataBase.get(count).getTipoValor().equals(type)){
                    String dados = dataBase.get(count).toString();
                    responseValues.add(dados);
                }
            }
        }
        return responseValues;
    }

    public ArrayList<String> getAuthoritiesValues(ArrayList<DBelement> dataBase, ArrayList<String> authoritiesValues, String domain, String type){
        for (int count = 0; count<dataBase.size(); count++){
            if (dataBase.get(count).getParametro().equals(domain)){
                if (dataBase.get(count).getTipoValor().equals("NS")){
                    String dados = dataBase.get(count).toString();
                    authoritiesValues.add(dados);
                }
            }
        }
        return authoritiesValues;
    }

    public ArrayList<String> getExtraValues(ArrayList<DBelement> dataBase, ArrayList<String> value, ArrayList<String> extraValues){
        for (int count = 0; count < value.size(); count++){
            String sResp = value.get(count).toString();
            String[] authSplit = sResp.split(";");
            String paramForR = authSplit[2];
            for (int i = 0; i < dataBase.size();i++){
                if (dataBase.get(i).getTipoValor().equals("A")){
                    if (dataBase.get(i).getParametro().equals(paramForR)){
                        String dados = dataBase.get(i).toString();
                        extraValues.add(dados);
                    }
                }
            }
        }
        return extraValues;
    }

}




/*
for (int count = 0; count<dataBase.size(); count++){
            if (dataBase.get(count).getParametro().equals(domain)){
                if (dataBase.get(count).getTipoValor().equals(type)){
                    String dados = dataBase.get(count).toString();
                    responseValues.add(dados);
                }
            }
        }
        System.out.println(responseValues);
 */

/*
 for (int count = 0; count<dataBase.size(); count++){
            if (dataBase.get(count).getParametro().equals(domain)){
                if (dataBase.get(count).getTipoValor().equals("NS")){
                    String dados = dataBase.get(count).toString();
                    authoritiesValues.add(dados);
                }
            }
        }

 */

/*
for (int count = 0; count < responseValues.size(); count++){
            String sResp = responseValues.get(count).toString();
            String[] authSplit = sResp.split(";");
            String paramForR = authSplit[2];
            for (int i = 0; i < dataBase.size();i++){
             if (dataBase.get(i).getTipoValor().equals("A")){
                 if (dataBase.get(i).getParametro().equals(paramForR)){
                     String dados = dataBase.get(i).toString();
                     extraValues.add( dados);
                 }
             }
            }
        }
        System.out.println(extraValues);
 */

/*
for (int count = 0; count < authoritiesValues.size(); count++){
            String sAuth = authoritiesValues.get(count).toString();
            String[] authSplit = sAuth.split(";");
            String paramForA = authSplit[2];
            for (int i = 0; i < dataBase.size();i++){
                if (dataBase.get(i).getTipoValor().equals("A")){
                    if (dataBase.get(i).getParametro().equals(paramForA)){
                        String dados = dataBase.get(i).toString();
                        extraValues.add(dados);
                    }
                }
            }
        }
        System.out.println(extraValues);
 */

