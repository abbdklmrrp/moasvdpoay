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
     * Anna Rysakova
     *
     * @param string
     * @return
     */
    public static Integer[] convertStringToIntegerArray(String string) {
        String[] stringArray = string.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
        Integer[] integerArray = new Integer[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            try {
                integerArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException nfe) {
                logger.error("Wrong parameter's type ", nfe.getMessage());
            }
        }
        return integerArray;
    }

    /**
     * Anna Rysakova
     *
     * @param initialCollection
     * @param compareCollection
     * @param <T>
     * @return
     */
    public static <T> Collection<T> getUniqueElementsInFirstCollection(Collection<T> initialCollection, Collection<T> compareCollection) {
        Collection<T> resultCollection = new ArrayList<>(initialCollection);
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
