package shop.util;

import org.apache.commons.beanutils.BeanUtils;
import shop.web.annotation.NUMBERS;
import shop.web.annotation.NotNULL;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Amysue on 2016/3/23.
 */
public class RequestUtil {

    public static Object setFileds(Class<?> clz, HttpServletRequest req) {
        Map<String, String[]> paraMap  = req.getParameterMap();
        Set<String>           paraKeys = paraMap.keySet();
        req.setAttribute("errMap", null);
        Map<String, String> errMap = null;
        try {
            Object bean  = clz.newInstance();
            Field  field = null;
            for (String key : paraKeys) {
                try {
                    field = clz.getField("key");
                    String[] values = paraMap.get(key);
                    Object   value;
                    if (values.length > 1) {
                        value = values;
                    } else {
                        value = values[0];
                    }
                    String errMsg = validate(field, value);
                    if (!errMsg.equals("")) {
                        errMap.put(key, errMsg);
                    } else {
                        BeanUtils.setProperty(bean, key, value);
                    }
                } catch (NoSuchFieldException | InvocationTargetException e) {
                    continue;
                }

            }
            if (errMap != null) {
                req.setAttribute("errMap", errMap);
            }
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String validate(Field field, Object value) {
        Map<String, String> errMap      = new HashMap<>();
        Annotation[]        annotations = field.getDeclaredAnnotations();
        String              errMsg      = "";
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            String     methodName = "validate" + annotation.getClass().getName();
            try {
                Method m = RequestUtil.class.getDeclaredMethod(methodName, Object.class, Field.class);
                m.setAccessible(true);
                String error = (String) m.invoke(null, value, field);
                if (error != null) {
                    if (i == annotations.length - 1) {
                        errMsg += error + ".";
                    } else {
                        errMsg += error + ",";
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return errMsg;
    }

    private static String validateNotNULL(Object obj, Field f) {
        String errMsg = f.getAnnotation(NotNULL.class).errMsg();
        if (obj == null) {
            return errMsg;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.trim().equals("")) {
                return errMsg;
            }
        }
        return null;
    }

    private static String validateNUMBERS(Object obj, Field f) {
        String errMsg = f.getAnnotation(NUMBERS.class).errMsg();
        int value = f.getAnnotation(NUMBERS.class).value();
        if (obj == null) {
            return errMsg;
        }
        String str = (String)obj;
        if (!str.matches("\\d{"+value+",}")) {
            return errMsg;
        }
        return null;

    }
}
