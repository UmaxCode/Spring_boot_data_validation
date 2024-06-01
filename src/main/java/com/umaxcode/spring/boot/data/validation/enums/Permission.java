package com.umaxcode.spring.boot.data.validation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
   // Resource - permission

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create"),
    MANAGER_DELETE("manager:delete");

    private final String permission;
}
