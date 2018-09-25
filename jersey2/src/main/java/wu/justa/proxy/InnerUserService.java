package wu.justa.proxy;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class InnerUserService {
	
	private static final ConcurrentHashMap<Integer, InnerUser> AllUsers = new ConcurrentHashMap<>();
	
	static {
		
		// load all users
		InnerUser user = new InnerUser();
		user.setId(1);
		user.setName("Justin");
		AllUsers.put(1,  user);
		
		user = new InnerUser();
		user.setId(2);
		user.setName("Rita");
		AllUsers.put(2,  user);
	}
	
	public static InnerUser load(Integer userId)  throws SQLException{
		return AllUsers.get(userId);
	}

}
