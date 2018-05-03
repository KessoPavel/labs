package Logic;

import DAO.JDBCDocumentDAO;
import Documents.Book;
import Documents.Document;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by kesso on 8.3.17.
 * class for work whis database
 */
public class DataBaseLogic {
    private static final Logger log = Logger.getLogger(JDBCDocumentDAO.class);
    private Connection connection = null;

    /**
     * connection for database
     */
    public DataBaseLogic(){
        try {
            Class.forName("org.sqlite.JDBC");
            if(connection == null) {
                connection = DriverManager.getConnection("jdbc:sqlite:HomeKat.db");
                log.info("connecting from database");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Connection
     */
    public Connection getConnection(){
        return this.connection;
    }

    /**
     * close database
     * @throws SQLException
     */
    public void closeDataBase() throws SQLException {
        connection.close();
    }
}
