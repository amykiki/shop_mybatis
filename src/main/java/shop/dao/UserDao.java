package shop.dao;

import shop.model.Pager;
import shop.model.User;
import shop.mybatis.map.UserMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/17.
 */
public class UserDao extends BaseDao<User>{
    public UserDao() {
        super(UserMapper.class);
    }

    @Override
    public User load(int id) {
        return super.load(id);
    }

    @Override
    public int add(User obj) {
        //// TODO: find unique username 2016/3/18

        int id = super.add(obj);
        return id;
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public int update(User obj) {
        return super.update(obj);
    }

    @Override
    public List<User> loadLists() {
        return super.loadLists();
    }

    @Override
    public Pager<User> find(Map<String, Object> params) {
        return super.find(params);
    }
}
