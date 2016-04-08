package shop.dao;

import shop.enums.Role;
import shop.model.User;
import shop.mybatis.map.UserMapper;
import shop.util.ShopException;

import java.util.List;

/**
 * Created by Amysue on 2016/3/17.
 */
public class UserDao extends BaseDao<User> implements IUserDao {
    public UserDao() {
        super(UserMapper.class);
    }

    @Override
    public int add(User obj) throws ShopException {
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
    public User loadByName(String name) throws ShopException{
        return super.loadByName(name);
    }

    @Override
    public User login(String username, String password) throws ShopException {
        User u = loadByName(username);
        if (u == null) {
            throw new ShopException("没有" + username + "的用户");
        }
        if (!u.getPassword().equals(password)) {
            throw new ShopException("密码不正确");
        }
        return u;
    }

    @Override
    public int delete(int id) throws ShopException {
        return super.delete(id);
    }

    @Override
    public int updateAuth(Role role, List<Integer> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        Object[]   params = new Object[]{role, list};
        Class<?>[] pClz   = new Class[]{Role.class, List.class};
        try {
            return (int) super.runMethod("updateAuth", params, pClz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User load(int id, boolean addr) throws ShopException{
        try {
            if (addr) {
                return load(id);
            } else {
                Object[]   params = new Object[]{id};
                Class<?>[] pClz   = new Class[]{int.class};
                return (User) super.runMethod("loadNoAddr", params, pClz);
            }
        } catch (Exception e) {
            throw new ShopException("获取用户id为" + id + "的对象失败");
        }
    }

}
