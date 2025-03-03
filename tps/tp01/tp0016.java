import java.util.Scanner;

public class tp0016 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        processarPalavra(sc);
        sc.close();
    }

    public static void processarPalavra(Scanner sc) {
     
        String palavra = sc.nextLine();

        if (palavra.length() == 3 && palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I' && palavra.charAt(2) == 'M') {
            return; 
        }

        boolean eUmPalindromo = verificarpalin(palavra);

        if (eUmPalindromo) {
            System.out.println("SIM");
        } else {
            System.out.println("NAO");
        }

        processarPalavra(sc);
    }

    public static boolean verificarpalin(String palavra) {
        boolean resultado = true;
        int tamanho = palavra.length();

        for (int i = 0; i < tamanho / 2; i++) {
            if (palavra.charAt(i) != palavra.charAt(tamanho - 1 - i)) {
                resultado = false;
            }
        }

        return resultado;
    }
}
