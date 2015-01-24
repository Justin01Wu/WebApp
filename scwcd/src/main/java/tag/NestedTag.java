/*
 * NestedTag.java
 *
 * Created on November 13, 2006, 5:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 *
 * @author justinwu
 */
public class NestedTag  extends SimpleTagSupport {
    
    //private JspTag parentTag;
    // nesting depth for this tag, But it can't work, Why?
    public void doTag() throws JspException, IOException {
        JspContext context = this.getJspContext();
        JspFragment fragment = getJspBody();
        if(fragment != null) {
            //fragment.invoke(context.getOut());
            fragment.invoke(null);
        } else{
            int count = 1;
            Tag parent = (Tag)getParent();
            while( (parent=parent.getParent()) != null) count++;
            getJspContext().getOut().write("depth is : "+count);
        }
    }
    /*
    public void setParent(JspTag parentTag){
        this.parentTag=parentTag;        
    }
    public JspTag getParent(){
        return parentTag;        
    }
     */
    
    
    
}