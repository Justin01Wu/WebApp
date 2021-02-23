package wu.justa.service;

import org.glassfish.hk2.api.Factory;

import wu.justin.bean.User;
import wu.justin.rest2.user.UserService;

//from https://stackoverflow.com/questions/32119962/jersey-custom-context-injection
public class UserContextProvider implements Factory<User> {

    @Override
    public User provide() {
    	User u = UserService.getUserById(56239);
        return u;
    }

    @Override
    public void dispose(User bar) {}
}
