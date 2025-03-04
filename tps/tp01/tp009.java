import java.util.Scanner;

public class tp009 {
    public static boolean saoAnagramas(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
    
        // Array para contar a ocorrência de cada caractere (considerando caracteres ASCII)
        int[] contador = new int[256];  // Usando 256 para considerar todos os caracteres ASCII
    
        for (int i = 0; i < str1.length(); i++) {
            char ch1 = str1.charAt(i);
            char ch2 = str2.charAt(i);
    
            if (ch1 >= 'A' && ch1 <= 'Z') {
                ch1 = (char) (ch1 + 32);  
            }
            if (ch2 >= 'A' && ch2 <= 'Z') {
                ch2 = (char) (ch2 + 32); 
            }
    
            // Verifica se os caracteres são válidos para o índice do contador (ASCII)
            if (ch1 < 0 || ch1 > 255 || ch2 < 0 || ch2 > 255) {
                System.out.println("Caractere inválido: " + ch1 + " ou " + ch2);
                return false; 
            }
    
            // Incrementa o contador para str1 e decrementa para str2
            contador[ch1]++;
            contador[ch2]--;
        }
    
        // Verifica se todos os contadores são zero, indicando que as strings são anagramas
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
        Scanner scanner = new Scanner(System.in);
        String entrada = scanner.nextLine();
        
        while (!(entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M')) {

            entrada = scanner.nextLine();
       

            int separadorIndex = -1;
            for (int i = 0; i < entrada.length() - 2; i++) {
                if (entrada.charAt(i) == ' ' && entrada.charAt(i + 1) == '-' && entrada.charAt(i + 2) == ' ') {
                    separadorIndex = i;
                 
                }
            }
            

            if (separadorIndex == -1) {
                continue; 
            }

            // Extrai manualmente a primeira string (str1)
            char[] str1Chars = new char[separadorIndex];
            for (int i = 0; i < separadorIndex; i++) {
                str1Chars[i] = entrada.charAt(i);
            }

            // Extrai manualmente a segunda string (str2)
            int str2Length = entrada.length() - separadorIndex - 3;  // " - " ocupa 3 caracteres
            char[] str2Chars = new char[str2Length];
            for (int i = 0; i < str2Length; i++) {
                str2Chars[i] = entrada.charAt(separadorIndex + 3 + i);
            }

            int start1 = 0;
            while (start1 < str1Chars.length && str1Chars[start1] == ' ') {
                start1++;
            }

            int end1 = str1Chars.length - 1;
            while (end1 >= start1 && str1Chars[end1] == ' ') {
                end1--;
            }

            int start2 = 0;
            while (start2 < str2Chars.length && str2Chars[start2] == ' ') {
                start2++;
            }

            int end2 = str2Chars.length - 1;
            while (end2 >= start2 && str2Chars[end2] == ' ') {
                end2--;
            }

            // Cria as novas strings sem espaços usando um loop
            char[] finalStr1 = new char[end1 - start1 + 1];
            for (int i = start1; i <= end1; i++) {
                finalStr1[i - start1] = str1Chars[i];
            }

            char[] finalStr2 = new char[end2 - start2 + 1];
            for (int i = start2; i <= end2; i++) {
                finalStr2[i - start2] = str2Chars[i];
            }

            // Verifica se as strings sem espaços são anagramas
            if (saoAnagramas(new String(finalStr1), new String(finalStr2))) {
                System.out.println("SIM");
            } else {
                System.out.println(mapearNao());
            }
        }

        scanner.close();
    }
}
