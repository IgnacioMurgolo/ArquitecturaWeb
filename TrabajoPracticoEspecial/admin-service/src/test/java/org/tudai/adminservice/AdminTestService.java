package org.tudai.adminservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tudai.adminservice.dto.AdminDTO;
import org.tudai.adminservice.entity.Admin;
import org.tudai.adminservice.repository.AdminRepository;
import org.tudai.adminservice.service.AdminService;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        // Si necesitas configuración inicial, puedes incluirla aquí
    }

    @Test
    void save_shouldSaveAdmin() {
        // Configuración
        AdminDTO adminDTO = new AdminDTO("Admin Name", "admin@example.com", 10.0, 2.0);
        Admin admin = new Admin(adminDTO.getName(), adminDTO.getEmail(), adminDTO.getPricePerKm(), adminDTO.getExtraPricePause());

        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        // Ejecutar
        adminService.save(adminDTO);

        // Verificar
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void getAll_shouldReturnAllAdmins() {
        // Configuración
        Admin admin = new Admin("Admin Name", "admin@example.com", 10.0, 2.0);
        when(adminRepository.findAll()).thenReturn(List.of(admin));

        // Ejecutar
        List<AdminDTO> result = adminService.getAll();

        // Verificar
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Admin Name", result.get(0).getName());
        assertEquals("admin@example.com", result.get(0).getEmail());
    }

    @Test
    void getEffectivePricePerKm_shouldUpdatePriceIfEffectiveDateReached() {
        // Configuración
        Admin admin = new Admin("Admin Name", "admin@example.com", 10.0, 2.0, LocalDate.now(), 12.0);
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        // Ejecutar
        Double effectivePrice = adminService.getEffectivePricePerKm();

        // Verificar
        assertEquals(12.0, effectivePrice);
        assertNull(admin.getFuturePrice());
        assertNull(admin.getEffectiveDate());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void setFuturePrice_shouldSetNewPriceAndEffectiveDate() {
        // Configuración
        Admin admin = new Admin("Admin Name", "admin@example.com", 10.0, 2.0);
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        LocalDate effectiveDate = LocalDate.now().plusDays(5);
        Double futurePrice = 15.0;

        // Ejecutar
        adminService.setFuturePrice(futurePrice, effectiveDate);

        // Verificar
        assertEquals(futurePrice, admin.getFuturePrice());
        assertEquals(effectiveDate, admin.getEffectiveDate());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void deleteById_shouldDeleteAdmin() {
        // Ejecutar
        adminService.deleteById(1L);

        // Verificar
        verify(adminRepository, times(1)).deleteById(1L);
    }

    @Test
    void update_shouldUpdateAdminDetails() {
        // Configuración
        Admin admin = new Admin("Old Name", "old@example.com", 10.0, 2.0);
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        AdminDTO adminDTO = new AdminDTO("New Name", "new@example.com", 12.0, 3.0);

        // Ejecutar
        adminService.update(1L, adminDTO);

        // Verificar
        assertEquals("New Name", admin.getName());
        assertEquals("new@example.com", admin.getEmail());
        assertEquals(12.0, admin.getPricePerKm());
        assertEquals(3.0, admin.getExtraPricePause());
        verify(adminRepository, times(1)).save(admin);
    }
}

