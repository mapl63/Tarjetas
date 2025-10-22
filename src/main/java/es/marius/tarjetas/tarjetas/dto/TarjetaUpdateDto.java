package es.marius.tarjetas.tarjetas.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TarjetaUpdateDto {

    private final String numero;
    private final String cvc;
    private final LocalDate fechaCaducidad;
    //private final String titular;
    private final Double saldo;

}
