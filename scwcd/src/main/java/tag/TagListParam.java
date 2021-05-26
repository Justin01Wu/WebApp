/*
 * TagParam.java
 *
 * Created on November 13, 2006, 12:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author justinwu
 */
public class TagListParam extends TagSupport {

	private static final long serialVersionUID = 1L;
	private List<String> people;

	public int doStartTag() throws JspException {
		if (people == null || people.isEmpty()) {
			return EVAL_BODY_INCLUDE;
		}
		try {
			for(String person: people) {
				pageContext.getOut().print("<p>" + person +"</p>");	
			}			
		} catch (Exception e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	public List<String> getPeople() {
		return people;
	}

	public void setPeople(List<String> people) {
		this.people = people;
	}

}
