import java.util.*;

class Atleta implements Comparable<Atleta> {
    String nome;
    int peso;

    public Atleta(String nome, int peso) {
        this.nome = nome;
        this.peso = peso;
    }

    // Define a ordenação: primeiro por peso decrescente, depois por nome crescente
    @Override
    public int compareTo(Atleta outro) {
        if (this.peso != outro.peso) {
            return outro.peso - this.peso; // peso decrescente
        } else {
            return this.nome.compareTo(outro.nome); // nome crescente
        }
    }

    @Override
    public String toString() {
        return nome + " " + peso;
    }
}

public class ConcursoPeso {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // quantidade de atletas
        List<Atleta> atletas = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String nome = sc.next();
            int peso = sc.nextInt();
            atletas.add(new Atleta(nome, peso));
        }

        Collections.sort(atletas); // usa compareTo

        for (Atleta atleta : atletas) {
            System.out.println(atleta);
        }

        sc.close();
    }
}
