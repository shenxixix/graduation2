package com.yolo.hw.user.domain.custom.dao;

import com.yolo.hw.user.domain.auto.platform.entity.RentVMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    List<RentVMenu> findUserMenus(@Param("userId") Long userId);
}
