package by.epam.bokhan.content;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;


public class RequestContent {
    private HashMap<String, Object> requestParameters;
    private HashMap<String, Object> requestAttributes;

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


    public void insertParameter(String key, Object value) {
        requestParameters.put(key, value);
    }

    public void insertAttribute(String key, Object attribute) {
        requestAttributes.put(key, attribute);
    }


    public HashMap<String, Object> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(HashMap<String, Object> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(HashMap<String, Object> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }
}

