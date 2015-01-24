package tag;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.JspException;
public class TagSequence implements Tag {
        public int doStartTag() throws JspException {
            System.out.println("doStartTag..4");
            return SKIP_BODY;
        }

        public int doEndTag() throws JspException {
            System.out.println("doEndTag..5");
            return EVAL_PAGE;
        }
        public void release(){
            System.out.println("Release..1");
        }

        public Tag getParent() {
            System.out.println("getParent");
            return null;
        }
        public void setParent(Tag tag) {
            System.out.println("setParent..3");
        }
        public void setPageContext(PageContext pc){
            System.out.println("setPageContext..2");
        }
}
