package com.example.capstone.pizza_delivery_service.security;

import com.example.capstone.pizza_delivery_service.controller.Controller;
import com.example.capstone.pizza_delivery_service.entity.AuthGroupEntity;
import com.example.capstone.pizza_delivery_service.entity.CustomersCredentialsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserPrincipal implements UserDetails {
    Logger logger = LoggerFactory.getLogger(Controller.class);
    private final CustomersCredentialsEntity user;
    private List<AuthGroupEntity> authGroups;

    public UserPrincipal(CustomersCredentialsEntity user, List<AuthGroupEntity> authGroups){
        super();
        this.user = user;
        this.authGroups = authGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(null==authGroups){
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        authGroups.forEach(group->{
            grantedAuthorities.add(new SimpleGrantedAuthority(group.getAuthgroup()));

        });
        logger.info(grantedAuthorities.toString());
        return grantedAuthorities;

    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


