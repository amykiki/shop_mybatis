package shop.mybatis.map;

import shop.model.User;

import java.util.HashMap;

/**
 * Created by Amysue on 2016/3/17.
 */
public interface UserMapper {
    public User load (int id);
}
