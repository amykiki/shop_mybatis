package shop.util;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import shop.model.Role;
import shop.model.User;
import shop.mybatis.map.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Amysue on 2016/3/20.
 */
public class addUsers {

    public static void main(String[] args) {
        addUsers addusers = new addUsers();
        addusers.addMultipleUsers();
    }

    private Document getDoc() {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(this.getClass().getClassLoader().getResourceAsStream("harry.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public void addMultipleUsers() {
        Document doc = getDoc();
        Element root = doc.getRootElement();
        int size = root.nodeCount();
        Random random = new Random();
        SqlSession session = BatisUtil.getSession(ExecutorType.BATCH);
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            for (int i = 0; i < size; i++) {
                Node node = root.node(i);
                if (node instanceof Element) {
                    Element ele = (Element) node;
                    String username = ele.elementTextTrim("username");
                    String nickname = ele.elementTextTrim("nickname");
                    User u = new User();
                    u.setUsername(username);
                    u.setNickname(nickname);
                    int j = random.nextInt(8);
                    if (j > 6) {
                        u.setRole(Role.ADMIN);
                    } else {
                        u.setRole(Role.NORMAL);
                    }
                    u.setPassword("amy123-" + j);
                    mapper.add(u);
                }
            }
            session.commit();
            session.clearCache();
        } finally {
            session.close();
        }
    }

}
