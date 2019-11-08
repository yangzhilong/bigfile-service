 package com.longge.bigfile.util;

import com.longge.bigfile.dto.request.BaseDto;
import com.longge.bigfile.dto.request.PostUploadRequestDto;

import lombok.NonNull;

/**
 * @author roger yang
 * @date 11/04/2019
 */
public class RedisKeyUtils {
    private static final String LOCK = "LOCK:";
    private static final String SEPARATOR = "：";
    
    public static final String MD5 = "MD5";
    public static final String BEGIN_DATE = "BEGIN_DATE";
    public static final String FILE_NAME = "FILE_NAME";
    
    public static final String SLICE_SIZE = "SLICE_SIZE";
    public static final String TOTAL_SIZE = "TOTAL_SIZE";
    
    public static final String TOTAL_SLICE = "TOTAL_SLICE";
    
    public static final String S3_PATH = "S3_PATH";
    public static final String S3_UPLOAD_ID = "S3_UPLOAD_ID";
    
    public static String getBigFileInfoHashKey(@NonNull BaseDto dto) {
        return dto.getSys().concat(SEPARATOR).concat(dto.getMd5());
    }
    
    public static String getBigFileInfoHashKey(@NonNull String sys, @NonNull String md5) {
        return sys.concat(SEPARATOR).concat(md5);
    }
    
    public static String getWaitSliceKey(@NonNull BaseDto dto) {
        return dto.getSys().concat(SEPARATOR).concat(dto.getMd5()).concat(SEPARATOR).concat("WAIT");
    }
    
    public static String getEndSliceKey(@NonNull BaseDto dto) {
        return dto.getSys().concat(SEPARATOR).concat(dto.getMd5()).concat(SEPARATOR).concat("END");
    }
    
    public static String getSliceLock(@NonNull PostUploadRequestDto dto, int sliceIndex) {
        return LOCK.concat(dto.getSys()).concat(SEPARATOR).concat(dto.getMd5()).concat(SEPARATOR).concat(String.valueOf(sliceIndex));
    }
    
    public static String getFileLock(@NonNull BaseDto dto) {
        return LOCK.concat(dto.getSys()).concat(SEPARATOR).concat(dto.getMd5());
    }
}
