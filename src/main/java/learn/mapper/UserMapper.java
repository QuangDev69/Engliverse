package learn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import learn.model.User;

@Mapper
public interface UserMapper {
	User selectById(Integer Id);
	List<User> selectAll();
	int insert(User user);
	int update(User user);
	int deleteById(Integer Id);
	
    User findUserByUsername(String username);

}
