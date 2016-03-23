package shop.web.annotation;

import shop.model.Role;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Amysue on 2016/3/23.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    Role value() default Role.ADMIN;
    boolean equalID() default false;
}
