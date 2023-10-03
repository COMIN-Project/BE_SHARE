package com.example.newcomin.service;

import com.example.newcomin.entity.Admin;
import java.util.List;

public interface AdminService {
    Admin createAdmin(Admin admin);

    Admin getAdminById(Long adminId);
    List<Admin> getAllAdmins();
}
