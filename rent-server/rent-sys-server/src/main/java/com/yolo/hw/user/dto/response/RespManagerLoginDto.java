package com.yolo.hw.user.dto.response;

import com.yolo.hw.user.domain.auto.platform.entity.RentVManager;
import com.yolo.hw.user.domain.auto.platform.entity.RentVMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RespManagerLoginDto {

    @ApiModelProperty(notes = "用户token")
    private String token;

    @ApiModelProperty(notes = "用户信息")
    private User userInfo = new User();

    @Data
    @ApiModel("用户信息")
    public static class User extends RentVManager {
        
        @ApiModelProperty("企业名称")
        private String epName;

        @ApiModelProperty(notes = "权限菜单")
        private List<ResRentMenu> menus = new ArrayList<>();
    }

    @Data
    public static class ResRentMenu extends RentVMenu {

        @ApiModelProperty(notes = "子菜单")
        private List<ResRentMenu> childMenus = new ArrayList<>();
    }
}
