package shop.dao;

import org.apache.ibatis.session.SqlSession;
import shop.mybatis.map.UserMapper;
import shop.model.User;
import shop.util.BatisUtil;

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
}
