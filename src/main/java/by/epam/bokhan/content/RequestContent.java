package by.epam.bokhan.content;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;


public class RequestContent {
    private HashMap<String, Object> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private HashMap<String, Object> requestParameterValues;

    public RequestContent() {
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParameterValues = new HashMap<>();
    }

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
    }


    public void insertParameter(String key, Object value) {
        requestParameters.put(key, value);
    }

    public void insertAttribute(String key, Object attribute) {
        sessionAttributes.put(key, attribute);
    }

    public void insertParameterValue(String key, Object attribute) {
        requestParameterValues.put(key, attribute);
    }


    public HashMap<String, Object> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(HashMap<String, Object> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(HashMap<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public HashMap<String, Object> getRequestParameterValues() {
        return requestParameterValues;
    }

    public void setRequestParameterValues(HashMap<String, Object> requestParameterValues) {
        this.requestParameterValues = requestParameterValues;
    }
}

