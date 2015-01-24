/*
 * MathTag.java
 *
 * Created on 2006年11月18日, 上午11:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author rita
 */
public class MathTag extends SimpleTagSupport
        implements DynamicAttributes{
    double num = 0;
    String output = "";
    
    public void setNum(double num) {
        this.num = num;
    }
    
    public void setDynamicAttribute(String uri, String localName,
            Object value ) throws JspException {
        
        double val = Double.parseDouble((String)value);
        if (localName == "min") {
            output = output + "<tr><td>The minimum of "+num+" and "+
                    val + "</td><td>" + Math.min(num, val) + "</td></tr>\r\n";
            
        } else if (localName == "max") {
            output = output + "<tr><td>The maximum of "+num+" and "+
                    val + "</td><td>" + Math.max(num, val) + "</td></tr>\r\n";
        } else if (localName == "pow") {
            output = output + "<tr><td>"+num+" raised to the "+val+
                    " power"+"</td><td>"+Math.pow(num, val)+"</td></tr>\r\n";
        }
    }
    
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print(output);
        getJspContext().getOut().print("<tr><td><b>");
        getJspBody().invoke(null);
        getJspContext().getOut().print("</b></td><td>Tag insert this to a cell</td></tr>");
    }
    
}
