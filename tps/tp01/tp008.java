import java.util.Scanner;

public class tp008 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada = scanner.nextLine();
        while (!(entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M')) {
        int sooma = somar(entrada);
        System.out.println(sooma);
        entrada = scanner.nextLine();
        }
    }

    public static int somar(String s) {
   int soma = 0;
        for (int i = 0; i < s.length(); i++) {
            soma += s.charAt(i) - '0'; 
        }
        return soma;
    }
}
 
    

