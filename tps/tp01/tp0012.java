import java.util.Scanner;

public class tp0012 {

    public static boolean validarSenha(String senha) {
        if (senha.length() < 8) {
            return false;
        }

        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temEspecial = false;

        for (int i = 0; i < senha.length(); i++) {
            char ch = senha.charAt(i);

            if (ch >= 'A' && ch <= 'Z') {
                temMaiuscula = true;
            }            
            else if (ch >= 'a' && ch <= 'z') {
                temMinuscula = true;
            }
            else if (ch >= '0' && ch <= '9') {
                temNumero = true;
            }
            else if ( (ch == '-' || ch == '.' || ch == ',' || ch == '!' || ch == '?' || ch == ':' || ch == ';' || 
            ch == ')' || ch == '(' || ch == '*' || ch == '&' || ch == '^' || ch == '%' || ch == '$' || 
            ch == '#' || ch == '@' || ch == '!' || ch == '"' || ch == '\'' || ch == '<' || ch == '>' || 
            ch == '/' || ch == '`' || ch == '~' || ch == ' ' || ch == '}' || ch == '{' || ch == '_' || 
            ch == ']' || ch == '[' || ch == '=')) {
                temEspecial = true;
            }
        }

        return temMaiuscula && temMinuscula && temNumero && temEspecial;
    }

    public static String mapearNao() {
        return "N" + (char) 195 + "O";
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");  
        String entrada = scanner.nextLine();

        while (!(entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M')) {
            if (validarSenha(entrada)) {
                System.out.println("SIM");
            } else {
                System.out.println(mapearNao()); 
            }
            entrada = scanner.nextLine();
        }

        scanner.close();
    }
}
