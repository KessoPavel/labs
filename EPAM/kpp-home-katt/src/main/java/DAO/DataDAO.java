package DAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kesso on 6.3.17.
 */

/**
 * DAO interface
 * @param <Type>
 */
public interface DataDAO<Type>{
    public void insert(Type data) throws SQLException;
    public List<Type> select();
}
