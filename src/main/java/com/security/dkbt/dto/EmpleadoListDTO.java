package com.security.dkbt.dto;

public record EmpleadoListDTO(
        String codigo,
        String nombre,
        String apellido,
        String cargo,
        String distrito,
        String provincia,
        String departamento
) {}