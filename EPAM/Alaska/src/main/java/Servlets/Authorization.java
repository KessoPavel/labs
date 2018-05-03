package Servlets;

import Rooms.RoomLogic;
import User.User;
import User.UserLogic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kesso on 11.05.17.
 */
@WebServlet("/authorization")
public class Authorization extends HttpServlet {

    /**
     * check user
     * show reservation window if this normal user
     * show list of room if it is admin
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String eMail = request.getParameter("eMail");
        String password = request.getParameter("password");

        User user = UserLogic.entranceCheck(eMail, password);
        if (user == null) {
            request.setAttribute("error", "error");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_en.jsp");
            dispatcher.forward(request, response);
            return;
        }

        HttpSession se = request.getSession(true);
        se.setAttribute("user", user);
        if (eMail.equals("admin")) {
            se.setAttribute("type", "admin");
        } else {
            se.setAttribute("type", "user");
        }

        if (eMail.equals("admin")) {   //admin
            try {
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
            } catch (NullPointerException ex) {
                String html = RoomLogic.getRoom("en");

                request.setAttribute("table", html);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/listOfRooms_en.jsp");
                dispatcher.forward(request, response);
            }
            return;
        } else {   //user
            if (se.getAttribute("l").equals("en")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation_en.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation_ru.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    /**
     * show authorization window without error message
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        request.setAttribute("error", "");

        HttpSession se = request.getSession(true);
        try {
            if (se.getAttribute("l").equals("en")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_en.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_ru.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NullPointerException ex) {
            se.setAttribute("l", "en");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_en.jsp");
            dispatcher.forward(request, response);
        }
    }
}
