package shop.util;

import org.apache.commons.beanutils.Converter;
import shop.enums.PStatus;

/**
 * Created by Amysue on 2016/4/11.
 */
public class PStatusConvert implements Converter {
    @Override
    public Object convert(Class type, Object value) {
        if (value == null) {
            throw new RuntimeException("value is null");
        }
        if (value.getClass() != String.class) {
            throw new RuntimeException("type is not match");
        }
        String valueStr = (String) value;
        if (valueStr.trim().length() == 0) {
            throw new RuntimeException("type is not match");
        }
        try {
            PStatus status = PStatus.valueOf(valueStr);
            System.out.println("===============Convert PStatus==============");
            return status;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
