package com.security.dkbt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.dkbt.dto.EmpleadoDetailDTO;
import com.security.dkbt.dto.EmpleadoListDTO;
import com.security.dkbt.entity.EmpleadoEntity;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
	
	Optional<EmpleadoEntity> findByCodigoAndEliminadoFalse(String codigo);

	@Query("""
			    SELECT new com.security.dkbt.dto.EmpleadoListDTO(
			        e.codigo,
			        e.nombre,
			        e.apellido,
			        c.nombre,
			        d.nombre,
			        p.nombre,
			        dep.nombre
			    )
			    FROM EmpleadoEntity e
			    JOIN e.cargo c
			    JOIN e.distrito d
			    JOIN d.provincia p
			    JOIN p.departamento dep
			    WHERE e.eliminado = false
			      AND (
			            :search IS NULL OR
			            LOWER(e.nombre)   LIKE LOWER(CONCAT('%', :search, '%')) OR
			            LOWER(e.apellido) LIKE LOWER(CONCAT('%', :search, '%'))
			      )
			      AND (:cargoId IS NULL OR c.id = :cargoId)
			      AND (:distritoId IS NULL OR d.id = :distritoId)
			      AND (:provinciaId IS NULL OR p.id = :provinciaId)
			      AND (:departamentoId IS NULL OR dep.id = :departamentoId)
			""")
	List<EmpleadoListDTO> buscarLista(@Param("search") String search, @Param("cargoId") Integer cargoId,
			@Param("distritoId") String distritoId, @Param("provinciaId") String provinciaId,
			@Param("departamentoId") String departamentoId);

	@Query("SELECT new com.security.dkbt.dto.EmpleadoDetailDTO("
			+ "e.codigo, e.nombre, e.apellido, c.nombre, d.nombre, e.dni, e.telefono, p.nombre, dep.nombre) "
			+ "FROM EmpleadoEntity e " + "JOIN e.cargo c " + "JOIN e.distrito d " + "JOIN d.provincia p "
			+ "JOIN p.departamento dep " + "WHERE e.eliminado = false " + "AND e.codigo = :codigo")
	Optional<EmpleadoDetailDTO> buscarDetalle(@Param("codigo") String codigo);

}
