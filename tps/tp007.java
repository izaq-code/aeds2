import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Locale;

class Show { 
    // Atributos
    private String showId;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private String dateAdded;
    private int releaseYear;
    private String rating;
    private String duration;
    private String[] listedIn;

    // Construtores
    public Show() {
        this.showId = null;
        this.type = null;
        this.title = null;
        this.director = null;
        this.cast = null;
        this.country = null;
        this.dateAdded = null;
        this.releaseYear = 0;
        this.rating = null;
        this.duration = null;
        this.listedIn = null;
    }

    public Show(String showId, String type, String title, String director, String[] cast, String country,
            String dateAdded, int releaseYear, String rating, String duration, String[] listedIn) {
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = new String[cast.length];
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.listedIn = new String[listedIn.length];
    }

    // Métodos de acesso (Getters e Setters)
    public String getShowId() { return showId; }
    public void setShowId(String x) { this.showId = (x.equals("")) ? "NaN" : x; }

    public String getType() { return type; }
    public void setType(String x) { this.type = (x.equals("")) ? "NaN" : x; }

    public String getTitle() { return title; }
    public void setTitle(String x) { this.title = (x.equals("")) ? "NaN" : x; }

    public String getDirector() { return director; }
    public void setDirector(String x) { this.director = (x.equals("")) ? "NaN" : x; }

    public String[] getCast() { return cast; }
    public void setCast(String[] x) {
        if(x.length == 1 && x[0].equals("")) {
            this.cast = new String[] { "NaN" };
        } else {
            this.cast = x;
        }
    }

    public String getCountry() { return country; }
    public void setCountry(String x) { this.country = (x.equals("")) ? "NaN" : x; }

    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String x) { this.dateAdded = (x.equals("")) ? "NaN" : x; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int x) { this.releaseYear = x; }

    public String getRating() { return rating; }
    public void setRating(String x) { this.rating = (x.equals("")) ? "NaN" : x; }

    public String getDuration() { return duration; }
    public void setDuration(String x) { this.duration = (x.equals("")) ? "NaN" : x; }

    public String[] getListedIn() { return listedIn; }
    public void setListedIn(String[] listedIn) {
        if(listedIn.length == 1 && listedIn[0].equals("")) {
            this.listedIn = new String[] { "NaN" };
        } else {
            this.listedIn = listedIn;
        }
    }

    // Métodos auxiliares
    public Show Clone() {
        return new Show(this.showId, this.type, this.title, this.director, this.cast, 
                      this.country, this.dateAdded, this.releaseYear, this.rating, 
                      this.duration, this.listedIn);
    }

    public int StrToInt(String str) {
        int len = str.length();
        int data = 0;

        for(int i = 0; i < len; i++) {
            char c = str.charAt(i);
            data = (10 * data) + (c - '0');
        }

        return data;
    }

    public void Ler(String in) throws Exception {
        String simplify = "";
        boolean flag = true;
        int len = in.length();

        for(int i = 0; i < len; i++) {
            char c = in.charAt(i);
            if (c == '"')
                flag = !flag;
            else if (c == ',' && flag)
                simplify += '|';
            else
                simplify += c;
        }

        String[] ShowStr = simplify.split("\\|");
        setShowId(ShowStr[0]);
        setType(ShowStr[1]);
        setTitle(ShowStr[2]);
        setDirector(ShowStr[3]);

        String[] CastAux = ShowStr[4].split(",\\s*");
        int castl = CastAux.length;
        quickSort(0, castl - 1, CastAux);
        setCast(CastAux);
        setCountry(ShowStr[5]);
        setDateAdded(ShowStr[6]);

        setReleaseYear(StrToInt(ShowStr[7]));
        setRating(ShowStr[8]);
        setDuration(ShowStr[9]);

        String[] ListedInAux = ShowStr[10].split(",\\s*");
        int ListedLen = ListedInAux.length;
        quickSort(0, ListedLen - 1, ListedInAux);
        setListedIn(ListedInAux);
    }

    public static Show Create(String in) throws Exception {
        Show created = new Show();
        created.Ler(in);
        return created;
    }

    public static Show[] csv(String caminho) throws Exception {
        Show[] shows = new Show[1369];
        File file = new File(caminho);
        if(!file.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado: " + caminho);
        }
        try(Scanner sc = new Scanner(file)) {
            String data;
            int i = 0;
            while(sc.hasNext()) {
                data = sc.nextLine();
                shows[i++] = Create(data);
            }
        }
        return shows;
    }

    public static void swap(int i, int j, String[] array) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void quickSort(int esq, int dir, String[] array) {
        if (array == null || array.length == 0 || esq >= dir)
            return;
        int i = esq, j = dir;
        String pivo = array[esq + (dir - esq) / 2]; 
        while (i <= j) {
            while (array[i].compareTo(pivo) < 0) i++;
            while (array[j].compareTo(pivo) > 0) j--;
            if (i <= j) {
                swap(i, j, array);
                i++;
                j--;
            }
        }
        if (esq < j) quickSort(esq, j, array);
        if (i < dir) quickSort(i, dir, array);
    }

    public void Imprimir() {
        System.out.print("=> ");
        System.out.print(showId + " ## ");
        System.out.print(title + " ## ");
        System.out.print(type + " ## ");
        System.out.print(director + " ## ");
        System.out.print("[");
        if(cast != null && cast.length > 0)
            System.out.print(String.join(", ", cast));
        System.out.print("]");
        System.out.print(" ## ");
        System.out.print(country + " ## ");
        System.out.print(dateAdded + " ## ");
        System.out.print(releaseYear + " ## ");
        System.out.print(rating + " ## ");
        System.out.print(duration + " ## ");
        System.out.print("[");
        if(listedIn != null && listedIn.length > 0)
            System.out.print(String.join(", ", listedIn));
        System.out.print("] ## ");
        System.out.println(); 
    }
}

