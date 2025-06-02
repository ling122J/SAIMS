package com.example.entity.authority;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Permission {
    private Integer pid;

    private String permissionName;

    private String permissionCode;

    private String resourceUrl;

    private String resourceType;

    private Integer parentId;

    private Integer orderNo;

    private String icon;

}