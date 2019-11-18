 package com.longge.bigfile.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.longge.bigfile.common.GlobalResponse;
import com.longge.bigfile.constant.ErrorConstants;
import com.longge.bigfile.dto.request.PreUploadRequestDto;
import com.longge.bigfile.dto.response.UploadResponseDto;

import reactor.core.publisher.Mono;

/**
 * @author roger yang
 * @date 11/15/2019
 */
public class ProcessBlockHandler {
    
    public static Mono<GlobalResponse<UploadResponseDto>> preUpload(PreUploadRequestDto dto, BlockException e) {
        return Mono.just(GlobalResponse.buildFail(ErrorConstants.SYSTEM_BUSY));
    }
}
