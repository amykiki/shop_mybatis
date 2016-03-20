package shop.mybatis.map;

import org.apache.ibatis.session.RowBounds;
import shop.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/17.
 */
public interface UserMapper {
    public User load (int id);

    public int add(User user);
    public int delete(int id);

    public int update(User user);

    public List<User> loadLists();

    public List<User> find(Map<String, Object> map);

    public int findCount(Map<String, Object> map);
}
