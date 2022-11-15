package parsingConfServer;

import java.util.Scanner;

public class mainTest {
    public static void main(String[] args) {
        ConfigurationConf cn = new ConfigurationConf();
        Scanner obj = new Scanner(System.in);
        System.out.println("Coloque o caminho do ficheiro de configuração:\n");

        String path = obj.nextLine();
        cn.setConfElements(path);
        System.out.println("Path " + path + " lido corretamente");
        if (cn.isSP()){
            System.out.println("É um SP!");
        }

    }
}
