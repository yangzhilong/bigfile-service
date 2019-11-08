package com.longge.bigfile.util;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 
 * @author roger yang
 * @date 7/15/2019
 */
public class ForEachUtils {
    
    /**
     * 
     * @param <T>
     * @param startIndex
     * @param elements
     * @param action
     */
    public static <T> void forEach(int startIndex,Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        if(startIndex < 0) {
            startIndex = 0;
        }
        int index = 0;
        for (T element : elements) {
            index++;
            if(index <= startIndex) {
                continue;
            }
            
            action.accept(index-1, element);
        }
    }
}