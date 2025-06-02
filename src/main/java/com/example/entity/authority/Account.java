package com.example.entity.authority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Data
@Component
public class Account implements UserDetails {
    private Integer uid;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String realName;
    private boolean enabled;
    private String email;
    private Date createTime;
    private Date lastLogin;

    @JsonIgnore
    private List<Role> roleList;    //用户的角色列表,数据库查询

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role r : this.roleList) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getRoleCode()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
