 package com.longge.bigfile.rest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.longge.bigfile.common.GlobalResponse;
import com.longge.bigfile.constant.ErrorConstants;
import com.longge.bigfile.dto.request.PostUploadRequestDto;
import com.longge.bigfile.dto.request.PreUploadRequestDto;
import com.longge.bigfile.dto.response.UploadResponseDto;
import com.longge.bigfile.handler.ProcessBlockHandler;
import com.longge.bigfile.service.ProcessService;
import com.longge.bigfile.util.RedisKeyUtils;
import com.longge.bigfile.util.RedisUtils;
import com.longge.bigfile.util.S3ClientUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

/**
 * @author roger yang
 * @date 10/31/2019
 */
@RestController
@RequestMapping("/v1/api")
@Slf4j
public class ProcessRest {
    @Autowired
    private ProcessService processService;
    
    @PostMapping("/pre")
    @SentinelResource(value = "ProcessRest_pre", blockHandlerClass = ProcessBlockHandler.class, blockHandler = "preUpload")
    public Mono<GlobalResponse<UploadResponseDto>> preUpload(@RequestBody @Valid PreUploadRequestDto dto) {
        log.info("begin call preUpload, request is : {}", JSONObject.toJSONString(dto));
        
        Mono<GlobalResponse<UploadResponseDto>> resp = processService.preUpload(dto);
        
        log.info("end call preUpload, response is : {}", JSONObject.toJSONString(resp));
        return resp;
    }
    
    /**
     * 
     * @param file
     * @param dto
     * @return
     */
    @PostMapping(value="/upload", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<GlobalResponse<UploadResponseDto>> postUpload(@RequestParam(value="file", required = true) MultipartFile file, @Valid PostUploadRequestDto dto) {
        log.info("begin to upload file, request is:{}", JSONObject.toJSONString(dto));
        Mono<GlobalResponse<UploadResponseDto>> resp = null;
        
        try(Entry entry = SphU.entry("ProcessRest_postUpload");) {
            resp = processService.postUpload(file, dto);
        } catch (BlockException  e) {
            log.warn("the system is busy, please try again later");
            resp = Mono.just(GlobalResponse.buildFail(ErrorConstants.SYSTEM_BUSY));
        }
        
        log.info("end upload file, response is:{}", JSONObject.toJSONString(resp));
        return resp;
    }
    
    @GetMapping("/download")
    public void download(@RequestParam String file, @RequestParam String sys,HttpServletResponse response) throws Exception {
        
        String fileName = RedisUtils.hashGet(RedisKeyUtils.getBigFileInfoHashKey(sys, file), RedisKeyUtils.FILE_NAME);
        if(StringUtils.isBlank(fileName)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            GetObjectRequest objRequest = GetObjectRequest.builder().bucket(S3ClientUtils.getConfig(sys).getBucketName()).key(file).build();
            try(ServletOutputStream out = response.getOutputStream(); 
                ResponseInputStream<GetObjectResponse> is = S3ClientUtils.getClient(sys).getObject(objRequest);) {
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition,Content-Length");
                
                byte[] buffer = new byte[1024];
                
                int i = is.read(buffer);
                while(i != -1) {
                    out.write(buffer, 0, i);
                    i = is.read(buffer);
                }
                is.close();
                out.flush();
            }
        }
         
    }
}
