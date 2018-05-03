package DAO;

import Documents.Song;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by kesso on 8.3.17.
 * class for working with a table of songs in the database
 */
public class JDBCSongDAO implements DataDAO<Song> {

    private static final Logger log = Logger.getLogger(JDBCDocumentDAO.class);
    private Connection connection = null;

    /**
     * method that connects to database
     * @param connection Connection
     */
    public void setConnection(Connection connection){
        this.connection = connection;
    }

    /**
     * create a table films on the database, if it is not already created
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'songs' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'name' text, 'length' text, 'resolution' text,'creator' text, 'description' text," +
                "'executor' text, 'songName' text, 'album' text, 'year' INTEGER );");
        log.info("create ar open table songs");
    }

    /**
     * adding new record to the table
     * @param song information for recording
     */
    @Override
    public void insert(Song song) {
        try {
            Statement statement = connection.createStatement();

            String name = song.getName();
            String length = new Long(song.getLength()).toString();
            String resolution = song.getResolution();
            String creator = song.getCreator();
            String executor = song.getExecutor();
            String songName = song.getSongName();
            String album = song.getAlbum();
            String description = song.getDescription();
            int year = song.getYear();


            statement.execute("INSERT INTO songs (name, length, resolution, creator, description," +
                    " executor, songName, album, year)" +
                    "VALUES ('" + name + "', '" + length + "', '" + resolution + "', '" + creator +
                    "', '" + description + "', '" + executor + "', '" + songName + "', '" + album + "', " + year + ");");
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * change record in the database
     * @param song
     * @param songName
     * @param executor
     * @param album
     * @param year
     * @param description
     */
    public void update(Song song, String songName, String executor,String album,
                       int year, String description){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM songs WHERE name = '" + song.getName()  + "';");

            while(resultSet.next()){
                if(song.getDescription().equals(resultSet.getString("description")) &&
                        song.getSongName().equals(resultSet.getString("songName")) &&
                        song.getExecutor().equals(resultSet.getString("executor")) &&
                        song.getAlbum().equals(resultSet.getString("album")) &&
                        (song.getYear() == resultSet.getInt("year"))){
                    ResultSet updateResultSet = statement.executeQuery("update songs set description = '" + description +
                            "', songName = '" + songName + "'" +
                            ", executor = '" + executor + "'" +
                            ", album = '" + album + "'" +
                            ", year = " + year + " where id = '" + resultSet.getString("id") + "'");
                    updateResultSet.close();
                    resultSet.close();
                    statement.close();
                    return;
                }
            }
            log.error("элемент для редактирвания не наден в базе данных");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * deletion record in database
     * @param song information to dell
     */
    public void delete(Song song){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM songs WHERE name = '" + song.getName() + "' AND " +
                    " length = '" + song.getLength() + "' AND resolution = '" + song.getResolution() + "' AND " +
                    " creator = '" + song.getCreator() + "' AND description = '" + song.getDescription() + "' AND " +
                    " executor = '" + song.getExecutor() + "' AND songName = '" + song.getSongName() + "' AND " +
                    " album = '" + song.getAlbum() + "' AND year = " + song.getYear() + ";");
            if(resultSet.next()){
                ResultSet delResultSet = statement.executeQuery("DELETE FROM songs WHERE id = " + resultSet.getInt("id") + " ");
                delResultSet.close();
            }
            resultSet.close();
            statement.close();
        }catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * retrieving a array of records in table
     * @return ArrayList containing records in the table
     */
    @Override
    public ArrayList<Song> select() {
        ArrayList<Song> songs = new ArrayList<Song>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM songs");

            Song song= null;
            while(resultSet.next()){
                song = new Song();
                song.setName(resultSet.getString("name"));
                song.setLength(Long.parseLong(resultSet.getString("length")));
                song.setResolution(resultSet.getString("resolution"));
                song.setCreator(resultSet.getString("creator"));
                song.setDescription(resultSet.getString("description"));
                song.setExecutor(resultSet.getString("executor"));
                song.setSongName(resultSet.getString("songName"));
                song.setAlbum(resultSet.getString("album"));
                song.setYear(resultSet.getInt("year"));


                songs.add(song);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }
}
