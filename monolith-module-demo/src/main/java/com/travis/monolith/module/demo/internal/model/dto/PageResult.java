package com.travis.monolith.module.demo.internal.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author toby
 * @date 2020-02-20 18:20
 */
@Data
@ApiModel
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -4541334059548331714L;

    /**
     * 查询结果
     */
    @ApiModelProperty("查询结果")
    private List<T> records;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private long total;

    /**
     * 请求记录数
     */
    @ApiModelProperty(value = "请求记录数")
    private long size;

    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数")
    private long current;

    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private long pages;
}
