package com.security.dkbt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.security.dkbt.dto.IdNombreDTO;
import com.security.dkbt.entity.DistritoEntity;

@Repository
public interface DistritoRepository extends JpaRepository<DistritoEntity, String> {

	@Query("""
			select new com.security.dkbt.dto.IdNombreDTO(d.id, d.nombre)
			from DistritoEntity d
			where d.provincia.id = :provinciaId
			""")
	List<IdNombreDTO> listarPorProvincia(String provinciaId);
}
