package es.marius.tarjetas.tarjetas.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TarjetaCreateDto {

    private final String numero;

    //@Digits(integer = 3 ,fraction = 0, message = "Juan ahora tengo un 9 en cliente")
    @Pattern(regexp = "[0-9]{3}",message = "CVC Debe tener solo 3 digitos.")
    private final String cvc;

    @Future(message = "La fecha debe ser posterior al actual")
    private final LocalDate fechaCaducidad;

    private final String titular;
    private final Double saldo;

}
