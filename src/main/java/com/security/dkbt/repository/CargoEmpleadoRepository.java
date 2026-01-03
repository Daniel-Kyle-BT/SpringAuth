package com.security.dkbt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.security.dkbt.entity.CargoEmpleadoEntity;
import com.security.dkbt.dto.IdNombreDTO;

@Repository
public interface CargoEmpleadoRepository extends JpaRepository<CargoEmpleadoEntity, Integer> {

    @Query("select new com.security.dkbt.dto.IdNombreDTO(c.id, c.nombre) from CargoEmpleadoEntity c")
    List<IdNombreDTO> listar();
    
}
