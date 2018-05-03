package Servlets;

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
 * Created by kesso on 12.05.17.
 */
@WebServlet("/singup")
public class SingUp extends HttpServlet {
    /**
     * create new account
     * do to reservation
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
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");

        //add to database
        User user = UserLogic.creatingNewUser(firstName, secondName, eMail, password, "");
        if (user == null) {
            request.setAttribute("error", "e-Mail error");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/singup_en.jsp");
            dispatcher.forward(request, response);
            return;
        }


        HttpSession se = request.getSession(true);
        se.setAttribute("user", user);
        se.setAttribute("type", "user");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation_en.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * get sing out window without error
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
                RequestDispatcher dispatcher = request.getRequestDispatcher("/singup_en.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/singup_ru.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NullPointerException ex) {
            se.setAttribute("l", "en");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/singup_en.jsp");
            dispatcher.forward(request, response);
        }
    }


}
