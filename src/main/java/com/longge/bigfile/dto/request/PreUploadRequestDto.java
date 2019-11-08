 package com.longge.bigfile.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author roger yang
 * @date 10/31/2019
 */
@Getter
@Setter
public class PreUploadRequestDto extends BaseDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 766299718836190966L;
    
    /**
     * file name
     */
    @NotBlank
    private String fileName;
    /**
     * file Total file size
     */
    @NotNull
    private Long totalSize;
}
