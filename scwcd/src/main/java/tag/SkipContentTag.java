package tag;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
public class SkipContentTag extends TagSupport {

	private static final long serialVersionUID = 1L;

		public int doStartTag() throws JspException {
            return SKIP_BODY;
        }

        public int doEndTag() throws JspException {
            return SKIP_PAGE;
        }

}
