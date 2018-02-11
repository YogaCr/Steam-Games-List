package id.sch.smktelkom_mlg.tugas02.xirpl338.steamapigames;

/**
 * Created by Sakata Yoga on 11/02/2018.
 */

public class Game {
    private String title;
    private String urlImage;
    private String developer;
    private String publisher;

    public Game(String title, String urlImage, String developer, String publisher) {
        this.title = title;
        this.urlImage = urlImage;
        this.developer = developer;
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getPublisher() {
        return publisher;
    }
}
