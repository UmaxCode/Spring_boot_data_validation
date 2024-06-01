package com.umaxcode.spring.boot.data.validation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

import static com.umaxcode.spring.boot.data.validation.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(

            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    MANAGER_READ,
                    MANAGER_CREATE,
                    MANAGER_UPDATE,
                    MANAGER_DELETE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_CREATE,
                    MANAGER_UPDATE,
                    MANAGER_DELETE
            )
    );


    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
       var authorities = new java.util.ArrayList<>(getPermissions().stream().map(
               permission -> new SimpleGrantedAuthority(permission.getPermission())
       ).toList());

       authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

      return authorities;
    }
}
