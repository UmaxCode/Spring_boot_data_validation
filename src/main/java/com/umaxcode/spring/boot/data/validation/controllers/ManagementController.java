package com.umaxcode.spring.boot.data.validation.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/management")
@PreAuthorize(value = "hasAnyRole('ADMIN', 'MANAGER')")
public class ManagementController {

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('admin:read', 'manager:read')")
    public String get(){
        return "GET:: management controller";
    }

    @PutMapping
    @PreAuthorize(value = "hasAnyAuthority('admin:update', 'manager:update')")
    public String put(){
        return "PUT:: management controller";
    }

    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('admin:create', 'manager:create')")
    public String post(){
        return "POST:: management controller";
    }

    @DeleteMapping
    @PreAuthorize(value = "hasAnyAuthority('admin:delete', 'admin:delete')")
    public String delete(){
        return "DELETE:: management controller";
    }
}
