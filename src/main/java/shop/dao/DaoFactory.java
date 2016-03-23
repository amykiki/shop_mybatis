package shop.dao;

import shop.util.PropertiesUtil;
import shop.util.ShopDi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by Amysue on 2016/3/20.
 */
public class DaoFactory {

    private static final String file = "/shop-config.properties";


    public static void setDao(Object obj) {
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(ShopDi.class)) {
                ShopDi di      = m.getAnnotation(ShopDi.class);
                String daoName = di.value();
                if (daoName == null || daoName.equals("")) {
                    String mName = m.getName();
                    daoName = lowerFirst(mName.substring(3));
                }
                Object dao = getDao(daoName);
                m.setAccessible(true);
                try {
                    m.invoke(obj, dao);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    private static String lowerFirst(String string) {
        char[] strs = string.toCharArray();
        if (strs[0] >= 97 && strs[0] <= 122) {
            strs[0] += 32;
        }
        return new String(strs);
    }

    public static Object getDao(String name) {
        Properties prop    = PropertiesUtil.getDaoProp();
        String     daoName = prop.getProperty(name);
        Object     dao     = null;
        try {
            Class clz = Class.forName(daoName);
            dao = clz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return dao;
    }

}
