import java.util.Scanner;

public class tp005 {

    public static String substituirVariaveis(String expressao, int entrada[]) {
        String resultado = "";

        for (int i = 1; i < expressao.length(); i++) {
            char caractere = expressao.charAt(i);
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
        }

        return resultado;
    }

    public static String removerNot(String expressao) {
        String semNot = "";
        int i = 0;

        while (i < expressao.length()) {
            if (i + 5 < expressao.length() && // Impede que acesse índice inexistente
                expressao.charAt(i) == 'n' &&
                expressao.charAt(i + 1) == 'o' &&
                expressao.charAt(i + 2) == 't' &&
                expressao.charAt(i + 3) == '(' &&
                expressao.charAt(i + 4) == '0' &&
                expressao.charAt(i + 5) == ')') {

                semNot += '1';
                i += 6; // Avança o índice para pular o not()
            } else if (i + 5 < expressao.length() && // Impede que acesse índice inexistente
                       expressao.charAt(i) == 'n' &&
                       expressao.charAt(i + 1) == 'o' &&
                       expressao.charAt(i + 2) == 't' &&
                       expressao.charAt(i + 3) == '(' &&
                       expressao.charAt(i + 4) == '1' &&
                       expressao.charAt(i + 5) == ')') {

                semNot += '0';
                i += 6; // Avança o índice para pular o not()
            } else {
                semNot += expressao.charAt(i);
                i++;
            }
        }

        return semNot;
    }

    public static String removerAnd(String expressao) {
        String semAnd = "";
        int i = 0;

        while (i < expressao.length()) {
            if (i + 9 < expressao.length() && // Impede que acesse índice inexistente
                expressao.charAt(i) == 'a' &&
                expressao.charAt(i + 1) == 'n' &&
                expressao.charAt(i + 2) == 'd' &&
                expressao.charAt(i + 3) == '(' &&
                expressao.charAt(i + 9) == ')') {

                if (expressao.charAt(i + 4) == expressao.charAt(i + 8))
                    semAnd += expressao.charAt(i + 4);
                else
                    semAnd += '0';

                i += 10; // Avança o índice para pular o And()

            } else if (i + 13 < expressao.length() && // Impede que acesse índice inexistente
                       expressao.charAt(i) == 'a' &&
                       expressao.charAt(i + 1) == 'n' &&
                       expressao.charAt(i + 2) == 'd' &&
                       expressao.charAt(i + 3) == '(' &&
                       expressao.charAt(i + 4) != 'n' &&
                       expressao.charAt(i + 4) != 'o' &&
                       expressao.charAt(i + 4) != 'a' &&
                       expressao.charAt(i + 13) == ')') {

                if (expressao.charAt(i + 4) == expressao.charAt(i + 12) && expressao.charAt(i + 4) == expressao.charAt(i + 8))
                    semAnd += expressao.charAt(i + 4);
                else
                    semAnd += '0';

                i += 14; // Avança o índice para pular o And()
            } else {
                semAnd += expressao.charAt(i);
                i++;
            }
        }

        return semAnd;
    }

    public static String removerOr(String expressao) {
        String semOr = "";
        int i = 0;

        while (i < expressao.length()) {
            if (i + 8 < expressao.length() && // Impede que acesse índice inexistente
                expressao.charAt(i) == 'o' &&
                expressao.charAt(i + 1) == 'r' &&
                expressao.charAt(i + 2) == '(' &&
                expressao.charAt(i + 8) == ')') {

                if (expressao.charAt(i + 3) == expressao.charAt(i + 7))
                    semOr += expressao.charAt(i + 3);
                else
                    semOr += '1';

                i += 9; // Avança o índice para pular o Or()

            } else if (i + 12 < expressao.length() && // Impede que acesse índice inexistente
                       expressao.charAt(i) == 'o' &&
                       expressao.charAt(i + 1) == 'r' &&
                       expressao.charAt(i + 2) == '(' &&
                       expressao.charAt(i + 3) != 'o' &&
                       expressao.charAt(i + 3) != 'a' &&
                       expressao.charAt(i + 3) != 'n' &&
                       expressao.charAt(i + 12) == ')') {

                if (expressao.charAt(i + 3) == expressao.charAt(i + 11) && expressao.charAt(i + 3) == expressao.charAt(i + 7))
                    semOr += expressao.charAt(i + 3);
                else
                    semOr += '1';

                i += 13; // Avança o índice para pular o Or()

                // Lidando com a exceção de um Or de 8 letras
            } else if (i + 7 < expressao.length() && // Impede que acesse índice inexistente
                       expressao.charAt(i) == 'o' &&
                       expressao.charAt(i + 1) == 'r' &&
                       expressao.charAt(i + 2) == '(' &&
                       expressao.charAt(i + 7) == ')') {

                if (expressao.charAt(i + 3) == expressao.charAt(i + 6))
                    semOr += expressao.charAt(i + 3);
                else
                    semOr += '1';

                i += 8; // Avança o índice para pular o Or()

            } else if (i + 16 < expressao.length() && // Impede que acesse índice inexistente
                       expressao.charAt(i) == 'o' &&
                       expressao.charAt(i + 1) == 'r' &&
                       expressao.charAt(i + 2) == '(' &&
                       expressao.charAt(i + 3) != 'o' &&
                       expressao.charAt(i + 3) != 'a' &&
                       expressao.charAt(i + 3) != 'n' &&
                       expressao.charAt(i + 16) == ')') {

                if (expressao.charAt(i + 3) == '1' || expressao.charAt(i + 7) == '1' || expressao.charAt(i + 11) == '1' || expressao.charAt(i + 15) == '1')
                    semOr += '1';
                else
                    semOr += '0';

                i += 17; // Avança o índice para pular o Or()

            } else {
                semOr += expressao.charAt(i);
                i++;
            }
        }

        return semOr;
    }

    public static String calcularAlgebraBooleana(String expressao, int entrada[]) {
        String resultado = substituirVariaveis(expressao, entrada);
        int iteracoes = 0;

        while (iteracoes < 10) {
            resultado = removerNot(resultado);
            resultado = removerAnd(resultado);
            resultado = removerOr(resultado);
            iteracoes++;
        }

        return resultado;
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