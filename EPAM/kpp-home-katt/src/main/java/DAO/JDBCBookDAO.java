package DAO;

import Documents.Book;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * class for working with a table of books in the database
 */
public class JDBCBookDAO implements DataDAO<Book> {
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
     * create a table books on the database, if it is not already created
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'books' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'name' text, 'length' text, 'resolution' text,'creator' text, description text," +
                "'bookName' text, 'autor' text, 'genre' text);");
        log.info("create ar open table documents");
    }


    /**
     * adding new record to the table
     * @param book information for recording
     * @throws SQLException
     */
    @Override
    public void insert(Book book) throws SQLException {
        try {
            Statement statement = connection.createStatement();

            String name = book.getName();
            String length = new Long(book.getLength()).toString();
            String resolution = book.getResolution();
            String creator = book.getCreator();
            String description = book.getDescription();
            String bookName = book.getBookName();
            String autor = book.getAutor();
            String genre = book.getGenre();


            statement.execute("INSERT INTO books (name, length, resolution, creator, description," +
                    " bookName, autor, genre)" +
                    "VALUES ('" + name + "', '" + length + "', '" + resolution + "', '" + creator +
                    "', '" + description + "', '" + bookName + "', '" + autor + "', '" + genre + "');");
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * change record in the database
     * @param book
     * @param bookName
     * @param autor
     * @param genre
     * @param description
     */
    public void update(Book book, String bookName, String autor, String genre,
                       String description){

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE name = '" + book.getName()  + "';");

            while(resultSet.next()){
                if(book.getDescription().equals(resultSet.getString("description")) &&
                        book.getBookName().equals(resultSet.getString("bookName")) &&
                        book.getAutor().equals(resultSet.getString("autor")) &&
                        book.getGenre().equals(resultSet.getString("genre"))){
                    ResultSet updateResultSet = statement.executeQuery("update books set description = '" + description +
                            "', bookName = '" + bookName + "'" +
                            ", autor = '" + autor + "'" +
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
     * @param book information to dell
     */
    public void delete(Book book){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE name = '" + book.getName() + "' AND " +
                    " length = '" + book.getLength() + "' AND resolution = '" + book.getResolution() + "' AND " +
                    " creator = '" + book.getCreator() + "' AND description = '" + book.getDescription() + "' AND " +
                    " bookName = '" + book.getBookName() + "' AND autor = '" + book.getAutor() + "' AND " +
                    " genre = '" + book.getGenre() + "';");
                ResultSet delResultSet = statement.executeQuery("DELETE FROM books WHERE id = " + resultSet.getInt("id"));
                delResultSet.close();
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
    public ArrayList<Book> select() {
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

            Book book= null;
            while(resultSet.next()){
                book = new Book();
                book.setName(resultSet.getString("name"));
                book.setLength(Long.parseLong(resultSet.getString("length")));
                book.setResolution(resultSet.getString("resolution"));
                book.setCreator(resultSet.getString("creator"));
                book.setDescription(resultSet.getString("description"));
                book.setBookName(resultSet.getString("bookName"));
                book.setAutor(resultSet.getString("autor"));
                book.setGenre(resultSet.getString("genre"));;

                books.add(book);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
