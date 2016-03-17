package test;

import org.junit.Test;
import shop.util.BatisUtil;

import static org.junit.Assert.*;

/**
 * Created by Amysue on 2016/3/17.
 */
public class BatisUtilTest {

    @Test
    public void testGetSession() throws Exception {
        BatisUtil.getSession();
    }
}