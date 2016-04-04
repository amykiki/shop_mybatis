import shop.model.Address;
import shop.enums.Role;
import shop.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Amysue on 2016/3/16.
 */
public class testMain {
    public static void main(String[] args) {
        Object o1 = new User();
        System.out.println(o1.getClass());
        System.out.println(o1.getClass().getName());
        User u1 = (User) o1;
        u1.setId(1);
        u1.setNickname("管理员");
        u1.setPassword("123");
        u1.setUsername("admin");
        u1.setRole(Role.ANON);
        System.out.println(u1);
        System.out.println(User.class.getName());
        try {
            Constructor c0 = Class.forName("shop.model.User").getConstructor(new Class[]{String.class, String.class, String.class, Role.class});
            User u2 = (User) c0.newInstance("amy", "123dd", "小公主", Role.ADMIN);
            System.out.println(u2);

            Method m1 = User.class.getMethod("getNickname");
            String name = (String)m1.invoke(u2);
            System.out.println(name);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object o2 = new Address();
        System.out.println(o2.getClass());
        System.out.println(o2.getClass().getName());

        if (o1 instanceof shop.model.Address) {
            System.out.println("yes");
        } else {
            System.out.println("NO");
        }

        try {
            Class cl = Class.forName("shop.model.User");
//            Class cl = Class.forName("java.lang.String");
            String modifiers = Modifier.toString(cl.getModifiers());
            if (modifiers.length() > 0) {
                System.out.println(modifiers);
            }
            printConstructors(cl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printConstructors(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor c : constructors) {
            String name = c.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            System.out.print(name + "(");

            Class[] paramTypes = c.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }
}
