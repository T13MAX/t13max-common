package com.t13max.ai.pathfinding.grid;


/**
 * 网格接口
 *
 * @author: t13max
 * @since: 15:34 2024/7/22
 */
public interface IGrid {

    boolean isInBound(int x, int y);

    boolean isWalkable(int x, int y);

    default boolean isValid(int x, int y) {
        return isInBound(x, y) && isWalkable(x, y);
    }

}
