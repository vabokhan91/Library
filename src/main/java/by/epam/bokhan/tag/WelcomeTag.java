package by.epam.bokhan.tag;

import by.epam.bokhan.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class WelcomeTag extends TagSupport {
    
    private static final String USER = "user";
    private static final String LANGUAGE = "language";
    private static final String RU_RU = "ru_ru";
    private static final String RU = "ru";
    private static final String EN_US = "en_US";
    private static final String EN = "en";
    private static final String US = "US";
    private static final String LANGUAGE_BUNDLE = "resource.language";
    private static final String MESSAGE_WELCOME_GUEST = "label.message.welcome.guest";
    private static final String MESSAGE_WELCOME_USER = "label.message.welcome.authorizeduser";

    @Override
    public int doStartTag() throws JspException {
        Locale locale = null;
        User user = (User) pageContext.getSession().getAttribute(USER);
        Object object = pageContext.getSession().getAttribute(LANGUAGE);

        if (object instanceof Locale) {
            locale = (Locale) object;
        } else if (object instanceof String) {
            String loc = (String) object;
            if (loc.equalsIgnoreCase(RU_RU)) {
                locale = new Locale(RU,RU);
            } else if (loc.equalsIgnoreCase(EN_US)) {
                locale = new Locale(EN, US);
            }
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(LANGUAGE_BUNDLE, locale);
        if (user == null) {
            try {
                JspWriter out = pageContext.getOut();
                out.write(resourceBundle.getString(MESSAGE_WELCOME_GUEST));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.write(resourceBundle.getString(MESSAGE_WELCOME_USER) +", " + user.getName());
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
