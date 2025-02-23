import java.util.Scanner; 

public class tp001{
      public static void main (String[] args){

      Scanner sc = new Scanner(System.in);
      // essa le a primeira palavra
      String palavra = sc.nextLine();

      //quando a palavra tiver o tamanho 3 e todos os carecteres forem confirmados com true ele ira interromper caso contrario ele continua
      while(!(palavra.length() == 3 && palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I' && palavra.charAt(2) == 'M' )){

      boolean eUmPalindromo = verificarpalin(palavra);

         if(eUmPalindromo){
              System.out.println("SIM");
         }else{
              System.out.println("NAO");
         }
      //foi chamado para ler as proximas palavras
       palavra = sc.nextLine();

       }

      sc.close();

      }


public static boolean verificarpalin(String palavra){
      boolean resultado = true;

      int tamanho = palavra.length();
      //vai vereficar se o ultimo caracter e o primeiro sao diferentes se for diferente ele n e um palindromo
      for(int i = 0; i < tamanho /2 ; i++){

              if(palavra.charAt(i) != palavra.charAt(tamanho - 1 - i)){
                      resultado = false;
              }
      }


      return resultado;
 }
} 