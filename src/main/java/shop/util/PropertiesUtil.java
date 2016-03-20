package shop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Amysue on 2016/3/20.
 */
public class PropertiesUtil {
    private static Properties daoProp = null;

    public static Properties getDaoProp() {
        if (daoProp == null) {
            synchronized (PropertiesUtil.class) {
                if (daoProp == null) {
                    daoProp = getProp("dao.properties");
                }
            }
        }
        return daoProp;
    }

    private static Properties getProp(String name) {
        Properties prop = new Properties();
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(name);
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
