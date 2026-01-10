package com.travis.monolith.module.demo.internal.model.dto;

import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author toby
 * @date 2019-03-31 18:34
 */
@Data
@Builder
@ApiModel
public class Response<T> implements Serializable {
    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    @Builder.Default
    private String code = ErrorCodeEnum.SUCCESS.getCode();

    /**
     * 状态码的具体说明
     */
    @ApiModelProperty("状态码的具体说明")
    @Builder.Default
    private String msg = ErrorCodeEnum.SUCCESS.getMsg();

    /**
     * 具体的数据对象
     */
    @ApiModelProperty("具体的数据对象")
    private T data;

}
