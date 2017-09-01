package by.epam.bokhan.tag;

import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by vbokh on 31.08.2017.
 */
public class AdminRoleTag extends TagSupport {
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
        User user = (User) pageContext.getSession().getAttribute(USER);
        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
