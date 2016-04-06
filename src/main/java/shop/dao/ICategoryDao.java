package shop.dao;

import shop.model.Category;
import shop.util.ShopException;

import java.util.List;

/**
 * Created by Amysue on 2016/4/5.
 */
public interface ICategoryDao {
    public int add(Category category) throws ShopException;
    public int delete(int id) throws ShopException;

    public int update(Category category);

    public Category load(int id) throws ShopException;

    public List<Category> loadLists();
}
