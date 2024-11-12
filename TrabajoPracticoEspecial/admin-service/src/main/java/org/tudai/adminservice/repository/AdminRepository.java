package org.tudai.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tudai.adminservice.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
