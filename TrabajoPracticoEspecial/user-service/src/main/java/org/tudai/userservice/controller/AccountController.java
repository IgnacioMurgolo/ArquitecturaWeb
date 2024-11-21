package org.tudai.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.userservice.dto.TripDTO;
import org.tudai.userservice.dto.AccountDTO;
import org.tudai.userservice.entity.Account;
import org.tudai.userservice.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody AccountDTO accountDTO) {
        try {
            accountService.save(accountDTO);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            List<AccountDTO> accountDTOList = accountService.getAll();
            if (accountDTOList.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok().body(accountDTOList);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/tripsWithAccountId/{accountId}")
    public ResponseEntity<?> getTripsByAccountId(@PathVariable String accountId) {
        try {
            List<TripDTO> tripDTOList = accountService.getTripsByAccountId(accountId);
            if (tripDTOList.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok().body(tripDTOList);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/{accountId}/status")
    public ResponseEntity<Void> updateAccountStatus(
            @PathVariable Long accountId,
            @RequestParam boolean status) {
        accountService.updateAccountStatus(accountId, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            accountService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        try {
            accountService.update(id, accountDTO);
            return ResponseEntity.ok("Cuenta actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar la cuenta");
        }
    }

}
