package com.security.dkbt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.dkbt.dto.IdNombreDTO;
import com.security.dkbt.entity.DepartamentoEntity;

public interface DepartamentoRepository extends JpaRepository<DepartamentoEntity, String> {

	@Query("select new com.security.dkbt.dto.IdNombreDTO(d.id, d.nombre) from DepartamentoEntity d")
	List<IdNombreDTO> listar();
}