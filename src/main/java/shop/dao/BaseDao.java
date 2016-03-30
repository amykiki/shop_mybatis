package shop.dao;

import org.apache.ibatis.session.SqlSession;
import shop.model.Pager;
import shop.util.BatisUtil;
import shop.util.ShopException;

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
//        System.out.println("=========Add " + this.getClass().getName() + " Begin===========");
//        DaoFactory.setDao(this);
        this.clz = clz;
    }

    public T load(int id) throws ShopException{
        T          obj     = null;
        SqlSession session = null;
        try {
            session = BatisUtil.getSession();
            Object mapper = session.getMapper(clz);
            Method load   = mapper.getClass().getDeclaredMethod("load", int.class);
            obj = (T) load.invoke(mapper, id);
        } catch (Exception e) {
            throw new ShopException("Load " + clz.getSimpleName() + " Failed", e);
        } finally {
            BatisUtil.closeSession(session);
        }
        if (obj == null) {
            throw new ShopException("Load " + clz.getSimpleName() + " Failed");
        }
        return obj;
    }

    public T loadByName(String name) {
        T          obj     = null;
        SqlSession session = null;
        try {
            session = BatisUtil.getSession();
            Object mapper     = session.getMapper(clz);
            Method loadByName = mapper.getClass().getDeclaredMethod("loadByName", String.class);
            obj = (T) loadByName.invoke(mapper, name);
        } catch (Exception e) {
            throw new RuntimeException("Load " + clz.getSimpleName() + " Failed", e);
        } finally {
            BatisUtil.closeSession(session);
        }
        return obj;
    }

    public int add(T obj) throws ShopException {
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

    public int delete(int id) throws ShopException{
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
            throw new ShopException("Delete " + clz.getSimpleName() + " Failed " + e.getMessage());
        } finally {
            BatisUtil.closeSession(session);
        }
        return affectedRows;
    }

    public int deleteLists(List<Integer> list) throws ShopException {
        if (list == null || list.size() == 0) {
            return 0;
        }
        SqlSession session      = null;
        int        affectedRows = 0;
        try {
            session = BatisUtil.getSession();
            Object mapper = session.getMapper(clz);
            Method deleteLists = mapper.getClass().getDeclaredMethod("deleteLists", List.class);
            affectedRows = (int) deleteLists.invoke(mapper, list);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new ShopException("Delete " + clz.getSimpleName() + " Failed " + e.getMessage());
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
        SqlSession session = null;
        List<T>    lists       = null;
        Pager<T>   pager       = new Pager<>();
        int        allItems    = 0;
        int        pageLimit   = (int) params.get("pageLimit");
        int        toPage      = (int) params.get("toPage");
        int        pageShow    = (int) params.get("pageShow");
        int        allPageNums = 0;
        int        begin       = 0;
        int        end         = 0;
        try {
            session = BatisUtil.getSession();
            Object mapper    = session.getMapper(clz);
            Method findCount = mapper.getClass().getDeclaredMethod("findCount", Map.class);
            allItems = (int) findCount.invoke(mapper, params);
            if (allItems == 0) {
                allPageNums = 0;
                toPage = 0;
            } else {
                allPageNums = (allItems - 1) / pageLimit + 1;
                if (toPage > allPageNums) {
                    toPage = allPageNums;
                } else if (toPage <= 0) {
                    toPage = 1;
                }
                //get begin and end index
                begin = toPage - pageShow / 2;
                if (begin < 1) {
                    begin = 1;
                }
                end = begin - 1 + pageShow;
                if (end > allPageNums) {
                    begin -= end - allPageNums;
                    if (begin < 1) {
                        begin = 1;
                    }
                    end = allPageNums;
                }
                int offset = (toPage - 1) * pageLimit;
                params.put("offset", offset);
                Method find = mapper.getClass().getDeclaredMethod("find", Map.class);
                lists = (List<T>) find.invoke(mapper, params);
            }
        } catch (Exception e) {
            throw new RuntimeException("Find " + clz.getSimpleName() + " Failed", e);
        } finally {
            BatisUtil.closeSession(session);

        }
        pager.setPageLimit(pageLimit);
        pager.setAllItems(allItems);
        pager.setAllPageNums(allPageNums);
        pager.setCurrentPage(toPage);
        pager.settLists(lists);
        pager.setBegin(begin);
        pager.setEnd(end);
        return pager;
    }

    public Object runMethod(String mName, Object[] params, Class<?>[] pClz) throws Exception{
        SqlSession session = null;
        Object obj= null;
        try {
            session = BatisUtil.getSession();
            Object mapper = session.getMapper(clz);
            Method m = mapper.getClass().getDeclaredMethod(mName, pClz);
            obj=  m.invoke(mapper, params);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new Exception(e);
        } finally {
            BatisUtil.closeSession(session);
        }
        return obj;
    }
}
