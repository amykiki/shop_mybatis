package shop.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Amysue on 2016/3/20.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ShopDi {

    String value() default "";
}
