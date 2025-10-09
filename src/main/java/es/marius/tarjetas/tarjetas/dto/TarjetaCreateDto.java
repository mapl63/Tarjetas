package es.marius.tarjetas.tarjetas.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TarjetaCreateDto {

    private final String numero;
    private final String cvc;
    private final LocalDate fechaCaducidad;
    private final String titular;
    private final Double saldo;

}
