package shop.dao;

import shop.model.Pager;
import shop.model.User;
import shop.util.ShopException;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/20.
 */
public interface IUserDao {
    public int add(User u) throws ShopException;

    public User load(int id);

    public int delete(int id);

    public int update(User u);

    public List<User> loadLists();

    public User loadByName(String name);

    public Pager<User> find(Map<String, Object> params);

    public User login(String username, String password) throws ShopException;

}
