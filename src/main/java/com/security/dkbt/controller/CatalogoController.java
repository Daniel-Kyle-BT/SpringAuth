package com.security.dkbt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dkbt.dto.IdNombreDTO;
import com.security.dkbt.repository.CargoEmpleadoRepository;
import com.security.dkbt.repository.DepartamentoRepository;
import com.security.dkbt.repository.DistritoRepository;
import com.security.dkbt.repository.ProvinciaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

    private final CargoEmpleadoRepository cargoRepo;
    private final DepartamentoRepository departamentoRepo;
    private final ProvinciaRepository provinciaRepo;
    private final DistritoRepository distritoRepo;

    // CARGOS
    @GetMapping("/cargos")
    public List<IdNombreDTO> cargos() {
        return cargoRepo.listar();
    }
    
    // UBICACIONES
    @GetMapping("/dptos")
    public List<IdNombreDTO> departamentos() {
        return departamentoRepo.listar();
    }

    @GetMapping("/dptos/{id}/provincias")
    public List<IdNombreDTO> provincias(@PathVariable String id) {
        return provinciaRepo.listarPorDepartamento(id);
    }

    @GetMapping("/provincias/{id}/distritos")
    public List<IdNombreDTO> distritos(@PathVariable String id) {
        return distritoRepo.listarPorProvincia(id);
    }
}
