package by.epam.bokhan.tag;

import by.epam.bokhan.entity.Role;
import by.epam.bokhan.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by vbokh on 01.09.2017.
 */
public class UserRoleTag extends TagSupport {
    private static final String USER = "user";

    @Override
    public int doStartTag() throws JspException {
        User user = (User) pageContext.getSession().getAttribute(USER);
        if (Role.CLIENT.equals(user.getRole())) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
