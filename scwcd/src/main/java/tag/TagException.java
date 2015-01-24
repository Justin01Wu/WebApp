package tag;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
public class TagException extends TagSupport {

	private static final long serialVersionUID = 1L;

		public int doStartTag() throws JspException {
            try{
                pageContext.getOut().print("This is from TagException");
            }catch(Exception e){
                throw new JspException(e);
            }
            if(true){
                throw new JspException("throw an Exception");
            }
            return SKIP_BODY;
        }

        public int doEndTag() throws JspException {
            System.out.println("Can't be here, because of exception ");

            return EVAL_PAGE;
        }
}
