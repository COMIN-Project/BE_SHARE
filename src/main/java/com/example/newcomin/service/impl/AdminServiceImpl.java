package com.example.newcomin.service.impl;

import com.example.newcomin.entity.Admin;
import com.example.newcomin.entity.User;
import com.example.newcomin.repository.AdminRepository;
import com.example.newcomin.repository.UserRepository;
import com.example.newcomin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository; // UserRepository 주입 추가

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository,
                            UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository; // UserRepository 주입
    }

    @Override
    public Admin createAdmin(User user) {
        if (user != null && user.getUserId() != null) {
            // User 객체에서 userId를 가져와서 해당하는 사용자를 찾음
            Long userId = user.getUserId();
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();

                // Admin 객체 생성 및 설정
                Admin admin = new Admin();
                admin.setUserId(foundUser);

                // Admin 객체를 저장하고 반환
                return adminRepository.save(admin);
            } else {
                // 사용자가 존재하지 않는 경우 예외 처리 또는 에러 메시지를 반환할 수 있습니다.
                throw new IllegalArgumentException("해당하는 사용자가 존재하지 않습니다.");
            }
        } else {
            // 사용자가 null이거나 userId가 null인 경우 예외 처리 또는 에러 메시지를 반환할 수 있습니다.
            throw new IllegalArgumentException("유효하지 않은 사용자 정보입니다.");
        }
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

    @Override
    public void deleteAdmin(Long adminId){
        adminRepository.deleteById(adminId);
    }
}