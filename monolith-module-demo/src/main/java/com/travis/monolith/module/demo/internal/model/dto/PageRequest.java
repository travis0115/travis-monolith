package com.travis.monolith.module.demo.internal.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author toby
 * @date 2020-02-20 18:20
 */
@Data
@Builder
@ApiModel("分页查询入参")
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest implements Serializable {
    private static final long serialVersionUID = -8957639623454410604L;
    /**
     * 每页显示记录的数量
     */
    @ApiModelProperty(value = "每页显示的数量，默认请求10条记录", example = "10")
    @NotNull
    private Integer size;

    /**
     * 当前请求页数
     */
    @ApiModelProperty(value = "当前请求页数，默认为1", example = "1")
    private Integer current;

    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private String orderItem;

    /**
     * 是否升序排列
     */
    @ApiModelProperty("是否升序 true-升序 false-降序")
    private Boolean asc;
}
