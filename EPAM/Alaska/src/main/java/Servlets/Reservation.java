package Servlets;

import Rooms.Room;
import Rooms.RoomLogic;
import User.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kesso on 14.05.17.
 */
@WebServlet("/reservation")
public class Reservation extends HttpServlet {
    /**
     * reservation rooms
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession se = request.getSession(true);
        if (se.isNew()) {
            se.invalidate();
            request.setAttribute("error", "");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_en.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String type = request.getParameter("type");
        String seats = request.getParameter("seats");
        String eMail = request.getParameter("eMail");
        if (eMail == null) {
            User user = (User) se.getAttribute("user");
            eMail = user.geteMail();
        }
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");
        //int number, String type, int numberOfSeats, String data, String user
        Room room = new Room(0, type, Integer.parseInt(seats), date1, eMail);

        room = RoomLogic.reservation(room);
        if (room == null) {
            return;
        }

        room.setData(date2);
        room.setUser(eMail);
        RoomLogic.saveRoom(room);

        request.setAttribute("message", "<h1>Room reserve</h1>" +
                "                             <h1>room number : " + room.getNumber() + "</h1>");


        RequestDispatcher dispatcher = request.getRequestDispatcher("/message.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * show reservation room
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        HttpSession se = request.getSession(true);

        if (se.getAttribute("type").equals("admin")) {
            if (se.getAttribute("l").equals("en")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/reservationAdmin_en.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/reservationAdmin_ru.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            if (se.getAttribute("l").equals("en")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation_en.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation_ru.jsp");
                dispatcher.forward(request, response);
            }
        }

    }

}
