import java.util.Scanner;

public class tp0018 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String frase = sc.nextLine();
        processar(frase, sc);
    }

    public static void processar(String frase, Scanner sc) {
        if (frase.length() == 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M') {
            return;
        }

        String cesar = cripto(frase);
        System.out.println(cesar);

        frase = sc.nextLine();
        processar(frase, sc);
    }

    public static String cripto(String frase) {
        String resultado = "";
        int desloque = 3;

        for (int i = 0; i < frase.length(); i++) {
            char ch = frase.charAt(i);

            if (ASCII(ch) || simbolovalido(ch)) {
                resultado += (char) (ch + desloque);
            } else {
                resultado += ch;
            }
        }
        return resultado;
    }

    private static boolean ASCII(char ch) {
        return (ch >= 'A' && ch <= 'Z') || 
               (ch >= 'a' && ch <= 'z') || 
               (ch >= '0' && ch <= '9') || 
               (ch == '\u00e6' || ch == '\u00f8' || ch == '\u00e5' ||  // æ, ø, å
                ch == '\u00c6' || ch == '\u00d8' || ch == '\u00c5' ||  // Æ, Ø, Å
                (ch >= '\u00e0' && ch <= '\u017f') ||  // Caracteres acentuados minúsculos
                (ch >= '\u00c0' && ch <= '\u017f'));  // Caracteres acentuados maiúsculos
    }

    private static boolean simbolovalido(char ch) {
        return (ch == '-' || ch == '.' || ch == ',' || ch == '!' || ch == '?' || ch == ':' || ch == ';' || 
        ch == ')' || ch == '(' || ch == '*' || ch == '&' || ch == '^' || ch == '%' || ch == '$' || 
        ch == '#' || ch == '@' || ch == '!' || ch == '"' || ch == '\'' || ch == '<' || ch == '>' || 
        ch == '/' || ch == '`' || ch == '~' || ch == ' ' || ch == '}' || ch == '{' || ch == '_' || 
        ch == ']' || ch == '[' || ch == '=');
    }
}
