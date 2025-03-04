import java.util.Scanner;

public class tp0019 {

    public static String substituirVariaveis(String expressao, int entrada[], int i) {
        if (i >= expressao.length()) return "";
        
        char caractere = expressao.charAt(i);
        String resultado = "";
        
        switch (caractere) {
            case 'A':
                resultado += entrada[1];
                break;
            case 'B':
                resultado += entrada[2];
                break;
            case 'C':
                resultado += entrada[3];
                break;
            default:
                resultado += caractere;
                break;
        }
        
        return resultado + substituirVariaveis(expressao, entrada, i + 1);
    }

    public static String removerNot(String expressao, int i, String semNot) {
        if (i >= expressao.length()) return semNot;
        
        // Verifica se é o padrão "not(0)" ou "not(1)"
        if (i + 5 < expressao.length() && expressao.charAt(i) == 'n' &&
            expressao.charAt(i + 1) == 'o' && expressao.charAt(i + 2) == 't' &&
            expressao.charAt(i + 3) == '(' && expressao.charAt(i + 4) == '0' &&
            expressao.charAt(i + 5) == ')') {
            return removerNot(expressao, i + 6, semNot + '1');
        } else if (i + 5 < expressao.length() && expressao.charAt(i) == 'n' &&
                   expressao.charAt(i + 1) == 'o' && expressao.charAt(i + 2) == 't' &&
                   expressao.charAt(i + 3) == '(' && expressao.charAt(i + 4) == '1' &&
                   expressao.charAt(i + 5) == ')') {
            return removerNot(expressao, i + 6, semNot + '0');
        } else {
            return removerNot(expressao, i + 1, semNot + expressao.charAt(i));
        }
    }

    public static String removerAnd(String expressao, int i, String semAnd) {
        if (i >= expressao.length()) return semAnd;

        if (i + 9 < expressao.length() && expressao.charAt(i) == 'a' &&
            expressao.charAt(i + 1) == 'n' && expressao.charAt(i + 2) == 'd' &&
            expressao.charAt(i + 3) == '(' && expressao.charAt(i + 9) == ')') {
            char result = (expressao.charAt(i + 4) == expressao.charAt(i + 8)) ? expressao.charAt(i + 4) : '0';
            return removerAnd(expressao, i + 10, semAnd + result);
        } else if (i + 13 < expressao.length() && expressao.charAt(i) == 'a' &&
                   expressao.charAt(i + 1) == 'n' && expressao.charAt(i + 2) == 'd' &&
                   expressao.charAt(i + 3) == '(' && expressao.charAt(i + 4) != 'n' &&
                   expressao.charAt(i + 4) != 'o' && expressao.charAt(i + 4) != 'a' &&
                   expressao.charAt(i + 13) == ')') {
            char result = (expressao.charAt(i + 4) == expressao.charAt(i + 12) &&
                           expressao.charAt(i + 4) == expressao.charAt(i + 8)) ? expressao.charAt(i + 4) : '0';
            return removerAnd(expressao, i + 14, semAnd + result);
        } else {
            return removerAnd(expressao, i + 1, semAnd + expressao.charAt(i));
        }
    }

    public static String removerOr(String expressao, int i, String semOr) {
        if (i >= expressao.length()) return semOr;

        if (i + 8 < expressao.length() && expressao.charAt(i) == 'o' &&
            expressao.charAt(i + 1) == 'r' && expressao.charAt(i + 2) == '(' &&
            expressao.charAt(i + 8) == ')') {
            char result = (expressao.charAt(i + 3) == expressao.charAt(i + 7)) ? expressao.charAt(i + 3) : '1';
            return removerOr(expressao, i + 9, semOr + result);
        } else if (i + 12 < expressao.length() && expressao.charAt(i) == 'o' &&
                   expressao.charAt(i + 1) == 'r' && expressao.charAt(i + 2) == '(' &&
                   expressao.charAt(i + 3) != 'o' && expressao.charAt(i + 3) != 'a' &&
                   expressao.charAt(i + 3) != 'n' && expressao.charAt(i + 12) == ')') {
            char result = (expressao.charAt(i + 3) == expressao.charAt(i + 11) &&
                           expressao.charAt(i + 3) == expressao.charAt(i + 7)) ? expressao.charAt(i + 3) : '1';
            return removerOr(expressao, i + 13, semOr + result);
        } else if (i + 7 < expressao.length() && expressao.charAt(i) == 'o' &&
                   expressao.charAt(i + 1) == 'r' && expressao.charAt(i + 2) == '(' &&
                   expressao.charAt(i + 7) == ')') {
            char result = (expressao.charAt(i + 3) == expressao.charAt(i + 6)) ? expressao.charAt(i + 3) : '1';
            return removerOr(expressao, i + 8, semOr + result);
        } else if (i + 16 < expressao.length() && expressao.charAt(i) == 'o' &&
                   expressao.charAt(i + 1) == 'r' && expressao.charAt(i + 2) == '(' &&
                   expressao.charAt(i + 3) != 'o' && expressao.charAt(i + 3) != 'a' &&
                   expressao.charAt(i + 3) != 'n' && expressao.charAt(i + 16) == ')') {
            char result = (expressao.charAt(i + 3) == '1' || expressao.charAt(i + 7) == '1' ||
                           expressao.charAt(i + 11) == '1' || expressao.charAt(i + 15) == '1') ? '1' : '0';
            return removerOr(expressao, i + 17, semOr + result);
        } else {
            return removerOr(expressao, i + 1, semOr + expressao.charAt(i));
        }
    }

    public static String calcularAlgebraBooleana(String expressao, int entrada[]) {
        String resultado = substituirVariaveis(expressao, entrada, 0);
        return calcularRecursivamente(resultado, entrada, 0);
    }

    public static String calcularRecursivamente(String expressao, int entrada[], int iteracoes) {
        if (iteracoes >= 10) return expressao;
        
        String resultado = removerNot(expressao, 0, "");
        resultado = removerAnd(resultado, 0, "");
        resultado = removerOr(resultado, 0, "");
        
        return calcularRecursivamente(resultado, entrada, iteracoes + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int entrada[] = new int[4];
        String expressao = "";

        entrada[0] = sc.nextInt();

        while (entrada[0] != 0) {
            entrada[1] = sc.nextInt();
            entrada[2] = sc.nextInt();

            if (entrada[0] > 2)
                entrada[3] = sc.nextInt();

            expressao = sc.nextLine();

            System.out.println(calcularAlgebraBooleana(expressao, entrada));
            entrada[0] = sc.nextInt();
        }

        sc.close();
    }
}
