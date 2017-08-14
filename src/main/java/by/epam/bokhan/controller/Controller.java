package by.epam.bokhan.controller;

import by.epam.bokhan.command.AbstractCommand;
import by.epam.bokhan.content.RequestContent;
import by.epam.bokhan.exception.ReceiverException;
import by.epam.bokhan.factory.CommandFactory;
import by.epam.bokhan.manager.ConfigurationManager;
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
import java.util.HashMap;
import java.util.Map;


@WebServlet({"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "command";
    private static final String PAGE = "page";
    private static final String TYPE_OF_TRANSITION = "type_of_transition";
    private static final String REDIRECT = "redirect";
    private static final String INVALIDATE = "invalidate";
    private static final String INDEX_PAGE = "path.page.index";
    private final String ERROR_PAGE_COMMAND = "/controller?command=error_page";

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
                getParametersFromContent(request,content);
                getAttributesFromContent(request, content);
                page = (String) content.getRequestParameters().get(PAGE);
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

                }
            } catch (ReceiverException e) {
                page = ERROR_PAGE_COMMAND;
                LOGGER.log(Level.ERROR, e.getMessage());
                response.sendRedirect(page);
            }

        }
    }

    private void getParametersFromContent(HttpServletRequest request, RequestContent content) {
        HashMap<String, Object> s = content.getRequestParameters();
        for (Map.Entry<String, Object> p : s.entrySet()) {
            String first = p.getKey();
            Object second = p.getValue();
            request.setAttribute(first, second);
        }
    }

    private void getAttributesFromContent(HttpServletRequest request, RequestContent content) {
        HashMap<String, Object> attributes = content.getSessionAttributes();
        for (Map.Entry<String, Object> a : attributes.entrySet()) {
            String first = a.getKey();
            Object second = a.getValue();
            request.getSession().setAttribute(first, second);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyConnections();
    }
}
