package by.epam.bokhan.controller;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.DAOException;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.factory.CommandFactory;
import by.epam.bokhan.manager.ConfigurationManager;
import by.epam.bokhan.manager.MessageManager;
import by.epam.bokhan.pool.ConnectionPool;
import by.epam.bokhan.receiver.Receiver;
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
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by vbokh on 13.07.2017.
 */
@WebServlet({"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String COMMAND = "command";
    private final String PAGE = "page";
    private final String TYPE_OF_TRANSITION = "type_of_transition";
    private final String REDIRECT = "redirect";
    private final String INVALIDATE = "invalidate";
    private final String INDEX_PAGE = "path.page.index";
    private final String USER = "user";

    public Controller() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter(COMMAND) != null) {
            String page;
            CommandFactory factory = new CommandFactory();
            RequestContent content = new RequestContent();
            content.extractValues(request);
            AbstractCommand command = factory.defineCommand(content);
            try {
                command.execute(content);
                page = (String) content.getRequestParameters().get(PAGE);
                getAttributesFromContent(request, content);
                if (page != null) {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                    String typeOfTransition = (String) request.getAttribute(TYPE_OF_TRANSITION);
                    if (typeOfTransition == null || !typeOfTransition.equalsIgnoreCase(REDIRECT)) {
                        if ((Boolean) content.getRequestParameters().get(INVALIDATE)) {
                            request.getSession().invalidate();
                        }
                        dispatcher.forward(request, response);
                    } else {
                        response.sendRedirect(page);
                    }
                } else {
            page = ConfigurationManager.getProperty(INDEX_PAGE);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);

        }}catch (ReceiverException e) {
                page = "/controller?command=error_page";
                LOGGER.log(Level.ERROR, e.getMessage());
                response.sendRedirect(page);
            }

    }}

    private void getAttributesFromContent(HttpServletRequest request, RequestContent content) {
        HashMap<String, Object> s = content.getRequestParameters();
        for (Map.Entry<String, Object> p : s.entrySet()) {
            String first = p.getKey();
            Object second = p.getValue();
            if (first.equalsIgnoreCase(USER)) {
                request.getSession().setAttribute(first, second);
            } else {
                request.setAttribute(first, second);
            }
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyConnections();
    }
}
