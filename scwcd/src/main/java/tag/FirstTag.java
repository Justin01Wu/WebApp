package tag;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
public class FirstTag extends TagSupport {

	private static final long serialVersionUID = 43654679651L;

		public int doStartTag() throws JspException {
            try{
                pageContext.getOut().print("This is from FirstTag");
            }catch(Exception e){
                throw new JspException(e);
            }
            return SKIP_BODY;
        }
}
