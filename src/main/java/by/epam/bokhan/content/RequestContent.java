package by.epam.bokhan.content;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;


public class RequestContent {
    private HashMap<String, Object> requestParameters;

    public RequestContent() {
        requestParameters = new HashMap<>();
    }

    public void extractValues(HttpServletRequest request) {
        Enumeration requestParameters = request.getParameterNames();
        while (requestParameters.hasMoreElements()) {
            String name = (String) requestParameters.nextElement();
            String value = request.getParameter(name);
            this.requestParameters.put(name, value);
        }
    }


    public void insertAttributes(String key, Object value) {
        requestParameters.put(key, value);
    }


    public HashMap<String, Object> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(HashMap<String, Object> requestParameters) {
        this.requestParameters = requestParameters;
    }


}

