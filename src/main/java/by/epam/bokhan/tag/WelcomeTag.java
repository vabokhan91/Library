package by.epam.bokhan.tag;

import by.epam.bokhan.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class WelcomeTag extends TagSupport {


    @Override
    public int doStartTag() throws JspException {
        Locale locale = null;
        User user = (User) pageContext.getSession().getAttribute("user");
        Object object = pageContext.getSession().getAttribute("language");

        if (object instanceof Locale) {
            locale = (Locale) object;
        } else if (object instanceof String) {
            String loc = (String) object;
            if (loc.equalsIgnoreCase("ru_ru")) {
                locale = new Locale("ru","RU");
            } else if (loc.equalsIgnoreCase("en_US")) {
                locale = new Locale("en","US");
            }
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource.language", locale);
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
