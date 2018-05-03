package Servlets;

import Rooms.RoomLogic;

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
@WebServlet("/listofrooms")
public class ListOfRooms extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

        if (se.getAttribute("l").equals("en")) {
            String html = RoomLogic.getRoom("en");

            request.setAttribute("table", html);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/listOfRooms_en.jsp");
            dispatcher.forward(request, response);
        } else {
            String html = RoomLogic.getRoom("ru");

            request.setAttribute("table", html);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/listOfRooms_ru.jsp");
            dispatcher.forward(request, response);
        }

    }
}
