package com.security.dkbt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Departamento")
public class DepartamentoEntity {

    @Id
    @Column(name = "id_departamento", columnDefinition = "CHAR(2)")
    @EqualsAndHashCode.Include
    private String id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}
