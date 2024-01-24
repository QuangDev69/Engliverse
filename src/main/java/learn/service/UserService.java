package learn.service;

import learn.model.User;
import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    List<User> getAllUsers();
    boolean createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(Integer id);
}
