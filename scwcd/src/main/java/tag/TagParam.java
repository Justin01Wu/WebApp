/*
 * TagParam.java
 *
 * Created on November 13, 2006, 12:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author justinwu
 */
public class TagParam extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String age;
    
        public int doStartTag() throws JspException {
            try{
                pageContext.getOut().print("TagParam output:"+age);
            }catch(Exception e){
                throw new JspException(e);
            }
            return EVAL_BODY_INCLUDE;
        }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    
}
