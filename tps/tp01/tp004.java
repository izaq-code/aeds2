import java.util.*;
import java.io.*;

public class tp004 {
    static Random gerador = new Random(4);

    public static String alteracaoAleatoria(String s) {
        char letra1 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);
        char letra2 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);
        return s.replace(letra1, letra2);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String frase = sc.nextLine();

        while (!(frase.length() == 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M')) {
            String alteracao = alteracaoAleatoria(frase);
            System.out.println(alteracao);

            frase = sc.nextLine();
        }
    }


}

