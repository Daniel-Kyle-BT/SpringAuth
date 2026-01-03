package com.security.dkbt.dto;



public record EmpleadoCreateUpdateDTO(

       String codigo,
       String nombre,
       String apellido,
       String dni,
       String telefono,

       String distritoId,
       Integer cargoId
) {}