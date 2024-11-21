package org.tudai.userservice;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.tudai.userservice.dto.AccountDTO;
import org.tudai.userservice.dto.TripDTO;
import org.tudai.userservice.entity.Account;
import org.tudai.userservice.feign.TripClient;
import org.tudai.userservice.repository.AccountRepository;
import org.tudai.userservice.service.AccountService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @MockBean
    private TripClient tripClient;

    @InjectMocks
    private AccountService accountService;
    private AccountDTO accountDTO;

    @BeforeEach
    void setUp() {
        accountDTO = new AccountDTO();
        accountDTO.setBalance(100.0);
        accountDTO.setCreationDate(new Date());
    }

    @Test
    void testSaveAccount() {
        Account mockAccount = new Account(accountDTO.getBalance(), accountDTO.getCreationDate());
        mockAccount.setId(1L);

        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        AccountDTO savedAccount = accountService.save(accountDTO);

        assertNotNull(savedAccount);
        assertEquals(accountDTO.getBalance(), savedAccount.getBalance());
        assertEquals(accountDTO.getCreationDate(), savedAccount.getCreationDate());

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void getAllTest() {
        // Datos de prueba
        List<Account> accounts = Arrays.asList(
                new Account(100.0, new Date()),
                new Account(200.0, new Date())
        );

        when(accountRepository.findAll()).thenReturn(accounts);

        List<AccountDTO> result = accountService.getAll();


        assertNotNull(result);
        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void getTripsByAccountIdTest() {
        Long idTrip = 1L;
        List<TripDTO> trips = Arrays.asList(new TripDTO(), new TripDTO());

        when(tripClient.getTripsByAccountId(String.valueOf(idTrip))).thenReturn(trips);

        List<TripDTO> result = accountService.getTripsByAccountId(String.valueOf(idTrip));

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tripClient, times(1)).getTripsByAccountId(String.valueOf(idTrip));
    }

    @Test
    void updateTest() {
        Long accountId = 1L;
        AccountDTO updateDTO = new AccountDTO(150.0, new Date());
        Account account = new Account(100.0, new Date());
        account.setId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO updatedAccountDTO = accountService.update(accountId, updateDTO);


        assertNotNull(updatedAccountDTO);
        assertEquals(updateDTO.getBalance(), updatedAccountDTO.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void updateAccountStatusTest() {
        Long accountId = 1L;
        Account account = new Account(100.0, new Date());
        account.setId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(account));

        accountService.updateAccountStatus(accountId, false);

        assertFalse(account.getActive());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void deleteByIdTest() {
        Long accountId = 1L;

        accountService.deleteById(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void updateAccountStatus_ThrowsEntityNotFoundException() {
        Long accountId = 999L;
        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.updateAccountStatus(accountId, false));

        verify(accountRepository, times(1)).findById(accountId);
    }
}
