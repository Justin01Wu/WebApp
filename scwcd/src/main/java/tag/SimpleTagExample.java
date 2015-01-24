/*
 * MathTag.java
 *
 * Created on 2006年11月18日, 上午11:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author rita
 */
public class SimpleTagExample extends SimpleTagSupport{
    
public void doTag() throws JspException, IOException
{
    getJspContext().getOut().print("I can't believe it's so simple!");
}
    
}
