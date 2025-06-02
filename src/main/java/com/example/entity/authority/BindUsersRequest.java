package com.example.entity.authority;


import lombok.Data;
import java.util.List;

@Data
public class BindUsersRequest {
    private Integer rid;
    private List<Integer> uidList;
}
