import java.io.*;
import java.util.*;

public class tp0013 {
    public static void main(String[] args) {
        try {
            // Leitura dos dados
            List<Show> shows = readShowsFromFile("/tmp/disneyplus.csv");
            
            // Leitura dos IDs de entrada
            List<Show> selectedShows = readSelectedShows(shows);
            
            // Ordenação e medição de desempenho
            Show[] showArray = selectedShows.toArray(new Show[0]);
            PerformanceMetrics metrics = sortAndMeasurePerformance(showArray);
            
            // Saída dos resultados
            printResults(showArray, metrics);
            
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    // ==================== MÉTODOS DE LEITURA DE ARQUIVO ====================

    private static List<Show> readShowsFromFile(String filePath) throws IOException {
        List<Show> shows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Pular cabeçalho
            String line;
            while ((line = readCompleteLine(br)) != null) {
                Show show = new Show();
                show.read(line);
                shows.add(show);
            }
        }
        return shows;
    }

    private static String readCompleteLine(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        int quoteCount = 0;
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
            quoteCount += countQuotes(line);
            if (quoteCount % 2 == 0) break;
            sb.append("\n");
        }

        return sb.length() == 0 ? null : sb.toString();
    }

    private static int countQuotes(String line) {
        return (int) line.chars().filter(c -> c == '"').count();
    }

    // ==================== MÉTODOS DE PROCESSAMENTO ====================

    private static List<Show> readSelectedShows(List<Show> shows) {
        List<Show> selected = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            String id = scanner.nextLine().trim();
            if (id.equals("FIM")) break;
            
            shows.stream()
                .filter(s -> s.getShowId().equals(id))
                .findFirst()
                .ifPresent(selected::add);
        }
        
        scanner.close();
        return selected;
    }

    private static PerformanceMetrics sortAndMeasurePerformance(Show[] shows) {
        long startTime = System.currentTimeMillis();
        int comparisons = mergeSort(shows, 0, shows.length - 1);
        long endTime = System.currentTimeMillis();
        
        return new PerformanceMetrics(comparisons, endTime - startTime);
    }

    // ==================== ALGORITMO DE ORDENAÇÃO ====================

    private static int mergeSort(Show[] arr, int left, int right) {
        int comparisons = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            comparisons += mergeSort(arr, left, mid);
            comparisons += mergeSort(arr, mid + 1, right);
            comparisons += merge(arr, left, mid, right);
        }
        return comparisons;
    }

    private static int merge(Show[] arr, int left, int mid, int right) {
        int comparisons = 0;
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Show[] leftArray = Arrays.copyOfRange(arr, left, mid + 1);
        Show[] rightArray = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            comparisons++;
            int durationLeft = parseDuration(leftArray[i].getDuration());
            int durationRight = parseDuration(rightArray[j].getDuration());

            if (durationLeft < durationRight || 
                (durationLeft == durationRight && 
                 leftArray[i].getTitle().compareToIgnoreCase(rightArray[j].getTitle()) <= 0)) {
                arr[k++] = leftArray[i++];
            } else {
                arr[k++] = rightArray[j++];
            }
        }

        while (i < n1) arr[k++] = leftArray[i++];
        while (j < n2) arr[k++] = rightArray[j++];

        return comparisons;
    }

    private static int parseDuration(String duration) {
        try {
            return Integer.parseInt(duration.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ==================== SAÍDA DE RESULTADOS ====================

    private static void printResults(Show[] shows, PerformanceMetrics metrics) {
        Arrays.stream(shows).forEach(Show::print);
        
        try (PrintWriter writer = new PrintWriter("877487_mergesort.txt")) {
            writer.printf("877487\t%d\t%d%n", metrics.getTime(), metrics.getComparisons());
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao escrever arquivo de métricas: " + e.getMessage());
        }
    }

    // ==================== CLASSES AUXILIARES ====================

    private static class PerformanceMetrics {
        private final int comparisons;
        private final long time;

        public PerformanceMetrics(int comparisons, long time) {
            this.comparisons = comparisons;
            this.time = time;
        }

        public int getComparisons() { return comparisons; }
        public long getTime() { return time; }
    }
}

class Show {
    private String showId;
    private String type;
    private String title;
    private String[] director;
    private String[] cast;
    private String country;
    private String dateAdded;
    private int releaseYear;
    private String rating;
    private String duration;
    private String[] listedIn;

    public void read(String line) throws IllegalArgumentException {
        String[] fields = splitCSV(line);
        if (fields.length < 11) {
            throw new IllegalArgumentException("Linha inválida - campos insuficientes");
        }

        this.showId = fields[0].trim();
        this.type = fields[1].trim();
        this.title = fields[2].trim();
        this.director = parseArrayField(fields[3]);
        this.cast = parseArrayField(fields[4]);
        this.country = parseSingleField(fields[5]);
        this.dateAdded = parseDateField(fields[6]);
        this.releaseYear = parseYearField(fields[7]);
        this.rating = fields[8].trim();
        this.duration = fields[9].trim();
        this.listedIn = parseArrayField(fields[10]);
    }

    private static String[] splitCSV(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(sb.length() == 0 ? "NaN" : sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.length() == 0 ? "NaN" : sb.toString());

        return fields.toArray(new String[0]);
    }

    private String[] parseArrayField(String field) {
        return field.equals("NaN") ? new String[0] : field.split(",\\s*");
    }

    private String parseSingleField(String field) {
        return field.equals("NaN") ? "NaN" : field.trim();
    }

    private String parseDateField(String field) {
        return field.equals("NaN") ? "March 1, 1900" : field.trim();
    }

    private int parseYearField(String field) {
        try {
            return field.equals("NaN") ? 0 : Integer.parseInt(field);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void print() {
        System.out.printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##%n",
            showId, title, type,
            director.length > 0 ? String.join(", ", director) : "NaN",
            cast.length > 0 ? Arrays.toString(cast) : "NaN",
            country, dateAdded, releaseYear, rating, duration,
            listedIn.length > 0 ? Arrays.toString(listedIn) : "NaN");
    }

    // Getters
    public String getShowId() { return showId; }
    public String getTitle() { return title; }
    public String getDuration() { return duration; }
}