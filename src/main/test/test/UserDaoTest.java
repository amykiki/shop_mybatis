package test;

import org.junit.Test;
import shop.dao.UserDao;

import static org.junit.Assert.*;

/**
 * Created by Amysue on 2016/3/17.
 */
public class UserDaoTest {
    UserDao udao = new UserDao();

    @Test
    public void testLoad() throws Exception {
        udao.load(1);
    }
}