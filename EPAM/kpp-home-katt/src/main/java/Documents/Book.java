package Documents;

import User.User;

import java.io.File;

/**
 * Created by kesso on 5.3.17.
 */

/**
 * class Book contains information about loaded book
 */
public class Book extends  Document {
    private String bookName;
    private String autor;
    private String genre;

    public Book(){
        super();
    }

    public Book(String name, long length, String resolution, String creator, String description,
                String bookName, String autor, String genre) {
        super(name, length, resolution, creator, description);
        this.bookName = bookName;
        this.autor = autor;
        this.genre = genre;
    }

    public Book(String dir, String creator, String description,
                String bookName, String autor, String genre) {
        super(dir, creator, description);
        this.bookName = bookName;
        this.autor = autor;
        this.genre = genre;
    }

    public Book(File file, String creator, String description,
                String bookName, String autor, String genre) {
        super(file, creator, description);
        this.bookName = bookName;
        this.autor = autor;
        this.genre = genre;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
