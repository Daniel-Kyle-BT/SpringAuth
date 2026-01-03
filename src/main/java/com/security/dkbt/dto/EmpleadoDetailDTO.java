package com.security.dkbt.dto;

public record EmpleadoDetailDTO(
        String codigo,
        String nombre,
        String apellido,
        String cargo,
        String distrito,
        String dni,
        String telefono,
        String provincia,
        String departamento
) {}