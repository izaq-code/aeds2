import java.io.*;
import java.util.*;

public class tp001{
    
        public static void main(String[] args) throws FileNotFoundException {
            Scanner sc = new Scanner(new File("disneyplus.csv"));


            while(sc.hasNextLine()){

                System.out.println(sc.nextLine());

            }


        }

}


class Show{
        
    private String Show_id;
    private String type;
    private String title;
    private String director;
    private List<String> cast;
    private String country;
    private Date dateAdded;
    private int releaseYear;
    private String rating;
    private String duration;
    private List<String> listedIn;

    public Show() {
        this.Show_id = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new ArrayList<>();
        this.country = "NaN";
        this.dateAdded = null;
        this.releaseYear = -1;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listedIn = new ArrayList<>();
    }

    public Show(String Show_id, String type, String title, String director, List<String> cast, String country, Date dateAdded, int releaseYear, String rating, String duration, List<String> listedIn) {
        this.Show_id = Show_id;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = new ArrayList<>(cast);
        Collections.sort(this.cast);
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.listedIn = new ArrayList<>(listedIn);
        Collections.sort(this.listedIn);
    }
    
    // Métodos Getters e Setters
    public String getId() { return Show_id; }
    public void setId(String Show_id) { this.Show_id = Show_id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public List<String> getCast() { return cast; }
    public void setCast(List<String> cast) { this.cast = new ArrayList<>(cast); Collections.sort(this.cast); }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDateAdded() { return dateAdded; }
    public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public List<String> getListedIn() { return listedIn; }
    public void setListedIn(List<String> listedIn) { this.listedIn = new ArrayList<>(listedIn); Collections.sort(this.listedIn); }
    



    public static Show ler(String linha){

        Show show = new Show();

        return show;

    }




}
