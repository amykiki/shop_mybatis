package shop.util;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Amysue on 2016/3/17.
 */
public class BatisUtil {
    private volatile static SqlSessionFactory sessionFactory = null;
    private static String resFolder = System.getProperty("user.dir") + "\\target\\classes\\";
    private static String batisConfig = "mybatis-config.xml";

    public static SqlSession getSession() {
        return getSession(ExecutorType.SIMPLE);
    }

    public static SqlSession getSession(ExecutorType type) {
        if (sessionFactory == null) {
            synchronized (BatisUtil.class) {
                if (sessionFactory == null) {
                    try {
                        InputStream is = Resources.getResourceAsStream(batisConfig);
                        sessionFactory = new SqlSessionFactoryBuilder().build(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sessionFactory.openSession(type);
    }

    public static void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }



}
