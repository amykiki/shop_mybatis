package test;

import shop.dao.DaoFactory;

/**
 * Created by Amysue on 2016/4/4.
 */
public class BaseTest {
    public BaseTest() {
        DaoFactory.setDao(this);
    }
}
