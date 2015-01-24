/*
 * ForwardTag.java
 *
 * Created on November 14, 2006, 8:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author justinwu
 */
public class ForwardTag extends TagSupport {
    
	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException {

        try {            
            //pageContext.forward("/index.html");
            
            HttpServletResponse response =(HttpServletResponse)pageContext.getResponse();
            String to=pageContext.getServletContext().getRealPath("/index.html");
            String to2=pageContext.getServletContext().getServletContextName();

            HttpServletRequest request =(HttpServletRequest)pageContext.getRequest();

            String contextPath=request.getContextPath();
            //response.sendRedirect("/scwcd/index.html");
            response.sendRedirect(contextPath+"/index.html");
             
        } catch (Exception ex) {
            throw new JspException(ex);
        }


        return EVAL_BODY_INCLUDE;
    }
  
    public int doEndTag() throws JspException {
        return SKIP_BODY;
    }  
}
