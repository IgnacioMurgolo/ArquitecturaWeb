package org.tudai.adminservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.adminservice.dto.AdminDTO;
import org.tudai.adminservice.entity.Admin;
import org.tudai.adminservice.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    @Transactional
    public void save(AdminDTO adminDTO) {
        Admin admin = new Admin(adminDTO.getName(), adminDTO.getEmail(), adminDTO.getPricePerKm(), adminDTO.getExtraPricePause());
        adminRepository.save(admin);
    }

    @Transactional
    public List<AdminDTO> getAll() {
        List<AdminDTO> result = new ArrayList<>();
        List<Admin> admins = adminRepository.findAll();
        for (Admin admin : admins) {
            AdminDTO adminDTO = convertToDto(admin);
            result.add(adminDTO);
        }
        return result;
    }

    @Transactional
    public void deleteById(Long accountId) {
        adminRepository.deleteById(accountId);
    }

    @Transactional
    public void update(Long id, AdminDTO adminDTO) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Admin not found with id " + id));
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPricePerKm(adminDTO.getPricePerKm());
        admin.setExtraPricePause(adminDTO.getExtraPricePause());

        adminRepository.save(admin);
    }

    private AdminDTO convertToDto(Admin admin) {
        return new AdminDTO(admin.getName(), admin.getEmail(), admin.getPricePerKm(), admin.getExtraPricePause());
    }
}
