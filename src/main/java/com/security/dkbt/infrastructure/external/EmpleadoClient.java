package com.security.dkbt.infrastructure.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.security.dkbt.infrastructure.external.dto.EmpleadoResponse;

@FeignClient(name = "MICRO-DRY-CLEANERS")
public interface EmpleadoClient {

	@GetMapping("/api/empleados/codigo/{codigo}")
	EmpleadoResponse obtenerPorCodigo(@PathVariable String codigo);
}
