 package com.longge.bigfile.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author roger yang
 * @date 11/04/2019
 */
public class SliceUtils {
    private SliceUtils() {}
    
    public static Integer getTotalSlice(Long sliceSize, Long totalSize) {
        BigDecimal t = new BigDecimal(String.valueOf(totalSize));
        BigDecimal[] results = t.divideAndRemainder(new BigDecimal(sliceSize));
       
        BigDecimal divide = results[0];
        BigDecimal mod = results[1];
        
        if(BigDecimal.ZERO.equals(mod)) {
            if(BigDecimal.ONE.compareTo(divide) > 0) {
                return 1;
            }
            return divide.intValue();
        }
        return divide.intValue() + 1;
    }
    
    public static Long[] getSliceStartAndEnd(Long sliceSize, Long totalSize, int sliceIndex) {
        Long start = sliceSize*sliceIndex;
        Long end = sliceSize*(sliceIndex+1);
        end = Math.min(end, totalSize);
        Long[] arr = new Long[2];
        arr[0] = start;
        arr[1] = end;
        return arr;
    }
    
    public static float getCompletionRatio(Long totalSlice, Long waitSlice) {
        BigDecimal bd = new BigDecimal(totalSlice-waitSlice).divide(new BigDecimal(totalSlice), 2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
