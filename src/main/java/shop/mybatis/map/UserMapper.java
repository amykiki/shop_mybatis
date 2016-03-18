package shop.mybatis.map;

import shop.model.User;

/**
 * Created by Amysue on 2016/3/17.
 */
public interface UserMapper {
    public User load (int id);

    public int add(User user);
}
