package com.security.dkbt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.security.dkbt.dto.EmpleadoCreateUpdateDTO;
import com.security.dkbt.dto.EmpleadoDetailDTO;
import com.security.dkbt.dto.EmpleadoListDTO;
import com.security.dkbt.service.EmpleadoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/empleado")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService service;

    @GetMapping
    public List<EmpleadoListDTO> listar(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer cargoId,
            @RequestParam(required = false) String distritoId,
            @RequestParam(required = false) String provinciaId,
            @RequestParam(required = false) String departamentoId
    ) {
        return service.listar(search, cargoId, distritoId, provinciaId, departamentoId);
    }

    // DETALLE
    @GetMapping("/{codigo}")
    public EmpleadoDetailDTO detalle(@PathVariable String codigo) {
        return service.obtenerDetalle(codigo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crear(@RequestBody EmpleadoCreateUpdateDTO dto) {
        service.crear(dto);
    }

    @PutMapping("/{codigo}")
    public void actualizar(
            @PathVariable String codigo,
            @RequestBody EmpleadoCreateUpdateDTO dto
    ) {
        service.actualizar(codigo, dto);
    }

    // ELIMINAR (soft delete)
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String codigo) {
        service.eliminar(codigo);
    }
}
