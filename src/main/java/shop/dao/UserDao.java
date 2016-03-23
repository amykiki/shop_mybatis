package shop.dao;

import shop.model.Pager;
import shop.model.User;
import shop.mybatis.map.UserMapper;
import shop.util.ShopException;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/17.
 */
public class UserDao extends BaseDao<User> implements IUserDao{
    public UserDao() {
        super(UserMapper.class);
    }

    @Override
    public int add(User obj) throws ShopException{
        User u = loadByName(obj.getUsername());
        if (u != null) {
            throw new ShopException("User " + obj.getUsername() + " has existed");
        }
        int id = super.add(obj);
        return id;
    }


    @Override
    public List<User> loadLists() {
        return super.loadLists();
    }

    @Override
    public User loadByName(String name) {
        return super.loadByName(name);
    }

    @Override
    public User login(String username, String password) throws ShopException{
        User u = loadByName(username);
        if (u == null) {
            throw new ShopException("没有" + username + "的用户");
        }
        if (!u.getPassword().equals(password)) {
            throw new ShopException("密码不正确");
        }
        return u;
    }
}
