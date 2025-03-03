import java.util.Scanner;

public class tp006 {

    public static boolean isVogal(char c) {
        String vogais = "aeiouAEIOU";
        int i = 0;
        while (i < vogais.length() && vogais.charAt(i) != c) {
            i++;
        }
        return i < vogais.length();
    }

    public static boolean isVogais(String s) {
        int i = 0;
        while (i < s.length() && isVogal(s.charAt(i))) {
            i++;
        }
        return i == s.length();
    }

    public static boolean isConsoantes(String s) {
        int i = 0;
        while (i < s.length() && ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) && !isVogal(s.charAt(i))) {
            i++;
        }
        return i == s.length();
    }

    public static boolean isInteiro(String s) {
        int i = (s.charAt(0) == '-' || s.charAt(0) == '+') ? 1 : 0;
        while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            i++;
        }
        return i == s.length();
    }

    public static boolean isReal(String s) {
        int i = (s.charAt(0) == '-' || s.charAt(0) == '+') ? 1 : 0;
        boolean ponto = false;
        while (i < s.length() && ((s.charAt(i) >= '0' && s.charAt(i) <= '9') || s.charAt(i) == '.' || s.charAt(i) == ',')) {
            if (s.charAt(i) == '.' || s.charAt(i) == ',') {
                ponto = !ponto;
            }
            i++;
        }
        return i == s.length() && ponto;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada = scanner.nextLine();
        while (!(entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M')) {
            String x1 = isVogais(entrada) ? "SIM" : "NAO";
            String x2 = isConsoantes(entrada) ? "SIM" : "NAO";
            String x3 = isInteiro(entrada) ? "SIM" : "NAO";
            String x4 = isReal(entrada) ? "SIM" : "NAO";
            System.out.println(x1 + " " + x2 + " " + x3 + " " + x4);
            entrada = scanner.nextLine();
        }
        scanner.close();
    }
}
