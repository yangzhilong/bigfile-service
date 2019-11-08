 package com.longge.bigfile.constant;

import com.longge.bigfile.common.BaseConstant;

import lombok.AllArgsConstructor;

/**
 * @author roger yang
 * @date 11/04/2019
 */
@AllArgsConstructor
public enum ErrorConstants implements BaseConstant<Integer> {
    SUCCESS(200, "Success"),
    
    INIT_S3_UPLOAD_ERROR(30001, "Init s3 upload fail, please contact the relevant personnel"),
    UPLOAD_S3_ERROR(30002,"Upload fail, please contact the relevant personnel"),
    COMPLETE_S3_ERROR(30003,"Complete fail, please contact the relevant personnel"),
    
    REDIS_KEY_NOT_EXISTS_ERROR(30100, "Upload information not found"),
    FILE_IS_UPLOADING_ERROR(301001,"The file is uploading, please wait a moment"),
    SOMEONE_IS_UPLOADING_SLICE_ERROR(301002,"Someone is uploading this file, please wait a moment"),
    
    
    FILE_END_MUST_MORE_THEN_BEGIN_ERROR(30200, "The file end position must be greater than or equal to the start position"),
    FILE_BEGIN_ERROR(30201, "The start position is error"),
    FILE_END_ERROR(30201, "The end position is error"),
    FILE_SIZE_ERROR(30201, "The file's size is error"),
    ;
    private Integer code;
    private String desc;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
