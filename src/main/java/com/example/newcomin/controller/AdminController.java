package com.example.newcomin.controller;

import com.example.newcomin.service.AdminService;
import com.example.newcomin.entity.Admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/admins")
public class AdminController {
    private AdminService adminService;

    // 등록
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin){
        Admin savedAdmin = adminService.createAdmin(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

    // 조회
    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("adminId") Long adminId){
        Admin admin = adminService.getAdminById(adminId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins(){
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins,HttpStatus.OK);
    }
}