import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class tp0014 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            int n = scanner.nextInt();
            
            RandomAccessFile arquivo = new RandomAccessFile("numeros.dat", "rw");
            
            // Lendo e gravando os números reais no arquivo
            for (int i = 0; i < n; i++) {
                double numero = scanner.nextDouble();
                arquivo.writeDouble(numero);
            }
            
            arquivo.close();
            
            arquivo = new RandomAccessFile("numeros.dat", "r");
            
            // Posicionando o ponteiro para o final do arquivo
            long posicao = (n - 1) * 8; 
            
            // Lendo os números de trás para frente
            for (int i = 0; i < n; i++) {
                arquivo.seek(posicao);
                
                double numero = arquivo.readDouble();
                
                System.out.println(numero);
                
                // Movendo o ponteiro para a posição anterior
                posicao -= 8;
            }
            
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
