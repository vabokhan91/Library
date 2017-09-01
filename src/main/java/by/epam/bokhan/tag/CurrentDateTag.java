package by.epam.bokhan.tag;


import by.epam.bokhan.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CurrentDateTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        Locale locale = Locale.getDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy", locale);
        String localDate = LocalDate.now().format(formatter);
        try {
            JspWriter out = pageContext.getOut();
            out.write(localDate);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
