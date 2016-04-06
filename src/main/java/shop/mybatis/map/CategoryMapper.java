package shop.mybatis.map;

import org.apache.ibatis.annotations.Param;
import shop.model.Category;

import java.util.Map;

/**
 * Created by Amysue on 2016/4/5.
 */
public interface CategoryMapper {
    public Category selectByPro(String name);

//    public void addFriendNode(@Param("friendCategory") Category c1, @Param("newCategory") Category c2, @Param("id") Integer id);
    public void addFriendNode(Map<String, Object> map);
    public void addChildNode(Map<String, Object> map);
}
