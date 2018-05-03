package Servlets;

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
@WebServlet("/exit")
public class Exit extends HttpServlet {
    /**
     * end of session
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
        se.invalidate();

        request.setAttribute("error", "");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/authorization_en.jsp");
        dispatcher.forward(request, response);
    }
}
