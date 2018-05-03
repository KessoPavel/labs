package Documents;

import User.User;
import javafx.util.Duration;

import java.io.File;

/**
 * Created by kesso on 5.3.17.
 */

/**
 * lass Film contains information about loaded film
 */
public class Film extends Document {
    private String filmName;
    private String producer;
    private String genre;

    public Film(){
        super();
    }

    public Film(String name, long length, String resolution, String creator, String description,
                String filmName, String producer, String genre) {
        super(name, length, resolution, creator, description);
        this.filmName = filmName;
        this.producer = producer;
        this.genre = genre;
    }

    public Film(String dir, String creator, String description,
                String filmName, String producer, String genre) {
        super(dir, creator, description);
        this.filmName = filmName;
        this.producer = producer;
        this.genre = genre;
    }

    public Film(File file, String creator, String description,
                String filmName, String producer, String genre) {
        super(file, creator, description);
        this.filmName = filmName;
        this.producer = producer;
        this.genre = genre;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
