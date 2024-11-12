package org.tudai.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tudai.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
