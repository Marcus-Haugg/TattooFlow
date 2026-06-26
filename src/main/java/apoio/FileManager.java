package apoio;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public static void adicionarLinhaNoArquivo(String conteudoLinha, String caminhoArquivo) {
        BufferedWriter escritor = null;
        try {
            escritor = new BufferedWriter(new FileWriter(caminhoArquivo, true));
            System.out.println("Salvando no arquivo: " + new File(caminhoArquivo).getAbsolutePath());
            escritor.write(conteudoLinha);
            escritor.newLine();
        } catch (IOException erro) {
            System.err.println("Erro ao salvar linha no arquivo: " + erro.getMessage());
        } finally {
            try {
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException e) {
                System.err.println("Erro ao fechar o arquivo: " + e.getMessage());
            }
        }
    }

    public static ArrayList<String> lerLinhasDoArquivo(String caminhoArquivo) {
        ArrayList<String> linhasLidas = new ArrayList();
        BufferedReader leitor = null;
        try {
            leitor = new BufferedReader(new FileReader(caminhoArquivo));
            String linhaAtual;
            while ((linhaAtual = leitor.readLine()) != null) {
                linhasLidas.add(linhaAtual);
            }
        } catch (IOException erro) {
            System.err.println("Erro ao ler o arquivo: " + erro.getMessage());
        } finally {
            try {
                if (leitor != null) {
                    leitor.close();
                }
            } catch (IOException e) {
                System.err.println("Erro ao fechar o arquivo: " + e.getMessage());
            }
        }

        return linhasLidas;
    }
}
