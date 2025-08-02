package com.example.apptly.backend.springboot.controller;

import com.example.apptly.backend.springboot.common.ApiResponse;
import com.example.apptly.backend.springboot.common.ApiResponseUtil;
import com.example.apptly.backend.springboot.dto.RoleDto;
import com.example.apptly.backend.springboot.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleDto>>> getAllRoles(){
        List<RoleDto> roles = roleService.gelAll();
        return ResponseEntity.ok(ApiResponseUtil.success("All roles fetched", roles));
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<ApiResponse<RoleDto>> getRoleByName(@PathVariable(name = "name") String name){
        RoleDto role = roleService.getRoleByName(name);
        return ResponseEntity.ok(ApiResponseUtil.success("Role found with name '"+ name +"'", role));
    }


}
