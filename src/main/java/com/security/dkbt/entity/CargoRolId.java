package com.security.dkbt.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class CargoRolId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "id_rol")
    private Integer idRol;
}