package com.security.dkbt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CargoRol")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CargoRolEntity  {

    @EmbeddedId
    private CargoRolId id;

    @Column(nullable = false)
    private boolean activo;
}