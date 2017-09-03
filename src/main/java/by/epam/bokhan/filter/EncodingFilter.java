package by.epam.bokhan.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

import static by.epam.bokhan.filter.FilterConstant.*;


@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name = ENCODING, value = UTF8_CHARSET, description = ENCODING_PARAM)})
public class EncodingFilter implements Filter {
    /*Character encoding*/
    private String code;

    public void init(FilterConfig config) throws ServletException {
        code = config.getInitParameter(ENCODING);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        code = null;
    }
}

