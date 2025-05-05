package tp02;
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

public class tp009 {
    // Variáveis de contagem
    public static int comp = 0;
    public static int mov = 0;

    // Implementação do Heapsort
    public static void heapsort(Show[] array, int n) {
        // Construção inicial do heap
        for (int tam = 2; tam <= n; tam++) {
            construirHeap(array, tam);
        }
        // Reorganização do heap
        int tam = n;
        while (tam > 1) {
            swap(array, 0, tam - 1);
            tam--;
            reconstroiHeap(array, tam);
        }
    }
    
    public static void construirHeap(Show[] array, int tam) {
        int i = tam - 1;
        while (i > 0 && maior(array[i], array[(i - 1) / 2])) {
            swap(array, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }
    
    public static void reconstroiHeap(Show[] array, int tam) {
        int i = 0;
        while (i < tam / 2) {
            int filho = getMaiorFilho(array, i, tam);
            if (maior(array[filho], array[i])) {
                swap(array, i, filho);
                i = filho;
            } else {
                break;
            }
        }
    }
    
    public static int getMaiorFilho(Show[] array, int i, int tam) {
        int filhoEsq = 2 * i + 1;
        int filhoDir = 2 * i + 2;
        if (filhoDir < tam) {
            return maior(array[filhoEsq], array[filhoDir]) ? filhoEsq : filhoDir;
        } else {
            return filhoEsq;
        }
    }
    
    public static boolean maior(Show s1, Show s2) {
        comp++;
        int cmp = s1.getDirector().compareToIgnoreCase(s2.getDirector());
        if (cmp > 0) return true;
        else if (cmp == 0)
            return s1.getTitle().compareToIgnoreCase(s2.getTitle()) > 0;
        return false;
    }
    
    public static void swap(Show[] array, int i, int j) {
        mov += 3;
        Show temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        MyIO.setCharset("UTF-8");
        Show[] base = Show.csv("/tmp/disneyplus.csv");
        Show[] vetor = new Show[1400];
        int n = 0;
    
        // Leitura dos IDs
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
    
        // Ordenação e medição de tempo
        long inicio = System.nanoTime();
        heapsort(vetor, n);
        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1e6;
    
        // Impressão dos resultados
        for (int i = 0; i < n; i++) {
            vetor[i].Imprimir();
        }
        
        // Gravação do log
        FileWriter fw = new FileWriter("matricula_heapsort.txt");
        fw.write("877487\t" + comp + "\t" + mov + "\t" + tempo);
        fw.close();
    }
}