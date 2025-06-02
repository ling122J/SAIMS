package com.example.entity.authority;

import lombok.Data;
import java.util.List;

@Data
public class BindRolesRequest {
    private Integer uid;
    private List<Integer> ridList;
}