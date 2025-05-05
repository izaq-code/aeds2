import java.io.*;
import java.util.*;

public class tp003 {
    private static final String CAMINHO_CSV = "/tmp/disneyplus.csv";
    private static final String CAMINHO_LOG = "877487_sequencial.txt"; 

    static class Serie {
        String title;

        Serie(String title) {
            this.title = title;
        }
    }

    static String[] separarLinhaCSV(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean aspas = false;

        for (char c : linha.toCharArray()) {
            if (c == '\"') {
                aspas = !aspas;
            } else if (c == ',' && !aspas) {
                campos.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        campos.add(sb.toString().trim());
        return campos.toArray(new String[0]);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Map<String, String> mapaCSV = new HashMap<>();
        List<Serie> vetor = new ArrayList<>();
        int comparacoes = 0;

        // Carregar todos os registros do CSV em um mapa
        BufferedReader arq = new BufferedReader(new FileReader(CAMINHO_CSV));
        arq.readLine(); // pula o cabeçalho
        String linha;
        while ((linha = arq.readLine()) != null) { // Verifica se a linha não é nula
            String[] partes = linha.split(",", 2);
            if (partes.length > 1) {
                mapaCSV.put(partes[0].trim(), linha);
            }
        }
        arq.close();

        // Parte 1: inserir registros no vetor
        while (true) {
            String entrada = in.readLine();
            if (entrada == null || entrada.equals("FIM")) break; // Verifica se é null ou FIM

            String dados = mapaCSV.get(entrada);
            if (dados != null) {
                String[] campos = separarLinhaCSV(dados);
                String titulo = campos[2].replace("\"", "").trim();
                vetor.add(new Serie(titulo));
            }
        }

        // Parte 2: pesquisa sequencial por título
        long inicio = System.currentTimeMillis();

        while (true) {
            String tituloPesquisado = in.readLine();
            if (tituloPesquisado == null || tituloPesquisado.equals("FIM")) break; // Verifica se é null ou FIM

            boolean encontrado = false;
            for (Serie s : vetor) {
                comparacoes++;
                if (s.title.equalsIgnoreCase(tituloPesquisado.trim())) { // Comparação insensível a maiúsculas/minúsculas
                    encontrado = true;
                    break;
                }
            }
            System.out.println(encontrado ? "SIM" : "NAO");
        }

        long fim = System.currentTimeMillis();
        long tempoExecucao = fim - inicio;

        // Criar o arquivo de log
        PrintWriter log = new PrintWriter(CAMINHO_LOG);
        log.println("877487" + "\t" + tempoExecucao + "\t" + comparacoes); // Substitua pela sua matrícula
        log.close();
    }
}
