package com.security.dkbt.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Empleado")
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, columnDefinition = "CHAR(6)")
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_distrito", nullable = false)
    private DistritoEntity distrito;

    @Column(name = "dni", nullable = false, unique = true, columnDefinition = "CHAR(8)")
    private String dni;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cargo", nullable = false)
    private CargoEmpleadoEntity cargo;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado = false;

    @Column(name = "fecha_eliminado")
    private LocalDateTime fechaEliminado;
}
