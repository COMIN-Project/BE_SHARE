package com.example.newcomin.service.impl;

import com.example.newcomin.entity.Admin;
import com.example.newcomin.repository.AdminRepository;
import com.example.newcomin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin createAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(Long adminId){
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        return optionalAdmin.get();
    }
    @Override
    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }
}