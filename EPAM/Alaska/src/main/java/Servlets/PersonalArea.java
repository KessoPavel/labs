package Servlets;

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
@WebServlet("/personalarea")
public class PersonalArea extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession se = request.getSession(true);
        if (se.isNew()) {
            se.invalidate();
            request.setAttribute("error", "");

            if (se.getAttribute("l").equals("en")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_en.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_ru.jsp");
                dispatcher.forward(request, response);
            }
        }


        User user = (User) se.getAttribute("user");

        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("secondName", user.getSecondName());
        request.setAttribute("eMail", user.geteMail());

        if (se.getAttribute("l").equals("en")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/personalArea_en.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/personalArea_ru.jsp");
            dispatcher.forward(request, response);
        }

    }
}
