package com.longge.bigfile.util;

import com.longge.bigfile.common.BaseConstant;

/**
 * get enum desc by codes util class
 * @author roger yang
 * @date 6/13/2019
 */
public class ConstantUtils {
    
    /**
     * get enum desc by code
     * @param <T>
     * @param clazz
     * @param codeValue
     * @return
     */
    public static <T extends Enum<T> & BaseConstant<T>>  String getEnumValue(Class<T> clazz ,
            Object codeValue) {
        String result = null;
        T[] enums = clazz.getEnumConstants();
        for(T obj : enums){
            if(obj.getCode().equals(codeValue)){
                result = obj.getDesc();
                break;
            }
        }
        return result;
    }
    
    /**
     * get enum by code
     * @param <T>
     * @param clazz
     * @param codeValue
     * @return
     */
    public static <T extends Enum<T> & BaseConstant<T>>  BaseConstant<T> getEnum(Class<T> clazz ,
            Object codeValue) {
        BaseConstant<T> result = null;
        T[] enums = clazz.getEnumConstants();
        for(T obj : enums){
            if(obj.getCode().equals(codeValue)){
                result = obj;
                break;
            }
        }
        return result;
    }
}