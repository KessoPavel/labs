package DAO;

import Documents.Document;
import Documents.Film;
import Documents.Song;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kesso on 8.3.17.
 * class for working with a table of films in the database
 */
public class JDBCFilmDAO implements DataDAO<Film> {

    private static final Logger log = Logger.getLogger(JDBCDocumentDAO.class);
    private Connection connection = null;

    /**
     * method that connects to database
     * @param connection Connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * create a table films on the database, if it is not already created
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'films' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'name' text, 'length' text, 'resolution' text,'creator' text, 'description' text," +
                "'filmName' text, 'producer' text, 'genre' text);");
        log.info("create ar open table films");
    }

    /**
     * change record in the database
     * @param film
     * @param filmName
     * @param producer
     * @param genre
     * @param description
     */
    public void update(Film film, String filmName, String producer,
                       String genre, String description){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM films WHERE name = '" + film.getName()  + "';");

            while(resultSet.next()){
                if(film.getDescription().equals(resultSet.getString("description")) &&
                        film.getFilmName().equals(resultSet.getString("filmName")) &&
                        film.getProducer().equals(resultSet.getString("producer")) &&
                        film.getGenre().equals(resultSet.getString("genre"))){
                    ResultSet updateResultSet = statement.executeQuery("update films set description = '" + description +
                            "', filmName = '" + filmName + "'" +
                            ", producer = '" + producer + "'" +
                            ", genre = '" + genre + "'" +
                            " where id = '" + resultSet.getString("id") + "'");
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
     * @param film information to dell
     */
    public void delete(Film film){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM films WHERE name = '" + film.getName() + "' AND " +
                    " length = '" + film.getLength() + "' AND resolution = '" + film.getResolution() + "' AND " +
                    " creator = '" + film.getCreator() + "' AND description = '" + film.getDescription() + "' AND " +
                    " filmName = '" + film.getFilmName() +"' AND producer = '" + film.getProducer() + "' AND " +
                    "genre = '" + film.getGenre() + "';");
                ResultSet delResultSet = statement.executeQuery("DELETE FROM films WHERE id = " + resultSet.getInt("id"));
                delResultSet.close();

            resultSet.close();
            statement.close();
        }catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * adding new record to the table
     * @param film
     * @throws SQLException
     */
    @Override
    public void insert(Film film) throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String name = film.getName();
            String length = new Long(film.getLength()).toString();
            String resolution = film.getResolution();
            String creator = film.getCreator();
            String description = film.getDescription();
            String filmName = film.getFilmName();
            String producer = film.getProducer();
            String genre = film.getGenre();


            statement.execute("INSERT INTO films (name, length, resolution, creator,description," +
                    " filmName, producer, genre) " +
                    "VALUES ('" + name + "', '" + length + "', '" + resolution + "', '" + creator +
                    "', '" + description + "', '" + filmName + "', '" + producer + "', '" + genre + "');");
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * retrieving a array of records in table
     * @return ArrayList containing records in the table
     */
    @Override
    public ArrayList<Film> select() {
        ArrayList<Film> films = new ArrayList<Film>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM films");

            Film film= null;
            while(resultSet.next()){
                film = new Film();
                film.setName(resultSet.getString("name"));
                film.setLength(Long.parseLong(resultSet.getString("length")));
                film.setResolution(resultSet.getString("resolution"));
                film.setCreator(resultSet.getString("creator"));
                film.setDescription(resultSet.getString("description"));
                film.setFilmName(resultSet.getString("filmName"));
                film.setProducer(resultSet.getString("producer"));
                film.setGenre(resultSet.getString("genre"));
                films.add(film);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }
}
