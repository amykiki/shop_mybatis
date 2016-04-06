package test;

import org.junit.Test;
import shop.dao.CategoryDao;
import shop.model.Category;

import static org.junit.Assert.*;

/**
 * Created by Amysue on 2016/4/5.
 */
public class CategoryDaoTest extends BaseTest {
    CategoryDao cDao = new CategoryDao();

    @Test
    public void testGetByName() throws Exception {
        String   name = "TELEVISIONS";
        Category c    = cDao.getByName(name);
        if (c != null) {
            System.out.println(c);
        }
    }

    @Test
    public void testAddFriend() throws Exception {
        Category c1 = new Category();
        c1.setName("时尚女装");
        c1.setParentCategory(new Category());
        c1.getParentCategory().setId(-1);
        Category c2 = new Category();
        c2.setName("运动户外");
        c2.setParentCategory(c1.getParentCategory());
        int id = cDao.addFriend(c1, c2);
        c2.setId(id);
        System.out.println(c2);
    }

    @Test
    public void testAddChild() throws Exception {
        Category c1 = new Category();
        c1.setName("时尚女装");
        c1.setId(5);
        Category c2 = new Category();
        c2.setName("连衣裙");
        c2.setParentCategory(c1.getParentCategory());
        int id = cDao.addChild(c1, c2);
        c2.setId(id);
        c2.setParentCategory(c1);
        System.out.println(c2);
    }
}