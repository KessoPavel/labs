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
 * Created by kesso on 15.05.17.
 */
@WebServlet("/chl")
public class CHL extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        HttpSession se = request.getSession(true);


        String type = request.getParameter("a_en");
        if (type != null) {
            se.setAttribute("l", "en");
            request.setAttribute("error", "");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_en.jsp");
            dispatcher.forward(request, response);
            return;
        }
        type = request.getParameter("a_ru");
        if (type != null) {
            se.setAttribute("l", "ru");
            request.setAttribute("error", "");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_ru.jsp");
            dispatcher.forward(request, response);
            return;
        }

        type = request.getParameter("sup_en");
        if (type != null) {
            se.setAttribute("l", "en");
            request.setAttribute("error", "");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/singup_en.jsp");
            dispatcher.forward(request, response);
            return;
        }

        type = request.getParameter("sup_ru");
        if (type != null) {
            se.setAttribute("l", "ru");
            request.setAttribute("error", "");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/singup_ru.jsp");
            dispatcher.forward(request, response);
            return;
        }


        type = request.getParameter("r_en");
        if (type != null) {
            se.setAttribute("l", "en");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation_en.jsp");
            dispatcher.forward(request, response);
            return;
        }
        type = request.getParameter("r_ru");
        if (type != null) {
            se.setAttribute("l", "ru");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation_ru.jsp");
            dispatcher.forward(request, response);
            return;
        }

        type = request.getParameter("ra_en");
        if (type != null) {
            se.setAttribute("l", "en");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/reservationAdmin_en.jsp");
            dispatcher.forward(request, response);
            return;
        }
        type = request.getParameter("ra_ru");
        if (type != null) {
            se.setAttribute("l", "ru");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/reservationAdmin_ru.jsp");
            dispatcher.forward(request, response);
            return;
        }

        type = request.getParameter("p_en");
        if (type != null) {
            se.setAttribute("l", "en");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/personalArea_en.jsp");
            dispatcher.forward(request, response);
            return;
        }
        type = request.getParameter("p_ru");
        if (type != null) {
            se.setAttribute("l", "ru");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/personalArea_ru.jsp");
            dispatcher.forward(request, response);
            return;
        }

        type = request.getParameter("l_en");
        if (type != null) {
            se.setAttribute("l", "en");
            String html = RoomLogic.getRoom("en");

            request.setAttribute("table", html);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listOfRooms_en.jsp");
            dispatcher.forward(request, response);
            return;
        }
        type = request.getParameter("l_ru");
        if (type != null) {
            se.setAttribute("l", "ru");
            String html = RoomLogic.getRoom("ru");

            request.setAttribute("table", html);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listOfRooms_ru.jsp");
            dispatcher.forward(request, response);
            return;
        }

    }
}
