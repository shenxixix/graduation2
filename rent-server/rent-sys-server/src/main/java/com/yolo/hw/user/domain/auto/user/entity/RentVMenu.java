package com.yolo.hw.user.domain.auto.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author shenxi
 * @since 2024-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rent_v_menu")
@ApiModel(value="RentVMenu对象", description="系统菜单表")
public class RentVMenu extends Model<RentVMenu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    @TableId("menu_id")
    private Long menuId;

    @ApiModelProperty(value = "菜单名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "父级菜单id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "跳转页面url")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "菜单类型0:目录;1:菜单;")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "菜单序号")
    @TableField("order_num")
    private Long orderNum;

    @ApiModelProperty(value = "更新时间戳")
    @TableField("update_timestamp")
    private Date updateTimestamp;

    @ApiModelProperty(value = "插入时间戳")
    @TableField("insert_timestamp")
    private Date insertTimestamp;


    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

}
