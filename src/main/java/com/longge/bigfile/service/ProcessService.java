 package com.longge.bigfile.service;

import org.springframework.web.multipart.MultipartFile;

import com.longge.bigfile.common.GlobalResponse;
import com.longge.bigfile.dto.request.PostUploadRequestDto;
import com.longge.bigfile.dto.request.PreUploadRequestDto;
import com.longge.bigfile.dto.response.UploadResponseDto;

/**
 * @author roger yang
 * @date 11/05/2019
 */
public interface ProcessService {
    GlobalResponse<UploadResponseDto> preUpload(PreUploadRequestDto dto) ;

    GlobalResponse<UploadResponseDto> postUpload(MultipartFile file, PostUploadRequestDto dto) ;
}
