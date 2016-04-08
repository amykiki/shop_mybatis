package shop.dao;

import shop.model.Category;
import shop.util.ShopException;

import java.util.List;

/**
 * Created by Amysue on 2016/4/5.
 */
public interface ICategoryDao {
    public int addFriend(Category newCategory, Category friendCategory) throws ShopException;
    public int addChild(Category newCategory, Category parentCategory) throws ShopException;
    public int addParent(Category newCategory, Category childCategoryLeft, Category childCategoryRight) throws ShopException;
    public int addNode(String type, Category newCategory, Category... cs) throws ShopException;
    public int delete(int id) throws ShopException;

    public int update(Category category);

    public Category load(int id) throws ShopException;
    public Category loadByName(String name) throws ShopException;

    public List<Category> loadLists();
    public List<Category> loadNodes(int parentid);
}
