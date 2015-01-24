package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
public class TagDependent extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

		public int doStartTag() throws JspException {
            try{
                pageContext.getOut().println("This is from doStartTag of TagDependent<br/>");
                if(bodyContent != null){
                    JspWriter out=bodyContent.getEnclosingWriter();
                    bodyContent.writeOut(out);
                 }
            }catch(Exception e){
                throw new JspException(e);
            }
            return EVAL_BODY_BUFFERED;
        }

        public int doEndTag() throws JspException {
            try{
                pageContext.getOut().println("This is from doEndTag of TagDependent<br/>");
            }catch(Exception e){
                throw new JspException(e);
            }
            return EVAL_PAGE;
        }

}
