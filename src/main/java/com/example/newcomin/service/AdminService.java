package com.example.newcomin.service;

import com.example.newcomin.entity.Admin;
import com.example.newcomin.entity.User;
import java.util.List;

public interface AdminService {
    Admin createAdmin(User user);
    Admin getAdminById(Long adminId);
    List<Admin> getAllAdmins();

    void deleteAdmin(Long adminId);
}
