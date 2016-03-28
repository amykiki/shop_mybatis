package shop.mybatis.map;

import org.apache.ibatis.annotations.Param;
import shop.model.Role;
import shop.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/17.
 */
public interface UserMapper {
    public User load (int id);
    public User loadNoAddr(int id);
    public User loadByName (String name);


    public int add(User user);
    public int delete(int id);

    public int update(User user);

    public List<User> loadLists();

    public List<User> find(Map<String, Object> map);

    public int findCount(Map<String, Object> map);

    public int deleteLists(List<Integer> list);

    public int updateAuth(@Param("role") Role role, @Param("list") List<Integer> list);
}
