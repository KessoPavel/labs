import Archive.Archive;
import Archive.DAO.JDBCDossierDAO;
import Data.Dossier;
import Data.Person;
import Data.StudyPlace;
import Data.WorkPalace;
import Server.Handler;
import Server.MainLogic;
import Users.DAO.JDBCUserDAO;

import java.sql.SQLException;

/**
 * Created by kesso on 24.04.17.
 */
public class ServerMain {
    public static void main(String[] args) {
        UserThread userThread = new UserThread();
        userThread.start();
        RestThread restThread = new RestThread();
        restThread.run();
    }
}