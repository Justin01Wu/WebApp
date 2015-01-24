/*
 * NestedTag2.java
 *
 * Created on November 13, 2006, 6:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 *
 * @author justinwu
 */
public class NestedTag2 extends BodyTagSupport {
    
    //private Tag parentTag;
    //private BodyContent _bodyContent;

	private static final long serialVersionUID = 1L;


	public int doStartTag() throws JspException {

        JspWriter out=this.pageContext.getOut();
        boolean isEnd=false;
        if(bodyContent != null){
            JspWriter out2=bodyContent.getEnclosingWriter();
            try {
                bodyContent.writeOut(out2);
            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }        
        
        BodyTagSupport parent=(BodyTagSupport)this.getParent();
        if(parent==null){
            return EVAL_BODY_BUFFERED;
        }
        
        BodyContent parentBobodyContent=parent.getBodyContent();
        if(parentBobodyContent==null){
            return EVAL_BODY_BUFFERED;
        }
        
        String outerContent=parentBobodyContent.getString();
        //if(outerContent!=null && outerContent.indexOf("TagDependent")!=-1){
            try {                    
                parentBobodyContent.clear();
                out.println("outer Tag output has been replaced with NestedTag2 output");
            } catch (IOException ex) {
                throw new JspException(ex);
            }                
       //}

        //JspWriter out2=this.getPreviousOut();        
        
        System.out.println("doStartTag");

        return EVAL_BODY_BUFFERED;
    }
    
    
    // nesting depth for this tag, But it can't work, Why?    
    public int doAfterBody() throws JspException {

        JspWriter out=this.pageContext.getOut();
        boolean isEnd=false;
        if(bodyContent == null){
            int count = 1;
            Tag parent = (Tag)getParent();
            while(parent!=null){
                count++;                
                parent=parent.getParent(); 
            }
            try {
                out.write("depth is : "+count);        
            } catch (IOException ex) {
                throw new JspException(ex);
            }        
        }
        //JspWriter out2=this.getPreviousOut();        
        
        System.out.println("doAfterBody");

        return EVAL_PAGE;
    }
    
    /*
    public void setParent(Tag parentTag){
        this.parentTag=parentTag;        
    }
    public Tag getParent(){
        return parentTag;        
    }
    
    public void setBodyContent(BodyContent bodyContent){
        this._bodyContent=bodyContent;
    }
    
    public BodyContent getBodyContent(){
        return this._bodyContent;
    } 
     */   
    
    
    
}
