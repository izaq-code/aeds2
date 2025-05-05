import java.io.*;
import java.text.*;
import java.util.*;

public class tp001 {
    private String show_id;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date date_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

    // Construtor padrão
    public tp001() {
        this.show_id = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[0];
        this.country = "NaN";
        try {
            this.date_added = sdf.parse("March 1, 1900");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.release_year = 0;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listed_in = new String[0];
    }

    public tp001(String show_id, String type, String title, String director, String[] cast,
                 String country, Date date_added, int release_year, String rating,
                 String duration, String[] listed_in) {
        this.show_id = show_id;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.date_added = date_added;
        this.release_year = release_year;
        this.rating = rating;
        this.duration = duration;
        this.listed_in = listed_in;
    }


    public void ler(String linha) {
        String[] campos = separarLinhaCSV(linha);
    
        this.show_id = campos[0].isEmpty() ? "NaN" : campos[0];
        this.type = campos[1].isEmpty() ? "NaN" : campos[1];
        this.title = campos[2].isEmpty() ? "NaN" : campos[2].replace("\"", ""); 
        this.director = campos[3].isEmpty() ? "NaN" : campos[3];
        this.cast = campos[4].isEmpty() ? new String[]{"NaN"} : ordenar(campos[4].split(","));
        this.country = campos[5].isEmpty() ? "NaN" : campos[5];
    
        try {
            this.date_added = campos[6].isEmpty() ? sdf.parse("March 1, 1900") : sdf.parse(campos[6]);
        } catch (ParseException e) {
            try {
                this.date_added = sdf.parse("March 1, 1900");
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    
        this.release_year = campos[7].isEmpty() ? 0 : Integer.parseInt(campos[7]);
        this.rating = campos[8].isEmpty() ? "NaN" : campos[8];
        this.duration = campos[9].isEmpty() ? "NaN" : campos[9];
        this.listed_in = campos[10].isEmpty() ? new String[0] : ordenar(campos[10].split(","));
    }


    public void imprimir() {
        String dataFormatada = (date_added != null) ? new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(date_added) : "NaN";
        
        System.out.print("=> " + show_id + " ## " + title + " ## " + type + " ## " + director + " ## ");
        System.out.print(Arrays.toString(cast) + " ## " + country + " ## " + dataFormatada + " ## ");
        System.out.print(release_year + " ## " + rating + " ## " + duration + " ## ");
        System.out.println(Arrays.toString(listed_in) + " ##");
    }

    private String[] separarLinhaCSV(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean dentroDeAspas = false;

        for (char c : linha.toCharArray()) {
            if (c == '\"') {
                dentroDeAspas = !dentroDeAspas;
            } else if (c == ',' && !dentroDeAspas) {
                campos.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        campos.add(sb.toString().trim());
        return campos.toArray(new String[0]);
    }

// Método para ordenar uma lista de strings
private String[] ordenar(String[] lista) {
    for (int i = 0; i < lista.length; i++) {
        lista[i] = lista[i].trim();  
    }
    Arrays.sort(lista);
    return lista;
}


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader arq = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
        String linha;
        Map<String, String> mapa = new HashMap<>();

        arq.readLine(); 
        while ((linha = arq.readLine()) != null) {
            String[] partes = linha.split(",", 2);
            if (partes.length > 1) {
                mapa.put(partes[0].trim(), linha);
            }
        }
        arq.close();

        while (true) {
            String id = br.readLine().trim();

            if (id.equals("FIM")) {
                break; 
            }

            String dados = mapa.get(id);
            if (dados != null) {
                tp001 s = new tp001();
                s.ler(dados);
                s.imprimir();
            } else {
                System.out.println("ID não encontrado.");
            }
        }
    }
}
