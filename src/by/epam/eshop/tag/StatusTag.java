package by.epam.eshop.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class StatusTag extends TagSupport {

    private static final String LOCAL_STATUS = "local.status.";
    private static final String PATH = "localization.local";
    private static final String LOCAL = "local";
    private static final String RU = "ru";
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        if (value.isEmpty()) {
            return SKIP_BODY;
        }
        try {
            String locale = (String) pageContext.getSession().getAttribute(LOCAL);
            if (locale == null) {
                locale = RU;
            }
            ResourceBundle bundle = ResourceBundle.getBundle(PATH, new Locale(locale.toUpperCase(), locale));
            pageContext.getOut().write(bundle.getString(LOCAL_STATUS + value.toLowerCase()));
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
