package Interface;

import Documents.Book;
import Documents.Document;
import Documents.Film;
import Documents.Song;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Created by kesso on 5.3.17.
 */
public class TableItem {
    private final SimpleStringProperty type;
    private final SimpleStringProperty name;
    private final SimpleStringProperty size;
    private final SimpleStringProperty creator;

    //for book
    private final SimpleStringProperty bookName;
    private final SimpleStringProperty autor;
    private final SimpleStringProperty genre;

    //for song
    private final SimpleStringProperty songName;
    private final SimpleStringProperty executor;
    private final SimpleStringProperty album;
    private final SimpleStringProperty year;

    //for film
    private final SimpleStringProperty filmName;
    private final SimpleStringProperty producer;
    private final SimpleStringProperty filmGenre;

    public TableItem(Document doc) {
        this.type = new SimpleStringProperty(doc.getResolution());
        this.name = new SimpleStringProperty(doc.getName());
        this.size = new SimpleStringProperty((doc.getLength() / 1024) + "KB");
        this.creator = new SimpleStringProperty(doc.getCreator());

        this.bookName = this.autor = this.genre = null;
        this.songName = this.executor = this.album = this.year = null;
        this.filmName = this.producer = this.filmGenre = null;
    }

    public TableItem(Book doc){
        this.type = new SimpleStringProperty(doc.getResolution());
        this.name = new SimpleStringProperty(doc.getName());
        this.size = new SimpleStringProperty((doc.getLength() / 1024) + "KB");
        this.creator = new SimpleStringProperty(doc.getCreator());

        this.bookName = new SimpleStringProperty(doc.getBookName());
        this.autor = new SimpleStringProperty(doc.getAutor());
        this.genre = new SimpleStringProperty(doc.getGenre());

        this.songName = this.executor = this.album = this.year = null;
        this.filmName = this.producer = this.filmGenre = null;
    }

    public TableItem(Song doc){
        this.type = new SimpleStringProperty(doc.getResolution());
        this.name = new SimpleStringProperty(doc.getName());
        this.size = new SimpleStringProperty((doc.getLength() / 1024) + "KB");
        this.creator = new SimpleStringProperty(doc.getCreator());

        this.songName = new SimpleStringProperty(doc.getSongName());
        this.executor = new SimpleStringProperty(doc.getExecutor());
        this.album = new SimpleStringProperty(doc.getAlbum());
        this.year = new SimpleStringProperty(new Integer(doc.getYear()).toString());

        this.bookName = this.autor = this.genre = null;
        this.filmName = this.producer = this.filmGenre = null;
    }

    public TableItem(Film doc){
        this.type = new SimpleStringProperty(doc.getResolution());
        this.name = new SimpleStringProperty(doc.getName());
        this.size = new SimpleStringProperty((doc.getLength() / 1024) + "KB");
        this.creator = new SimpleStringProperty(doc.getCreator());

        this.bookName = this.autor = this.genre = null;
        this.songName = this.executor = this.album = this.year = null;

        this.filmName = new SimpleStringProperty(doc.getFilmName());
        this.producer = new SimpleStringProperty(doc.getProducer());
        this.filmGenre = new SimpleStringProperty(doc.getGenre());
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public String getSize() {
        return size.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getType() {
        return type.get();
    }

    public void setCreator(String creator){
        this.creator.set(creator);
    }

    public String getCreator() {
        return creator.get();
    }

    public SimpleStringProperty creatorProperty() {
        return creator;
    }

    public String getBookName() {
        return bookName.get();
    }

    public SimpleStringProperty bookNameProperty() {
        return bookName;
    }

    public String getAutor() {
        return autor.get();
    }

    public SimpleStringProperty autorProperty() {
        return autor;
    }

    public String getGenre() {
        return genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public String getSongName() {
        return songName.get();
    }

    public SimpleStringProperty songNameProperty() {
        return songName;
    }

    public String getExecutor() {
        return executor.get();
    }

    public SimpleStringProperty executorProperty() {
        return executor;
    }

    public String getAlbum() {
        return album.get();
    }

    public SimpleStringProperty albumProperty() {
        return album;
    }

    public String getYear() {
        return year.get();
    }

    public SimpleStringProperty yearProperty() {
        return year;
    }

    public String getFilmName() {
        return filmName.get();
    }

    public SimpleStringProperty filmNameProperty() {
        return filmName;
    }

    public String getProducer() {
        return producer.get();
    }

    public SimpleStringProperty producerProperty() {
        return producer;
    }

    public String getFilmGenre() {
        return filmGenre.get();
    }

    public SimpleStringProperty filmGenreProperty() {
        return filmGenre;
    }
}
