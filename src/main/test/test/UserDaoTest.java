package test;

import org.junit.Before;
import org.junit.Test;
import shop.dao.DaoFactory;
import shop.dao.IUserDao;
import shop.dao.UserDao;
import shop.model.Address;
import shop.model.Pager;
import shop.model.Role;
import shop.model.User;
import shop.util.ShopDi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/17.
 */
public class UserDaoTest {
    IUserDao udao;

    public UserDaoTest() {
        DaoFactory.setDao(this);
    }

    @ShopDi("userDao")
    public void setUdao(IUserDao udao) {
        this.udao = udao;
    }

    @Test
    public void testLoad() throws Exception {
        User u = udao.load(19);
        System.out.println(u);
        if (u.getAddresses() != null) {
            for (Address addr : u.getAddresses()) {
                System.out.println(addr);
            }
        }
    }

    @Test
    public void testAdd() throws Exception {
        User u = new User();
        u.setUsername("amy2");
        u.setNickname("阿不思2");
        u.setPassword("34552");
        u.setRole(Role.ADMIN);
        int id = udao.add(u);
        System.out.println("Generated ID = " + id);
    }

    @Test
    public void testDelete() throws Exception {
        int affectedRows = udao.delete(353);
        System.out.println("affectedRows = " + affectedRows);
    }

    @Test
    public void testUpdate() throws Exception {
        User u = new User();
        u.setId(5);
        u.setPassword("34552");
//        u.setNickname("哈利");
        u.setRole(Role.NORMAL);
        int affectedRows = udao.update(u);
        System.out.println("affectedRows = " + affectedRows);
    }

    @Test
    public void testLoadLists() throws Exception {
        List<User> uLists = udao.loadLists();
        for (User u : uLists) {
            System.out.println(u);
        }
    }

    @Test
    public void testFind() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("pageLimit", 15);
        map.put("currentPage", 2);
//        map.put("username", "harry");
//        map.put("username", "amysue");
//        map.put("role", Role.ADMIN);
//        map.put("nickname", "哈利");
        Pager<User> uPager = udao.find(map);
        List<User> uList = uPager.gettLists();
        if (uList != null) {
            for (User u : uList) {
                System.out.println(u);
            }
        }
        System.out.println("All Items: " + uPager.getAllItems());
        System.out.println("All Page Nums: " + uPager.getAllPageNums());
        System.out.println("Page Items: " + uPager.getPageLimit());
        System.out.println("Current Page: " + uPager.getCurrentPage());
    }

    @Test
    public void testLoadByName() throws Exception {
        User u = udao.loadByName("Lily Evans");
        System.out.println(u);
        if (u.getAddresses() != null) {
            for (Address addr : u.getAddresses()) {
                System.out.println(addr);
            }
        }
    }
}