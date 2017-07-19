package by.epam.bokhan.controller;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.factory.CommandFactory;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vbokh on 13.07.2017.
 */
@WebServlet({"/controller"})
public class Controller extends HttpServlet {
    public Controller() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("command") != null) {
            CommandFactory factory = new CommandFactory();
            RequestContent content = new RequestContent();
            content.extractValues(request);
            AbstractCommand command = factory.defineCommand(content);
            try {
                command.execute(content);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String page = (String) content.getRequestParameters().get("page");
            HashMap<String, Object> s = content.getRequestParameters();
            for (Map.Entry<String, Object> p : s.entrySet()) {
                String first = p.getKey();
                Object second =  p.getValue();
//                kyda user'a
                request.getSession().setAttribute(first, second);
            }
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
                if (content.getRequestParameters().get("invalidate").equals("true")) {
                    request.getSession().invalidate();
                }
            }
        }
        else {
            String page = ConfigurationManager.getProperty("path.page.index");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);

        }

    }
}
