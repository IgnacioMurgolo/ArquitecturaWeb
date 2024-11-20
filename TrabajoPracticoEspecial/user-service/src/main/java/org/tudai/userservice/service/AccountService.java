package org.tudai.userservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.userservice.dto.TripDTO;
import org.tudai.userservice.dto.AccountDTO;
import org.tudai.userservice.entity.Account;
import org.tudai.userservice.feign.TripClient;
import org.tudai.userservice.repository.AccountRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TripClient tripClient;

    @Autowired
    public AccountService(AccountRepository accountRepository, TripClient tripClient) {
        this.accountRepository = accountRepository;
        this.tripClient = tripClient;
    }

    @Transactional
    public AccountDTO save(AccountDTO accountDTO) {
        Account account = new Account(accountDTO.getBalance(), accountDTO.getCreationDate());

        account = accountRepository.save(account);
        return convertToDto(account);
    }

    @Transactional
    public List<AccountDTO> getAll() {
        List<AccountDTO> result = new ArrayList<>();
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            AccountDTO accountDTO = convertToDto(account);
            result.add(accountDTO);
        }
        return result;
    }

    @Transactional
    public List<TripDTO> getTripsByAccountId(Long accountId) {
        return tripClient.getTripsByAccountId(accountId);
    }

    @Transactional
    public void deleteById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Transactional
    public AccountDTO update(Long id, AccountDTO accountDTO) {
        Account account = accountRepository.findById(id).get();
        account.setBalance(accountDTO.getBalance());
        account.setCreationDate(accountDTO.getCreationDate());

        account = accountRepository.save(account);
        return convertToDto(account);
    }

    private AccountDTO convertToDto(Account account) {
        return new AccountDTO(account.getBalance(), account.getCreationDate());
    }

    public void updateAccountStatus(Long accountId, boolean status) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));
        account.setActive(status);
        accountRepository.save(account);
    }
}
