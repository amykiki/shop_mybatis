package shop.web.annotation;

import shop.enums.EqualID;
import shop.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Amysue on 2016/3/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
    Role value() default Role.ADMIN;
    EqualID equalID() default EqualID.ALL;
}
