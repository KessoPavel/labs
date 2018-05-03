import Rooms.JDBCRoomsDAO;

import java.sql.SQLException;

/**
 * Created by kesso on 14.05.17.
 */
public class Ma {
    public static void main(String[] args) {

        JDBCRoomsDAO userDAO = new JDBCRoomsDAO();
        try {
            userDAO.createTable();
            userDAO.closeDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
