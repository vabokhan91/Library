package by.epam.bokhan.tag;

import by.epam.bokhan.entity.User;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by vbokh on 22.07.2017.
 */
public class WelcomeTag extends TagSupport {



    @Override
    public int doStartTag() throws JspException {
//        Locale locale = (Locale) pageContext.getSession().getAttribute("language");

        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource.language");

        User user = (User) pageContext.getSession().getAttribute("user");
        if (user == null) {
            try {
                JspWriter out = pageContext.getOut();
                out.write(resourceBundle.getString("label.message.welcome.guest"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.write(resourceBundle.getString("label.message.welcome.authorizeduser") + user.getName() + "!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
