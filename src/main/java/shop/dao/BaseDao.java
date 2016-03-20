package shop.dao;

import org.apache.ibatis.session.SqlSession;
import shop.model.Pager;
import shop.util.BatisUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        int        keyID   = -1;
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

    public int delete(int id) {
        SqlSession session      = null;
        int        affectedRows = 0;
        try {
            session = BatisUtil.getSession();
            Object mapper = session.getMapper(clz);
            Method delete = mapper.getClass().getDeclaredMethod("delete", int.class);
            affectedRows = (int) delete.invoke(mapper, id);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new RuntimeException("Delete " + clz.getSimpleName() + " Failed", e);
        } finally {
            BatisUtil.closeSession(session);
        }
        return affectedRows;
    }

    public int update(T obj) {
        SqlSession session      = null;
        int        affectedRows = 0;
        try {
            session = BatisUtil.getSession();
            Object mapper = session.getMapper(clz);
            Method update = mapper.getClass().getDeclaredMethod("update", obj.getClass());
            affectedRows = (int) update.invoke(mapper, obj);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new RuntimeException("Update " + clz.getSimpleName() + " Failed", e);
        } finally {
            BatisUtil.closeSession(session);
        }
        return affectedRows;
    }

    public List<T> loadLists() {
        SqlSession session = null;
        List<T>    lists   = new ArrayList<>();
        try {
            session = BatisUtil.getSession();
            Object mapper    = session.getMapper(clz);
            Method loadLists = mapper.getClass().getDeclaredMethod("loadLists");
            lists = (List<T>) loadLists.invoke(mapper);
        } catch (Exception e) {
            session.rollback();
            throw new RuntimeException("LoadLists " + clz.getSimpleName() + " Failed", e);
        } finally {
            BatisUtil.closeSession(session);
        }
        return lists;
    }

    public Pager<T> find(Map<String, Object> params) {
        SqlSession session;
        List<T>    lists;
        Pager<T>   pager   = new Pager<>();
        try {
            session = BatisUtil.getSession();
            Object mapper = session.getMapper(clz);
            Method find   = mapper.getClass().getDeclaredMethod("find", Map.class);
            lists = (List<T>) find.invoke(mapper, params);
        } catch (Exception e) {
            throw new RuntimeException("Find " + clz.getSimpleName() + " Failed", e);
        }
        pager.settLists(lists);
        return pager;
    }
}
