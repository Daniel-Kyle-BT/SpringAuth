package com.security.dkbt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.dkbt.dto.IdNombreDTO;
import com.security.dkbt.entity.ProvinciaEntity;

public interface ProvinciaRepository extends JpaRepository<ProvinciaEntity, String> {

	@Query("""
			select new com.security.dkbt.dto.IdNombreDTO(p.id, p.nombre)
			from ProvinciaEntity p
			where p.departamento.id = :departamentoId
			""")
	List<IdNombreDTO> listarPorDepartamento(String departamentoId);
}
