package com.security.dkbt.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.dkbt.infrastructure.persistence.entity.RolEntity;

@Repository
public interface  RolRepository extends JpaRepository<RolEntity, Long>{

	Optional<RolEntity> findByNombre(String nombre);
}
