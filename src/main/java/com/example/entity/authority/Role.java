package com.example.entity.authority;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Role {
    private Integer rid;
    private String roleCode;
    private String roleName;
}
