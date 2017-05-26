package jtelecom.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Anna Rysakova
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
     *                    which are included in the second collection
     * @see Collection
     * @see T
     */
    public static <T> Collection<T> firstCollectionMinusSecondCollection(Collection<T> originalCollection, Collection<T> compareCollection) {
        Collection<T> resultCollection = new ArrayList<>(originalCollection);
        resultCollection.removeAll(compareCollection);

        return resultCollection;
    }
}
