package com.library.parcial02.repository;

import com.library.parcial02.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
