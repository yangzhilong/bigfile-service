 package com.longge.bigfile.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author roger yang
 * @date 10/31/2019
 */
@Getter
@Setter
public class PostUploadRequestDto extends BaseDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3757708013609557848L;
    /**
     * the file start slice byte index
     */
    @NotNull
    @Min(0)
    private Long fileStart;
    
    /**
     * the file end slice byte index
     */
    @NotNull
    @Min(1)
    private Long fileEnd;
}