public class tp007 {
    // Variáveis para contagem
    public static int comparacoes = 0;
    public static int movimentacoes = 0;

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        MyIO.setCharset("ISO-8859-1");
        
        // Carrega os dados do arquivo CSV
        Show[] base = Show.csv("/tmp/disneyplus.csv");
        Show[] vetor = new Show[1400];
        int n = 0;

        // Leitura dos IDs de entrada
        String entrada = MyIO.readLine();
        while (!entrada.equals("FIM")) {
            for (Show s : base) {
                if (s.getShowId().equals(entrada)) {
                    vetor[n++] = s;
                    break;
                }
            }
            entrada = MyIO.readLine();
        }

        // Medição do tempo de execução
        long inicio = System.nanoTime();
        insertionSort(vetor, n);
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1e6;

        // Impressão dos resultados ordenados
        for (int i = 0; i < n; i++) {
            vetor[i].Imprimir();
        }

        // Geração do arquivo de log
        FileWriter log = new FileWriter("877487_insercao.txt");
        log.write("877487\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo);
        log.close();
    }

    /**
     * Implementação do Insertion Sort para ordenar por tipo e título
     */
    public static void insertionSort(Show[] vetor, int n) {
        for (int i = 1; i < n; i++) {
            Show chave = vetor[i];
            int j = i - 1;
            
            // Compara primeiro pelo tipo, depois pelo título se necessário
            while (j >= 0 && (vetor[j].getType().compareTo(chave.getType()) > 0 || 
                  (vetor[j].getType().equals(chave.getType()) && 
                   vetor[j].getTitle().compareTo(chave.getTitle()) > 0))) {
                comparacoes++;
                vetor[j + 1] = vetor[j];
                j--;
                movimentacoes++;
            }
            vetor[j + 1] = chave;
            movimentacoes++;
        }
    }
}