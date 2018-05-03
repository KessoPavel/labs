package Archive.DAO;

import Data.Dossier;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kesso on 23.04.17.
 */
public interface DAO {
    public void insert(Dossier dossier) throws SQLException;
    public ArrayList<Dossier> select();
    public void delete(Dossier dossier);
}
