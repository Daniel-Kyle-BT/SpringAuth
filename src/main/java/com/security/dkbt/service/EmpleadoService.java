package com.security.dkbt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.dkbt.dto.*;
import com.security.dkbt.entity.*;
import com.security.dkbt.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DistritoRepository distritoRepository;
    private final CargoEmpleadoRepository cargoRepository;

    @Transactional(readOnly = true)
    public List<EmpleadoListDTO> listar(
            String search,
            Integer cargoId,
            String distritoId,
            String provinciaId,
            String departamentoId
    ) {
        return empleadoRepository.buscarLista(
                search == null || search.isBlank() ? null : search,
                cargoId, distritoId, provinciaId, departamentoId
        );
    }

    @Transactional(readOnly = true)
    public EmpleadoDetailDTO obtenerDetalle(String codigo) {
        return empleadoRepository.buscarDetalle(codigo)
                .orElseThrow(() -> new IllegalStateException("Empleado no encontrado"));
    }

    public void crear(EmpleadoCreateUpdateDTO dto) {

        EmpleadoEntity e = new EmpleadoEntity();
        e.setCodigo(dto.codigo());
        e.setNombre(dto.nombre());
        e.setApellido(dto.apellido());
        e.setDni(dto.dni());
        e.setTelefono(dto.telefono());
        e.setDistrito(distritoRepository.getReferenceById(dto.distritoId()));
        e.setCargo(cargoRepository.getReferenceById(dto.cargoId()));

        empleadoRepository.save(e);
    }

    public void actualizar(String codigo, EmpleadoCreateUpdateDTO dto) {

        EmpleadoEntity e = empleadoRepository
                .findByCodigoAndEliminadoFalse(codigo)
        .orElseThrow(() -> new IllegalStateException("Empleado no encontrado"));

        e.setNombre(dto.nombre());
        e.setApellido(dto.apellido());
        e.setTelefono(dto.telefono());
        e.setDistrito(distritoRepository.getReferenceById(dto.distritoId()));
        e.setCargo(cargoRepository.getReferenceById(dto.cargoId()));
    }

    public void eliminar(String codigo) {

        EmpleadoEntity e = empleadoRepository
                .findByCodigoAndEliminadoFalse(codigo)
        .orElseThrow(() -> new IllegalStateException("Empleado no encontrado"));

        e.setEliminado(true);
        e.setFechaEliminado(LocalDateTime.now());
    }
}

