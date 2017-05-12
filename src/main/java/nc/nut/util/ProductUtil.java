package nc.nut.util;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Anna Rysakova on 07.05.2017.
 */
@Service
public class ProductUtil {
    private static Logger logger = LoggerFactory.getLogger(ProductUtil.class);

    /**
     * This method converts the received <code>String</code> into
     * an <code>Integer</code> array. The data in the line is stored in a comma.
     * Helper method for {@link nc.nut.controller.admin.UpdateProductController}
     * Helper method for {@link nc.nut.controller.admin.FillTariffController}
     *
     * @param string data in <code>String</code> format
     * @return Array of <code>Integer</code> from a convertible <code>String</code>
     */
    public static Integer[] convertStringToIntegerArray(String string) {
        String[] stringArray = string.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
        logger.debug("Convert received string to String array {} ", Arrays.toString(stringArray));
        Integer[] integerArray = new Integer[stringArray.length];
        logger.debug("Convert String array to Integer array {} ", Arrays.toString(integerArray));
        for (int i = 0; i < stringArray.length; i++) {
            try {
                integerArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException e) {
                logger.error("Wrong parameter's type ", e.getMessage());
            }
        }
        return integerArray;
    }

    /**
     * This method compare generic <code>Collection</code> and remove from first collection
     * elements which included in second <code>Collection</code>.
     * Helper method for {@link nc.nut.services.ProductService#fillInTariffWithServices(Integer, Integer[])}
     *
     * @see Collection
     * @see T
     * @param originalCollection original <code>Collection</code>
     * @param compareCollection collection from which the elements will be compared
     * @param <T> collection type
     * @return collection Collection of elements from the first collection,
     *         which are included in the second collection
     */
    public static <T> Collection<T> getUniqueElementsInFirstCollection(Collection<T> originalCollection, Collection<T> compareCollection) {
        Collection<T> resultCollection = new ArrayList<>(originalCollection);
        Collection<T> collectionForCompare = new ArrayList<>(compareCollection);
        resultCollection.removeAll(collectionForCompare);

        return resultCollection;
    }

    /**
     * Anna Rysakova
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> convertArrayToCollection(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    /**
     * Anna Rysakova
     *
     * @param collection
     * @return
     */
    public static Integer[] convertCollectionToArray(Collection<T> collection) {
        return collection.toArray(new Integer[collection.size()]);
    }
}
