package com.yolo.hw.user.domain.custom.dao;

import com.yolo.hw.user.domain.auto.platform.entity.RentVManager;
import org.apache.ibatis.annotations.Param;

public interface OrderDao {

    RentVManager findUser(@Param("id") Long id);
}
