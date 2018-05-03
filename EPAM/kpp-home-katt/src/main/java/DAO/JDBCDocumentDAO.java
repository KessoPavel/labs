package DAO;

import Documents.Document;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by kesso on 6.3.17.
 * class for working with a table of books in the database
 */
public class JDBCDocumentDAO implements DataDAO<Document> {
    private static final Logger log = Logger.getLogger(JDBCDocumentDAO.class);
    private Connection connection = null;

    /**
     * method that connects to database
     * @param con
     */
    public void setConnection(Connection con){
        this.connection = con;
    }

    /**
     * create a table documents on the database, if it is not already created
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'documents' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'name' text, 'length' text, 'resolution' text, 'creator' text, 'description' text);");
        log.info("create ar open table documents");
    }

    /**
     * adding new record to the table
     * @param document information for recording
     */
    @Override
    public void insert(Document document) {
        try {
            Statement statement = connection.createStatement();

            String name = document.getName();
            String length = new Long(document.getLength()).toString();
            String resolution = document.getResolution();
            String creator = document.getCreator();
            String description = document.getDescription();

            statement.execute("INSERT INTO documents (name, length, resolution, creator, description)" +
                    "VALUES ('" + name + "', '" + length + "', '" + resolution + "', '" + creator +
                    "', '" + description + "');");
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deletion record in database
     * @param document information to dell
     */
    public void delete(Document document){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM documents WHERE name = '" + document.getName() + "';");

            ResultSet delResultSet = statement.executeQuery("DELETE FROM documents WHERE id = " + resultSet.getInt("id") + " ");
            delResultSet.close();



            resultSet.close();
            statement.close();
        }catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * change record in the database
     * @param document information for change
     * @param description
     */
    public void update(Document document, String description){

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM documents WHERE name = '" + document.getName()  + "';");

            while(resultSet.next()){
                if(document.getDescription().equals(resultSet.getString("description"))){
                    ResultSet updateResultSet = statement.executeQuery("update documents set description = '" + description +
                            "' where id = '" + resultSet.getString("id") + "'");
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
     *  retrieving a array of records in table
     * @return ArrayList containing records in the table
     */
    @Override
    public ArrayList<Document> select() {
        ArrayList<Document> documents = new ArrayList<Document>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM documents");

            Document document= null;
            while(resultSet.next()){
                document = new Document();
                document.setName(resultSet.getString("name"));
                document.setLength(Long.parseLong(resultSet.getString("length")));
                document.setResolution(resultSet.getString("resolution"));
                document.setCreator(resultSet.getString("creator"));
                document.setDescription(resultSet.getString("description"));

                documents.add(document);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }
}
