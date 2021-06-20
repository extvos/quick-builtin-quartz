package org.extvos.builtin.quartz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Mingcai SHEN
 */
@ApiModel(value = "方法调用参数")
@Data
public class BeanParameter {

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "参数类型")
    private String type;

    @ApiModelProperty(value = "是否可以为空")
    private boolean nullable;
}
