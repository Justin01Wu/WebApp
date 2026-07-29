package wu.justa.service;

import java.sql.SQLException;

import org.glassfish.hk2.api.Factory;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import wu.justa.proxy.InnerUser;
import wu.justin.rest2.ApiUtil;

//from https://stackoverflow.com/questions/32119962/jersey-custom-context-injection
public class UserContextProvider implements Factory<InnerUser> {
	
	private final HttpServletRequest request;
	
    @Inject
    protected UserContextProvider(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public InnerUser provide() {
    	InnerUser u = null;
    	if (request != null) {
    		try {
				u = ApiUtil.getCurrentUser(request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}    	
        return u;
    }

    @Override
    public void dispose(InnerUser u) {}
}
