package es.marius.tarjetas.tarjetas.models;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Titular {

    private final Long id;

    private final String nombre;
    private final String apellido;
    private final String email;
    private final String dni;
    private final String telefono;
    private final LocalDate fechaNacimiento;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UUID uuid;

}
