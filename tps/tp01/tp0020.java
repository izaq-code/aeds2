import java.util.Scanner;

public class tp0020 {

    public static boolean isVogal(char c) {
        String vogais = "aeiouAEIOU";
        int i = 0;
        while (i < vogais.length() && vogais.charAt(i) != c) {
            i++;
        }
        return i < vogais.length();
    }

    public static boolean isVogais(String s) {
        return isVogaisRec(s, 0);
    }

    private static boolean isVogaisRec(String s, int i) {
        if (i == s.length()) return true;
        if (!isVogal(s.charAt(i))) return false;
        return isVogaisRec(s, i + 1);
    }

    public static boolean isConsoantes(String s) {
        return isConsoantesRec(s, 0);
    }

    private static boolean isConsoantesRec(String s, int i) {
        if (i == s.length()) return true;
        char c = s.charAt(i);
        if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) || isVogal(c)) return false;
        return isConsoantesRec(s, i + 1);
    }

    public static boolean isInteiro(String s) {
        return isInteiroRec(s, (s.charAt(0) == '-' || s.charAt(0) == '+') ? 1 : 0);
    }

    private static boolean isInteiroRec(String s, int i) {
        if (i == s.length()) return true;
        if (s.charAt(i) < '0' || s.charAt(i) > '9') return false;
        return isInteiroRec(s, i + 1);
    }

    public static boolean isReal(String s) {
        return isRealRec(s, (s.charAt(0) == '-' || s.charAt(0) == '+') ? 1 : 0, false);
    }

    private static boolean isRealRec(String s, int i, boolean ponto) {
        if (i == s.length()) return ponto;
        char c = s.charAt(i);
        if ((c >= '0' && c <= '9') || c == '.' || c == ',') {
            if (c == '.' || c == ',') {
                if (ponto) return false;
                ponto = true;
            }
            return isRealRec(s, i + 1, ponto);
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada = scanner.nextLine();
        processarEntrada(entrada, scanner);
        scanner.close();
    }

    private static void processarEntrada(String entrada, Scanner scanner) {
        if (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M') {
            return;
        }

        String x1 = isVogais(entrada) ? "SIM" : "NAO";
        String x2 = isConsoantes(entrada) ? "SIM" : "NAO";
        String x3 = isInteiro(entrada) ? "SIM" : "NAO";
        String x4 = isReal(entrada) ? "SIM" : "NAO";
        System.out.println(x1 + " " + x2 + " " + x3 + " " + x4);

        entrada = scanner.nextLine();
        processarEntrada(entrada, scanner);
    }
}

