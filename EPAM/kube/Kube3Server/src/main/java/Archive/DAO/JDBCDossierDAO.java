package Archive.DAO;

import Data.Dossier;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kesso on 23.04.17.
 */
public class JDBCDossierDAO implements DAO {
    private static final Logger log = Logger.getLogger(JDBCDossierDAO.class);
    private Connection connection = null;

    public JDBCDossierDAO(){
        try{
            Class.forName("org.sqlite.JDBC");
            if(connection == null){
                connection = DriverManager.getConnection("jdbc:sqlite:Kube.db");
                log.info("connecting from database");
            }
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public void closeDataBase() throws SQLException {
        connection.close();
    }

    public void createTable() throws  SQLException{
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists 'dossier'('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'dataOfCreation' text);");
        log.info("create or open table 'dossier'");
    }

    public void insert(Dossier dossier) throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO dossier (dataOfCreation)" +
                "VALUES ('" + dossier.getDataOfCreation() + "');");
        statement.close();
        Statement s = connection.createStatement();
        ResultSet resultSet = s.executeQuery("SELECT * FROM dossier LIMIT 1 OFFSET (SELECT COUNT(*) FROM dossier)-1"/*"SELECT * FROM dossier ORDER BY 'id' DESC LIMIT 1"*/);
        dossier.setID(resultSet.getInt("id"));
    }

    public ArrayList<Dossier> select() {
        ArrayList<Dossier> dossiers = new ArrayList<Dossier>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dossier");
            Dossier dossier = null;
            while(resultSet.next()){
                dossier = new Dossier();
                dossier.setID(resultSet.getInt("id"));
                dossier.setDataOfCreation(resultSet.getString("dataOfCreation"));

                dossiers.add(dossier);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return dossiers;
    }

    public void delete(Dossier dossier) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("DELETE FROM dossier WHERE id = " + dossier.getID() + " ");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
