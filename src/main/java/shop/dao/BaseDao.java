package shop.dao;

import org.apache.ibatis.session.SqlSession;
import shop.model.User;
import shop.util.BatisUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Amysue on 2016/3/18.
 */
public class BaseDao<T> {
    private Class clz;

    public BaseDao(Class clz) {
        this.clz = clz;
    }

    public T load(int id) {
        T          obj     = null;
        SqlSession session = null;
        try {
            session = BatisUtil.getSession();
            Object mapper = session.getMapper(clz);
            Method load   = mapper.getClass().getDeclaredMethod("load", int.class);
            obj = (T) load.invoke(mapper, id);
        } catch (Exception e) {
            throw new RuntimeException("Load " + clz.getSimpleName() + " Failed", e);
        } finally {
            BatisUtil.closeSession(session);
        }
        return obj;
    }

    public int add(T obj) {
        SqlSession session = null;
        int keyID = -1;
        try {
            session = BatisUtil.getSession();
            Object mapper = session.getMapper(clz);
            Method add    = mapper.getClass().getDeclaredMethod("add", obj.getClass());
            add.invoke(mapper, obj);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new RuntimeException("Database: Add " + clz.getSimpleName() + " Failed", e);
        } finally {
            BatisUtil.closeSession(session);
        }
        try {
            Field f = obj.getClass().getDeclaredField("id");
            f.setAccessible(true);
            keyID = (int) f.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Add " + clz.getSimpleName() + " Failed", e);
        }
        return keyID;
    }
}
