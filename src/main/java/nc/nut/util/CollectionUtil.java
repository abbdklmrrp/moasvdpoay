package nc.nut.util;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Anna Rysakova on 12.05.2017.
 */
public class CollectionUtil {

    /**
     * This method compare generic <code>Collection</code> and remove from first collection
     * elements which included in second <code>Collection</code>.
     *
     * @param originalCollection original <code>Collection</code>
     * @param compareCollection  collection from which the elements will be compared
     * @param <T>                collection type
     * @return collection Collection of elements from the first collection,
     * which are included in the second collection
     * @see Collection
     * @see T
     */
    // FIXME: 11.05.2017 move collection util
    public static <T> Collection<T> getUniqueElementsInFirstCollection(Collection<T> originalCollection, Collection<T> compareCollection) {
        Collection<T> resultCollection = new ArrayList<>(originalCollection);
        resultCollection.removeAll(compareCollection);

        return resultCollection;
    }

    /**
     * Anna Rysakova
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> List<T> convertArrayToList(T[] array) {
        return Arrays.asList(array);
    }

    /**
     * Anna Rysakova
     *
     * @param collection
     * @return
     */
    // FIXME: 11.05.2017
    public static T[] convertCollectionToArray(Collection<T> collection) {
//        return collection.toArray(new T[collection.size()]);
        return collection.toArray(new T[collection.size()]);
    }
}
