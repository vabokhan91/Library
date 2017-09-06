package by.epam.bokhan.tag;

import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class AdminRoleTag extends TagSupport {
    private static final String USER = "user";

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
