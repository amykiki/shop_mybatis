package test;

import org.junit.Test;
import shop.dao.UserDao;
import shop.model.Address;
import shop.model.Role;
import shop.model.User;

import static org.junit.Assert.*;

/**
 * Created by Amysue on 2016/3/17.
 */
public class UserDaoTest {
    UserDao udao = new UserDao();

    @Test
    public void testLoad() throws Exception {
        User u = udao.load(5);
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
        u.setUsername("Albus2");
        u.setNickname("阿不思2");
        u.setPassword("34552");
        u.setRole(Role.ADMIN);
        int id = udao.add(u);
        System.out.println("Generated ID = " + id);
    }
}