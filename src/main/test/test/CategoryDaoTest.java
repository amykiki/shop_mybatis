package test;

import org.junit.Test;
import shop.dao.CategoryDao;
import shop.model.Category;
import shop.util.ShopException;

import java.util.List;

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
        c1.setName("精华");
        Category c2 = new Category();
        c2.setName("面霜");
        int id = cDao.addFriend(c2, c1);
        System.out.println(cDao.load(id));
    }

    @Test
    public void testAddChild() throws Exception {
        Category c1 = new Category();
        c1.setName("洗发护发");
//        c1.setId(6);
        Category c2 = new Category();
        c2.setName("洗发水");
//        c2.setParentCategory(c1.getParentCategory());
        int id = cDao.addChild(c2, c1);
        /*c2.setId(id);
        c2.setParentCategory(c1);*/
        System.out.println(cDao.load(id));
    }

    @Test
    public void testLoad() {
        try {
            Category c = cDao.load(2);
            System.out.println(c);
        } catch (ShopException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLoadByName() throws Exception {
        Category c = cDao.loadByName("眼霜2");
        if (c != null) {
            System.out.println(c);
        } else {
            System.out.println("NULL");
        }
    }

    @Test
    public void testLoadNodes() throws Exception {
        List<Category> cs = cDao.loadNodes(4);
        for (Category c : cs) {
            System.out.println(c);
        }
    }

    @Test
    public void testAddParent() throws Exception {
        Category newCategory = new Category();
        newCategory.setName("个人护理");
        Category childCategoryLeft  = new Category();
        Category childCategoryRight = new Category();
        childCategoryLeft.setId(6);
        childCategoryRight.setId(7);
        int id = cDao.addParent(newCategory, childCategoryLeft, childCategoryRight);
        if (id > 0) {
            newCategory = cDao.loadByName(newCategory.getName());
            System.out.println(newCategory);
        } else {
            System.out.println("FAIL");
        }
    }

    @Test
    public void testDelete() throws Exception {
        cDao.delete(25);
    }

    @Test
    public void testUpdate() throws Exception {
        Category c = new Category();
        c.setId(26);
        c.setName("洁面");
        cDao.update(c);
    }

    @Test
    public void testLoadLists() throws Exception {
        cDao.loadLists();
    }
}