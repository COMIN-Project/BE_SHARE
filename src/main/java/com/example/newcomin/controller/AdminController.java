package com.example.newcomin.controller;

import com.example.newcomin.service.AdminService;
import com.example.newcomin.entity.Admin;
import com.example.newcomin.entity.User;

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
    public ResponseEntity<Admin> createAdmin(@RequestBody User user) {
        if (user != null && user.getUserId() != null) {
            // AdminService를 사용하여 Admin을 생성합니다.
            Admin savedAdmin = adminService.createAdmin(user);
            if (savedAdmin != null) {
                return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
            } else {
                // Admin 생성에 실패한 경우 처리할 코드를 추가할 수 있습니다.
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    // 삭제
    @DeleteMapping("/{adminId}")
    public  ResponseEntity<String> deleteAdmin(@PathVariable("adminId") Long adminId){
        adminService.deleteAdmin((adminId));
        return new ResponseEntity<>("관리자 정보를 삭제했습니다.", HttpStatus.OK);
    }

}