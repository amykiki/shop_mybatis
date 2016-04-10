package shop.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.web.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Amysue on 2016/3/23.
 */
public class RequestUtil {
    private static Logger logger = LogManager.getLogger();

    public static Object setFileds(Class<?> clz, HttpServletRequest req, Class annoClz, String action) {
        Map<String, String[]> paraMap  = req.getParameterMap();
        Set<String>           paraKeys = paraMap.keySet();
        Map<String, String>   errMap   = (Map<String, String>) req.getAttribute("errMap");

        Field[] declarFileds  = clz.getDeclaredFields();
        Object  bean          = null;
        boolean setProperties = false;
        try {
            bean = clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Field declarFiled : declarFileds) {
            declarFiled.setAccessible(true);
            String annoKey = declarFiled.getName();
            if (declarFiled.isAnnotationPresent(annoClz)) {
                String[] paraValues = paraMap.get(annoKey);
                if (paraValues == null) {
                    logger.debug(annoKey + " is not existed");
                    if (declarFiled.isAnnotationPresent(NotNull.class) && action.equals("add")) {
                        if (errMap.get(annoKey) != null) {
                            String str = errMap.get(annoKey);
                            errMap.put(annoKey, str + "," + annoKey + "属性是必须的");
                        } else {
                            errMap.put(annoKey, annoKey + "属性是必须的");
                        }
                        logger.debug(annoKey + " is NULL");
                    }
                    continue;
                }
                Object paraValue;
                if (paraValues.length > 1) {
                    paraValue = paraValues;
                } else {
                    paraValue = paraValues[0];
                }
                logger.debug("key = " + annoKey + ", value = " + paraValue);
                try {
                    if (declarFiled.isAnnotationPresent(NotNull.class) || NotNullCheck(paraValue)) {
                        String errMsg = validate(declarFiled, paraValue);
                        if (!errMsg.equals("")) {
                            logger.debug(annoKey + ":" + errMsg);
                            if (errMap.get(annoKey) != null) {
                                String str = errMap.get(annoKey);
                                errMap.put(annoKey, str + "," + errMsg);
                            } else {
                                errMap.put(annoKey, errMsg);
                            }
                        }
                    }
                    BeanUtils.setProperty(bean, annoKey, paraValue);
                    setProperties = true;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (setProperties) {
            return bean;
        } else {
            return null;
        }

    }

    private static boolean NotNullCheck(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof String) {
            String str = (String) o;
            if (str.trim().equals("")) {
                return false;
            }
        }
        return true;
    }

    public static String validate(Field field, Object value) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        String       errMsg      = "";
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            String     methodName = annotation.annotationType().getSimpleName();
            if (!methodName.startsWith("Check")) {
                continue;
            }
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

    private static String CheckEmpty(Object obj, Field f) {
        String errMsg = f.getAnnotation(CheckEmpty.class).errMsg();
        int    length = f.getAnnotation(CheckEmpty.class).length();
        if (obj == null) {
            return errMsg;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.trim().equals("") || str.length() < length) {
                return errMsg;
            }
        }
        return null;
    }

    private static String CheckNum(Object obj, Field f) {
        String errMsg = f.getAnnotation(CheckNum.class).errMsg();
        int    value  = f.getAnnotation(CheckNum.class).length();
        if (obj == null) {
            return errMsg;
        }
        String str = (String) obj;
        if (!str.matches("\\d{" + value + ",}")) {
            return errMsg;
        }
        return null;
    }

    private static String CheckImg(Object obj, Field f) {
        String   errMsg = f.getAnnotation(CheckImg.class).errMsg();
        String[] values = f.getAnnotation(CheckImg.class).value();
        String   str    = (String) obj;
        String   suffix = str.substring(str.lastIndexOf(".") + 1);
        for (String v : values) {
            if (suffix.equals(v)) {
                return null;
            }
        }
        return errMsg;
    }

    private static String CheckPrice(Object obj, Field f) {
        String errMsg = f.getAnnotation(CheckPrice.class).errMsg();
        String str    = (String) obj;
        if (obj == null) {
            return errMsg;
        }
        if (!str.matches("\\d+(\\.\\d{1,2})?")) {
            return errMsg;
        }
        return null;
    }
}
