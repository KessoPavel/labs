package Documents;

import User.User;
import javafx.util.Duration;

import java.io.File;

/**
 * Created by kesso on 5.3.17.
 */

/**
 * lass Song contains information about loaded song
 */
public class Song extends Document {
    private String executor;
    private String songName;
    private String album;
    private int year;

    public Song(){
        super();
    }

    public Song(String name, long length, String resolution, String creator, String description,
                String executor, String songName, String album, int year) {
        super(name, length, resolution, creator, description);
        this.executor = executor;
        this.songName = songName;
        this.album = album;
        this.year = year;
    }

    public Song(String dir, String creator, String description,
                String executor, String songName, String album, int year) {
        super(dir, creator, description);
        this.executor = executor;
        this.songName = songName;
        this.album = album;
        this.year = year;
    }

    public Song(File file, String creator, String description,
                String executor, String songName, String album, int year) {
        super(file, creator, description);
        this.executor = executor;
        this.songName = songName;
        this.album = album;
        this.year = year;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
