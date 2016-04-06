package shop.dao;

import shop.enums.Role;
import shop.model.Category;
import shop.mybatis.map.CategoryMapper;
import shop.util.ShopException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/5.
 */
public class CategoryDao extends BaseDao<Category> {
    public CategoryDao() {
        super(CategoryMapper.class);
    }

    public Category getByName(String name) {
        Object[]   params = new Object[]{name};
        Class<?>[] pClz   = new Class[]{String.class};
        try {
            return (Category) super.runSelectMethod("selectByPro", params, pClz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addFriend(Category friendCategory, Category newCategory) throws ShopException{
        int id = addNode(friendCategory, newCategory, "friend");
        return id;
    }

    public int addChild(Category parentCategory, Category newCategory) throws ShopException{
        int id = addNode(parentCategory, newCategory, "child");
        return id;
    }

    private int addNode(Category c1, Category newCategory, String type) throws ShopException{
        String categoryKey = "";
        String method      = "";
        if (type.equals("friend")) {
            categoryKey = "friendCategory";
            method = "addFriendNode";
        } else if (type.equals("child")) {
            categoryKey = "parentCategory";
            method = "addChildNode";
        }
        Map<String, Object> map = new HashMap<>();
        map.put(categoryKey, c1);
        map.put("newCategory", newCategory);
        map.put("id", -1);
        Object[]   params = new Object[]{map};
        Class<?>[] pClz   = new Class[]{Map.class};
        try {
            super.runMethod(method, params, pClz);
        } catch (Exception e) {
            throw new ShopException("add " + newCategory.getName() + " fail", e);
        }
        return (int) map.get("id");
    }
}
