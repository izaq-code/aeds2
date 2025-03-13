import java.util.Scanner;

public class tp009 {

    public static boolean saoAnagramas(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        int[] contador = new int[256]; // ASCII

        for (int i = 0; i < str1.length(); i++) {
            char ch1 = str1.charAt(i);
            char ch2 = str2.charAt(i);

            // converter para minúsculo manualmente
            if (ch1 >= 'A' && ch1 <= 'Z') {
                ch1 = (char)(ch1 + 32);
            }
            if (ch2 >= 'A' && ch2 <= 'Z') {
                ch2 = (char)(ch2 + 32);
            }

            contador[ch1]++;
            contador[ch2]--;
        }

        for (int i = 0; i < 256; i++) {
            if (contador[i] != 0) {
                return false;
            }
        }

        return true;
    }
    public static String mapearNao() {
        return "N" + (char) 195 + "O";
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();

        while (!(entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M')) {

            // encontrar posição do " - "
            int separador = -1;
            for (int i = 0; i < entrada.length() - 2; i++) {
                if (entrada.charAt(i) == ' ' && entrada.charAt(i + 1) == '-' && entrada.charAt(i + 2) == ' ') {
                    separador = i;
                    break;
                }
            }

            if (separador == -1) {
                entrada = sc.nextLine();
                continue;
            }

            // pegar str1 e str2 manualmente
            char[] str1Chars = new char[separador];
            for (int i = 0; i < separador; i++) {
                str1Chars[i] = entrada.charAt(i);
            }

            int str2Len = entrada.length() - (separador + 3);
            char[] str2Chars = new char[str2Len];
            for (int i = 0; i < str2Len; i++) {
                str2Chars[i] = entrada.charAt(separador + 3 + i);
            }

            // remover espaços iniciais e finais de str1
            int start1 = 0, end1 = str1Chars.length - 1;
            while (start1 <= end1 && str1Chars[start1] == ' ') start1++;
            while (end1 >= start1 && str1Chars[end1] == ' ') end1--;

            // remover espaços iniciais e finais de str2
            int start2 = 0, end2 = str2Chars.length - 1;
            while (start2 <= end2 && str2Chars[start2] == ' ') start2++;
            while (end2 >= start2 && str2Chars[end2] == ' ') end2--;

            // montar strings finais
            char[] finalStr1 = new char[end1 - start1 + 1];
            for (int i = start1; i <= end1; i++) {
                finalStr1[i - start1] = str1Chars[i];
            }

            char[] finalStr2 = new char[end2 - start2 + 1];
            for (int i = start2; i <= end2; i++) {
                finalStr2[i - start2] = str2Chars[i];
            }

            // montar as Strings completas a partir dos char[]
            String novaStr1 = "";
            for (int i = 0; i < finalStr1.length; i++) {
                novaStr1 += finalStr1[i];
            }

            String novaStr2 = "";
            for (int i = 0; i < finalStr2.length; i++) {
                novaStr2 += finalStr2[i];
            }

            // verificar se são anagramas
            if (saoAnagramas(novaStr1, novaStr2)) {
                System.out.println("SIM");
            } else {
                System.out.println(mapearNao());
            }

            entrada = sc.nextLine();
        }

        sc.close();
    }
}
