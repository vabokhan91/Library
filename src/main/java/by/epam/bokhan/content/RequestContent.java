package by.epam.bokhan.content;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class RequestContent {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";
    /* Map that stores request parameters*/
    private HashMap<String, Object> requestParameters;
    /*Map that stores session attributes*/
    private HashMap<String, Object> sessionAttributes;
    /* Map that stores request parameter values*/
    private HashMap<String, Object> requestParameterValues;
    /*Map that stores Parts objects (for multitype queries)*/
    private HashMap<String, Object> multiTypeParts;

    public RequestContent() {
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParameterValues = new HashMap<>();
        multiTypeParts = new HashMap<>();
    }
    /*Extracts all request parameters, request parameter values, session attributes, and objects of type Part from request*/
    public void extractValues(HttpServletRequest request) {
        Enumeration requestParameters = request.getParameterNames();
        while (requestParameters.hasMoreElements()) {
            String name = (String) requestParameters.nextElement();
            Object value = request.getParameter(name);
            this.requestParameters.put(name, value);
        }

        Enumeration sessionAttributes = request.getSession().getAttributeNames();
        while (sessionAttributes.hasMoreElements()) {
            String name = (String) sessionAttributes.nextElement();
            Object value = request.getSession().getAttribute(name);
            this.sessionAttributes.put(name, value);
        }

        Enumeration requestParameterValues = request.getParameterNames();
        while (requestParameterValues.hasMoreElements()) {
            String name = (String) requestParameterValues.nextElement();
            Object value = request.getParameterValues(name);
            this.requestParameterValues.put(name, value);
        }

        try {
            if (request.getContentType() != null && request.getContentType().toLowerCase().contains(CONTENT_TYPE_MULTIPART_FORM_DATA)) {
                Collection<Part> multitypeValues = request.getParts();
                for (Part part : multitypeValues) {
                    String name = part.getName();
                    multiTypeParts.put(name, part);
                }

            }
        } catch (IOException | ServletException e) {
            LOGGER.log(Level.ERROR, String.format("Exception in RequestContent. Reason : %s", e.getMessage()));
        }

    }

    /*Inserts all request parameters, session attributes into request*/
    public void insertValues(HttpServletRequest request) {
        for (Map.Entry<String, Object> p : requestParameters.entrySet()) {
            String first = p.getKey();
            Object second = p.getValue();
            request.setAttribute(first, second);
        }
        for (Map.Entry<String, Object> a : sessionAttributes.entrySet()) {
            String first = a.getKey();
            Object second = a.getValue();
            request.getSession().setAttribute(first, second);
        }
    }

    /*Inserts parameter into RequestContent object*/
    public void insertParameter(String key, Object value) {
        requestParameters.put(key, value);
    }

    /*Inserts attribute into RequestContent object*/
    public void insertAttribute(String key, Object attribute) {
        sessionAttributes.put(key, attribute);
    }

    public HashMap<String, Object> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public HashMap<String, Object> getRequestParameterValues() {
        return requestParameterValues;
    }

    public HashMap<String, Object> getMultiTypeParts() {
        return multiTypeParts;
    }
}

