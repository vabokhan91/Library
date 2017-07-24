package by.epam.bokhan.controller;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.factory.CommandFactory;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger();
    public Controller() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("command") != null) {
            CommandFactory factory = new CommandFactory();
            RequestContent content = new RequestContent();
            content.extractValues(request);
            AbstractCommand command = factory.defineCommand(content);

            try {
                command.execute(content);
            } catch (DAOException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
            }

            String page = (String) content.getRequestParameters().get("page");
            HashMap<String, Object> s = content.getRequestParameters();
            for (Map.Entry<String, Object> p : s.entrySet()) {
                String first = p.getKey();
                Object second =  p.getValue();
//                kyda user'a
                if (first.equalsIgnoreCase("user")) {
                    request.getSession().setAttribute(first,second);
                }else {
                    request.setAttribute(first,second);
                }
            }
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                String typeOfTransition = (String) request.getAttribute("type_of_transition");
                if(typeOfTransition== null || !typeOfTransition.equalsIgnoreCase("redirect")){
                    dispatcher.forward(request, response);
                    if (Boolean.parseBoolean((String) content.getRequestParameters().get("invalidate"))) {
                        request.getSession().invalidate();
                    }
                }else {
                    response.sendRedirect(page);
                }

            }
        }
        else {
            String page = ConfigurationManager.getProperty("path.page.index");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);

        }

    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyConnections();
    }
}
