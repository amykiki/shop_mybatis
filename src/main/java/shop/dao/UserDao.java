package shop.dao;

import org.apache.ibatis.session.SqlSession;
import shop.mybatis.map.UserMapper;
import shop.model.User;
import shop.util.BatisUtil;

/**
 * Created by Amysue on 2016/3/17.
 */
public class UserDao {
    public User load(int id) {
        SqlSession session = null;
        try {
            session = BatisUtil.getSession();
            UserMapper mapper = session.getMapper(UserMapper.class);
            User u = mapper.load(id);
            System.out.println(u);
        } finally {
            BatisUtil.closeSession(session);
        }
        return null;
    }

}
