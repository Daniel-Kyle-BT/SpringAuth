package com.security.dkbt.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UsuarioProcedureRepository {

    private final EntityManager entityManager;

    public Map<String, Object> validarEmpleadoParaUsuario(String codigoEmpleado) {

        StoredProcedureQuery sp =
            entityManager.createStoredProcedureQuery("SP_VALIDAR_EMPLEADO_PARA_USUARIO");

        sp.registerStoredProcedureParameter("_CODIGO_EMPLEADO", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("_ID_EMPLEADO", Long.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("_ID_ROL", Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("_StatusCode", Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("_Message", String.class, ParameterMode.OUT);

        sp.setParameter("_CODIGO_EMPLEADO", codigoEmpleado);
        sp.execute();

        Map<String, Object> result = new HashMap<>();
        result.put("_ID_EMPLEADO", sp.getOutputParameterValue("_ID_EMPLEADO"));
        result.put("_ID_ROL", sp.getOutputParameterValue("_ID_ROL"));
        result.put("_StatusCode", sp.getOutputParameterValue("_StatusCode"));
        result.put("_Message", sp.getOutputParameterValue("_Message"));
        
        return result;
    }

    public Map<String, Object> crearUsuario(
            Long idEmpleado,
            Integer idRol,
            String username,
            String passwordHash) {

        StoredProcedureQuery sp =
            entityManager.createStoredProcedureQuery("SP_REGISTRAR_USUARIO");

        sp.registerStoredProcedureParameter("_ID_EMPLEADO", Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("_ID_ROL", Integer.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("_USERNAME", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("_PASSWORD_HASH", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("_StatusCode", Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("_Message", String.class, ParameterMode.OUT);

        sp.setParameter("_ID_EMPLEADO", idEmpleado);
        sp.setParameter("_ID_ROL", idRol);
        sp.setParameter("_USERNAME", username);
        sp.setParameter("_PASSWORD_HASH", passwordHash);
        sp.execute();

        Map<String, Object> result = new HashMap<>();
        result.put("_StatusCode", sp.getOutputParameterValue("_StatusCode"));
        result.put("_Message", sp.getOutputParameterValue("_Message"));

        return result;
    }
}
