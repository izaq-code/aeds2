import java.util.Scanner;

public class tp0011 {

    public static int maiorSubstringSemRepeticao(String s) {
        int maxComprimento = 0;
        int i = 0;
        boolean[] visto = new boolean[256];

        for (int j = 0; j < s.length(); j++) {
            while (visto[s.charAt(j)]) {
                visto[s.charAt(i)] = false;
                i++;
            }
            visto[s.charAt(j)] = true;
            maxComprimento = Math.max(maxComprimento, j - i + 1);
        }

        // Se a maior substring sem repetição for igual ao comprimento da string e a string for sem repetições
        if (maxComprimento == s.length() && !temRepeticao(s)) {
            return 0;
        }

        return maxComprimento;
    }

    // Método para verificar se há repetições de caracteres na string
    private static boolean temRepeticao(String s) {
        boolean[] visto = new boolean[256];
        for (int i = 0; i < s.length(); i++) {
            if (visto[s.charAt(i)]) {
                return true; // Existe repetição
            }
            visto[s.charAt(i)] = true;
        }
        return false; // Não existe repetição
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();

        while (!(entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M')) {
            System.out.println(maiorSubstringSemRepeticao(entrada));
            entrada = sc.nextLine();
        }

        sc.close();
    }
}
