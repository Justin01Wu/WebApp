package wu.justin.rest2.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wu.justin.bean.Address;
import wu.justin.bean.Pair2;
import wu.justin.bean.TypeEnum;
import wu.justin.bean.User;

public class UserService {
	
	private UserService() {	}
	private static Map<Integer, User> users = new HashMap<>();
	static  {
		User user = createUser();
		users.put(user.getId(), user);
	}
	
	private synchronized static User createUser(){
		User user = new User(56239, "Justin Wu");
		user.addEmails("justin01.wu@gmail.com");
		user.addEmails("wuyg719@gmail.com");
		
		user.setBirthDate(new Date(1589313630011l));
		user.setType(TypeEnum.Developer);
		
		user.setPassword("abcd1234");
		
		Address homeAddress =  new Address();
		homeAddress.setId(123768);
		homeAddress.setCountry("Canada");
		homeAddress.setAddress("This is a tab character[	],but jackson will convert it into \t");
		
		user.setHomeAddress(homeAddress);
		
		user.setDesc("This is a sample to demo how Jackson convert a Java object tree into json");
	    return user;
		
	}
	
	public static synchronized User getUserById(Integer userId) {
		return users.get(userId);
		
	}
	
	public static synchronized List<User> getUserByRange(Pair2<Integer,Integer> range) {
		List<User> userList = new ArrayList<>(); 
		for(User user :users.values()) {
			if(user.getId()< range.getR()  && user.getId() > range.getL()) {
				userList.add(user);
			}
		}
		return userList;
		
	}
	
	public static synchronized User addUser (User user) {
		return users.put(user.getId(), user);
		
	}
}
