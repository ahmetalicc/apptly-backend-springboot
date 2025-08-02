package com.example.apptly.backend.springboot.controller;

import com.example.apptly.backend.springboot.common.ApiResponse;
import com.example.apptly.backend.springboot.common.ApiResponseUtil;
import com.example.apptly.backend.springboot.dto.RoleRequest;
import com.example.apptly.backend.springboot.dto.RoleResponse;
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

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest roleRequest){
        RoleResponse role = roleService.createRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseUtil.success("Role has been created", role, HttpStatus.CREATED));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles(){
        List<RoleResponse> roles = roleService.gelAll();
        return ResponseEntity.ok(ApiResponseUtil.success("All roles fetched", roles));
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleByName(@PathVariable(name = "name") String name){
        RoleResponse role = roleService.getRoleByName(name);
        return ResponseEntity.ok(ApiResponseUtil.success("Role found with name '"+ name +"'", role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable(name = "id") Long id){
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponseUtil.success("Role is deleted successfully"));
    }

}
