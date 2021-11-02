package org.uma.jmetal.util.grouping;

import java.util.List;

/**
 * Interface representing objects that group collections according to some criterion
 *
 * @param <S> The items to group (e.g., a list, an array, etc.)
 * @author Antonio J. Nebro
 */
public interface CollectionGrouping<S> {
    int numberOfGroups();

    void computeGroups(S itemsToGroup);

    List<Integer> getGroup(int groupIndex);
}
