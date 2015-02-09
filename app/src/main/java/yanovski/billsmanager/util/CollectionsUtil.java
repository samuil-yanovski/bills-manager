package yanovski.billsmanager.util;

import java.util.Collection;

/**
 * Created by Samuil on 2/8/2015.
 */
public class CollectionsUtil {
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || 0 == collection.size();
    }
}
