package shop.dao;

import shop.enums.Role;
import shop.model.Category;
import shop.mybatis.map.CategoryMapper;
import shop.util.ShopException;

import java.util.*;

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

    public int addFriend(Category newCategory, Category friendCategory) throws ShopException {
        int id = addNode("friend", newCategory, friendCategory);
        return id;
    }

    public int addChild(Category newCategory, Category parentCategory) throws ShopException {
        int id = addNode("child", newCategory, parentCategory);
        return id;
    }

    public int addParent(Category newCategory, Category childCategoryLeft, Category childCategoryRight) throws ShopException {
        int id = addNode("parent", newCategory, childCategoryLeft, childCategoryRight);
        return id;
    }

    private int addNode(String type, Category newCategory, Category... cs) throws ShopException {
        String categoryKey = "";
        String method      = "";

        if (loadByName(newCategory.getName()) != null) {
            throw new ShopException(newCategory.getName() + " has been added, can't be add again");
        }
        if (type.equals("friend")) {
            categoryKey = "friendCategory";
            method = "addFriendNode";
        } else if (type.equals("child")) {
            categoryKey = "parentCategory";
            method = "addChildNode";
        } else if (type.equals("parent")) {
            categoryKey = "childCategoryLeft";
            method = "addParentNode";
        }
        Map<String, Object> map = new HashMap<>();
        map.put(categoryKey, cs[0]);
        if (type.equals("parent")) {
            map.put("childCategoryRight", cs[1]);
        }
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

    @Override
    public Category load(int id) throws ShopException {
        return super.load(id);
    }

    @Override
    public Category loadByName(String name) {
        return super.loadByName(name);
    }

    @Override
    public int delete(int id) throws ShopException {
        return super.delete(id);
    }

    @Override
    public int update(Category obj) {
        return super.update(obj);
    }

    public List<Category> loadNodes(int parentid) {
        Object[]   params = new Object[]{parentid};
        Class<?>[] pClz   = new Class[]{int.class};
        try {
            return (List<Category>) super.runSelectMethod("loadNodes", params, pClz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Category> loadLists() {
        List<Category> lists = super.loadLists();
        int lastDepth = 0;
        for (int i = 0; i < lists.size(); i++) {
            lastDepth = printCategory(lists.get(i), lastDepth);
        }
        return lists;
    }

    private int printCategory(Category c, int lastDepth) {
        String name = c.getName() + "-" + c.getId() + "-" + c.getParentCategory().getId();
        if (c.getDepth() == 0) {
            if (lastDepth > 0) {
                System.out.println();
            }
            System.out.println(name);
        } else if (c.getDepth() == 1) {
            if (lastDepth >= 1) {
                System.out.println();
            }
            System.out.print("   " + name);
        } else if (c.getDepth() == 2) {
            if (lastDepth == 1) {
                System.out.print(": ");
            }
            System.out.print(name + " ");
        }
        return c.getDepth();
    }


}
