package com.yolo.hw.user.dto.response;

import lombok.Data;

@Data
public class Authentication {

    private String flowId;

    private String authenticationUrl;
}
