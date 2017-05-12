package jtelecom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by Anna Rysakova on 07.05.2017.
 */
@Service
public class ProductUtil {
    private static Logger logger = LoggerFactory.getLogger(ProductUtil.class);

    /**
     * This method converts the received <code>String</code> into
     * an <code>Integer</code> array. The data in the line is stored in a comma.
     *
     * @param string data in <code>String</code> format
     * @return Array of <code>Integer</code> from a convertible <code>String</code>
     */
    public static Integer[] convertStringToIntegerArray(String string) {
        String[] stringArray = string.replaceAll("[\\[\\]\\s]", "").split(",");
        logger.debug("Convert received string to String array {} ", Arrays.toString(stringArray));
        Integer[] integerArray = new Integer[stringArray.length];
        logger.debug("Convert String array to Integer array {} ", Arrays.toString(integerArray));
        for (int i = 0; i < stringArray.length; i++) {
            // FIXME: 11.05.2017 or validate
            integerArray[i] = Integer.parseInt(stringArray[i]);
//                logger.error("Wrong parameter's type ", e.getMessage());
        }
        return integerArray;
    }

}
