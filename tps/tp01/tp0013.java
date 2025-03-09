import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class tp0013 {
    private static String fazerRequisicaoHttp(String url) {
        URL site;
        InputStream fluxoEntrada;
        BufferedReader leitorBuffer;
        String resposta = "";
        String linha;

        try {
            site = new URI(url).toURL();
            fluxoEntrada = site.openStream();
            leitorBuffer = new BufferedReader(new InputStreamReader(fluxoEntrada));

            do {
                linha = leitorBuffer.readLine();
                if (linha != null) {
                    resposta += linha + "\n";
                }
            } while (linha != null);

            fluxoEntrada.close();
        } catch (Exception e) {
        }

        return resposta;
    }

    private static int[] contarLetras(String texto, char[] letras) {
        int[] contagemLetras = new int[letras.length];
        int i = 0;

        while (i < texto.length()) {
            char caractere = texto.charAt(i);
            int j = 0;

            while (j < letras.length) {
                switch (letras[j]) {
                    case 'a': case 'e': case 'i': case 'o': case 'u':
                    case '\u00E1': case '\u00E9': case '\u00ED': case '\u00F3': case '\u00FA':
                    case '\u00E0': case '\u00E8': case '\u00EC': case '\u00F2': case '\u00F9':
                    case '\u00E3': case '\u00F5': case '\u00E2': case '\u00EA': case '\u00EE':
                    case '\u00F4': case '\u00FB':
                    case 'b': case 'c': case 'd': case 'f': case 'g': case 'h': case 'j': case 'k':
                    case 'l': case 'm': case 'n': case 'p': case 'q': case 'r': case 's': case 't':
                    case 'v': case 'w': case 'x': case 'y': case 'z':
                        if (caractere == letras[j]) {
                            contagemLetras[j]++;
                        }
                        break;
                    default:
                        break;
                }
                j++;
            }
            i++;
        }

        return contagemLetras;
    }

    private static int[] contarPalavras(String texto, String[] palavras) {
        int[] contagemPalavras = new int[palavras.length];
        int i = 0;

        while (i < palavras.length) {
            String palavra = palavras[i];
            int j = 0;

            do {
                if (j + palavra.length() <= texto.length()) {
                    boolean igual = true;
                    int k = 0;

                    while (k < palavra.length()) {
                        if (texto.charAt(j + k) != palavra.charAt(k)) {
                            igual = false;
                            break;
                        }
                        k++;
                    }

                    if (igual) {
                        contagemPalavras[i]++;
                    }
                }
                j++;
            } while (j < texto.length() - palavra.length() + 1);
            i++;
        }

        return contagemPalavras;
    }

    private static void contarElementos(String url, String texto) {
        String conteudoPagina = fazerRequisicaoHttp(url);

        char[] vogais = { 'a', 'e', 'i', 'o', 'u', '\u00E1', '\u00E9', '\u00ED', '\u00F3', '\u00FA', '\u00E0', '\u00E8',
                '\u00EC', '\u00F2', '\u00F9', '\u00E3', '\u00F5', '\u00E2', '\u00EA', '\u00EE', '\u00F4', '\u00FB' };
        char[] consoantes = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w',
                'x', 'y', 'z' };
        String[] tags = { "<br>", "<table>" };

        int[] contagemVogais = contarLetras(conteudoPagina, vogais);
        int[] contagemConsoantes = contarLetras(conteudoPagina, consoantes);
        int totalConsoantes = 0;
        int[] contagemTags = contarPalavras(conteudoPagina, tags);

        contagemVogais[0] -= (1 * contagemTags[1]);
        contagemVogais[1] -= (1 * contagemTags[1]);
        totalConsoantes -= (3 * contagemTags[1]);
        totalConsoantes -= (2 * contagemTags[0]);

        int indice = 0;
        while (indice < vogais.length) {
            System.out.print(vogais[indice] + "(" + contagemVogais[indice] + ") ");
            indice++;
        }

        indice = 0;
        while (indice < contagemConsoantes.length) {
            totalConsoantes += contagemConsoantes[indice];
            indice++;
        }

        System.out.print("consoante(" + totalConsoantes + ") ");

        indice = 0;
        while (indice < tags.length) {
            System.out.print(tags[indice] + "(" + contagemTags[indice] + ") ");
            indice++;
        }

        System.out.println(texto);
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);

        String texto = leitor.nextLine();
        String url;

        while (!(texto.length() == 3 && texto.charAt(0) == 'F' && texto.charAt(1) == 'I' && texto.charAt(2) == 'M')) {
            url = leitor.nextLine();
            contarElementos(url, texto);
            texto = leitor.nextLine();
        }

        leitor.close();
    }
}