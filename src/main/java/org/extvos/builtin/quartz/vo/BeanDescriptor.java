package org.extvos.builtin.quartz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author shenmc
 */
@ApiModel(value = "BEAN实体")
@Data
public class BeanDescriptor {
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "方法列表")
    private List<BeanMethod> methods;

    @ApiModelProperty(value = "描述")
    private String description;
}
