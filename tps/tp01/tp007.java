import java.util.Scanner;

public class tp007 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada = scanner.nextLine();
        while (!(entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M')) {
        String invertida = inverterString(entrada);
        System.out.println(invertida);
        entrada = scanner.nextLine();
        }
    }

    public static String inverterString(String s) {
        char[] resultado = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            resultado[i] = s.charAt(s.length() - 1 - i);
        }
        return new String(resultado);
    }
}
 
    

