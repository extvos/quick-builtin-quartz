package org.extvos.builtin.quartz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author shenmc
 */
@ApiModel(value = "BEAN方法")
@Data
public class BeanMethod {
    @ApiModelProperty(value = "方法名称")
    private String name;

    @ApiModelProperty(value = "参数列表")
    private List<BeanParameter> parameters;

    @ApiModelProperty(value = "描述")
    private String description;
}
