package nc.nut.util;

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
    public static <T> Collection<T> getUniqueElementsInCollection(Collection<T> initialCollection, Collection<T> compareCollection) {
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

    public static void main(String[] args) {
        Integer[] a = new Integer[]{1, 2};
        Integer[] b = new Integer[]{2, 3};
        Collection a1 = convertArrayToCollection(a);
        Collection b1 = convertArrayToCollection(b);
        Collection<Integer> c = getUniqueElementsInCollection(a1, b1);
        System.out.println("Collection a: " + Arrays.toString(a1.toArray()));
        System.out.println("Collection b: " + Arrays.toString(b1.toArray()));
        System.out.println("Collection c: " + Arrays.toString(c.toArray()));
    }
}
