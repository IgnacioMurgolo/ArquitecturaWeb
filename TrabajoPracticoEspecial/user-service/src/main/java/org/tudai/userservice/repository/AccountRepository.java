package org.tudai.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tudai.userservice.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
